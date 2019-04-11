package cn.crm.entity;

import javax.persistence.*;

import lombok.Data;

import java.util.Date;

/**
 * 角色表
 */

@Data
@Table(name="sys_role")
public class SysRoleEntity{
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator = "Mysql")
	/**
	 * role_id
	 */
	@Column(name = "role_id")
	private Integer role_id;
	/**
	 * 角色名称
	 */
	@Column(name = "role_name")
	private String role_name;
	/**
	 * 角色描述
	 */
	@Column(name = "role_desc")
	private String role_desc;
	/**
	 * 开启状态 0 不开启  1 开启
	 */
	@Column(name = "role_isActive")
	private Integer role_isActive;
	/**
	 * 角色创建人
	 */
	@Column(name = "role_createId")
	private Integer role_createId;


	/**
	 * 角色创建时间
	 */
	@Column(name = "role_createTime")
	private Date role_createTime;
}
