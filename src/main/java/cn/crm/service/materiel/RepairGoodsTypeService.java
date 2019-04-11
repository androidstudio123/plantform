package cn.crm.service.materiel;



import cn.crm.entity.materiel.RepairGoodsEntity;
import cn.crm.entity.materiel.RepairGoodsTypeEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;

import javax.servlet.http.HttpServletRequest;

public interface RepairGoodsTypeService extends BaseService<RepairGoodsTypeEntity> {


    ResultData findRepairGoodsType(String goods_type_name);

    ResultData saveRepairGoodsType(RepairGoodsTypeEntity repairGoodsTypeEntity);

    ResultData updateRepairGoodsType(RepairGoodsTypeEntity repairGoodsTypeEntity);

    ResultData deleteRepairGoodsType(Integer goods_type_id);

    /**
     * 根据库名称与库类型查询对应的物品分类(手机端出库使用)
     * @param repertoryNameId  库名称ID
     * @param repertoryTypeId  库类型ID
     * @return
     */
    ResultData findByCondition(Integer repertoryNameId, Integer repertoryTypeId);
}
