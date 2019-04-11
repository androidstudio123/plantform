package cn.crm.entity.repair;

import javax.persistence.Table;
import javax.persistence.Column;

import lombok.Data;



@Data
@Table(name="")
public class RepairDataEntity{
		

	/**
	 * 记录ID
	 */
	@Column(name = "data_id")
	private Integer data_id;
	/**
	 * 维修物品类型ID
	 */
	@Column(name = "goods_type_id")
	private Integer goods_type_id;
	/**
	 * 维修物品型号ID
	 */
	@Column(name = "goods_model_id")
	private Integer goods_model_id;
	/**
	 * 维修区域ID
	 */
	@Column(name = "area_id")
	private Integer area_id;
	/**
	 * 维修物品ID
	 */
	@Column(name = "goods_id")
	private String goods_id;
	/**
	 * 维修人ID
	 */
	@Column(name = "user_id")
	private Integer user_id;
	/**
	 * 维修人姓名
	 */
	@Column(name = "user_name")
	private String user_name;
	/**
	 * 维修人电话
	 */
	@Column(name = "user_phone")
	private String user_phone;
	/**
	 * 维修时间
	 */
	@Column(name = "repair_time")
	private java.util.Date repair_time;
	/**
	 * 维修花费
	 */
	@Column(name = "money")
	private Double money;
	
	
	
}
