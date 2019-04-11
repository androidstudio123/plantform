package cn.crm.service.repair.impl;
import cn.crm.entity.SysUserEntity;
import cn.crm.entity.repair.DefaultFunctionEntity;
import cn.crm.entity.repair.RepairFunctionEntity;
import cn.crm.entity.repair.RepairRoleEntity;
import cn.crm.entity.repair.RepairRoleFunctionEntity;
import cn.crm.mapper.repair.DefaultFunctionMapper;
import cn.crm.mapper.repair.RepairFunctionMapper;
import cn.crm.mapper.repair.RepairRoleFunctionMapper;
import cn.crm.mapper.repair.RepairRoleMapper;
import cn.crm.mapper.sys.SysUserMapper;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.repair.RepairFunctionService;
import cn.crm.util.PageUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;


@Service
public class RepairFunctionServiceImpl extends BaseServiceImpl<RepairFunctionEntity> implements RepairFunctionService {
	
	@Autowired
	private RepairFunctionMapper repairFunctionMapper;

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private RepairRoleFunctionMapper repairRoleFunctionMapper;

	@Autowired
	private RepairRoleMapper repairRoleMapper;

	@Override
	public ResultData findRepairFunction(Integer pageNum,Integer  pageSize) {
//		PageHelper.startPage(pageNum,pageSize);
		PageHelper.startPage(1,10000);
		List<RepairFunctionEntity> repairFunctionEntities = repairFunctionMapper.selectAll();
		PageInfo<RepairFunctionEntity> repairFunctionEntityPageInfo = new PageInfo<>(repairFunctionEntities);
		return new ResultData(20000,true,"查询成功",repairFunctionEntityPageInfo);
	}

	@Override
	public ResultData saveRepairFunction(RepairFunctionEntity repairFunctionEntity) {
		if(repairFunctionEntity == null){
			return new ResultData(20001,false,"请输入功能信息");
		}
		int result = repairFunctionMapper.insertSelective(repairFunctionEntity);
		if(result>0){
			return new ResultData(20000,true,"新增成功");
		}
		return new ResultData(20002,false,"新增失败，请重试");
	}

	@Override
	public ResultData updateRepairFunction(RepairFunctionEntity repairFunctionEntity) {
		if(repairFunctionEntity == null){
			return new ResultData(20001,false,"请输入功能信息");
		}
		int result = repairFunctionMapper.updateByPrimaryKeySelective(repairFunctionEntity);
		if(result>0){
			return new ResultData(20000,true,"编辑成功");
		}
		return new ResultData(20002,false,"编辑成功，请重试");
	}

	@Override
	public ResultData deleteRepairFunction(Integer id) {
		if(id<0 || id == null){
			return new ResultData(20001,false,"请选择报修功能");
		}
		int result = repairFunctionMapper.deleteByPrimaryKey(id);
		if(result>0){
			return new ResultData(20000,true,"删除成功");
		}
		return new ResultData(20002,false,"删除失败，请重试");
	}

	@Override
	public ResultData findRepairFunctionByUserId(Integer userId) {
		//根据用户id查询用户对应的角色
		SysUserEntity sysUserEntity = sysUserMapper.selectByPrimaryKey(userId);
		//根据用户id查询角色id
		Integer role_id = sysUserEntity.getRole_id();
		if(role_id == null){
			//查询角色表中的默认角色，给用户分配默认的角色
			Example example = new Example(RepairRoleEntity.class);
			example.createCriteria().andEqualTo("is_default",0);
			RepairRoleEntity repairRoleEntity = repairRoleMapper.selectOneByExample(example);
			role_id = repairRoleEntity.getRole_id();
		}
		//根据角色id查询所对应的功能
		Example example = new Example(RepairRoleFunctionEntity.class);
		example.createCriteria().andEqualTo("role_Id",role_id);
		example.orderBy("function_id");
		List<RepairRoleFunctionEntity> repairRoleFunctionEntities = repairRoleFunctionMapper.selectByExample(example);
		//根据角色功能中间表的function_id查询功能表
		List<RepairFunctionEntity> list = new ArrayList<>();
		for(int i = 0; i<repairRoleFunctionEntities.size(); i++){
			Integer function_id = repairRoleFunctionEntities.get(i).getFunction_id();
			RepairFunctionEntity repairFunctionEntity = repairFunctionMapper.selectByPrimaryKey(function_id);
			list.add(repairFunctionEntity);
			if(list.size() == repairRoleFunctionEntities.size()){
				return new ResultData(20000,true,"查询成功",list);
			}
		}
		return new ResultData(20000,true,"查询成功",null);
	}
}
