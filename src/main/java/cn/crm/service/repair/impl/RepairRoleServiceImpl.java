package cn.crm.service.repair.impl;

import cn.crm.entity.SysUserEntity;
import cn.crm.entity.repair.RepairRoleAreaEntity;
import cn.crm.entity.repair.RepairRoleEntity;
import cn.crm.entity.repair.RepairRoleFunctionEntity;
import cn.crm.entity.repair.RepairRoleOrderTypeEntity;
import cn.crm.mapper.repair.RepairRoleAreaMapper;
import cn.crm.mapper.repair.RepairRoleFunctionMapper;
import cn.crm.mapper.repair.RepairRoleMapper;
import cn.crm.mapper.repair.RepairRoleOrderTypeMapper;
import cn.crm.mapper.sys.SysUserMapper;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.repair.RepairRoleAreaService;
import cn.crm.service.repair.RepairRoleFunctionService;
import cn.crm.service.repair.RepairRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;


@Service
public class RepairRoleServiceImpl extends BaseServiceImpl<RepairRoleEntity> implements RepairRoleService {
	
	@Autowired
	private RepairRoleMapper repairRoleMapper;
	@Autowired
	private RepairRoleFunctionMapper repairRoleFunctionMapper;
	@Autowired
	private RepairRoleOrderTypeMapper repairRoleOrderTypeMapper;
	@Autowired
	private RepairRoleAreaMapper repairRoleAreaMapper;

	@Autowired
	private SysUserMapper sysUserMapper;
	@Override
	public ResultData saveRepairRole(String roleName, String fids, String otids, String pids,Integer is_default,Integer role_flag) {
		//判断角色名称是否为空
		if(roleName == null || roleName == ""){
			return new ResultData(20001,false,"请输入角色名");
		}
		//判断是否存在角色名
		Example example = new Example(RepairRoleEntity.class);
		example.createCriteria().andEqualTo("role_name",roleName);
		List<RepairRoleEntity> repairRoleEntities = repairRoleMapper.selectByExample(example);
		if(repairRoleEntities.size()>0){
			return new ResultData(20002,false,"角色名称已经存在，请勿重复添加");
		}
		//判断是否是默认角色，如果是默认角色并且启用默认角色，将其他的默认角色禁用
			//根据is_default查询角色
        if(is_default == 0){
            Example defaultRoleExample = new Example(RepairRoleEntity.class);
            defaultRoleExample.createCriteria().andEqualTo("is_default",is_default);
            List<RepairRoleEntity> defaultRoleEntitys = repairRoleMapper.selectByExample(defaultRoleExample);
            if(defaultRoleEntitys.size()>0){
                //如果存在则禁用掉其他默认角色
                return new ResultData(20005,false,"已有默认角色，请勿重复添加");
            }
        }
		//添加到角色表
		RepairRoleEntity repairRoleEntity = new RepairRoleEntity();
		repairRoleEntity.setRole_name(roleName);
		repairRoleEntity.setIs_default(is_default);
		repairRoleEntity.setRole_flag(role_flag);
		int result = repairRoleMapper.insertSelective(repairRoleEntity);
		if(result < 1){
			return new ResultData(20006,false,"新增角色失败，请重试");
		}
//		Example example1 = new Example(RepairRoleEntity.class);
//		example1.createCriteria().andEqualTo("role_name",roleName);
//		RepairRoleEntity repairRoleEntity1 = repairRoleMapper.selectOneByExample(example1);
		//新增角色功能
		RepairRoleFunctionEntity repairRoleFunctionEntity = new RepairRoleFunctionEntity();
		List<RepairRoleFunctionEntity> list = new ArrayList<>();
		if(fids!=null && fids!=""){
			//分割功能id
			String[] fid = fids.split(",");
			if(fid.length>0){
				for(int i = 0; i < fid.length; i++){
					repairRoleFunctionEntity.setRole_Id(repairRoleEntity.getRole_id());
					repairRoleFunctionEntity.setFunction_id(Integer.valueOf(fid[i]));
					int res = repairRoleFunctionMapper.insertSelective(repairRoleFunctionEntity);
					if(res < 1){
						return new ResultData(20007,false,"新增角色功能失败，请重试");
					}
				}
			}
		}
		//新增工单类型
		RepairRoleOrderTypeEntity repairRoleOrderTypeEntity = new RepairRoleOrderTypeEntity();
		List<RepairRoleOrderTypeEntity> listOrderType = new ArrayList<>();
		if(otids!=null && otids != ""){
			//分割工单类型id
			String[] otid = otids.split(",");
			if(otid.length>0){
				for(int i = 0; i < otid.length; i++){
					repairRoleOrderTypeEntity.setRole_id(repairRoleEntity.getRole_id());
					repairRoleOrderTypeEntity.setOrder_type_id(Integer.valueOf(otid[i]));
					int resOrderType = repairRoleOrderTypeMapper.insertSelective(repairRoleOrderTypeEntity);
					if(resOrderType < 1){
						return new ResultData(20008,false,"新增工单类型失败");
					}
				}
			}
		}
		//新增工单区域
		RepairRoleAreaEntity repairRoleAreaEntity= new RepairRoleAreaEntity();
		List<RepairRoleAreaEntity> listArea = new ArrayList<>();
		if(pids!=null && pids!=""){
			//分割工单区域类型id
			String[] pid = pids.split(",");
			if(pid.length>0){
				for(int i = 0; i < pid.length; i++){
					repairRoleAreaEntity.setRole_id(repairRoleEntity.getRole_id());
					repairRoleAreaEntity.setArea_id(Integer.valueOf(pid[i]));
					int resArea = repairRoleAreaMapper.insertSelective(repairRoleAreaEntity);
					if(resArea < 1){
						return new ResultData(20009,false,"新增工单区域失败");
					}
				}
			}
		}
		return new ResultData(20000,true,"新增角色成功");
	}

