package cn.crm.mapper.sys;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import cn.crm.entity.SysAdminRoomgroupEntity;

import java.util.List;

public interface SysAdminRoomgroupMapper extends Mapper<SysAdminRoomgroupEntity>,MySqlMapper<SysAdminRoomgroupEntity>{


//    List<Integer> findRoomGroupIdsByAdminId(@Param("adminId") Integer adminId,@Param("state") Integer state);
}

