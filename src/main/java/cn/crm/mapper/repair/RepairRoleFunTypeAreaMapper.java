package cn.crm.mapper.repair;

import cn.crm.entity.repair.RepairRoleFunTypeAreaEntity;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;


public interface RepairRoleFunTypeAreaMapper extends Mapper<RepairRoleFunTypeAreaEntity>,MySqlMapper<RepairRoleFunTypeAreaEntity>{

    List<RepairRoleFunTypeAreaEntity> findPCRepairRole(@Param("roleName")String roleName);




}