	@Override
	@Transactional
	public ResultData updateRepairRole(Integer id,String roleName, String fids, String otids, String pids,Integer role_flag,Integer is_default) {
		//判断角色名称是否为空
		if(roleName == null || roleName == ""){
			return new ResultData(20001,false,"请输入角色名");
		}
		//判断是否存在角色名
		Example example = new Example(RepairRoleEntity.class);
		example.createCriteria().andEqualTo("role_name",roleName);
		List<RepairRoleEntity> repairRoleEntities = repairRoleMapper.selectByExample(example);
		RepairRoleEntity repairRoleEntity1 = repairRoleMapper.selectByPrimaryKey(id);
		if(repairRoleEntities.size()>0 && !repairRoleEntity1.getRole_name().equals(roleName)){
			return new ResultData(20002,false,"角色名称已经存在，请勿编辑成已有的角色");
		}
		RepairRoleEntity repairRoleEntity = repairRoleMapper.selectByPrimaryKey(id);
		if(is_default == 0){
			if(repairRoleEntity.getIs_default() == 0){
				//根据id编辑角色表
				repairRoleEntity.setRole_name(roleName);
				repairRoleEntity.setIs_default(is_default);
				int result = repairRoleMapper.updateByPrimaryKeySelective(repairRoleEntity);
				if (result < 1) {
					return new ResultData(20006, false, "编辑角色失败，请重试");
				}
			}else{
				Example example1 = new Example(RepairRoleEntity.class);
				example1.createCriteria().andEqualTo("is_default",0);
				List<RepairRoleEntity> repairRoleEntities1 = repairRoleMapper.selectByExample(example1);
				if(repairRoleEntities1.size()>0){
					return new ResultData(20007,false,"已存在默认角色，不能修改成默认角色");
				}
			}
		}
		repairRoleEntity.setRole_name(roleName);
		repairRoleEntity.setIs_default(is_default);
		int result = repairRoleMapper.updateByPrimaryKeySelective(repairRoleEntity);
		if (result < 1) {
			return new ResultData(20006, false, "编辑角色失败，请重试");
		}
		//根据id查询角色中能中间表中是否存在数据，如果存在数据则先删除中间表中的数据
		Example roleFunExample = new Example(RepairRoleFunctionEntity.class);
		roleFunExample.createCriteria().andEqualTo("role_Id", id);
		List<RepairRoleFunctionEntity> repairRoleFunctionEntities = repairRoleFunctionMapper.selectByExample(roleFunExample);
		if (repairRoleFunctionEntities.size() > 0) {
			//编辑角色功能中间表时先根据角色id删除中间表的数据
			int deleteRoleFun = repairRoleFunctionMapper.deleteByExample(roleFunExample);
			if (deleteRoleFun < 1) {
				return new ResultData(20006, false, "删除角色功能失败，请重试");
			}
			//删除之后再新增角色功能
			RepairRoleFunctionEntity repairRoleFunctionEntity = new RepairRoleFunctionEntity();
			List<RepairRoleFunctionEntity> list = new ArrayList<>();
			if (fids != null && fids != "") {
				//分割功能id
				String[] fid = fids.split(",");
				if (fid.length >0) {
					for (int i = 0; i < fid.length; i++) {
						repairRoleFunctionEntity.setRole_Id(id);
						repairRoleFunctionEntity.setFunction_id(Integer.valueOf(fid[i]));
						int res = repairRoleFunctionMapper.insertSelective(repairRoleFunctionEntity);
						if (res < 1) {
							return new ResultData(20007, false, "编辑角色功能失败，请重试");
						}
					}
				}
			}
		} else {
			//如果中间表中有数据并且用户选择的有角色功能，则直接添加角色功能表
			RepairRoleFunctionEntity repairRoleFunctionEntity = new RepairRoleFunctionEntity();
			List<RepairRoleFunctionEntity> list = new ArrayList<>();
			if (fids != null && fids != "") {
				//分割功能id
				String[] fid = fids.split(",");
				if (fid.length >0) {
					for (int i = 0; i < fid.length; i++) {
						repairRoleFunctionEntity.setRole_Id(id);
						repairRoleFunctionEntity.setFunction_id(Integer.valueOf(fid[i]));
						int res = repairRoleFunctionMapper.insertSelective(repairRoleFunctionEntity);
						if (res < 1) {
							return new ResultData(20007, false, "编辑角色功能失败，请重试");
						}
					}
				}
			}
		}
		//根据id查询角色工单类型中间表中是否存在数据，如果存在数据则先删除中间表中的数据
		Example roleTypeExample = new Example(RepairRoleOrderTypeEntity.class);
		roleTypeExample.createCriteria().andEqualTo("role_id", id);
		List<RepairRoleOrderTypeEntity> repairRoleOrderTypeEntities = repairRoleOrderTypeMapper.selectByExample(roleTypeExample);
		//判断角色工单类型表中是否存在数据，如果存在数据先删除中间表中的数据
		if (repairRoleOrderTypeEntities.size() > 0) {
			//编辑角色工单类型中间表时先根据角色id删除中间表的数据
			int deleteRoleOrderType = repairRoleOrderTypeMapper.deleteByExample(roleTypeExample);
			if (deleteRoleOrderType < 1) {
				return new ResultData(20007, false, "删除角色订单类型失败，请重试");
			}
			//添加角色工单类型
			RepairRoleOrderTypeEntity repairRoleOrderTypeEntity = new RepairRoleOrderTypeEntity();
			List<RepairRoleOrderTypeEntity> listOrderType = new ArrayList<>();
			if (otids != null && otids != "") {
				//分割工单类型id
				String[] otid = otids.split(",");
				if (otid.length > 0) {
					for (int i = 0; i < otid.length; i++) {
						repairRoleOrderTypeEntity.setRole_id(id);
						repairRoleOrderTypeEntity.setOrder_type_id(Integer.valueOf(otid[i]));
						int resOrderType = repairRoleOrderTypeMapper.insertSelective(repairRoleOrderTypeEntity);
						if (resOrderType < 1) {
							return new ResultData(20008, false, "编辑工单类型失败");
						}
					}
				}
			}
		} else {
			//如果中间表中没有数据并且传入的有数据，则直接添加数据
			//添加角色工单类型
			RepairRoleOrderTypeEntity repairRoleOrderTypeEntity = new RepairRoleOrderTypeEntity();
			List<RepairRoleOrderTypeEntity> listOrderType = new ArrayList<>();
			if (otids != null && otids != "") {
				//分割工单类型id
				String[] otid = otids.split(",");
				if (otid.length > 0) {
					for (int i = 0; i < otid.length; i++) {
						repairRoleOrderTypeEntity.setRole_id(id);
						repairRoleOrderTypeEntity.setOrder_type_id(Integer.valueOf(otid[i]));
						int resOrderType = repairRoleOrderTypeMapper.insertSelective(repairRoleOrderTypeEntity);
						if (resOrderType < 1) {
							return new ResultData(20008, false, "编辑工单类型失败");
						}
					}
				}
			}
		}
		//根据id查询角色区域类型中间表中是否存在数据，如果存在数据则先删除中间表中的数据
		Example roleAreaExample = new Example(RepairRoleAreaEntity.class);
		roleAreaExample.createCriteria().andEqualTo("role_id", id);
		List<RepairRoleAreaEntity> repairRoleAreaEntities = repairRoleAreaMapper.selectByExample(roleAreaExample);
		if (repairRoleAreaEntities.size() > 0) {
			//编辑角色工单类型中间表时先根据角色id删除中间表的数据
			int deleteRoleArea = repairRoleAreaMapper.deleteByExample(roleAreaExample);
			if (deleteRoleArea < 1) {
				return new ResultData(20007, false, "删除角色区域失败，请重试");
			}
			//新增工单区域
			RepairRoleAreaEntity repairRoleAreaEntity = new RepairRoleAreaEntity();
			List<RepairRoleAreaEntity> listArea = new ArrayList<>();
			if (pids != null && pids != "") {
				//分割工单区域类型id
				String[] pid = pids.split(",");
				if (pid.length > 0) {
					for (int i = 0; i < pid.length; i++) {
						repairRoleAreaEntity.setRole_id(id);
						repairRoleAreaEntity.setArea_id(Integer.valueOf(pid[i]));
						int resArea = repairRoleAreaMapper.insertSelective(repairRoleAreaEntity);
						if (resArea < 1) {
							return new ResultData(20009, false, "编辑工单区域失败");
						}
					}
				}
			}
		} else {
			//新增工单区域
			RepairRoleAreaEntity repairRoleAreaEntity = new RepairRoleAreaEntity();
			List<RepairRoleAreaEntity> listArea = new ArrayList<>();
			if (pids != null && pids != "") {
				//分割工单区域类型id
				String[] pid = pids.split(",");
				if (pid.length > 0) {
					for (int i = 0; i < pid.length; i++) {
						repairRoleAreaEntity.setRole_id(id);
						repairRoleAreaEntity.setArea_id(Integer.valueOf(pid[i]));
						int resArea = repairRoleAreaMapper.insertSelective(repairRoleAreaEntity);
						if (resArea < 1) {
							return new ResultData(20009, false, "编辑工单区域失败");
						}
					}
				}
			}
		}
		return new ResultData(20000,true,"编辑成功");
	}

