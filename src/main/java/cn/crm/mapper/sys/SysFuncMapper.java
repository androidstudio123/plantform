package cn.crm.mapper.sys;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import cn.crm.entity.SysFuncEntity;

import java.util.List;

public interface SysFuncMapper extends Mapper<SysFuncEntity>,MySqlMapper<SysFuncEntity>{

    List<SysFuncEntity> findAllSysFun(Integer roleId);
}

