package cn.crm.mapper.sys;

import cn.crm.vo.SysUsergroupVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import cn.crm.entity.SysUsergroupEntity;

import java.util.List;

/**
 * TODO 在此加入类描述  用户组mapper
 *
 * @author MYZ
 * @version 2019-03-13 15:22:22
 * @copyright
 */
@Component
public interface SysUsergroupMapper extends Mapper<SysUsergroupEntity>, MySqlMapper<SysUsergroupEntity> {


    List<SysUsergroupVo> findUsergroupsByAdaminId( Integer adminId);

    /**
     * 根据管理员id查询对应的用户组信息
     * @param admin_id  管理员id
     * @return
     */
    public List<SysUsergroupVo> findUsergroup(@Param("admin_id") Integer admin_id);

    /**
     * 查询此分组是否有下级
     * @param userGroup_id
     * @return
     * findUsergroupByGroupid
     */
    public List<SysUsergroupEntity> findUsergroupByGroupid(@Param("userGroup_id") Integer userGroup_id);

    /**
     * 查询该id下的子级id
     * @param userGroup_id
     * @return
     */
    public List<Integer> findNextUserGroupId(@Param("userGroup_id")Integer userGroup_id);

    /**
     * 根据管理员id查询对应的学校id
     * @param admin_id
     * @return
     */
    public List<Integer> findSchoolId(@Param("admin_id") Integer admin_id);

    /**
     * 根据学校id查询对应的父类id为0的用户组id
     * @param school_id
     * @return
     */
    public  Integer findUserGroupId(Integer school_id);

}

