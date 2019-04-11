package cn.crm.service.room.impl;


import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.SysAdminRoomgroupEntity;
import cn.crm.entity.SysRoomEntity;
import cn.crm.entity.SysRoomgroupEntity;
import cn.crm.mapper.sys.SysAdminRoomgroupMapper;
import cn.crm.mapper.sys.SysRoomMapper;
import cn.crm.mapper.sys.SysRoomgroupMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.room.SysRoomgroupService;
import cn.crm.util.AdminEntityUtil;
import cn.crm.util.UserEntityUtil;
import cn.crm.util.slideVerification.validate.StringUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * SysRoomgroupEntity ServiceImpl
 * @author  hzg
 *
 */
@Service
public class SysRoomgroupServiceImpl extends BaseServiceImpl<SysRoomgroupEntity> implements SysRoomgroupService {

	@Autowired
	private SysRoomgroupMapper roomgroupMapper;


	@Autowired
	private SysAdminRoomgroupMapper adminRoomgroupMapper;

	@Autowired
	private SysRoomMapper roomMapper;


	/**
	 * 新增房间分组
	 * @param entity
	 * @return
	 */
	@Override
    @Transactional
	public ResultData saveRoomGroup(SysRoomgroupEntity entity) {
		// 名字，上级ID不能为空
		if (entity == null || StringUtils.isEmpty(entity.getRoomGroup_name()) || entity.getRoomGroup_parentId() == null) {
			return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
		}
		SysAdminEntity admin = AdminEntityUtil.getAdminFromSession();
		if (admin == null) {
			return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage());
		}
		int i = roomgroupMapper.insertSelective(entity);
		if (i > 0) {
			Integer roomGroup_id = entity.getRoomGroup_id();
			// 当前登录adminID 以及其所有上级ID
			Set<Integer> adminIds = new HashSet<>();
			adminIds.add(admin.getAdmin_id());
			Integer parentId = admin.getAdmin_parentId() == null ? 0 : admin.getAdmin_parentId();

			if (parentId > 0) {
				AdminEntityUtil.getAllParentAdminId(adminIds, parentId);
			}
			List<SysAdminRoomgroupEntity> list = new ArrayList<>();
			for (Integer adminId : adminIds) {
				if (adminId == null || adminId == 0) {
					continue;
				}
				SysAdminRoomgroupEntity roomgroupEntity = new SysAdminRoomgroupEntity(adminId, roomGroup_id);
				list.add(roomgroupEntity);
			}
			// 新增管理员对房间的权限
			if (list.size() > 0) {
				adminRoomgroupMapper.insertList(list);
			}
			return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
		} else {
			return new ResultData(ResultCode.ERROR.getCode(), false, ResultCode.ERROR.getMessage());
		}
	}

	/**
	 * 修改房间分组
	 * @param entity
	 * @return
	 */
	@Override
    @Transactional
	public ResultData editRoomGroup(SysRoomgroupEntity entity) {
		// ID不能为空
		if (entity == null || entity.getRoomGroup_id() == null) {
			return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
		}
		int i = roomgroupMapper.updateByPrimaryKeySelective(entity);
		if (i > 0) {
			return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
		} else {
			return new ResultData(ResultCode.ERROR.getCode(), false, ResultCode.ERROR.getMessage());
		}
	}

	/**
	 * 删除分组
	 * @param roomGroupId
	 * @return
	 */
	@Override
	public ResultData deleteRoomGroup(Integer roomGroupId) {
		if(roomGroupId != null){
			Example example = new Example(SysRoomEntity.class);
			example.createCriteria().andEqualTo("roomGroup_id",roomGroupId);
			// 判断房间组是否正在使用
			int count = roomMapper.selectCountByExample(example);
			if(count == 0){
				int i = roomgroupMapper.deleteByPrimaryKey(roomGroupId);
				if (i > 0) {
					return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
				} else {
					return new ResultData(ResultCode.ERROR.getCode(), false, ResultCode.ERROR.getMessage());
				}
			}else{
				return new ResultData(ResultCode.FOREIGNNODEERROR.getCode(), false, ResultCode.FOREIGNNODEERROR.getMessage());
			}
		}else{
			return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
		}
	}

	/**
	 * 查询管理员有权限的房间组
	 * @param adminId
	 * @return
	 */
	@Override
	public List<SysRoomgroupEntity> findAdminRoomgroup(Integer adminId) {
		return roomgroupMapper.findAdminRoomgroup(adminId);
	}


    /**
     * @data 2019年3月19日09:17:01
     * @param childrenIds 储存所有查询到的子分组ID
     * @param parentIds   第一次查询的父分组ID
     * @param adminRoomGroupId 管理拥有权限的分组ID
     */
    @Override
	public void getAllChildrenId(Set<Integer> childrenIds,List<Integer> parentIds,Set<Integer> adminRoomGroupId){
	    if(childrenIds != null && parentIds != null && parentIds.size() > 0){
	        // 储存下一次要循环查询的ID
            List<Integer> iterList = new ArrayList<>();
            for (Integer parentId : parentIds) {
                SysRoomgroupEntity entity = new SysRoomgroupEntity();
                entity.setRoomGroup_parentId(parentId);
                // 跟据parentId 查询所有子分组
                List<SysRoomgroupEntity> select = roomgroupMapper.select(entity);
                for (SysRoomgroupEntity roomgroupEntity : select) {
                    Integer roomGroup_id = roomgroupEntity.getRoomGroup_id();
                    //判断管理员拥有权限分组当中是否有
                    if(adminRoomGroupId.contains(roomGroup_id)){
                        boolean add = childrenIds.add(roomGroup_id);
                        if(add){
                            iterList.add(roomGroup_id);
                        }
                    }
                }
            }
            if(iterList.size() > 0){
                getAllChildrenId(childrenIds,iterList,adminRoomGroupId);
            }
        }
    }

}