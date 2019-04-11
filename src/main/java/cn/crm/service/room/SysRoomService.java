package cn.crm.service.room;


import cn.crm.entity.SysRoomEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;

import java.util.List;
import java.util.Map;

public interface SysRoomService extends BaseService<SysRoomEntity> {


    ResultData saveRoom(SysRoomEntity entity, String userIds);

    ResultData editRoom(SysRoomEntity entity, String userIds);

    ResultData deleteByIds(String roomIds);

    List<SysRoomEntity> findListRoom(Map<String, Object> paramMap);
}
