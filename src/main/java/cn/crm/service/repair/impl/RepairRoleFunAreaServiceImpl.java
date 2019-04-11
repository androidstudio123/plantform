package cn.crm.service.repair.impl;

import cn.crm.entity.repair.RepairGoodsEntity;
import cn.crm.entity.repair.RepairRoleFunTypeAreaEntity;
import cn.crm.mapper.repair.RepairRoleFunTypeAreaMapper;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.repair.RepairRoleFunAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RepairRoleFunAreaServiceImpl extends BaseServiceImpl<RepairRoleFunTypeAreaEntity> implements RepairRoleFunAreaService {
	
	@Autowired
	private RepairRoleFunTypeAreaMapper repairRoleFunTypeAreaMapper;


	@Override
	public List<RepairRoleFunTypeAreaEntity> findRepairRole(String roleName) {
		return repairRoleFunTypeAreaMapper.findPCRepairRole(roleName);
	}
}
