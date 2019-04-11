package cn.crm.entity.repair;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Table(name="repair_area")
public class RepairAreaEntity{
		

	/**
	 * 主键ID
	 */
	@Id
	@Column(name = "area_id")
	@GeneratedValue(generator = "JDBC")
	private Integer area_id;
	/**
	 * 维修区域名称
	 */
	@Column(name = "area_name")
	private String area_name;
	/**
	 * 上级ID
	 */
	@Column(name = "parent_id")
	private Integer parent_id;

	/**
	 * 上级ID
	 */
	@Column(name = "school_id")
	private Integer school_id;

	/**
	 * 上级ID
	 */
	@Column(name = "type")
	private Integer type;
	
	
	
}
