package cn.crm.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 用户房间中间表
 */
@Data
@Table(name = "sys_user_room")
@ApiModel(description = "用户房间关联实体类")
public class SysUserRoomEntity {

    /**
     * 用户id
     */
    @Column(name = "user_id")
    @ApiModelProperty(value = "用户id")
    private Integer user_id;

    /**
     * 房间id
     */
    @Column(name="room_id")
    @ApiModelProperty(value = "房间id")
    private Integer room_id;


    public SysUserRoomEntity() {
        super();
    }

    public SysUserRoomEntity(Integer user_id, Integer room_id) {
        this.user_id = user_id;
        this.room_id = room_id;
    }
}
