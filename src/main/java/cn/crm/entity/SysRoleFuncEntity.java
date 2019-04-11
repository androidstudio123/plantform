package cn.crm.entity;

import javax.persistence.*;

import lombok.Data;

/**
 * 角色资源导航
 */

@Data
@Table(name="sys_role_func")
public class SysRoleFuncEntity{

	/**
	 * 角色id
	 */
	@Column(name = "role_id")
	private Integer role_id;
	/**
	 * 功能id
	 */
	@Column(name = "fun_id")
	private Integer fun_id;
	/**
	 * 状态
	 */
	@Column(name="state")
	private Integer state;


	public SysRoleFuncEntity() {
	}

	public SysRoleFuncEntity(Integer role_id) {
		this.role_id = role_id;
	}

	public SysRoleFuncEntity(Integer role_id, Integer fun_id) {
		this.role_id = role_id;
		this.fun_id = fun_id;
	}
}
