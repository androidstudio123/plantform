package cn.crm.mapper.sys;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import cn.crm.entity.SysRoomEntity;

import java.util.List;
import java.util.Map;


public interface SysRoomMapper extends Mapper<SysRoomEntity>,MySqlMapper<SysRoomEntity>{



    List<SysRoomEntity> findListRoom(Map<String, Object> paramMap);
}

