package cn.crm.service.repair.impl;

import cn.crm.entity.repair.RepairRoleFunctionEntity;
import cn.crm.mapper.repair.RepairRoleFunctionMapper;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.repair.RepairRoleFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RepairRoleFunctionServiceImpl extends BaseServiceImpl<RepairRoleFunctionEntity> implements RepairRoleFunctionService {
	
	@Autowired
	private RepairRoleFunctionMapper repairRoleFunctionMapper;
	

}
