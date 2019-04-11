package cn.crm.controller.repair;

import cn.crm.entity.repair.OrderTypeEntity;
import cn.crm.logaop.SystemLog;
import cn.crm.result.ResultData;
import cn.crm.service.repair.OrderTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName OrderTypeController
 * @Author HJW
 * @Date 2019/3/21 15:23
 */
@RestController
@RequestMapping(value="/ordertype")
public class OrderTypeController {

    @Autowired
    private OrderTypeService orderTypeService;

    /**
     * 查询所有工单类型
     * @return
     */
    @GetMapping("findOrderType")
    public ResultData findOrderType(Integer pageNum,Integer pageSize,String type_name){
        ResultData resultData = orderTypeService.findOrderType(pageNum,pageSize,type_name);
        return resultData;
    }

    /**
     * 新增工单类型
     * @param orderTypeEntity
     * @return
     */
    @PostMapping("saveOrderType")
    @SystemLog(methods = "新增工单类型",module = "新增工单类型")
    public ResultData saveOrderType(@RequestBody OrderTypeEntity orderTypeEntity){
        ResultData resultData = orderTypeService.saveOrderType(orderTypeEntity);
        return resultData;
    }
    @PostMapping("updateOrderType")
    @SystemLog(methods = "编辑工单类型",module = "编辑工单类型")
    public ResultData updateOrderType(@RequestBody OrderTypeEntity orderTypeEntity){
        ResultData resultData = orderTypeService.updateOrderType(orderTypeEntity);
        return resultData;
    }

    @PostMapping("deleteOrderType")
    @SystemLog(methods = "删除工单类型",module = "删除工单类型")
    public ResultData deleteOrderType(Integer id){
        ResultData resultData = orderTypeService.deleteOrderType(id);
        return resultData;
    }
}
