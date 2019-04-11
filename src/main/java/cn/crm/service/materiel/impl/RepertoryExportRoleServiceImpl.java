package cn.crm.service.materiel.impl;

import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.SysUserEntity;
import cn.crm.entity.materiel.RepertoryExportRoleConfigEntity;
import cn.crm.entity.materiel.RepertoryExportRoleEntity;
import cn.crm.mapper.materiel.RepertoryExportRoleConfigMapper;
import cn.crm.mapper.materiel.RepertoryExportRoleMapper;
import cn.crm.mapper.sys.SysAdminMapper;
import cn.crm.mapper.sys.SysUserMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.materiel.RepertoryExportRoleService;
import cn.crm.util.AdminEntityUtil;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class RepertoryExportRoleServiceImpl extends BaseServiceImpl<RepertoryExportRoleEntity> implements RepertoryExportRoleService {
	
	@Autowired
	private RepertoryExportRoleMapper repertoryExportRoleMapper;

	@Autowired
    private RepertoryExportRoleConfigMapper roleConfigMapper;

	@Autowired
    private SysUserMapper sysUserMapper;

	@Autowired
    private SysAdminMapper sysAdminMapper;


    /**
     * 新增一个库管角色
     * @param roleName  库管角色名称
     * @param roleConfig 角色配置字符串
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData saveExportRole(String roleName, String roleConfig, HttpServletRequest request) {
        //获取当前登录的管理员信息
        SysAdminEntity sysAdminEntity = (SysAdminEntity) request.getSession().getAttribute("sysAdminEntity");
        if(sysAdminEntity == null){
            return new ResultData(ResultCode.OUTDATE.getCode(),false,ResultCode.OUTDATE.getMessage(),null);
        }
        if(roleName == null || roleConfig == null){
            return new ResultData(ResultCode.ERROR.getCode(),false,"数据不完整",null);
        }
        //1.保存出库角色名称
        RepertoryExportRoleEntity roleEntity = new RepertoryExportRoleEntity();
        roleEntity.setExport_role_name(roleName);
        roleEntity.setCreate_admin_id(sysAdminEntity.getAdmin_id());
        int insert = repertoryExportRoleMapper.insert(roleEntity);
        if(insert <= 0){
            return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage(),null);
        }
        //2.保存出库角色配置
        List<RepertoryExportRoleConfigEntity> list = new ArrayList<>();
        List<RepertoryExportRoleConfigEntity> roleConfigs = JSONArray.parseArray(roleConfig, RepertoryExportRoleConfigEntity.class);
        for (RepertoryExportRoleConfigEntity config : roleConfigs) {
            config.setExport_role_id(roleEntity.getExport_role_id());   //对应的角色ID
            list.add(config);
        }
        int i = roleConfigMapper.insertList(list);
        if(i <= 0){
            return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage(),null);
        }
        return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),null);
    }


    /**
     * 删除一个出库角色
     * @param roleId  角色ID
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData deleteExportRole(Integer roleId) {
        if(roleId == null){
            return new ResultData(ResultCode.ERROR.getCode(),false,"数据不完整",null);
        }
        //1.判断此角色是否被用户引用
        Example example = new Example(SysUserEntity.class);
        example.createCriteria().andEqualTo("export_role_id",roleId);
        List<SysUserEntity> sysUserEntities = sysUserMapper.selectByExample(example);
        Example example3 = new Example(SysAdminEntity.class);
        example.createCriteria().andEqualTo("export_role_id",roleId);
        List<SysAdminEntity> sysAdminEntities = sysAdminMapper.selectByExample(example3);

        if(sysUserEntities.size() > 0 || sysAdminEntities.size() > 0){
            return new ResultData(ResultCode.ERROR.getCode(),false,"该角色正在被使用,不能删除!",null);
        }
        //2.删除角色表里的内容
        int i = repertoryExportRoleMapper.deleteByPrimaryKey(roleId);
        if(i <= 0){
            return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage(),null);
        }
        //3.清除配置表里的内容
        Example example1 = new Example(RepertoryExportRoleConfigEntity.class);
        example1.createCriteria().andEqualTo("export_role_id",roleId);
        int i1 = roleConfigMapper.deleteByExample(example1);
        if(i1 <= 0){
            return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage(),null);
        }
        return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),null);
    }

    /**
     * 修改一个库管角色
     * @param roleId  角色ID
     * @param roleName 库管角色名称
     * @param roleConfig  角色配置字符串
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData updateExportRole(Integer roleId, String roleName,String roleConfig) {
        //1.更新角色表里的内容
        RepertoryExportRoleEntity roleEntity = new RepertoryExportRoleEntity();
        roleEntity.setExport_role_name(roleName);
        roleEntity.setExport_role_id(roleId);
        int i = repertoryExportRoleMapper.updateByPrimaryKeySelective(roleEntity);
        if(i < 0){
            return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage(),null);
        }
        //2.清除配置表里的内容
        Example example1 = new Example(RepertoryExportRoleConfigEntity.class);
        example1.createCriteria().andEqualTo("export_role_id",roleId);
        int i1 = roleConfigMapper.deleteByExample(example1);
        if(i1 < 0){
            return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage(),null);
        }
        //3.保存出库角色配置
        List<RepertoryExportRoleConfigEntity> list = new ArrayList<>();
        List<RepertoryExportRoleConfigEntity> roleConfigs = JSONArray.parseArray(roleConfig, RepertoryExportRoleConfigEntity.class);
        for (RepertoryExportRoleConfigEntity config : roleConfigs) {
            config.setExport_role_id(roleEntity.getExport_role_id());   //对应的角色ID
            list.add(config);
        }
        int i2 = roleConfigMapper.insertList(list);
        if(i2 <= 0){
            return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage(),null);
        }
        return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),null);
    }

    /**
     * 根据当前登录的管理员查询出库角色信息
     * @param request
     * @return
     */
    @Override
    public ResultData findExportRoleByAdmin(HttpServletRequest request,String exportRoleName) {
        //获取当前登录的管理员信息
        SysAdminEntity sysAdminEntity = (SysAdminEntity) request.getSession().getAttribute("sysAdminEntity");
        if(sysAdminEntity == null){
            return new ResultData(ResultCode.OUTDATE.getCode(),false,ResultCode.OUTDATE.getMessage(),null);
        }
        //查询出此管理员下的所有子级管理员
        Set<Integer> set = new HashSet<Integer>();
        List<Integer> list = new ArrayList<Integer>();
        list.add(sysAdminEntity.getAdmin_id());
        AdminEntityUtil.getAllChildAdminId(set,list);
        set.add(sysAdminEntity.getAdmin_id());
        //查询对应的角色信息
        List<Map<String, Object>> exportRoleByAdmin = repertoryExportRoleMapper.findExportRoleByAdmin(set,exportRoleName);
        return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),exportRoleByAdmin);
    }


    /**
     * 查询指定库管角色的详情
     * @param roleId 库管角色ID
     * @return
     */
    @Override
    public ResultData findExportRoleDetail(Integer roleId) {
        if (roleId == null){
            return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage(),null);
        }
        List<Map<String, Object>> exportRoleDetail = repertoryExportRoleMapper.findExportRoleDetail(roleId);

        return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),exportRoleDetail);
    }


}
