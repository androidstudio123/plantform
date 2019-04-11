package cn.crm.mapper.materiel;


import cn.crm.entity.materiel.RepertoryExportRoleEntity;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RepertoryExportRoleMapper extends Mapper<RepertoryExportRoleEntity>,MySqlMapper<RepertoryExportRoleEntity> {

    /**
     * 根据管理员id查询所有的库管角色信息
     * @param set
     * @return
     */
    List<Map<String,Object>> findExportRoleByAdmin(@Param("set") Set<Integer> set,@Param("exportRoleName") String exportRoleName);

    /**
     * 查询指定库管角色的详情
     * @param roleId 库管角色ID
     * @return
     */
    List<Map<String,Object>> findExportRoleDetail(@Param("roleId") Integer roleId);


    List<Map<String,Object>> findExportRoleByAdmind(@Param("set") Set<Integer> set);

    /**
     * 根据管理员ID查询其拥有的出库角色
     * @param set 管理员ID集合
     * @return
     */
    List<RepertoryExportRoleEntity> findExportRoleByAdminId(@Param("set") Set<Integer> set);
}

