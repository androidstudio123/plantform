package cn.crm.service.sys;


import cn.crm.entity.SysFuncEntity;
import cn.crm.entity.SysRoleFuncEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;

import java.util.List;

public interface SysRoleFuncService extends BaseService<SysRoleFuncEntity> {
    List<SysFuncEntity> findFunByRoleid(Integer roleId);

    /**
     * 给角色授权
     */
    public ResultData addFunByRoleid(String text);

}
