package cn.crm.entity.repair;

import javax.persistence.Table;
import javax.persistence.Column;

import lombok.Data;


@Data
@Table(name="repair_role_function")
public class RepairRoleFunctionEntity{
		

	/**
	 * 报修角色ID
	 */
	@Column(name = "role_Id")
	private Integer role_Id;
	/**
	 * 功能ID
	 */
	@Column(name = "function_id")
	private Integer function_id;
	
	
	
}
