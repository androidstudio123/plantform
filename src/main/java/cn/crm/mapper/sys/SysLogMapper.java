package cn.crm.mapper.sys;

import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import cn.crm.entity.SysLogEntity;
@Component
public interface SysLogMapper extends Mapper<SysLogEntity>,MySqlMapper<SysLogEntity>{






}

