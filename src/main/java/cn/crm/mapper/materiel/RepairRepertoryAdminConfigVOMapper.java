package cn.crm.mapper.materiel;

import cn.crm.entity.materiel.RepairRepertoryAdminConfigEntity;
import cn.crm.vo.RepairRepertoryAdminConfigVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;


public interface RepairRepertoryAdminConfigVOMapper extends Mapper<RepairRepertoryAdminConfigVO>,MySqlMapper<RepairRepertoryAdminConfigVO>{


    List<RepairRepertoryAdminConfigEntity> findRepertoryAdminConfig();



}

