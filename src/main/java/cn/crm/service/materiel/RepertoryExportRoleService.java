package cn.crm.service.materiel;


import cn.crm.entity.materiel.RepertoryExportRoleEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;

import javax.servlet.http.HttpServletRequest;

public interface RepertoryExportRoleService extends BaseService<RepertoryExportRoleEntity> {

    /**
     * 新增一个库管角色
     * @param roleName  库管角色名称
     * @param roleConfig 角色配置字符串
     * @return
     */
    ResultData saveExportRole(String roleName, String roleConfig, HttpServletRequest request);

    /**
     * 删除一个出库角色
     * @param roleId  角色ID
     * @return
     */
    ResultData deleteExportRole(Integer roleId);

    /**
     * 修改一个库管角色
     * @param roleId  角色ID
     * @param roleName 库管角色名称
     * @param roleConfig  角色配置字符串
     * @return
     */
    ResultData updateExportRole(Integer roleId, String roleName, String roleConfig);

    /**
     * 根据当前登录的管理员查询出库角色信息
     * @param request
     * @return
     */
    ResultData findExportRoleByAdmin(HttpServletRequest request,String exportRoleName);

    /**
     * 查询指定库管角色的详情
     * @param roleId 库管角色ID
     * @return
     */
    ResultData findExportRoleDetail(Integer roleId);

}
