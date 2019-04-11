package cn.crm.entity.repair;

import javax.persistence.Table;
import javax.persistence.Column;

import lombok.Data;


@Data
@Table(name="")
public class RepairGoodsTypeEntity{
		

	/**
	 * 主键ID
	 */
	@Column(name = "goods_type_id")
	private Integer goods_type_id;
	/**
	 * 耗材类型名称
	 */
	@Column(name = "goods_type_name")
	private Integer goods_type_name;
	
	
	
}
