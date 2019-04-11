package cn.crm.mapper.materiel;


import cn.crm.entity.materiel.RepairGoodsExportEntity;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface RepairGoodsExportMapper extends Mapper<RepairGoodsExportEntity>,MySqlMapper<RepairGoodsExportEntity> {


    List<RepairGoodsExportEntity> findExportRecordCount(@Param("goods_name")String goods_name,@Param("producer_id")Integer producer_id,@Param("repertory_name_id")Integer repertory_name_id,
                                                        @Param("goods_type_id")Integer goods_type_id,@Param("repertory_type_id")Integer repertory_type_id);
    List<RepairGoodsExportEntity> findExportSumCount(@Param("goods_name")String goods_name,@Param("producer_id")Integer producer_id,@Param("repertory_name_id")Integer repertory_name_id,
                                                        @Param("goods_type_id")Integer goods_type_id,@Param("repertory_type_id")Integer repertory_type_id);

    Integer findSum();

    Integer findCount();
}

