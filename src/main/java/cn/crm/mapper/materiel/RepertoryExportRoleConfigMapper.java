package cn.crm.mapper.materiel;


import cn.crm.entity.materiel.RepertoryExportRoleConfigEntity;
import cn.crm.entity.materiel.RepertoryExportRoleEntity;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;
public interface RepertoryExportRoleConfigMapper extends Mapper<RepertoryExportRoleConfigEntity>,MySqlMapper<RepertoryExportRoleConfigEntity> {

    /**
     * 根据其对应的出库角色信息查询其管理的库信息
     * @param exportRole  出库角色集合
     * @return
     */
    List<RepertoryExportRoleConfigEntity> findByExportRoleId(@Param("exportRole") List<RepertoryExportRoleEntity> exportRole);
}

