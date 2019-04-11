package cn.crm.mapper.materiel;

import cn.crm.entity.materiel.RepairGoodsEntity;
import cn.crm.entity.materiel.RepairRepertoryAdminConfigEntity;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;


public interface RepairRepertoryAdminConfigMapper extends Mapper<RepairRepertoryAdminConfigEntity>,MySqlMapper<RepairRepertoryAdminConfigEntity>{



}

