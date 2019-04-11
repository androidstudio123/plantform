package cn.crm.entity.materiel;

import javax.persistence.*;

import lombok.Data;


@Data
@Table(name="repertory_export_role")
public class RepertoryExportRoleEntity{
		

	/**
	 * 主键ID
	 */
	@Column(name = "export_role_id")
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer export_role_id;
	/**
	 * 出库角色名
	 */
	@Column(name = "export_role_name")
	private String export_role_name;

	/**
	 * 创建此角色的管理员ID
	 */
	@Column(name = "create_admin_id")
	private Integer create_admin_id;
	
	
	
}
