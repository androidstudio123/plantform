package cn.crm.service.sys;


import cn.crm.entity.SysRoleEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface SysRoleService extends BaseService<SysRoleEntity> {

    List<SysRoleEntity> findAllSysRole(Integer role_isActive, String role_name,Integer admin_id);
    ResultData updateRole(SysRoleEntity sysRoleEntity);
    ResultData deleteRole(Integer role_id);
    SysRoleEntity findRoleByid(Integer roleId);
    ResultData addRole(SysRoleEntity sysRoleEntity, HttpServletRequest request);

}
