package cn.crm.mapper.repair;

import cn.crm.entity.repair.RepairRoleAreaEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

@Component
public interface RepairRoleAreaMapper extends Mapper<RepairRoleAreaEntity>,MySqlMapper<RepairRoleAreaEntity>{


    /**
     * 根据用户查询此用户所负责的区域
     * @param userId  用户ID
     * @return
     */
    List<Integer> findRoleAreaByUser(@Param("userId") Integer userId);

}

