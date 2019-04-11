package cn.crm.service.sys.impl;

import cn.crm.entity.SysLogEntity;
import cn.crm.mapper.sys.SysLogMapper;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.sys.SysLogService;
import cn.crm.util.PageUtil;
import cn.crm.util.slideVerification.validate.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class SysLogServiceImpl extends BaseServiceImpl<SysLogEntity> implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;


    /**
     * 查询日志信息
     *
     * @param log_user 用户名
     * @param log_ip   请求地址
     * @param flag     用于判断根据用户名查询还是根据请求地址查询 flag:1 根据用户名 2 根据请求地址
     * @return
     */
    @Override
    public List<SysLogEntity> findAllLog(String log_user, String log_ip, Integer flag) {
        //创建查询条件
        Example example = new Example(SysLogEntity.class);
        Example.Criteria criteria = example.createCriteria();
        //判断用户名是否为空
        if (StringUtils.isNotEmpty(log_user)) {
            criteria.andLike("log_user", "%" + log_user + "%");
        } else if (StringUtils.isNotEmpty(log_ip)) {
            //判断请求ip是否为空
            criteria.andLike("log_ip", "%" + log_ip + "%");
        }
        //调用查询方法
        PageUtil.startPage("log_time", "desc");
        List<SysLogEntity> sysLogEntities = sysLogMapper.selectByExample(example);
        return sysLogEntities;
    }
}
