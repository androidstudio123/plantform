package cn.crm.service.repair;


import cn.crm.entity.repair.OrderTypeEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;


public interface OrderTypeService extends BaseService<OrderTypeEntity> {

    //查询工单类型
    ResultData findOrderType(Integer pageNum,Integer pageSize,String type_name);
    //新增工单类型
    ResultData saveOrderType(OrderTypeEntity orderTypeEntity);
    //编辑工单类型
    ResultData updateOrderType(OrderTypeEntity orderTypeEntity);
    //删除工单类型
    ResultData deleteOrderType(Integer id);
}