	@Override
	@Transactional
	public ResultData deleteRepair(Integer id) {
		if(id<0 || id == null){
			return new ResultData(20001,false,"请选择报修角色");
		}
		//根据角色id查询用户表中是否存在数据，如果有提示不让删除
		Example userExample = new Example(SysUserEntity.class);
		userExample.createCriteria().andEqualTo("role_id",id);
		List<SysUserEntity> sysUserEntities = sysUserMapper.selectByExample(userExample);
		if(sysUserEntities.size()>0){
			return new ResultData(20008,false,"该角色下有对应的用户，删除失败");
		}
		//删除角色
		int deleteRepairRole = repairRoleMapper.deleteByPrimaryKey(id);
		if(deleteRepairRole<1){
			return new ResultData(20002,false,"删除失败，请重试");
		}
		//删除角色功能
		Example repairRoleFunExample = new Example(RepairRoleFunctionEntity.class);
		repairRoleFunExample.createCriteria().andEqualTo("role_Id",id);
		List<RepairRoleFunctionEntity> repairRoleFunctionEntities = repairRoleFunctionMapper.selectByExample(repairRoleFunExample);
		if(repairRoleFunctionEntities.size()>0){
			for (int i = 0;i<repairRoleFunctionEntities.size(); i++ ){
				int delete = repairRoleFunctionMapper.delete(repairRoleFunctionEntities.get(i));
				if(delete<1){
					return new ResultData(20003,false,"删除角色功能失败");
				}
			}
		}
		//删除角色工单类型
		Example repairRoleOrderTypeExample = new Example(RepairRoleOrderTypeEntity.class);
		repairRoleOrderTypeExample.createCriteria().andEqualTo("role_id",id);
		List<RepairRoleOrderTypeEntity> repairRoleOrderTypeEntities = repairRoleOrderTypeMapper.selectByExample(repairRoleOrderTypeExample);
		if(repairRoleOrderTypeEntities.size()>0){
			for (int i = 0; i<repairRoleOrderTypeEntities.size(); i++){
				int deleteRepairRoleOrderType = repairRoleOrderTypeMapper.delete(repairRoleOrderTypeEntities.get(i));
				if(deleteRepairRoleOrderType<1){
					return new ResultData(20004,false,"删除角色工单类型失败,请重试");
				}
			}
		}
		//删除角色工单区域
		Example repairRoleAreaExample = new Example(RepairRoleAreaEntity.class);
		repairRoleAreaExample.createCriteria().andEqualTo("role_id",id);
		List<RepairRoleAreaEntity> repairRoleAreaEntities = repairRoleAreaMapper.selectByExample(repairRoleAreaExample);
		if(repairRoleAreaEntities.size()>0){
			for (int i = 0; i<repairRoleAreaEntities.size(); i++){
				int deleteRepairRoleArea = repairRoleAreaMapper.delete(repairRoleAreaEntities.get(i));
				if(deleteRepairRoleArea<1){
					return new ResultData(20004,false,"删除角色工单类型失败,请重试");
				}
			}
		}
		return new ResultData(20000,true,"删除成功");
	}
}
