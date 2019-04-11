package cn.crm.service.materiel.impl;


import cn.crm.entity.SysUserEntity;
import cn.crm.entity.materiel.RepairGoodsProducerEntity;
import cn.crm.entity.materiel.RepairGoodsRepertoryNameEntity;
import cn.crm.entity.materiel.RepairRepertoryAdminConfigEntity;
import cn.crm.entity.materiel.RepertoryExportRoleConfigEntity;
import cn.crm.mapper.materiel.RepairGoodsRepertoryNameMapper;
import cn.crm.mapper.materiel.RepairRepertoryAdminConfigMapper;
import cn.crm.mapper.materiel.RepertoryExportRoleConfigMapper;
import cn.crm.mapper.materiel.RepertoryExportRoleMapper;
import cn.crm.mapper.sys.SysUserMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.materiel.RepairGoodsRepertoryNameService;
import cn.crm.util.AdminEntityUtil;
import cn.crm.util.PageUtil;
import cn.crm.util.StringUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;


@Service
public class RepairGoodsRepertoryNameServiceImpl extends BaseServiceImpl<RepairGoodsRepertoryNameEntity> implements RepairGoodsRepertoryNameService {
	
	@Autowired
	private RepairGoodsRepertoryNameMapper repairGoodsRepertoryNameMapper;

	@Autowired
	private RepairRepertoryAdminConfigMapper repairRepertoryAdminConfigMapper;

	@Autowired
	private RepertoryExportRoleMapper repertoryExportRoleMapper;

	@Autowired
	private RepertoryExportRoleConfigMapper repertoryExportRoleConfigMapper;
	@Autowired
	private SysUserMapper sysUserMapper;


	@Override
	public ResultData findRepairGoodsRepertoryName(Integer pageNum, Integer pageSize, String repertory_name) {
		if(pageNum == null || pageNum < 0){
			pageNum = 1;
		}
		if(pageSize == null || pageSize < 0){
			pageSize = 10;
		}
		PageUtil.startPage();
		if(StringUtils.isNotEmpty(repertory_name)){
			Example example = new Example(RepairGoodsRepertoryNameEntity.class);
			example.createCriteria().andLike("repertory_name","%"+repertory_name+"%");
			List<RepairGoodsRepertoryNameEntity> repairGoodsRepertoryNameEntities = repairGoodsRepertoryNameMapper.selectByExample(example);
			return new ResultData(20000,true,"查询成功",new PageInfo<>(repairGoodsRepertoryNameEntities));
		}
		return new ResultData(20000,true,"查询成功",new PageInfo<>(repairGoodsRepertoryNameMapper.selectAll()));
	}

	@Override
	public ResultData saveRepairGoodsRepertoryName(RepairGoodsRepertoryNameEntity repairGoodsRepertoryNameEntity) {
		if(repairGoodsRepertoryNameEntity == null){
			return new ResultData(20001,false,"库名称信息不能为空");
		}
		Example example = new Example(RepairGoodsRepertoryNameEntity.class);
		example.createCriteria().andEqualTo("repertory_name",repairGoodsRepertoryNameEntity.getRepertory_name());
		List<RepairGoodsRepertoryNameEntity> repairGoodsRepertoryNameEntities = repairGoodsRepertoryNameMapper.selectByExample(example);
		if(repairGoodsRepertoryNameEntities.size()>0){
			return new ResultData(20003,false,"库名称已存在");
		}
		int i = repairGoodsRepertoryNameMapper.insertSelective(repairGoodsRepertoryNameEntity);
		if(i>0){
			return new ResultData(20000,true,"新增成功");
		}
		return new ResultData(20002,false,"网络错误，请重试");
	}

