package cn.crm.service.materiel.impl;

import cn.crm.entity.materiel.*;
import cn.crm.mapper.materiel.*;
import cn.crm.entity.materiel.RepairGoodsImportEntity;
import cn.crm.entity.materiel.RepairGoodsRepertoryTypeEntity;
import cn.crm.entity.materiel.RepairRepertoryAdminConfigEntity;
import cn.crm.mapper.materiel.RepairGoodsImportMapper;
import cn.crm.mapper.materiel.RepairGoodsRepertoryTypeMapper;
import cn.crm.mapper.materiel.RepairRepertoryAdminConfigMapper;
import cn.crm.entity.SysUserEntity;
import cn.crm.entity.materiel.RepairGoodsImportEntity;
import cn.crm.entity.materiel.RepairGoodsRepertoryTypeEntity;
import cn.crm.entity.materiel.RepairRepertoryAdminConfigEntity;
import cn.crm.mapper.materiel.RepairGoodsImportMapper;
import cn.crm.mapper.materiel.RepairGoodsRepertoryTypeMapper;
import cn.crm.mapper.materiel.RepairRepertoryAdminConfigMapper;
import cn.crm.mapper.sys.SysUserMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.materiel.RepairGoodsImportService;
import cn.crm.service.materiel.RepairGoodsRepertoryTypeService;
import cn.crm.util.AdminEntityUtil;
import cn.crm.util.PageUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;


@Service
public class RepairGoodsRepertoryTypeServiceImpl extends BaseServiceImpl<RepairGoodsRepertoryTypeEntity> implements RepairGoodsRepertoryTypeService {
	
	@Autowired
	private RepairGoodsRepertoryTypeMapper repairGoodsRepertoryTypeMapper;

	@Autowired
	private RepairRepertoryAdminConfigMapper repairRepertoryAdminConfigMapper;

	@Autowired
	private RepertoryExportRoleMapper repertoryExportRoleMapper;

	@Autowired
	private RepertoryExportRoleConfigMapper repertoryExportRoleConfigMapper;


	@Autowired
	private SysUserMapper sysUserMapper;

	@Override
	public ResultData findRepairGoodsRepertoryTypeAll(Integer pageNum,Integer pageSize,String repertory_type_name) {
		if(repertory_type_name != null || repertory_type_name != ""){
			PageUtil.startPage();
			Example example = new Example(RepairGoodsRepertoryTypeEntity.class);
			example.createCriteria().andLike("repertory_type_name", "%"+repertory_type_name+"%");
			List<RepairGoodsRepertoryTypeEntity> repairGoodsRepertoryTypeEntities = repairGoodsRepertoryTypeMapper.selectByExample(example);
			return new ResultData(20000,true,"查询成功",new PageInfo<>(repairGoodsRepertoryTypeEntities));
		}
		PageUtil.startPage();
		return new ResultData(20000,true,"查询成功",new PageInfo<>(repairGoodsRepertoryTypeMapper.selectAll()));
	}

	@Override
	public ResultData saveRepairGoodsRepertoryType(RepairGoodsRepertoryTypeEntity repairGoodsRepertoryTypeEntity) {
		if(repairGoodsRepertoryTypeEntity == null){
			return new ResultData(20001,false,"库类型信息不能为空，请重试");
		}
		Example example = new Example(RepairGoodsRepertoryTypeEntity.class);
		example.createCriteria().andEqualTo("repertory_type_name",repairGoodsRepertoryTypeEntity.getRepertory_type_name());
		List<RepairGoodsRepertoryTypeEntity> repairGoodsRepertoryTypeEntities = repairGoodsRepertoryTypeMapper.selectByExample(example);
		if(repairGoodsRepertoryTypeEntities.size()>0){
			return new ResultData(20003,false,"库类型已存在，请勿重复添加");
		}
		int i = repairGoodsRepertoryTypeMapper.insertSelective(repairGoodsRepertoryTypeEntity);
		if(i>0){
			return new ResultData(20000,true,"新增成功");
		}
		return new ResultData(20002,false,"网络错误，请重试");
	}

	@Override
	public ResultData updateRepairGoodsRepertoryType(RepairGoodsRepertoryTypeEntity repairGoodsRepertoryTypeEntity) {
		if(repairGoodsRepertoryTypeEntity == null){
			return new ResultData(20001,false,"库类型信息不能为空，请重试");
		}
		Example example = new Example(RepairGoodsRepertoryTypeEntity.class);
		example.createCriteria().andEqualTo("repertory_type_name",repairGoodsRepertoryTypeEntity.getRepertory_type_name());
		List<RepairGoodsRepertoryTypeEntity> repairGoodsRepertoryTypeEntities = repairGoodsRepertoryTypeMapper.selectByExample(example);
		RepairGoodsRepertoryTypeEntity repairGoodsRepertoryTypeEntity1 = repairGoodsRepertoryTypeMapper.selectByPrimaryKey(repairGoodsRepertoryTypeEntity.getRepertory_type_id());
		if(repairGoodsRepertoryTypeEntities.size()>0 && !repairGoodsRepertoryTypeEntity1.getRepertory_type_name().equals(repairGoodsRepertoryTypeEntity.getRepertory_type_name())){
			return new ResultData(20003,false,"库类型已存在，请勿修改成该库类型");
		}
		int i = repairGoodsRepertoryTypeMapper.updateByPrimaryKeySelective(repairGoodsRepertoryTypeEntity);
		if(i>0){
			return new ResultData(20000,true,"编辑成功");
		}
		return new ResultData(20002,false,"网络错误，请重试");
	}

