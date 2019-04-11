package cn.crm.entity.repair;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;


@Data
@Table(name="repair_role_area")
public class RepairRoleAreaEntity{


	/**
	 * 角色ID
	 */
	@Column(name = "role_id")
	private Integer role_id;

	/**
	 * 维修区域ID
	 */
	@Column(name = "area_id")
	private Integer area_id;
	
	
	
}
