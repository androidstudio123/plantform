package cn.crm.service.repair.impl;


import cn.crm.controller.wxmessage.HttpClientUtils;
import cn.crm.entity.SysUserEntity;
import cn.crm.entity.repair.OrderCommentEntity;
import cn.crm.entity.repair.OrderEntity;
import cn.crm.mapper.repair.*;
import cn.crm.mapper.sys.SysUserMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.repair.OrderService;
import cn.crm.service.repair.RepairAreaService;
import cn.crm.util.IdGenerator;
import cn.crm.util.IoUploadUtil;
import cn.crm.util.PropertiesUtil;
import cn.crm.util.UserEntityUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Slf4j
public class OrderServiceImpl extends BaseServiceImpl<OrderEntity> implements OrderService {
	
	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderCommentMapper orderCommentMapper;

	@Autowired
	private RepairRoleAreaMapper roleAreaMapper;

	@Autowired
	private RepairRoleOrderTypeMapper orderTypeMapper;

	@Autowired
    private SysUserMapper userMapper;

	@Autowired
    private SchoolConfigMapper schoolConfigMapper;

	@Autowired
    RepairAreaService repairAreaService;




    /**
     * 提交工单
     * @param orderEntity
     * @return
     */
    @Override
    public ResultData commitOrder(OrderEntity orderEntity) {
        ResultData resultData = new ResultData();
        if(orderEntity == null){
            return new ResultData(ResultCode.ERROR.getCode(),false,"报修信息为空!",null);
        }
        Integer bx_id = orderEntity.getBx_id();
        SysUserEntity userEntity = userMapper.selectByPrimaryKey(bx_id);
        if(userEntity == null){
            return new ResultData(ResultCode.ERROR.getCode(),false,"该用户不存在!",null);
        }
        Date date = new Date();
        orderEntity.setCreate_time(date);
        orderEntity.setOrder_status(0);   //待接单
        orderEntity.setOrder_no(IdGenerator.idGen());
        int insert = orderMapper.insert(orderEntity);
        if(insert < 0){
            return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage(),null);
        }
        //获取此工单负责的维修工openId
        List<String> openIds = userMapper.findOpenIdByAreaAndOrderType(orderEntity.getArea_id(), orderEntity.getFix_type_id());
        //此工单的报修时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        String createTime = simpleDateFormat.format(date);
        //此工单的故障区域
        String areaName = repairAreaService.findAreaNameById(orderEntity.getArea_id());  //故障区域名称
        String room = orderEntity.getRoom();  //故障区域房间
        //开始发送模板消息
        StringBuilder sb = new StringBuilder();
        for (String openId : openIds) {
            Boolean aBoolean = sendRepairMessage(openId, orderEntity.getBx_name(), areaName, createTime, orderEntity.getOrder_desc(), orderEntity.getBx_phone());
            if(!aBoolean){
                sb.append(openId).append("#");
            }
        }
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), "发送微信模板消息失败的openId有:" + sb.toString());
    }

    /**
     * 维修工接单
     * @param orderId 工单ID
     * @param userId 用户ID
     * @return
     */
    @Override
    public ResultData acceptOrder(Integer orderId,Integer userId) {
        if(orderId == null){
            return new ResultData(ResultCode.ERROR.getCode(),false,"未指定要进行维修的工单!",null);
        }
        if(userId == null){
            return new ResultData(ResultCode.ERROR.getCode(),false,"用户为空!",null);
        }
        SysUserEntity userEntity = userMapper.selectByPrimaryKey(userId);
        if(userEntity == null){
            return new ResultData(ResultCode.OUTDATE.getCode(),false,ResultCode.OUTDATE.getMessage(),null);
        }
        OrderEntity orderEntity = orderMapper.selectByPrimaryKey(orderId);
        if(orderEntity == null){
            return new ResultData(ResultCode.ERROR.getCode(),false,"要操作的工单不存在!",null);
        }
        if(orderEntity.getOrder_status() != 0){  // 0待接单
            return new ResultData(ResultCode.ERROR.getCode(),false,"该工单暂不能接单!",null);
        }
        //判断此维修工到底是否有权利接此单
        //查询出此工作人员负责的区域以及其负责维修类型
        List<Integer> areas = roleAreaMapper.findRoleAreaByUser(userEntity.getUser_id());
        List<Integer> types = orderTypeMapper.findOrderTypeByUser(userEntity.getUser_id());
        if(!areas.contains(orderEntity.getArea_id()) || !types.contains(orderEntity.getFix_type_id())){
            return new ResultData(ResultCode.ERROR.getCode(),false,"操作失败,该工单的维修类型或故障区域不在您负责的范围!",null);
        }
        orderEntity.setOrder_status(1);   //1已接单,维修中
        orderEntity.setStart_time(new Date());
        orderEntity.setWorker_id(userEntity.getUser_id());
        int i = orderMapper.updateByPrimaryKeySelective(orderEntity);
        if(i > 0){
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), null);
        }
        return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage(),null);
    }

    /**
     * 工单完工
     * @param orderId 要完工的工单ID
     * @param userId 用户ID
     * @return
     */
    @Override
    public ResultData finishOrder(Integer orderId,Integer userId) {
        if(orderId == null || userId == null){
            return new ResultData(ResultCode.ERROR.getCode(),false,"数据不完整!",null);
        }
        SysUserEntity userEntity = userMapper.selectByPrimaryKey(userId);
        if(userEntity == null){
            return new ResultData(ResultCode.OUTDATE.getCode(),false,ResultCode.OUTDATE.getMessage(),null);
        }
        OrderEntity orderEntity = orderMapper.selectByPrimaryKey(orderId);
        if(orderEntity == null){
            return new ResultData(ResultCode.ERROR.getCode(),false,"要操作的工单不存在!",null);
        }
        if(!userId.equals(orderEntity.getWorker_id())){
            return new ResultData(ResultCode.ERROR.getCode(),false,"您不是此工单的维修工,无权进行完工操作!",null);
        }
        if(orderEntity.getOrder_status() != 1){ //1已接单,维修中
            return new ResultData(ResultCode.ERROR.getCode(),false,"该工单暂不能完工!",null);
        }
        orderEntity.setOrder_status(2);   // 2已完工,待评价
        orderEntity.setFinish_time(new Date());
        orderEntity.setWorker_id(userEntity.getUser_id());
        int i = orderMapper.updateByPrimaryKeySelective(orderEntity);
        if(i > 0){
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), null);
        }
        return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage(),null);

    }

    /**
     * 评价工单
     * @param orderId  工单ID
     * @param rank  1好评 2中评 3差评
     * @param content 评论内容
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData commentOrder(Integer orderId, Integer rank,Integer userId, String content) {
        if(orderId == null || rank == null || content == null || userId == null){
            return new ResultData(ResultCode.ERROR.getCode(),false,"操作失败,数据不完整!",null);
        }
        SysUserEntity userEntity = userMapper.selectByPrimaryKey(userId);
        if(userEntity == null){
            return new ResultData(ResultCode.OUTDATE.getCode(),false,ResultCode.OUTDATE.getMessage(),null);
        }
        OrderEntity orderEntity = orderMapper.selectByPrimaryKey(orderId);
        if(orderEntity == null){
            return new ResultData(ResultCode.ERROR.getCode(),false,"要操作的工单不存在!",null);
        }
        if(!userId.equals(orderEntity.getBx_id())){
            return new ResultData(ResultCode.ERROR.getCode(),false,"您不是此工单的报修者,无权进行评价!",null);
        }
        if(orderEntity.getOrder_status() != 2){  //2已完工,待评价
            return new ResultData(ResultCode.ERROR.getCode(),false,"该工单暂不能评论!",null);
        }
        OrderCommentEntity orderCommentEntity = new OrderCommentEntity();
        orderCommentEntity.setCommentator_id(userEntity.getUser_id());
        orderCommentEntity.setComment_content(content);
        orderCommentEntity.setComment_time(new Date());
        orderCommentEntity.setOrder_id(orderEntity.getOrder_id());
        orderCommentEntity.setOrder_no(orderEntity.getOrder_no());
        orderCommentEntity.setRank(rank);
        int insert = orderCommentMapper.insert(orderCommentEntity);
        if(insert < 0){
            return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage(),null);
        }
        orderEntity.setOrder_status(3);  //3已评价,待回访
        int i = orderMapper.updateByPrimaryKeySelective(orderEntity);
        if(i <= 0){
            return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage(),null);
        }
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), null);
    }

    /**
     * 回访工单
     * @param orderId 工单ID
     * @return
     */
    @Override
    public ResultData visitOrder(Integer orderId,Integer userId) {
        if(orderId == null || userId == null){
            return new ResultData(ResultCode.ERROR.getCode(),false,"操作失败,数据不完整!",null);
        }
        SysUserEntity userEntity = userMapper.selectByPrimaryKey(userId);
        if(userEntity == null){
            return new ResultData(ResultCode.OUTDATE.getCode(),false,ResultCode.OUTDATE.getMessage(),null);
        }
        OrderEntity orderEntity = orderMapper.selectByPrimaryKey(orderId);
        if(orderEntity == null){
            return new ResultData(ResultCode.ERROR.getCode(),false,"要操作的工单不存在!",null);
        }
        if(orderEntity.getOrder_status() != 3){ //3已评价,待回访
            return new ResultData(ResultCode.ERROR.getCode(),false,"该工单暂不能进行回访!",null);
        }
        orderEntity.setOrder_status(4);   //4已回访
        orderEntity.setVisiting_time(new Date());
        int i = orderMapper.updateByPrimaryKeySelective(orderEntity);
        if(i > 0){
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), null);
        }
        return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage(),null);

    }

    /**
     * 取消工单
     * @param orderId 工单ID
     * @return
     */
    @Override
    public ResultData cancelOrder(Integer orderId,Integer userId) {
        if(orderId == null || userId == null){
            return new ResultData(ResultCode.ERROR.getCode(),false,"数据不完整!",null);
        }
        SysUserEntity userEntity = userMapper.selectByPrimaryKey(userId);
        if(userEntity == null){
            return new ResultData(ResultCode.ERROR.getCode(),false,"该用户不存在!",null);
        }
        OrderEntity orderEntity = orderMapper.selectByPrimaryKey(orderId);
        if(orderEntity == null){
            return new ResultData(ResultCode.ERROR.getCode(),false,"要操作的工单不存在!",null);
        }
        if(!userId.equals(orderEntity.getBx_id())){
            return new ResultData(ResultCode.ERROR.getCode(),false,"您不是此工单的报修者,无权进行取消!",null);
        }
        if(orderEntity.getOrder_status() != 0){ // 0待接单
            return new ResultData(ResultCode.ERROR.getCode(),false,"该工单暂不能进行取消!",null);
        }

        orderEntity.setOrder_status(5);   //4已取消
        orderEntity.setCancel_time(new Date());
        orderEntity.setCanceled_id(userEntity.getUser_id()); //取消人ID
        int i = orderMapper.updateByPrimaryKeySelective(orderEntity);
        if(i > 0){
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), null);
        }
        return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage(),null);
    }

    /**
     * 根据订单状态,查询此用户的订单情况
     * @param orderStatus  工单状态 0待接单 1已接单,维修中 2已完工,待评价 3已评价,待回访 4已回访 5已取消
     * @param schoolId  用户所选的学校ID
     * @return
     */
    @Override
    public ResultData appFindOrderByOrderStatus(Integer orderStatus, Integer schoolId,Integer userId) {
        if(schoolId == null || orderStatus == null || userId == null){
            return new ResultData(ResultCode.ERROR.getCode(),false,"查询信息不完整!",null);
        }
        SysUserEntity userEntity = userMapper.selectByPrimaryKey(userId);
        if(userEntity == null){
            return new ResultData(ResultCode.OUTDATE.getCode(),false,ResultCode.OUTDATE.getMessage(),null);
        }

        //1.查询自己报修的工单
        List<Map<String, Object>> myselfMaps = orderMapper.appFindOrderByOrderStatusCommen(schoolId, userEntity.getUser_id(), orderStatus);

        //2.查询出此用户所负责的工单(不包含自己报修的工单)
        //查询出此工作人员负责的区域以及其负责维修类型
        List<Map<String, Object>> otherMaps = new ArrayList<>();
        List<Integer> areas = roleAreaMapper.findRoleAreaByUser(userEntity.getUser_id());
        List<Integer> types = orderTypeMapper.findOrderTypeByUser(userEntity.getUser_id());
        if(areas != null && types != null && areas.size() != 0 && types.size() != 0 ){
            otherMaps = orderMapper.appFindOrderByOrderStatus(areas, types, schoolId, userEntity.getUser_id(), orderStatus);
        }
        //3.将两个查询到的数据合并到一起
        if(otherMaps != null && otherMaps.size() > 0){
            boolean b = myselfMaps.addAll(otherMaps);
            if(b){
                return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),myselfMaps);
            }else{
                return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage(),null);
            }
        }

        return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),myselfMaps);

    }

    /**
     *  PC端查询所有订单
     * @param pageNum
     * @param pageSize
     * @param order_no
     * @return
     */
    @Override
    public ResultData findPCOrder(Integer pageNum, Integer pageSize, String order_no) {
        Example example = new Example(OrderEntity.class);
        example.createCriteria().andEqualTo("order_no",order_no);
        List<OrderEntity> orderEntities = orderMapper.selectByExample(example);
        return new ResultData(20000,true,"查询成功",orderEntities);
    }

    /**
     * 根据工单ID查询工单详情
     * @param orderId 工单ID
     * @return
     */
    @Override
    public ResultData findOrderDetails(Integer orderId) {
        if(orderId == null){
            return new ResultData(ResultCode.ERROR.getCode(),false,"工单ID不能为空",null);
        }
        Map<String, Object> orderDetails = orderMapper.findOrderDetails(orderId);
        return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),orderDetails);
    }

    /**
     * 手机端首页(统计有接单权限的用户接单的数量,其实就是统计该用户接单的数量)
     * @param schoolId  学校ID
     * @param userId 用户ID
     * @return
     */
    @Override
    public ResultData appFindOrderByUserPower(Integer schoolId, Integer userId) {
        if(schoolId == null || userId == null){
            return new ResultData(ResultCode.ERROR.getCode(),false,"数据不完整!",null);
        }
        SysUserEntity userEntity = userMapper.selectByPrimaryKey(userId);
        if(userEntity == null){
            return new ResultData(ResultCode.OUTDATE.getCode(),false,ResultCode.OUTDATE.getMessage(),null);
        }
        Map<String, Object> map = orderMapper.appFindOrderByUserPower(schoolId, userId);
        return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),map);
    }

    /**
     * 上传工单中的故障图片
     * @param file  图片
     * @return
     */
    @Override
    public ResultData uploadOrderPic(MultipartFile file) {
        String upload = IoUploadUtil.upload(file, "/repair/order/");
        if(upload == null || "".equals(upload)){
            return new ResultData(ResultCode.ERROR.getCode(),false,"上传失败!",null);
        }
        return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),upload);
    }

    /**
     * 按工单状态统计数量
     * @return
     */
    @Override
    public ResultData countByOrderStatus(Integer schoolId, Integer userId) {
//        0待接单 1已接单,维修中 2已完工,待评价 3已评价,待回访 4已回访 5已取消
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        Map<Integer,Integer> map = new HashMap<>();
        for (Integer orderStatus : list) {
            ResultData resultData = appFindOrderByOrderStatus(orderStatus, schoolId, userId);
            List<Map<String, Object>> data = (List<Map<String, Object>>) resultData.getData();
            if(data == null){
                map.put(orderStatus,0);
            }else{
                map.put(orderStatus,data.size());
            }
        }
        return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),map);
    }


    /**
     * 发送模板消息
     * @param openId   要接收的用户openId
     * @param bxName  报修人姓名
     * @param area  故障区域
     * @param createTime  报修时间
     * @param content 故障描述
     * @param phone  联系人电话
     * @return
     */
    private  Boolean sendRepairMessage(String openId,String bxName,String area,String createTime,String content,String phone){
        String appid = PropertiesUtil.getValue("APPID");
        String secret= PropertiesUtil.getValue("APPSECRET");
        //获取token的微信接口
        String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
        //1.微信获取access_token
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> exchange = restTemplate.exchange(getTokenUrl, HttpMethod.GET, null, String.class);
        Map map = JSON.parseObject(exchange.getBody(), Map.class);
        Map<String, Object> map2 = new HashMap<>();
        String access_token = (String) map.get("access_token");
        //2.发送模板消息
        //发送模板消息的微信接口
        String postUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
        //2.1 封装模板数据
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("touser", openId);   // 要接收模板消息的微信用户openId
        jsonObject.put("template_id", "zYB30G7IqNoDEeFeLTF_IDRt1Ew32NPHNJxXf3tWgVg");   //所使用的消息模板ID
        jsonObject.put("url", "http://www.baidu.com");   //跳转的路径(非必须)
        JSONObject first = new JSONObject();
        JSONObject data = new JSONObject();
        first.put("value", "联系电话:" + phone);
        first.put("color", "#173177");
        JSONObject keyword1 = new JSONObject();
        keyword1.put("value", bxName);
        keyword1.put("color", "#173177");
        JSONObject keyword2 = new JSONObject();
        keyword2.put("value", area);
        keyword2.put("color", "#173177");
        JSONObject keyword3 = new JSONObject();
        keyword3.put("value", createTime);
        keyword3.put("color", "#173177");
        JSONObject keyword4 = new JSONObject();
        keyword4.put("value", content);
        keyword4.put("color", "#173177");
        JSONObject remark = new JSONObject();
        remark.put("value", "请尽快处理");
        remark.put("color", "#173177");

        data.put("first",first);
        data.put("keyword1",keyword1);
        data.put("keyword2",keyword2);
        data.put("keyword3",keyword3);
        data.put("keyword4",keyword4);
        data.put("remark",remark);

        jsonObject.put("data", data);
        //2.2 发送模板消息
        String s = "";
        try {
            s = HttpClientUtils.sendPostJsonStr(postUrl, jsonObject.toJSONString());
            log.info("发送模板消息,返回的结果:" + s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        com.alibaba.fastjson.JSONObject result = JSON.parseObject(s);
        int errcode = result.getIntValue("errcode");
        log.info("发送模板消息返回的状态码:" + errcode);

        if(errcode == 0){
            // 发送成功
            log.info("openID:" + openId + ",发送模板消息成功");
            return true;

        } else {
            // 发送失败
            log.info("openID:" + openId + ",发送模板消息失败");
            return false;
        }
    }




}
