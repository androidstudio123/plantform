package cn.crm.mapper.materiel;

import cn.crm.entity.materiel.RepairGoodsImportEntity;
import cn.crm.entity.materiel.RepairGoodsRepertoryTypeEntity;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;


public interface RepairGoodsRepertoryTypeMapper extends Mapper<RepairGoodsRepertoryTypeEntity>,MySqlMapper<RepairGoodsRepertoryTypeEntity>{

    /**
     * 根据库管角色ID与库名称ID查询对应的库类型
     * @param export_role_id  库管角色ID
     * @param repertoryNameId  库名称ID
     * @return
     */
    List<RepairGoodsRepertoryTypeEntity> findRepertoryTypeByCondition(@Param("export_role_id") Integer export_role_id,
                                                                      @Param("repertoryNameId") Integer repertoryNameId);
}

