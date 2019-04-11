package cn.crm.mapper.repair;


import cn.crm.entity.repair.RepairRoleOrderTypeEntity;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface RepairRoleOrderTypeMapper extends Mapper<RepairRoleOrderTypeEntity>, MySqlMapper<RepairRoleOrderTypeEntity> {

    /**
     * 根据用户查询此用户所负责的工单类型
     * @param userId 用户ID
     * @return
     */
    List<Integer> findOrderTypeByUser(@Param("userId") Integer userId);
}

