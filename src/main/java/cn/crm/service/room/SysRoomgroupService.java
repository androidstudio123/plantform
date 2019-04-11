package cn.crm.service.room;


import cn.crm.entity.SysRoomgroupEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;

import java.util.List;
import java.util.Set;

public interface SysRoomgroupService extends BaseService<SysRoomgroupEntity> {

    ResultData saveRoomGroup(SysRoomgroupEntity entity);

    ResultData editRoomGroup(SysRoomgroupEntity entity);

    ResultData deleteRoomGroup(Integer roomGroupId);

    List<SysRoomgroupEntity> findAdminRoomgroup(Integer adminId);

    void getAllChildrenId(Set<Integer> childrenIds, List<Integer> parentIds, Set<Integer> adminRoomGroupId);
}