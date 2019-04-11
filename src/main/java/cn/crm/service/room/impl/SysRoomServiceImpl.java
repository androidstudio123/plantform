package cn.crm.service.room.impl;


import cn.crm.entity.SysRoomEntity;
import cn.crm.entity.SysUserRoomEntity;
import cn.crm.mapper.sys.SysRoomMapper;
import cn.crm.mapper.sys.SysUserRoomMapper;

import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.room.SysRoomService;
import cn.crm.util.AdminEntityUtil;
import cn.crm.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SysRoomServiceImpl extends BaseServiceImpl<SysRoomEntity> implements SysRoomService {
	
	@Autowired
	private SysRoomMapper roomMapper;


	@Autowired
	private SysUserRoomMapper userRoomMapper;


	/**
	 * 新增房间
	 * @param entity
	 * @return
	 */
	@Override
	@Transactional
	public ResultData saveRoom(SysRoomEntity entity, String userIds) {
		//数据为空
		if(entity == null || StringUtils.isBlank(entity.getRoom_name()) || entity.getRoomGroup_id() == null){
			return new ResultData(ResultCode.EMPTYPARAMS.getCode(),false,ResultCode.EMPTYPARAMS.getMessage());
		}
		String room_imei = entity.getRoom_imei();
		String room_sn = entity.getRoom_sn();
		// imei号不能为空 sn号不能为空
		if(StringUtils.isBlank(room_imei) || StringUtils.isBlank(room_sn)){
			return new ResultData(ResultCode.IMEIORSN_EMPTY.getCode(),false,ResultCode.IMEIORSN_EMPTY.getMessage());
		}else{
			SysRoomEntity roomEntity = new SysRoomEntity();
			roomEntity.setRoom_imei(room_imei);
			// 查询IMEI号是否已经存在数据库
			int count = roomMapper.selectCount(roomEntity);
			if(count > 0){
				return new ResultData(ResultCode.IMEI_EXIST.getCode(),false,ResultCode.IMEI_EXIST.getMessage());
			}
		}
		int i = roomMapper.insertSelective(entity);
		if(i > 0){
			// 如果新增房间成功，添加授权用户
			if(StringUtils.isNotBlank(userIds)){
				String[] split = userIds.split(",");
				List<SysUserRoomEntity> list = new ArrayList<>();
				for (String id : split) {
					SysUserRoomEntity userRoomEntity = new SysUserRoomEntity(Integer.parseInt(id),entity.getRoom_id());
					list.add(userRoomEntity);
				}
				if(list.size() > 0){
					userRoomMapper.insertList(list);
				}
			}
			return new ResultData(ResultCode.SUCCESS.getCode(),false,ResultCode.SUCCESS.getMessage());
		}else{
			return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage());
		}
	}

	/**
	 * 修改房间
	 * @param entity
	 * @return
	 */
	@Override
	@Transactional
	public ResultData editRoom(SysRoomEntity entity, String userIds) {
		if(entity == null || entity.getRoom_id() == null){
			return new ResultData(ResultCode.EMPTYPARAMS.getCode(),false,ResultCode.EMPTYPARAMS.getMessage());
		}


		String room_imei = entity.getRoom_imei();
		String room_sn = entity.getRoom_sn();
		// imei号不能为空 sn号不能为空
		if(StringUtils.isBlank(room_imei) || StringUtils.isBlank(room_sn)){
			return new ResultData(ResultCode.IMEIORSN_EMPTY.getCode(),false,ResultCode.IMEIORSN_EMPTY.getMessage());
		}else{
			SysRoomEntity roomEntity = new SysRoomEntity();
			roomEntity.setRoom_imei(room_imei);
			// 查询IMEI号是否已经存在数据库
			SysRoomEntity select = roomMapper.selectOne(roomEntity);
			if(select != null){
				Integer room_id = select.getRoom_id();
				Integer room_id1 = entity.getRoom_id();
				if(room_id == room_id1){
					return new ResultData(ResultCode.IMEI_EXIST.getCode(),false,ResultCode.IMEI_EXIST.getMessage());
				}
			}
		}
		int i = roomMapper.updateByPrimaryKeySelective(entity);
		if(i > 0){
			//先清空原先的管理员授权
			int count = userRoomMapper.deleteByAdminId(AdminEntityUtil.getAdminIdFromSession(),entity.getRoom_id());
			// 如果新增房间成功，添加授权用户
			if(StringUtils.isNotBlank(userIds)){
				String[] split = userIds.split(",");
				List<SysUserRoomEntity> list = new ArrayList<>();
				for (String id : split) {
					SysUserRoomEntity userRoomEntity = new SysUserRoomEntity(Integer.parseInt(id),entity.getRoom_id());
					list.add(userRoomEntity);
				}
				if(list.size() > 0){
					int i1 = userRoomMapper.insertList(list);
				}
			}
			return new ResultData(ResultCode.SUCCESS.getCode(),false,ResultCode.SUCCESS.getMessage());
		}else{
			return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage());
		}
	}

	@Override
	@Transactional
	public ResultData deleteByIds(String roomIds) {
		if(StringUtils.isNotBlank(roomIds)){
			String[] split = roomIds.split(",");
			List<Integer> deleteIds = new ArrayList<>();
			for (String s : split) {
				int id = Integer.parseInt(s);
				int count = userRoomMapper.findCountByRoomId(id);
				if(count > 0){
					return new ResultData(ResultCode.ROOM_DELETE_ERROR.getCode(),false,ResultCode.ROOM_DELETE_ERROR.getMessage());
				}
				deleteIds.add(id);
			}
			Example example = new Example(SysRoomEntity.class);
			Example.Criteria criteria = example.createCriteria();
			criteria.andIn("room_id",deleteIds);
			int i = userRoomMapper.deleteByExample(example);
			if(i > 0){
				return new ResultData(ResultCode.SUCCESS.getCode(),false,ResultCode.SUCCESS.getMessage());
			}
			return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage());
		}
		return new ResultData(ResultCode.EMPTYPARAMS.getCode(),false,ResultCode.EMPTYPARAMS.getMessage());
	}

	@Override
	public List<SysRoomEntity> findListRoom(Map<String, Object> paramMap) {
		return roomMapper.findListRoom(paramMap);
	}
}
