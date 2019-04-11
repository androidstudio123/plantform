package cn.crm.mapper.sys;

import cn.crm.entity.SysRoomEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import cn.crm.entity.SysUserRoomEntity;

import java.util.List;
import java.util.Map;

/**
 * TODO 在此加入类描述   用户和房间关联mapper
 * @copyright
 * @author MYZ
 * @version  2019-03-13 15:21:58
 */
@Component
public interface SysUserRoomMapper extends Mapper<SysUserRoomEntity>,MySqlMapper<SysUserRoomEntity>{

    /**
     * 删除管理员有权限的用户房间权限
     * @param adminId
     * @param roomId
     * @return
     */
    Integer deleteByAdminId(@Param("adminId") Integer adminId,@Param("roomId") Integer roomId);

    /**
     * 查询房间绑定用户的个数
     * @param roomId
     * @return
     */
    Integer findCountByRoomId(@Param("roomId") Integer roomId);

}

