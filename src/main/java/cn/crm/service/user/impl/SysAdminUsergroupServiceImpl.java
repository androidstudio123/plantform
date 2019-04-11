package cn.crm.service.user.impl;

import cn.crm.entity.SysAdminUsergroupEntity;
import cn.crm.mapper.sys.SysAdminUsergroupMapper;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.user.SysAdminUsergroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysAdminUsergroupServiceImpl extends BaseServiceImpl<SysAdminUsergroupEntity> implements SysAdminUsergroupService {

    @Autowired
    private SysAdminUsergroupMapper sysAdminUsergroupMapper;

    @Override
    public List<Integer> findUserGroupIdsByAdminid(Integer adminId, Integer state) {
        return sysAdminUsergroupMapper.findUserGroupIdsByAdminid(adminId,state);
    }
}
