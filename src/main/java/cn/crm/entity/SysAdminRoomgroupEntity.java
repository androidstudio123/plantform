package cn.crm.entity;

import javax.persistence.*;

import lombok.Data;

/**
 * 管理员房间组
 */
@Data
@Table(name="sys_admin_roomgroup")
public class SysAdminRoomgroupEntity{

	/**
	 * admin_id
	 */
	@Column(name = "admin_id")
	private Integer admin_id;
	/**
	 * roomGroup_id
	 */
	@Column(name = "roomGroup_id")
	private Integer roomGroup_id;

	@Column(name = "state")
	private Integer state;

	public SysAdminRoomgroupEntity(Integer admin_id) {
		this.admin_id = admin_id;
	}
	public SysAdminRoomgroupEntity() {
	}

	public SysAdminRoomgroupEntity(Integer admin_id ,Integer roomGroup_id) {
		this.roomGroup_id = roomGroup_id;
		this.admin_id = admin_id;
	}
}
