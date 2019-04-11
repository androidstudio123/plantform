package cn.crm.mapper.materiel;

import cn.crm.entity.materiel.RepairGoodsRepertoryNameEntity;
import cn.crm.entity.repair.RepairGoodsTypeEntity;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;


public interface RepairGoodsRepertoryNameMapper extends Mapper<RepairGoodsRepertoryNameEntity>,MySqlMapper<RepairGoodsRepertoryNameEntity>{


    /**
     * 根据库管角色查询其管理的库
     * @param export_role_id  角色ID
     * @return
     */
    List<RepairGoodsRepertoryNameEntity> findRepertoryNameByRoleId(@Param("export_role_id") Integer export_role_id);
}

