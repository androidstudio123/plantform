package cn.crm.service.user.impl;


import cn.crm.entity.*;
import cn.crm.mapper.sys.SysAdminUsergroupMapper;
import cn.crm.mapper.sys.SysUserMapper;
import cn.crm.mapper.sys.SysUsergroupMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.user.SysUsergroupService;
import cn.crm.util.AdminEntityUtil;
import cn.crm.vo.SysUsergroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @param {NAME}
 * @Description: TODO  用户组服务层实现类
 * @Author:MYZ
 */
@Service
public class SysUsergroupServiceImpl extends BaseServiceImpl<SysUsergroupEntity> implements SysUsergroupService {

    @Autowired
    private SysUsergroupMapper sysUsergroupMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysAdminUsergroupMapper sysAdminUsergroupMapper;


    /**
     * 根据管理员id查询对应的用户组信息
     * MYZ
     *
     * @param admin_id 管理员id
     * @return
     */
    @Override
    public List<SysUsergroupVo> findUsergroup(Integer admin_id) {
        return sysUsergroupMapper.findUsergroup(admin_id);
    }

    /**
     * 根据用户组id查询这条用户组详细信息
     * MYZ
     *
     * @param userGroup_id 用户组id
     * @return
     */
    @Override
    public ResultData findUsergroupByUsergroupId(Integer userGroup_id) {
        //判断传过来的分组id是否为空
        if (userGroup_id == null) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage(), null);
        }
        //调用查询方法
        SysUsergroupEntity sysUsergroupEntity = sysUsergroupMapper.selectByPrimaryKey(userGroup_id);
        return new ResultData(ResultCode.SUCCESS.getCode(), false, ResultCode.SUCCESS.getMessage(), sysUsergroupEntity);
    }


    /**
     * 新增用户组
     * MYZ
     *
     * @param sysUsergroupEntity 用户组实体类
     * @return
     */
    @Override
    @Transactional
    public ResultData addUsergroup(SysUsergroupEntity sysUsergroupEntity, HttpServletRequest request) {
        //从session中获取用户信息
        SysAdminEntity sysAdminEntity1 = (SysAdminEntity) request.getSession().getAttribute("sysAdminEntity");
        //判断获取信息是否为空
        if (sysAdminEntity1 == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage());
        }
        //判断传过来的实体类是否为空
        if (sysUsergroupEntity == null) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
        }
        //获取传递过来的用户组名
        String userGroup_name = sysUsergroupEntity.getUserGroup_name();
        Integer userGroup_id = sysUsergroupEntity.getUserGroup_id();
        //判断用户名是否为空
        if (userGroup_name == null || "".equals(userGroup_name.trim())){
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(),false,ResultCode.EMPTYPARAMS.getMessage());
        }
        //判断用户组名是否存在
        Example example =new Example(SysUsergroupEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userGroup_name",userGroup_name);
        List<SysUsergroupEntity> sysUsergroupEntities = sysUsergroupMapper.selectByExample(example);
        //判断查询数据是否为空
        if (sysUsergroupEntities.size()>0 && sysUsergroupEntities != null){
            return new ResultData(ResultCode.ALREADYEXISTS.getCode(),false,ResultCode.ALREADYEXISTS.getMessage());
        }
        //设置创建时间
        sysUsergroupEntity.setUserGroup_createTime(new Date());
        //设置创建人id
        sysUsergroupEntity.setUserGroup_createId(sysAdminEntity1.getAdmin_id());
        //调用新增方法
        int insert = sysUsergroupMapper.insert(sysUsergroupEntity);
        //判断返回结果
        if (insert > 0) {
            Integer userGroupId = sysUsergroupEntity.getUserGroup_id();
            // 当前登录adminID 以及其所有上级ID
            Set<Integer> adminIds = new HashSet<>();
            adminIds.add(sysAdminEntity1.getAdmin_id());
            Integer parentId = sysAdminEntity1.getAdmin_parentId() == null ? 0 : sysAdminEntity1.getAdmin_parentId();

            if (parentId > 0) {
                adminIds.add(parentId);
                AdminEntityUtil.getAllParentAdminId(adminIds, parentId);
            }
            List<SysAdminUsergroupEntity> list = new ArrayList<>();
            //定义state状态为1
            int state =1;
            for (Integer adminId : adminIds) {
                if (adminId == null || adminId == 0) {
                    continue;
                }
                //创建一个管理员和用户组中间表实体类
                SysAdminUsergroupEntity sysAdminUsergroupEntity = new SysAdminUsergroupEntity(adminId, userGroupId,state);
                list.add(sysAdminUsergroupEntity);
            }
            //判断list
            if (list.size() > 0) {
                //调用新增方法
                int insert1 = sysAdminUsergroupMapper.insertList(list);
                //判断返回结果
                if (insert1 > 0) {
                    return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
                }
                return new ResultData(ResultCode.RETRY.getCode(), false, ResultCode.RETRY.getMessage());
            }
        }
        return new ResultData(ResultCode.ERROR.getCode(), false, ResultCode.ERROR.getMessage());
    }

    /**
     * 修改用户组信息
     * MYZ
     *
     * @param sysUsergroupEntity 用户组实体类
     * @return
     */
    @Override
    public ResultData updateUsergroup(SysUsergroupEntity sysUsergroupEntity, HttpServletRequest request) {
        //从session中获取用户信息
        SysAdminEntity sysAdminEntity1 = (SysAdminEntity) request.getSession().getAttribute("sysAdminEntity");
        //判断获取信息是否为空
        if (sysAdminEntity1 == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage());
        }
        //判断传过来的实体类是否为空
        if (sysUsergroupEntity == null) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
        }
        //设置修改时间
        sysUsergroupEntity.setUserGroup_updateTime(new Date());
        //设置修改人id
        sysUsergroupEntity.setUserGroup_updateId(sysAdminEntity1.getAdmin_id());
        //调用修改方法
        int i = sysUsergroupMapper.updateByPrimaryKeySelective(sysUsergroupEntity);
        //判断返回结果
        if (i > 0) {
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
        }
        return new ResultData(ResultCode.ERROR.getCode(), false, ResultCode.ERROR.getMessage());
    }

    /**
     * 根据用户组id删除用户组
     * MYZ
     *
     * @param userGroup_id 用户组id
     * @return
     */
    @Override
    @Transactional
    public ResultData deleteUser(Integer userGroup_id) {
        //判断用户组id是否为空
        if (userGroup_id == null) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
        }
        //查询该用户组id下是否有下级
        List<SysUsergroupEntity> usergroupEntityList = sysUsergroupMapper.findUsergroupByGroupid(userGroup_id);
        //判断查询数据是否为空
        if (usergroupEntityList != null && usergroupEntityList.size() > 0) {
            return new ResultData(ResultCode.RETRY.getCode(), false, ResultCode.RETRY.getMessage());
        }
        //查询该用户组下面是否有用户信息
        Example example = new Example(SysUserEntity.class);
        example.createCriteria().andEqualTo("userGroup_id", userGroup_id);
        //调用查询方法
        List<SysUserEntity> sysUserEntities = sysUserMapper.selectByExample(example);
        //判断返回信息
        if (sysUserEntities.size() > 0 && sysUserEntities != null) {
            return new ResultData(ResultCode.RETRY.getCode(), false, ResultCode.RETRY.getMessage());
        }
        //调用删除方法
        int i = sysUsergroupMapper.deleteByPrimaryKey(userGroup_id);
        //判断返回结果
        if (i > 0) {
            //根据用户组id查询用户组和管理员中间表
            Example example1 = new Example(SysAdminUsergroupEntity.class);
            example1.createCriteria().andEqualTo("userGroup_id", userGroup_id);
            //.调用查询方法
            List<SysAdminUsergroupEntity> sysAdminUsergroupEntities = sysAdminUsergroupMapper.selectByExample(example1);
            //判断查询结果
            if (sysUserEntities.size() > 0 && sysUserEntities != null) {
                //调用删除方法
                int i1 = sysAdminUsergroupMapper.deleteByExample(example1);
                //判断返回结果
                if (i1 > 0) {
                    return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
                }
                return new ResultData(ResultCode.ERROR.getCode(), false, ResultCode.ERROR.getMessage());
            }
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
        }
        return new ResultData(ResultCode.ERROR.getCode(), false, ResultCode.ERROR.getMessage());
    }


    /**
     * 查询该id下的子级id
     *
     * @param userGroup_id
     * @return
     */
    @Override
    public List<Integer> findNextUserGroupId(Integer userGroup_id) {
        return sysUsergroupMapper.findNextUserGroupId(userGroup_id);
    }

    @Override
    public ResultData findUserManagerGroup() {
        List<SysUsergroupEntity> sysUsergroupEntities = sysUsergroupMapper.selectAll();
        return new ResultData(20000,true,"查询成功",sysUsergroupEntities);
    }

}
