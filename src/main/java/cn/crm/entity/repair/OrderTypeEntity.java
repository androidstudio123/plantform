package cn.crm.entity.repair;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

import lombok.Data;
@Data
@Table(name="order_type")
public class OrderTypeEntity{
		

	/**
	 * 主键ID
	 */
	@Id
	@Column(name = "type_id")
	private Integer type_id;
	/**
	 * 类型名称
	 */
	@Column(name = "type_name")
	private String type_name;
	/**
	 * 是否启用  0不启用 1启用
	 */
	@Column(name = "type_state")
	private Integer type_state;
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;
	
	
	
}
