package cn.crm.service.materiel.impl;


import cn.crm.entity.materiel.RepertoryExportRoleConfigEntity;
import cn.crm.mapper.materiel.RepertoryExportRoleConfigMapper;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.materiel.RepertoryExportRoleConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepertoryExportRoleConfigServiceImpl extends BaseServiceImpl<RepertoryExportRoleConfigEntity> implements RepertoryExportRoleConfigService {
	
	@Autowired
	private RepertoryExportRoleConfigMapper repertoryExportRoleConfigMapper;



}
