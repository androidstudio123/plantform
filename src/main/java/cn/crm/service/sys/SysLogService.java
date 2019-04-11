package cn.crm.service.sys;


import cn.crm.entity.SysLogEntity;
import cn.crm.service.BaseService;

import java.util.List;

/**
* @Description: TODO  日志服务层接口
* @param {NAME}
* @Author:MYZ
*/
public interface SysLogService extends BaseService<SysLogEntity> {
    /**
     * 查询日志信息
     * @param log_user  用户名
     * @param log_ip  请求地址
     * @param flag  用于判断根据用户名查询还是根据请求地址查询 flag:1 根据用户名 2 根据请求地址
     * @return
     */
    public List<SysLogEntity> findAllLog(String log_user,String log_ip,Integer flag);
}
