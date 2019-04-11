package cn.crm.service.user;


import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.SysUserEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @param {NAME}
 * @Description: TODO  用户信息服务层接口
 * @Author:MYZ
 */
public interface SysUserService extends BaseService<SysUserEntity> {




    /**
     * 查询用户组下的用户信息
     *
     *
     *
     * @param user_name 根据用户名模糊查询
     * @param userGroup_id       用户组id
     * @return
     */
    public ResultData findUser( String user_name,Integer userGroup_id,Integer admin_id);

    /**
     * 根据学校id查询用户信息
     * @param schoolId
     * @return
     */
    public List<SysUserEntity> findUserBySchoolId (Integer schoolId);

    /**
     * 根据用户id查询这条用户详细信息
     *
     * @param user_id
     * @return
     */
    public ResultData findUserByUserId(Integer user_id);

    /**
     * 新增用户信息
     *
     * @param sysUserEntity 用户信息实体类
     * @return
     */
    public ResultData addUser(SysUserEntity sysUserEntity);

    /**
     * 修改用户信息
     *
     * @param sysUserEntity 用户信息实体类
     * @return
     */
    public ResultData updateUser(SysUserEntity sysUserEntity,HttpServletRequest request);

    /**
     * 根据用户id删除用户信息
     *
     * @param user_id 用户id
     * @return
     */
    public ResultData deleteUser(Integer user_id);

    /**
     * 上传用户头像
     *
     * @param request
     * @param file    上传文件
     * @return
     */
    public ResultData uploadUserAvatar(HttpServletRequest request, MultipartFile file);

    /**
     * 用于导出用户信息到excel(不分页)
     *
     * @param userGroup_id 用户组id
     * @return
     */
    public List<SysUserEntity> finduser(HttpServletRequest request,Integer admin_id, Integer userGroup_id, String user_name);

    //给用户分配角色
    ResultData distributionRole(Integer id, Integer role_id);


    /**
     * 导入用户信息
     * @param file     Excel文件
     * @param userGroupId 用户分组ID
     * @param falg     如果有重复是否覆盖
     * @return
     */
    ResultData improtUserExcel(MultipartFile file, Integer userGroupId,Integer falg);

    /**
     * 根据学号、工号查询用户基本信息
     * @param user_number
     * @return
     */
    SysUserEntity findUserByUserNumber(Integer user_number);

    /**
     * 修改手机号
     * @param userPhone
     * @return
     */
    ResultData updatePhone(String userPhone,HttpServletRequest request);

    //用户分配出库角色id
    ResultData distributionExport(Integer user_id, Integer export_role_id);
}
