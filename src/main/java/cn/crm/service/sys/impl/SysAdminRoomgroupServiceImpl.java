package cn.crm.service.sys.impl;


import cn.crm.entity.SysAdminRoomgroupEntity;
import cn.crm.entity.SysRoomgroupEntity;
import cn.crm.mapper.sys.SysAdminRoomgroupMapper;
import cn.crm.mapper.sys.SysRoomgroupMapper;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.sys.SysAdminRoomgroupService;
import cn.crm.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysAdminRoomgroupServiceImpl extends BaseServiceImpl<SysAdminRoomgroupEntity> implements SysAdminRoomgroupService {
	
	@Autowired
	private SysAdminRoomgroupMapper adminRoomgroupMapper;

	@Autowired
	private SysRoomgroupMapper sysRoomgroupMapper;



	@Override
	public List<SysRoomgroupEntity> findAdminRoomgroup(Integer adminId) {
		return sysRoomgroupMapper.findAdminRoomgroup(adminId);
	}

	/**
	 * 给管理员授权房间组
	 * @param adminId 管理员ID
	 * @param roomGroups 房间组
	 * @return
	 */
	@Override
	@Transactional
	public Integer authorizationRoomGroup(Integer adminId, String roomGroups) {
		int count = 0;
		if(adminId != null){
			// 先清空旧的权限
			Example example = new Example(SysAdminRoomgroupEntity.class);
			example.createCriteria().andEqualTo("admin_id",adminId);
			count = adminRoomgroupMapper.deleteByExample(example);
			if(StringUtils.isNotBlank(roomGroups)){
				String[] split = roomGroups.split(",");
				Set<SysAdminRoomgroupEntity> set = new HashSet<>();
				for (String s : split) {
					int i = Integer.parseInt(s);
					SysAdminRoomgroupEntity entity = new SysAdminRoomgroupEntity(adminId,i);
					set.add(entity);
				}
				if(set.size() > 0){
					List<SysAdminRoomgroupEntity> list = new ArrayList();
					list.addAll(set);
					count += adminRoomgroupMapper.insertList(list);

				}
			}
		}
		return count;
	}

//	@Override
//	public List<Integer> findRoomGroupIdsByAdminId(Integer adminId,Integer state) {
//		return adminRoomgroupMapper.findRoomGroupIdsByAdminId(adminId,state);
//	}
}
