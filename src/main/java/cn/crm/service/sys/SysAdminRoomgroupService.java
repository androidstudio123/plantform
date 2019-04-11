package cn.crm.service.sys;


import cn.crm.entity.SysAdminRoomgroupEntity;
import cn.crm.entity.SysRoomgroupEntity;
import cn.crm.service.BaseService;

import java.util.List;

public interface SysAdminRoomgroupService extends BaseService<SysAdminRoomgroupEntity> {


    List<SysRoomgroupEntity> findAdminRoomgroup(Integer adminId);

    Integer authorizationRoomGroup(Integer adminId,String roomGroups);

//    List<Integer> findRoomGroupIdsByAdminId(Integer adminId,Integer state);

}
