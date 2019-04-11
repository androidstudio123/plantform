package cn.crm.service.user;


import cn.crm.entity.SysUserEntity;
import cn.crm.entity.SysUsergroupEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;
import cn.crm.vo.SysUsergroupVo;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
* @Description: TODO  用户组服务层接口
* @param {NAME}
* @Author:MYZ
*/
public interface SysUsergroupService extends BaseService<SysUsergroupEntity> {

    /**
     * 根据管理员id查询对应的用户组信息
     * @param admin_id  管理员id
     * @return
     */
    public List<SysUsergroupVo> findUsergroup(Integer admin_id);

    /**
     * 根据用户组id查询这条用户组详细信息
     * @param userGroup_id  用户组id
     * @return
     */
    public ResultData findUsergroupByUsergroupId(Integer userGroup_id);

    /**
     * 新增用户组
     * @param sysUsergroupEntity  用户组实体类
     * @return
     */
    public ResultData addUsergroup(SysUsergroupEntity sysUsergroupEntity, HttpServletRequest request);

    /**
     * 修改用户信息
     * @param sysUsergroupEntity  用户组实体类
     * @return
     */
    public ResultData updateUsergroup(SysUsergroupEntity  sysUsergroupEntity,HttpServletRequest request);

    /**
     * 根据用户组id删除用户组
     * @param userGroup_id  用户组id
     * @return
     */
    public ResultData deleteUser(Integer userGroup_id);


    /**
     * 查询该id下的子级id
     * @param userGroup_id
     * @return
     */
    public List<Integer> findNextUserGroupId(Integer userGroup_id);

    //查询用户管理左边的分组架构
    ResultData findUserManagerGroup();
}
