package cn.crm.service.materiel;



import cn.crm.entity.materiel.RepairGoodsImportEntity;
import cn.crm.entity.materiel.RepairGoodsProducerEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;

public interface RepairGoodsProducerService extends BaseService<RepairGoodsProducerEntity> {

    //查询所有生产厂家
    ResultData findRepairGoodsProducerAll(String producer_name, Integer pageNum, Integer pageSize);
    //新增生产厂家
    ResultData saveRepairGoodsProducer(RepairGoodsProducerEntity repairGoodsProducerEntity);
    //编辑生产厂家
    ResultData updateRepairGoodsProducer(RepairGoodsProducerEntity repairGoodsProducerEntity);
    //删除生产厂家
    ResultData deleteRepairGoodsProducer(Integer producer_id);

    /**
     * 根据库名称,库类型以及物品类型查询对应的生产厂家(手机端出库使用)
     * @param repertoryNameId 库名称ID
     * @param repertoryTypeId 库类型ID
     * @param goodsTypeId  物品分类ID
     * @return
     */
    ResultData findByCondition(Integer repertoryNameId, Integer repertoryTypeId, Integer goodsTypeId);
}
