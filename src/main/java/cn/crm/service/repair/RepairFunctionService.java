package cn.crm.service.repair;


import cn.crm.entity.repair.RepairFunctionEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;


public interface RepairFunctionService extends BaseService<RepairFunctionEntity> {

    //查询报修功能
    ResultData findRepairFunction(Integer pageNum,Integer pageSize);
    //新增报修功能
    ResultData saveRepairFunction(RepairFunctionEntity repairFunctionEntity);
    //编辑报修功能
    ResultData updateRepairFunction(RepairFunctionEntity repairFunctionEntity);
    //删除报修功能
    ResultData deleteRepairFunction(Integer id);
    //根据用户id查询该用户拥有的功能
    ResultData findRepairFunctionByUserId(Integer userId);
}
