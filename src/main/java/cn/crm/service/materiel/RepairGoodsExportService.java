package cn.crm.service.materiel;


import cn.crm.entity.materiel.RepairGoodsExportEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;

public interface RepairGoodsExportService extends BaseService<RepairGoodsExportEntity> {

    //入库类型
    Integer selectCount(Integer flag);
    /**
     * 从仓库里出货物
     * @param exportEntity
     * @return
     */
    ResultData saveExportGoods(RepairGoodsExportEntity exportEntity);

    /**
     * 根据维修人员查询出库记录
     * @param userId    用户ID
     * @param goodsTypeId  物品分类ID
     * @param repertoryTypeId  库类型ID
     * @param repertoryNameId   库名称ID
     * @param flag  维修类型标识 0维修 1换新
     * @return
     */
    ResultData findByUser(Integer userId, Integer goodsTypeId, Integer repertoryTypeId, Integer repertoryNameId, Integer flag);
}
