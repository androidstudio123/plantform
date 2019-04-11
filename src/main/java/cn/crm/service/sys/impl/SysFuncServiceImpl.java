package cn.crm.service.sys.impl;


import cn.crm.entity.SysFuncEntity;
import cn.crm.mapper.sys.SysFuncMapper;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.sys.SysFuncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysFuncServiceImpl extends BaseServiceImpl<SysFuncEntity> implements SysFuncService {
	
	@Autowired
	private SysFuncMapper sysFuncMapper;


	@Override
	public List<SysFuncEntity> findAllSysFun(Integer roleId) {
		return sysFuncMapper.findAllSysFun(roleId);
	}

	@Override
	public List<SysFuncEntity> findFunsByAdminId(Map<String, String> map) {
		return null;
	}
}
