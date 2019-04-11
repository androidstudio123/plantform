package cn.crm.service.materiel;



import cn.crm.entity.materiel.RepairGoodsImportEntity;
import cn.crm.entity.materiel.RepairRepertoryAdminConfigEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface RepairRepertoryAdminConfigService extends BaseService<RepairRepertoryAdminConfigEntity> {


    /**
     * 根据管理员信息,查询其管理的仓库集合
     * @param adminId  管理员ID
     * @return
     */
    List<RepairRepertoryAdminConfigEntity> findConfigByAdmin(Integer adminId);
    //根据当前登录用户查询仓库信息
//    List<RepairRepertoryAdminConfigEntity> findRepertoryAdminConfig(Integer adminId);
    //添加库管配置信息
    ResultData saveRepairRepertoryAdminConfig(Integer repertory_name_id,String admin_ids,Integer repertory_type_id, HttpServletRequest request);
    //编辑库管配置信息
    ResultData updateRepairRepertoryAdminConfig(Integer repertory_config_id,Integer repertory_name_id,String admin_ids ,Integer repertory_type_id,HttpServletRequest request);
    //删除库管配置信息
    ResultData deleteRepairRepertoryAdminConfig(Integer id, HttpServletRequest request);
}
