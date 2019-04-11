package cn.crm.mapper.sys;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import cn.crm.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * TODO 在此加入类描述  用户信息mapper
 * @copyright
 * @author MYZ
 * @version  2019-03-13 15:21:30
 */
@Component
public interface SysUserMapper extends Mapper<SysUserEntity>,MySqlMapper<SysUserEntity>{
    /**
     * 根据管理员id查询所有用户的信息
     * @param admin_id
     * @param user_name  可根据用户名模糊查询
     * @return
     */
    public List<SysUserEntity> findAllUser(@Param("admin_id") Integer admin_id,@Param("user_name") String user_name);

    /**
     * 根据管理员id查询对应的用户id
     * @param admin_id
     * @return
     */
    public List<Integer> finduser(@Param("admin_id") Integer admin_id);

    /**
     * 根据学校id查询用户信息
     * @param schoolId
     * @return
     */
    public List<SysUserEntity> findUserBySchoolId (@Param("schoolId") Integer schoolId);

    /**
     * 根据故障地点与故障类型,查询对应的维修工OpenId
     * @param areaId  故障区域
     * @param fixTypeId  故障类型
     * @return
     */
    List<String> findOpenIdByAreaAndOrderType(@Param("areaId") Integer areaId, @Param("fixTypeId") Integer fixTypeId);
}

