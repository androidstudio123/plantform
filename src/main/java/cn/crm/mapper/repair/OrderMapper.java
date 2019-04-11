package cn.crm.mapper.repair;

import cn.crm.entity.repair.OrderEntity;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

public interface OrderMapper extends Mapper<OrderEntity>,MySqlMapper<OrderEntity>{

    /**
     *  根据用户统计订单数量
     * @param areas   用户所负责的区域
     * @param types   用户所负责的故障类型
     * @param schoolId  当前用户所选的学校
     * @param userId  用户ID
     * @return
     */
    Map<String,Object> countOrder(@Param("areas") List<Integer> areas, @Param("types") List<Integer> types,
                                  @Param("schoolId") Integer schoolId, @Param("userId") Integer userId );


    /**
     * 根据订单状态,查询此用户的订单情况(针对非普通用户,也就是工作人员)
     * @param orderStatus  工单状态 0待接单 1已接单,维修中 2已完工,待评价 3已评价,待回访 4已回访 5已取消
     * @param areas   用户所负责的区域
     * @param types   用户所负责的故障类型
     * @param schoolId  当前用户所选的学校
     * @param userId  用户ID
     * @return
     */
    List<Map<String,Object>> appFindOrderByOrderStatus(@Param("areas") List<Integer> areas, @Param("types") List<Integer> types,
                                                       @Param("schoolId") Integer schoolId, @Param("userId") Integer userId,
                                                       @Param("orderStatus") Integer orderStatus);

    /**
     * 根据订单状态,查询此用户的订单情况(针对普通用户)
     * @param schoolId  当前用户所选的学校
     * @param user_id  用户ID
     * @param orderStatus  工单状态 0待接单 1已接单,维修中 2已完工,待评价 3已评价,待回访 4已回访 5已取消
     * @return
     */
    List<Map<String,Object>> appFindOrderByOrderStatusCommen(@Param("schoolId")Integer schoolId,  @Param("userId")Integer user_id,
                                                             @Param("orderStatus")Integer orderStatus);

    /**
     * 根据工单ID查询工单详情
     * @param orderId 工单ID
     * @return
     */
    Map<String,Object> findOrderDetails(@Param("orderId") Integer orderId);

    /**
     * 手机端首页(统计有接单权限的用户接单的数量,其实就是统计该用户接单的数量)
     * @param schoolId  学校ID
     * @param userId 用户ID
     * @return
     */
    Map<String,Object>appFindOrderByUserPower(@Param("schoolId") Integer schoolId, @Param("userId") Integer userId);

    /**
     * 根据用户负责的区域以及类型统计待接单的数量
     * @param areas  用户所负责的区域
     * @param types  用户负责的类型
     * @return
     */
    Map<String,Object> countUnreceivedOrders(List<Integer> areas, List<Integer> types);
}

