package cn.crm.service.terrace.impl;


import cn.crm.entity.terrace.RepairSchoolArticleEntity;
import cn.crm.mapper.terrace.RepairSchoolArticleMapper;
import cn.crm.service.terrace.RepairSchoolArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crm.service.BaseServiceImpl;

/**
 * TODO 在此加入类描述  学校和文章类型服务层接口实现类
 * @copyright
 * @author MYZ
 * @version  2019-03-25 10:01:40
 */

@Service
public class RepairSchoolArticleServiceImpl extends BaseServiceImpl<RepairSchoolArticleEntity>  implements RepairSchoolArticleService {
	
	@Autowired
	private RepairSchoolArticleMapper repairSchoolArticleMapper;
	

}
