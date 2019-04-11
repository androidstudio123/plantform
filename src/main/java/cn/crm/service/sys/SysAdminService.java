package cn.crm.service.sys;


import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.SysRoleEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;

import java.util.List;

public interface SysAdminService extends BaseService<SysAdminEntity> {



    List<SysRoleEntity> findRoleByAdminAuth(Integer adminId);

    public List<SysAdminEntity> findAllAdmin();

    List<SysAdminEntity> findAllParnetsByChildId(Integer childId);
    List<SysAdminEntity> findByParentId(Integer parentId);
    SysAdminEntity findByAdminName(String adminName);
    /**
     * 修改用户密码
     * @param admin_password  密码
     */
    public ResultData updatePassword(String admin_password);

    /**
     * 重置密码
     *
     * @param admin_password  密码
     * @param admin_id  用户id
     * @return
     */
    public ResultData resstingPassword(Integer admin_id, String admin_password);

    //给管理员分配出库角色id
    ResultData distributionExport(Integer admin_id, Integer export_role_id);
}
