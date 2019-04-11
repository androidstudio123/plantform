package cn.crm.service.materiel;



import cn.crm.entity.materiel.RepairGoodsImportEntity;
import cn.crm.entity.materiel.RepairGoodsRepertoryTypeEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;

public interface RepairGoodsRepertoryTypeService extends BaseService<RepairGoodsRepertoryTypeEntity> {

    //查询所有库类型
    ResultData findRepairGoodsRepertoryTypeAll(Integer pageNum,Integer pageSize,String repertory_type_name);
    //新增库类型
    ResultData saveRepairGoodsRepertoryType(RepairGoodsRepertoryTypeEntity repairGoodsRepertoryTypeEntity);
    //编辑库类型
    ResultData updateRepairGoodsRepertoryType(RepairGoodsRepertoryTypeEntity repairGoodsRepertoryTypeEntity);
    //删除库类型
    ResultData deleteRepairGoodsRepertoryType(Integer repertory_type_id);
    //查询管理员对应的库名称对应的库类型
    ResultData findRepertoryNameByAdmin(Integer repertory_name_id, Integer admin_id);

    /**
     * 根据微信用户ID与库名ID查询其拥有的库类型(手机端出库使用)
     * @param userId  微信用户ID
     * @param repertoryNameId  库名称ID
     * @return
     */
    ResultData findRepertoryTypeByCondition(Integer userId, Integer repertoryNameId);
}
