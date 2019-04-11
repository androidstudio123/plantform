package cn.crm.controller.repair;

import cn.crm.entity.repair.OrderEntity;
import cn.crm.logaop.SystemLog;
import cn.crm.mapper.repair.OrderAreaTypeUserVoMapper;
import cn.crm.result.ResultData;
import cn.crm.service.repair.OrderAreaTypeUserVoService;
import cn.crm.service.repair.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName OrderController
 * @Author HJW
 * @Date 2019/3/21 15:21
 */
@RestController
@RequestMapping(value="/order")
@Api(description = "工单相关操作")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderAreaTypeUserVoService orderAreaTypeUserVoService;
    /**
     * 提交工单
     * @param orderEntity
     * @return
     */
    @PostMapping("/commitOrder")
    @ApiOperation(value = "提交工单", notes = "提交工单")
    public ResultData commitOrder(@RequestBody OrderEntity orderEntity){
        ResultData resultData = orderService.commitOrder(orderEntity);
        return resultData;
    }

    /**
     * 维修工接单
     * @param orderId 工单ID
     * @param userId 用户ID
     * @return
     */
    @GetMapping("/acceptOrder")
    @ApiOperation(value = "维修工接单", notes = "维修工接单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "工单ID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "query")
    })
    public ResultData acceptOrder(Integer orderId,Integer userId){
        ResultData resultData = orderService.acceptOrder(orderId,userId);
        return resultData;
    }

    /**
     * 工单完工
     * @param orderId 要完工的工单ID
     * @param userId 用户ID
     * @return
     */
    @GetMapping("/finishOrder")
    @ApiOperation(value = "工单完工", notes = "工单完工")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "工单ID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "query")
    })
    public ResultData finishOrder(Integer orderId,Integer userId){
        ResultData resultData = orderService.finishOrder(orderId,userId);
        return resultData;
    }

    /**
     * 评价工单
     * @param orderId  工单ID
     * @param rank  1好评 2中评 3差评
     * @param content 评论内容
     * @param userId 用户ID
     * @return
     */
    @PostMapping("/commentOrder")
    @ApiOperation(value = "评价工单", notes = "评价工单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "工单ID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "rank", value = "1好评 2中评 3差评", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "评论内容", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "string", paramType = "query")
    })
    public ResultData commentOrder(Integer orderId,Integer rank,Integer userId ,String content){
        ResultData resultData = orderService.commentOrder(orderId, rank,userId,content);
        return resultData;
    }

    /**
     * 回访工单
     * @param orderId 工单ID
     * @return
     */
    @GetMapping("/visitOrder")
    @ApiOperation(value = "回访工单", notes = "回访工单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "工单ID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "query")
    })
    public ResultData visitOrder(Integer orderId,Integer userId){
        ResultData resultData = orderService.visitOrder(orderId,userId);
        return resultData;
    }

    /**
     * 取消工单
     * @param orderId 工单ID
     * @return
     */
    @GetMapping("/cancelOrder")
    @ApiOperation(value = "取消工单", notes = "取消工单")
    @ApiImplicitParam(name = "orderId", value = "工单ID", required = true, dataType = "int", paramType = "query")
    public ResultData cancelOrder(Integer orderId,Integer userId){
        ResultData resultData = orderService.cancelOrder(orderId,userId);
        return resultData;
    }


    /**
     * 根据订单状态,查询此用户的订单情况
     * @param orderStatus  工单状态 0待接单 1已接单,维修中 2已完工,待评价 3已评价,待回访 4已回访 5已取消
     * @param schoolId  用户所选的学校ID
     * @param userId  用户ID
     * @return
     */
    @GetMapping("/appFindOrderByOrderStatus")
    @ApiOperation(value = "根据订单状态,查询此用户的订单情况", notes = "根据订单状态,查询此用户的订单情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schoolId", value = "用户所选的学校ID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "orderStatus", value = "工单状态 0待接单 1已接单,维修中 2已完工,待评价 3已评价,待回访 4已回访 5已取消", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "query")
    })
    public ResultData appFindOrderByOrderStatus(Integer orderStatus,Integer schoolId,Integer userId){
        ResultData resultData = orderService.appFindOrderByOrderStatus(orderStatus, schoolId,userId);
        return resultData;
    }
    @GetMapping("findOrderTypeSchoolVO")
    @SystemLog(methods = "PC端查询订单",module = "PC端查询所有订单")
    public ResultData findOrderTypeSchoolVO(Integer pageNum,Integer pageSize,String order_no,Integer order_status){
        ResultData resultData = orderAreaTypeUserVoService.findOrderManager(order_status,order_no,pageNum,pageSize);
        return resultData;
    }

    /**
     * 根据工单ID查询工单详情
     * @param orderId 工单ID
     * @return
     */
    @GetMapping("/findOrderDetails")
    @ApiOperation(value = "根据工单ID查询工单详情", notes = "根据工单ID查询工单详情")
    @ApiImplicitParam(name = "orderId", value = "工单ID", required = true, dataType = "int", paramType = "query")
    public ResultData findOrderDetails(Integer orderId){
        ResultData resultData = orderService.findOrderDetails(orderId);
        return resultData;
    }

    /**
     * 手机端首页(统计有接单权限的用户接单的数量,其实就是统计该用户接单的数量)
     * @param schoolId  学校ID
     * @param userId 用户ID
     * @return
     */
    @GetMapping("/appFindOrderByUserPower")
    @ApiOperation(value = "手机端首页(统计有接单权限的用户接单的数量,其实就是统计该用户接单的数量)", notes = "手机端首页(统计有接单权限的用户接单的数量,其实就是统计该用户接单的数量)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schoolId", value = "用户所选的学校ID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "query")
    })
    public ResultData appFindOrderByUserPower(Integer schoolId,Integer userId){
        ResultData resultData = orderService.appFindOrderByUserPower(schoolId, userId);
        return resultData;
    }

    /**
     * 上传工单中的故障图片
     * @param file  图片
     * @return
     */
    @PostMapping("/uploadOrderPic")
    @ApiOperation(value = "上传工单中的故障图片", notes = "上传工单中的故障图片")
    @ApiImplicitParam(name = "file", value = "图片", required = true, dataType = "file", paramType = "query")
    public ResultData uploadOrderPic(MultipartFile file){
        ResultData resultData = orderService.uploadOrderPic(file);
        return resultData;
    }

    /**
     * 按工单状态统计数量
     * @return
     */
    @ApiOperation(value = "按工单状态统计数量", notes = "按工单状态统计数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schoolId", value = "用户所选的学校ID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/countByOrderStatus")
    public ResultData countByOrderStatus(Integer schoolId,Integer userId){
        ResultData resultData = orderService.countByOrderStatus(schoolId, userId);
        return resultData;
    }

}
