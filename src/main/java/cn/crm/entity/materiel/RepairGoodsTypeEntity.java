package cn.crm.entity.materiel;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Table(name="repair_goods_type")
public class RepairGoodsTypeEntity {
		

	/**
	 * 主键ID
	 */
	@Column(name = "goods_type_id")
	@Id
	private Integer goods_type_id;
	/**
	 * 物品类型名称
	 */
	@Column(name = "goods_type_name")
	private String goods_type_name;
	
	
}
