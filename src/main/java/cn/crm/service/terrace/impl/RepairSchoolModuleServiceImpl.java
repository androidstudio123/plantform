package cn.crm.service.terrace.impl;


import cn.crm.entity.terrace.RepairSchoolModuleEntity;
import cn.crm.mapper.terrace.RepairSchoolModuleMapper;
import cn.crm.service.terrace.RepairSchoolModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crm.service.BaseServiceImpl;

/**
 * TODO 在此加入类描述  学校和模块服务层接口实现类
 * @copyright
 * @author MYX
 * @version  2019-03-25 10:01:25
 */

@Service
public class RepairSchoolModuleServiceImpl extends BaseServiceImpl<RepairSchoolModuleEntity>  implements RepairSchoolModuleService {
	
	@Autowired
	private RepairSchoolModuleMapper repairSchoolModuleMapper;
	

}
