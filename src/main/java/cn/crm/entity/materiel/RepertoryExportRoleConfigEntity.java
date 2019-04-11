package cn.crm.entity.materiel;

import javax.persistence.Table;
import javax.persistence.Column;

import lombok.Data;

@Data
@Table(name="repertory_export_role_config")
public class RepertoryExportRoleConfigEntity{
		


	/**
	 * 出库角色ID
	 */
	@Column(name = "export_role_id")
	private Integer export_role_id;
	/**
	 * 库名称ID
	 */
	@Column(name = "repertory_name_id")
	private Integer repertory_name_id;
	/**
	 * 库类型ID
	 */
	@Column(name = "repertory_type_id")
	private Integer repertory_type_id;
	
	
	
}
