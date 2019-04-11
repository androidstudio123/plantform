package cn.crm.entity;

import javax.persistence.*;

import lombok.Data;

import java.util.List;

/**
 * 管理员用户组
 */
@Data
@Table(name="sys_admin_usergroup")
public class SysAdminUsergroupEntity{
		

	/**
	 * 管理员id
	 */
	@Column(name = "admin_id")
	private Integer admin_id;
	/**
	 * 用户组id
	 */
	@Column(name = "userGroup_id")
	private Integer userGroup_id;

	@Column(name = "state")
	private Integer state;


	public SysAdminUsergroupEntity() {
	}

	public SysAdminUsergroupEntity(Integer admin_id) {
		this.admin_id = admin_id;
	}

	public SysAdminUsergroupEntity(Integer admin_id, Integer userGroup_id) {
		this.admin_id = admin_id;
		this.userGroup_id = userGroup_id;
	}

	public SysAdminUsergroupEntity(Integer admin_id, Integer userGroup_id, Integer state) {
		this.admin_id = admin_id;
		this.userGroup_id = userGroup_id;
		this.state = state;
	}
}
