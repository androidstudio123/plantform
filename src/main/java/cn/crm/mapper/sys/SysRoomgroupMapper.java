package cn.crm.mapper.sys;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import cn.crm.entity.SysRoomgroupEntity;

import java.util.List;


public interface SysRoomgroupMapper extends Mapper<SysRoomgroupEntity>,MySqlMapper<SysRoomgroupEntity>{

    //根据管理员id查询管理员所管理的用户组
    List<SysRoomgroupEntity>  findAdminRoomgroup(Integer adminId);

}

