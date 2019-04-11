package cn.crm.mapper.materiel;

import cn.crm.entity.materiel.RepairGoodsEntity;
import cn.crm.entity.materiel.RepairGoodsTypeEntity;
import cn.crm.entity.materiel.RepairRepertoryAdminConfigEntity;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;


public interface RepairGoodsTypeMapper extends Mapper<RepairGoodsTypeEntity>,MySqlMapper<RepairGoodsTypeEntity>{


    /**
     * 根据库名称与库类型查询对应的物品分类(手机端出库使用)
     * @param repertoryNameId  库名称ID
     * @param repertoryTypeId  库类型ID
     * @return
     */
    List<RepairGoodsTypeEntity> findByCondition(@Param("repertoryNameId") Integer repertoryNameId,
                                                @Param("repertoryTypeId") Integer repertoryTypeId);
}

