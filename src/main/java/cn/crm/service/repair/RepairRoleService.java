package cn.crm.service.repair;


import cn.crm.entity.repair.RepairRoleEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;


public interface RepairRoleService extends BaseService<RepairRoleEntity> {

    //新增工单角色
    ResultData saveRepairRole(String roleName, String fids, String otids, String pids,Integer is_default,Integer role_flag);
    //编辑工单角色
    ResultData updateRepairRole(Integer id,String roleName, String fids, String otids, String pids,Integer role_flag,Integer is_default);
    //删除工单角色
    ResultData deleteRepair(Integer id);
}
