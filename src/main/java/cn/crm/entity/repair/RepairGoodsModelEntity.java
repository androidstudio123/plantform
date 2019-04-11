package cn.crm.entity.repair;

import javax.persistence.Table;
import javax.persistence.Column;

import lombok.Data;


@Data
@Table(name="")
public class RepairGoodsModelEntity{
		

	/**
	 * 主键ID
	 */
	@Column(name = "goods_model_id")
	private Integer goods_model_id;
	/**
	 * 对应的耗材类型id
	 */
	@Column(name = "goods_type_id")
	private Integer goods_type_id;
	/**
	 * 型号名称
	 */
	@Column(name = "goods_model_name")
	private String goods_model_name;
	
	
	
}
