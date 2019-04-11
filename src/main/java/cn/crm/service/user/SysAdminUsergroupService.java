package cn.crm.service.user;


import cn.crm.entity.SysAdminUsergroupEntity;
import cn.crm.service.BaseService;

import java.util.List;
import java.util.Map;

public interface SysAdminUsergroupService extends BaseService<SysAdminUsergroupEntity> {

    List<Integer> findUserGroupIdsByAdminid(Integer adminId, Integer state);
}
