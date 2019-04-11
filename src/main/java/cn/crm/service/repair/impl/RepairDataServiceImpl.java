package cn.crm.service.repair.impl;


import cn.crm.entity.repair.RepairDataEntity;
import cn.crm.mapper.repair.RepairDataMapper;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.repair.RepairDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class RepairDataServiceImpl extends BaseServiceImpl<RepairDataEntity> implements RepairDataService {
	
	@Autowired
	private RepairDataMapper repairDataMapper;
	

}
