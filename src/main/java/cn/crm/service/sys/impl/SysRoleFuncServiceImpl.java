package cn.crm.service.sys.impl;


import cn.crm.entity.SysFuncEntity;
import cn.crm.entity.SysRoleFuncEntity;
import cn.crm.mapper.sys.SysRoleFuncMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.sys.SysRoleFuncService;
import cn.crm.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SysRoleFuncServiceImpl extends BaseServiceImpl<SysRoleFuncEntity> implements SysRoleFuncService {

    @Autowired
    private SysRoleFuncMapper sysRoleFuncMapper;

    /**
     * 给角色授权
     */
    @Override
    @Transactional
    public ResultData addFunByRoleid(String text) {
        if (StringUtils.isEmpty(text)) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage(), null);
        }
        List<SysRoleFuncEntity> list = JSONArray.parseArray(text, SysRoleFuncEntity.class);
        //获取角色id
        Integer role_id = list.get(0).getRole_id();
        //先清空之前的权限
        Example example = new Example(SysRoleFuncEntity.class);
        example.createCriteria().andEqualTo("role_id", role_id);
        List<SysRoleFuncEntity> sysRoleFuncEntities = sysRoleFuncMapper.selectByExample(example);
        //判断查询是否为空
        if (sysRoleFuncEntities.size() > 0 && sysRoleFuncEntities != null) {
            int i1 = sysRoleFuncMapper.deleteByExample(example);
            if (i1 < 0) {
                return new ResultData(ResultCode.ERROR.getCode(), false, ResultCode.ERROR.getMessage(), null);
            }
        }
        //调用新增方法
        int i = sysRoleFuncMapper.insertList(list);
        if (i > 0) {
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), null);
        }
        return new ResultData(ResultCode.ERROR.getCode(), true, ResultCode.ERROR.getMessage(), null);
    }

    @Override
    public List<SysFuncEntity> findFunByRoleid(Integer roleId) {
        List<SysFuncEntity> sysFuncEntities = sysRoleFuncMapper.findFunByRoleid(roleId);
        return sysFuncEntities;
    }
}
