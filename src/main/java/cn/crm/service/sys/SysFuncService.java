package cn.crm.service.sys;


import cn.crm.entity.SysFuncEntity;
import cn.crm.service.BaseService;

import java.util.List;
import java.util.Map;

public interface SysFuncService extends BaseService<SysFuncEntity> {
    //查询登录人菜单
    List<SysFuncEntity> findAllSysFun(Integer adminId);
    List<SysFuncEntity> findFunsByAdminId(Map<String,String> map);


}
