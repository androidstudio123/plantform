package cn.crm.service.sys.impl;


import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.SysRoleEntity;
import cn.crm.mapper.sys.SysAdminMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.sys.SysAdminService;
import cn.crm.util.PageUtil;
import cn.crm.util.PasswordUtil;
import cn.crm.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SysAdminServiceImpl extends BaseServiceImpl<SysAdminEntity> implements SysAdminService {

    @Autowired
    private SysAdminMapper sysAdminMapper;

    /**
     * 查找上级
     * @param childId
     * @return
     */
    @Override
    public List<SysAdminEntity> findAllParnetsByChildId(Integer childId) {
        List<SysAdminEntity> lists = new ArrayList<>();
        findAllParent(lists,childId);
        return  lists;
    }

    /**
     * 查找所有上级
     */

     protected  void findAllParent(List<SysAdminEntity> lists,Integer childId){
         SysAdminEntity sysAdminEntity = sysAdminMapper.selectByPrimaryKey(childId);
         if(sysAdminEntity.getAdmin_parentId()!=0){
             lists.add(sysAdminEntity);
             findAllParent(lists,sysAdminEntity.getAdmin_id());

         }else{
             lists.add(sysAdminEntity);
         }
     }


    /**
     * 查询所有管理员
     *
     * @return
     */
    @Override
    public List<SysAdminEntity> findAllAdmin() {
        return sysAdminMapper.selectAll();
    }


    /**
     * 查询下级
     *
     * @param parentId
     * @return
     */
    @Override
    public List<SysAdminEntity> findByParentId(Integer parentId) {
        Example example = new Example(SysAdminEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("admin_parentId", parentId);
        return sysAdminMapper.selectByExample(example);
    }

    /**
     * 根据名称查询
     *
     * @param adminName
     * @return
     */
    @Override
    public SysAdminEntity findByAdminName(String adminName) {
        Example example = new Example(SysAdminEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("admin_name", adminName);
        List<SysAdminEntity> sysAdminEntities = sysAdminMapper.selectByExample(example);
        return sysAdminEntities.size() == 0 ? null : sysAdminEntities.get(0);
    }

    /**
     * 修改用户密码
     *
     * @param admin_password 密码
     */
    @Override
    public ResultData updatePassword(String admin_password) {
        //取出用户登录后的用户信息
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        SysAdminEntity sysAdminEntity = (SysAdminEntity) principals.getPrimaryPrincipal();
        //判断获取用户信息是否为空
        if (sysAdminEntity == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage());
        }
        //判断密码是否为空
        if (StringUtils.isEmpty(admin_password)) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
        }
        //设置密码
        sysAdminEntity.setAdmin_password(admin_password);
        //对密码进行加密
        new PasswordUtil().encryptPassword(sysAdminEntity);
        //调用修改方法
        int i = sysAdminMapper.updateByPrimaryKeySelective(sysAdminEntity);
        //判断是否修改成功
        if (i > 0) {
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
        }
        return new ResultData(ResultCode.ERROR.getCode(), false, ResultCode.ERROR.getMessage());
    }

    /**
     * 重置密码
     *
     * @param admin_password 密码
     * @param admin_id       用户id
     * @return
     */
    @Override
    public ResultData resstingPassword(Integer admin_id, String admin_password) {
        //取出用户登录后的用户信息
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        SysAdminEntity sysAdminEntity = (SysAdminEntity) principals.getPrimaryPrincipal();
        //判断获取用户信息是否为空
        if (sysAdminEntity == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage());
        }
        //判断用户ID和密码是否为空
        if (admin_id == null && StringUtils.isEmpty(admin_password)) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
        }
        //根据id 查询重置用户信息
        SysAdminEntity sysAdminEntity1 = sysAdminMapper.selectByPrimaryKey(admin_id);
        //判断查询到用户信息是否为空
        if (sysAdminEntity1 == null) {
            return new ResultData(ResultCode.RETRY.getCode(), false, ResultCode.RETRY.getMessage());
        }
        //设置密码
        sysAdminEntity1.setAdmin_password(admin_password);
        //加密
        new PasswordUtil().encryptPassword(sysAdminEntity);
        //调用修改方法
        int i = sysAdminMapper.updateByPrimaryKeySelective(sysAdminEntity1);
        //判断返回结果是否修改成功
        if (i > 0) {
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
        }
        return new ResultData(ResultCode.ERROR.getCode(), false, ResultCode.ERROR.getMessage());
    }

    @Override
    public ResultData distributionExport(Integer admin_id, Integer export_role_id) {
        //根据id查询管理员信息
        SysAdminEntity sysAdminEntity = sysAdminMapper.selectByPrimaryKey(admin_id);
        sysAdminEntity.setExport_role_id(export_role_id);
        int i = sysAdminMapper.updateByPrimaryKeySelective(sysAdminEntity);
        if(i>0){
            return new ResultData(20000,true,"分配成功");
        }
        return new ResultData(20001,false,"分配失败，请重试");
    }

    /**
     * 根据管理员id查询子级创建的角色
     * @param adminId
     * @return
     */
    @Override
    public List<SysRoleEntity> findRoleByAdminAuth(Integer adminId) {
        return sysAdminMapper.findRoleByAdminAuth(adminId);
    }
}
