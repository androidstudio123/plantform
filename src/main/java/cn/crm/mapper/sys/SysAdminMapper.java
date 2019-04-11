package cn.crm.mapper.sys;

import cn.crm.entity.SysRoleEntity;
import cn.crm.entity.SysRoomgroupEntity;
import cn.crm.entity.SysUsergroupEntity;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import cn.crm.entity.SysAdminEntity;

import java.util.List;
import java.util.Set;
@Component
public interface SysAdminMapper extends Mapper<SysAdminEntity>,MySqlMapper<SysAdminEntity>{
    SysAdminEntity findAdminAllInfo(Integer adminId);
    List<SysRoomgroupEntity> findRoomsGroupsByAdminid(Integer adminId);
    List<SysUsergroupEntity> findUserGroupsByAdminid(Integer adminId);
    List<SysRoleEntity> findRoleByAdminAuth(Integer adminId);





}