	@Override
	public ResultData deleteRepairGoodsRepertoryType(Integer repertory_type_id) {
		if(repertory_type_id == null || repertory_type_id < 0){
			return new ResultData(20001,false,"请选择库类型");
		}
		//判断库管配置里面是否存在该库名称，如果存在不删除
		Example example = new Example(RepairRepertoryAdminConfigEntity.class);
		example.createCriteria().andEqualTo("repertory_type_id",repertory_type_id);
		List<RepairRepertoryAdminConfigEntity> repairRepertoryAdminConfigEntities = repairRepertoryAdminConfigMapper.selectByExample(example);
		if(repairRepertoryAdminConfigEntities.size()>0){
			return new ResultData(20003,false,"该库管类型正在被使用，请勿删除");
		}
		int i = repairGoodsRepertoryTypeMapper.deleteByPrimaryKey(repertory_type_id);
		if(i>0){
			return new ResultData(20000,true,"删除成功");
		}
		return new ResultData(20002,false,"网络错误，请重试");
	}

	@Override
	public ResultData findRepertoryNameByAdmin(Integer repertory_name_id, Integer admin_id) {
		//查询出此管理员下的所有子级管理员
		Set<Integer> set = new HashSet<Integer>();
		List<Integer> list = new ArrayList<Integer>();
		list.add(admin_id);
		AdminEntityUtil.getAllChildAdminId(set,list);
		set.add(admin_id);
		List<Map<String, Object>> exportRoleByAdminId = repertoryExportRoleMapper.findExportRoleByAdmind(set);
		List<Integer> exportRoleIdList = new ArrayList<>();
		for(int i = 0; i<exportRoleByAdminId.size(); i++){
			Integer export_role_id = (Integer) exportRoleByAdminId.get(i).get("export_role_id");
			exportRoleIdList.add(export_role_id);
		}
		//根据角色id查询角色库名称库类型中间表
		Set<Integer> repertoryTypeidList = new HashSet<>();
		for(int i = 0;i<exportRoleIdList.size();i++){
			Example example = new Example(RepertoryExportRoleConfigEntity.class);
			Example.Criteria criteria = example.createCriteria();
			criteria.andEqualTo("export_role_id",exportRoleIdList.get(i));
			criteria.andEqualTo("repertory_name_id",repertory_name_id);
			List<RepertoryExportRoleConfigEntity> repertoryExportRoleConfigEntities = repertoryExportRoleConfigMapper.selectByExample(example);
			for(int j = 0;j<repertoryExportRoleConfigEntities.size();j++){
				Integer repertory_type_id = repertoryExportRoleConfigEntities.get(j).getRepertory_type_id();
				repertoryTypeidList.add(repertory_type_id);
			}
		}
		List<RepairGoodsRepertoryTypeEntity> repairGoodsRepertoryTypeEntities = new ArrayList<>();
		for (Integer integer : repertoryTypeidList) {
			//根据repertory_type_id查询库类型.
			RepairGoodsRepertoryTypeEntity repairGoodsRepertoryTypeEntity = repairGoodsRepertoryTypeMapper.selectByPrimaryKey(integer);
			repairGoodsRepertoryTypeEntities.add(repairGoodsRepertoryTypeEntity);
		}
		return new ResultData(20000,true,"查询成功",repairGoodsRepertoryTypeEntities);
	}

	/**
	 * 根据微信用户ID与库名ID查询其拥有的库类型(手机端出库使用)
	 * @param userId  微信用户ID
	 * @param repertoryNameId  库名称ID
	 * @return
	 */
    @Override
    public ResultData findRepertoryTypeByCondition(Integer userId, Integer repertoryNameId) {
		SysUserEntity userEntity = sysUserMapper.selectByPrimaryKey(userId);
		if(userEntity == null){
			return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),null);
		}
		List<RepairGoodsRepertoryTypeEntity> repertoryTypeByCondition = repairGoodsRepertoryTypeMapper.findRepertoryTypeByCondition(userEntity.getExport_role_id(), repertoryNameId);
		return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),repertoryTypeByCondition);
    }
}
