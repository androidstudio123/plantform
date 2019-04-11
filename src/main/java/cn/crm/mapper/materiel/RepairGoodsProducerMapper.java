package cn.crm.mapper.materiel;

import cn.crm.entity.materiel.RepairGoodsImportEntity;
import cn.crm.entity.materiel.RepairGoodsProducerEntity;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;


public interface RepairGoodsProducerMapper extends Mapper<RepairGoodsProducerEntity>,MySqlMapper<RepairGoodsProducerEntity>{


    /**
     * 根据库名称,库类型以及物品类型查询对应的生产厂家(手机端出库使用)
     * @param repertoryNameId 库名称ID
     * @param repertoryTypeId 库类型ID
     * @param goodsTypeId  物品分类ID
     * @return
     */
    List<RepairGoodsProducerEntity> findByCondition(@Param("repertoryNameId") Integer repertoryNameId,
                                                    @Param("repertoryTypeId") Integer repertoryTypeId,@Param("goodsTypeId") Integer goodsTypeId);
}

