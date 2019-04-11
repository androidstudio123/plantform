package cn.crm.mapper.sys;

import cn.crm.entity.SysFuncEntity;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import cn.crm.entity.SysRoleFuncEntity;

import java.util.List;

public interface SysRoleFuncMapper extends Mapper<SysRoleFuncEntity>,MySqlMapper<SysRoleFuncEntity>{

    List<SysFuncEntity> findFunByRoleid(Integer roleId);

}

