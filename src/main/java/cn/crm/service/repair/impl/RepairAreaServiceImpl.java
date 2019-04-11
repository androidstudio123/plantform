package cn.crm.service.repair.impl;

import cn.crm.entity.repair.RepairAreaEntity;
import cn.crm.mapper.repair.RepairAreaMapper;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.repair.RepairAreaService;
import cn.crm.util.PageUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class RepairAreaServiceImpl extends BaseServiceImpl<RepairAreaEntity> implements RepairAreaService {
	
	@Autowired
	private RepairAreaMapper repairAreaMapper;


	@Override
	public ResultData findRepairArea(Integer pageNum, Integer pageSize,Integer school_id) {
		Example example = new Example(RepairAreaEntity.class);
		example.createCriteria().andEqualTo("school_id",school_id);
		List<RepairAreaEntity> repairAreaEntities = repairAreaMapper.selectByExample(example);
		return new ResultData(20000,true,"查询成功",repairAreaEntities);
	}

	@Override
	public ResultData saveRepairArea(RepairAreaEntity repairAreaEntity) {
		if(repairAreaEntity == null){
			return new ResultData(20001,false,"报修区域不能为空");
		}
		int result = repairAreaMapper.insertSelective(repairAreaEntity);
		if(result>0){
			return new ResultData(20000,true,"新增成功");
		}
		return new ResultData(20002,false,"新增失败，请重试");
	}

	@Override
	public ResultData updateReapirArea(RepairAreaEntity repairAreaEntity) {
		if(repairAreaEntity == null){
			return new ResultData(20001,false,"报修区域不能为空");
		}
		int result = repairAreaMapper.updateByPrimaryKeySelective(repairAreaEntity);
		if(result>0){
			return new ResultData(20000,true,"编辑成功");
		}
		return new ResultData(20002,false,"编辑失败，请重试");
	}

	@Override
	public ResultData deleteReapirArea(Integer id) {
		if(id<0 || id == null){
			return new ResultData(20001,false,"请选择报修区域");
		}
		//查询该区域下是否有子区域
		Example example = new Example(RepairAreaEntity.class);
		example.createCriteria().andEqualTo("parent_id",id);
		List<RepairAreaEntity> repairAreaEntities = repairAreaMapper.selectByExample(example);
		if(repairAreaEntities.size()>0){
			return new ResultData(20003,false,"该区域下有子区域，请勿删除");
		}
		int result = repairAreaMapper.deleteByPrimaryKey(id);
		if(result>0){
			return new ResultData(20000,true,"删除成功");
		}
		return new ResultData(20002,false,"删除失败，请重试");
	}

	/**
	 * 根据维修区域ID,查询此维修区域的全名
	 * @param areaId  维修区域ID
	 * @return
	 */
    @Override
    public String findAreaNameById(Integer areaId) {
    	List<String> list = new ArrayList();
		findAreaNameByIdInList(areaId, list);
		Collections.reverse(list);
		StringBuilder sb = new StringBuilder();
		for (String s : list) {
			sb.append(s);
		}
		return sb.toString();
	}

	/**
	 * 根据区域ID,将其所有的父级区域名称存到list集合中
	 * @param areaId  区域ID
	 * @param list
	 * @return
	 */
	private List<String> findAreaNameByIdInList(Integer areaId,List<String> list){
		RepairAreaEntity repairAreaEntity = repairAreaMapper.selectByPrimaryKey(areaId);
		if(repairAreaEntity != null && repairAreaEntity.getParent_id() != 0) {
			list.add(repairAreaEntity.getArea_name());
			this.findAreaNameByIdInList(repairAreaEntity.getParent_id(), list);
		}else if(repairAreaEntity != null && repairAreaEntity.getParent_id() == 0){
			list.add(repairAreaEntity.getArea_name());
		}
		return list;
	}
}
