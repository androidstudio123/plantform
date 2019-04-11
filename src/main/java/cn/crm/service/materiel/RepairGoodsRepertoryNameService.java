package cn.crm.service.materiel;


import cn.crm.entity.materiel.RepairGoodsRepertoryNameEntity;
import cn.crm.entity.repair.RepairGoodsTypeEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;


public interface RepairGoodsRepertoryNameService extends BaseService<RepairGoodsRepertoryNameEntity> {

    //查询所有库名称
    ResultData findRepairGoodsRepertoryName(Integer pageNum, Integer pageSize, String repertory_name);
    //新增库名称
    ResultData saveRepairGoodsRepertoryName(RepairGoodsRepertoryNameEntity repairGoodsRepertoryNameEntity);
    //编辑库名称
    ResultData updateRepairGoodsRepertoryName(RepairGoodsRepertoryNameEntity repairGoodsRepertoryNameEntity);
    //删除库名称
    ResultData deleteRepairGoodsRepertoryName(Integer repertory_name_id);

    ResultData findRepertoryNameByAdmin(Integer admin_id);

    /**
     * 根据微信用户ID查询其管理的库
     * @param userId  前台微信用户ID
     * @return
     */
    ResultData findRepertoryNameByUserId(Integer userId);
}
