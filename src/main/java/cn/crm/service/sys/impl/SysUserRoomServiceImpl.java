package cn.crm.service.sys.impl;


import cn.crm.mapper.sys.SysUserRoomMapper;
import cn.crm.service.sys.SysUserRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.crm.entity.SysUserRoomEntity;

import cn.crm.service.BaseServiceImpl;

/**
 * TODO 在此加入类描述  用户和房间关联服务层实现类
 * @copyright
 * @author MYZ
 * @version  2019-03-13 15:21:58
 */

@Service
public class SysUserRoomServiceImpl extends BaseServiceImpl<SysUserRoomEntity>  implements SysUserRoomService {
	
	@Autowired
	private SysUserRoomMapper sysUserRoomMapper;
	

}
