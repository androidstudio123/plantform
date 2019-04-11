package cn.crm.service.repair;


import cn.crm.entity.repair.OrderEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;
import org.springframework.web.multipart.MultipartFile;


public interface OrderService extends BaseService<OrderEntity> {

    /**
     * 提交工单
     * @param orderEntity
     * @return
     */
    ResultData commitOrder(OrderEntity orderEntity);

    /**
     * 维修工接单
     * @param orderId 工单ID
     * @param userId 用户ID
     * @return
     */
    ResultData acceptOrder(Integer orderId,Integer userId);

    /**
     * 工单完工
     * @param orderId 要完工的工单ID
     * @param userId 用户ID
     * @return
     */
    ResultData finishOrder(Integer orderId,Integer userId);

    /**
     * 评价工单
     * @param orderId  工单ID
     * @param rank  1好评 2中评 3差评
     * @param content 评论内容
     * @param userId 用户ID
     * @return
     */
    ResultData commentOrder(Integer orderId, Integer rank,Integer userId, String content);

    /**
     * 回访工单
     * @param orderId 工单ID
     * @return
     */
    ResultData visitOrder(Integer orderId,Integer userId);

    /**
     * 取消工单
     * @param orderId 工单ID
     * @return
     */
    ResultData cancelOrder(Integer orderId,Integer userId);


    /**
     * 根据订单状态,查询此用户的订单情况
     * @param orderStatus  工单状态 0待接单 1已接单,维修中 2已完工,待评价 3已评价,待回访 4已回访 5已取消
     * @param schoolId  用户所选的学校ID
     * @param userId  用户ID
     * @return
     */
    ResultData appFindOrderByOrderStatus(Integer orderStatus, Integer schoolId,Integer userId);
    //PC端查询所有订单
    ResultData findPCOrder(Integer pageNum, Integer pageSize, String order_no);

    /**
     * 根据工单ID查询工单详情
     * @param orderId 工单ID
     * @return
     */
    ResultData findOrderDetails(Integer orderId);

    /**
     * 手机端首页(统计有接单权限的用户接单的数量,其实就是统计该用户接单的数量)
     * @param schoolId  学校ID
     * @param userId 用户ID
     * @return
     */
    ResultData appFindOrderByUserPower(Integer schoolId, Integer userId);

    /**
     * 上传工单中的故障图片
     * @param file  图片
     * @return
     */
    ResultData uploadOrderPic(MultipartFile file);

    /**
     * 按工单状态统计数量
     * @return
     */
    ResultData countByOrderStatus(Integer schoolId, Integer userId);
}
