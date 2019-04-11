package cn.crm.service.repair.impl;
import java.util.List;
import javax.annotation.Resource;

import cn.crm.entity.repair.DefaultFunctionEntity;
import cn.crm.mapper.repair.DefaultFunctionMapper;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.repair.DefaultFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO 在此加入类描述
 * @copyright {@link hzg}
 * @author hzg
 * @version  2019-03-26 09:19:29
 * @see cn.yr.service.impl.DefaultFunction
 */

@Service
public class DefaultFunctionServiceImpl extends BaseServiceImpl<DefaultFunctionEntity> implements DefaultFunctionService {
	
	@Autowired
	private DefaultFunctionMapper defaultFunctionMapper;
	

}
