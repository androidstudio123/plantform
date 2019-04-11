package cn.crm.entity.repair;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

import lombok.Data;


@Data
@Table(name="repair_role")
public class RepairRoleEntity{
		

	/**
	 * 主键ID
	 */
	@Id
	@Column(name = "role_id")
	@GeneratedValue(generator = "JDBC")
	private Integer role_id;
	/**
	 * 角色名
	 */
	@Column(name = "role_name")
	private String role_name;
	/**
	 * 描述
	 */
	@Column(name = "role_desc")
	private String role_desc;
	/**
	 * 是否启用
	 */
	@Column(name = "role_flag")
	private Integer role_flag;
	/**
	 * 是否是默认角色
	 */
	@Column(name = "is_default")
	private Integer is_default;
	
	
}