	@Override
	public ResultData updateRepairGoodsRepertoryName(RepairGoodsRepertoryNameEntity repairGoodsRepertoryNameEntity) {
		if(repairGoodsRepertoryNameEntity == null){
			return new ResultData(20001,false,"库名称信息不能为空");
		}
		Example example = new Example(RepairGoodsRepertoryNameEntity.class);
		example.createCriteria().andEqualTo("repertory_name",repairGoodsRepertoryNameEntity.getRepertory_name());
		List<RepairGoodsRepertoryNameEntity> repertoryNameEntityList = repairGoodsRepertoryNameMapper.selectByExample(example);
		RepairGoodsRepertoryNameEntity repairGoodsRepertoryNameEntity1 = repairGoodsRepertoryNameMapper.selectByPrimaryKey(repairGoodsRepertoryNameEntity.getRepertory_name_id());

		if(repertoryNameEntityList.size()>0 && !repairGoodsRepertoryNameEntity1.getRepertory_name().equals(repairGoodsRepertoryNameEntity.getRepertory_name())){
			return new ResultData(20003,false,"库名称已存在，请勿修改成该库名称");
		}
		int i = repairGoodsRepertoryNameMapper.updateByPrimaryKeySelective(repairGoodsRepertoryNameEntity);
		if(i>0){
			return new ResultData(20000,true,"编辑成功");
		}
		return new ResultData(20002,false,"网络错误，请重试");
	}

	@Override
	public ResultData deleteRepairGoodsRepertoryName(Integer repertory_name_id) {
		if(repertory_name_id == null || repertory_name_id < 0){
			return new ResultData(20001,false,"库名称信息不能为空");
		}
		//判断库管配置里面是否存在该库名称，如果存在不删除
		Example example = new Example(RepairRepertoryAdminConfigEntity.class);
		example.createCriteria().andEqualTo("repertory_name_id",repertory_name_id);
		List<RepairRepertoryAdminConfigEntity> repairRepertoryAdminConfigEntities = repairRepertoryAdminConfigMapper.selectByExample(example);
		if(repairRepertoryAdminConfigEntities.size()>0){
			return new ResultData(20003,false,"该库管名称正在被使用，请勿删除");
		}
		int i = repairGoodsRepertoryNameMapper.deleteByPrimaryKey(repertory_name_id);
		if(i>0){
			return new ResultData(20000,true,"删除成功");
		}
		return new ResultData(20002,false,"网络错误，请重试");
	}

	@Override
	public ResultData findRepertoryNameByAdmin(Integer admin_id) {
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
		Set<Integer> repertoryNameNidList = new HashSet<>();

		for(int i = 0;i<exportRoleIdList.size();i++){
			Example example = new Example(RepertoryExportRoleConfigEntity.class);
			Example.Criteria criteria = example.createCriteria();
			criteria.andEqualTo("export_role_id",exportRoleIdList.get(i));
			List<RepertoryExportRoleConfigEntity> repertoryExportRoleConfigEntities = repertoryExportRoleConfigMapper.selectByExample(example);
			for(int j = 0;j<repertoryExportRoleConfigEntities.size();j++){
				Integer repertory_name_id = repertoryExportRoleConfigEntities.get(j).getRepertory_name_id();
				repertoryNameNidList.add(repertory_name_id);
			}
		}
		List<RepairGoodsRepertoryNameEntity> repertoryNameEntityList = new ArrayList<>();
		for (Integer integer : repertoryNameNidList) {
			RepairGoodsRepertoryNameEntity repairGoodsRepertoryNameEntity = repairGoodsRepertoryNameMapper.selectByPrimaryKey(integer);
			repertoryNameEntityList.add(repairGoodsRepertoryNameEntity);
		}
		return new ResultData(20000,true,"查询成功",repertoryNameEntityList);
	}


	/**
	 * 根据微信用户ID查询其管理的库
	 * @param userId  前台微信用户ID
	 * @return
	 */
    @Override
    public ResultData findRepertoryNameByUserId(Integer userId) {
		SysUserEntity userEntity = sysUserMapper.selectByPrimaryKey(userId);
		if(userEntity == null){
			return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),null);
		}
		List<RepairGoodsRepertoryNameEntity> repertoryNameByRoleId = repairGoodsRepertoryNameMapper.findRepertoryNameByRoleId(userEntity.getExport_role_id());
		return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),repertoryNameByRoleId);
    }
}
