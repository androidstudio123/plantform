package cn.crm.service.room.impl;


import cn.crm.entity.SysRoomTypeEntity;
import cn.crm.mapper.sys.SysRoomTypeMapper;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.room.SysRoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoomTypeServiceImpl extends BaseServiceImpl<SysRoomTypeEntity> implements SysRoomTypeService {
	
	@Autowired
	private SysRoomTypeMapper sysRoomTypeMapper;
	

}
