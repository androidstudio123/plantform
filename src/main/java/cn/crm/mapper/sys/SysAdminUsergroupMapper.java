package cn.crm.mapper.sys;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import cn.crm.entity.SysAdminUsergroupEntity;

import java.util.List;

@Component
public interface SysAdminUsergroupMapper extends Mapper<SysAdminUsergroupEntity>, MySqlMapper<SysAdminUsergroupEntity> {
    /**
     * 根据管理员id查询对应的用户组id
     * @param admin_id
     * @return
     */

    public List<Integer> findUserGroupIds(@Param("admin_id") Integer admin_id);

    List<Integer> findUserGroupIdsByAdminid(@Param("adminId") Integer adminId, @Param("state") Integer state);
}

