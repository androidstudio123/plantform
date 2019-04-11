package cn.crm.entity.repair;

import javax.persistence.Table;
import javax.persistence.Column;

import lombok.Data;


@Data
@Table(name="")
public class RepairGoodsEntity{
		

	/**
	 * 主键ID
	 */
	@Column(name = "goods_id")
	private Integer goods_id;
	/**
	 * 物品名称
	 */
	@Column(name = "goods_name")
	private String goods_name;
	/**
	 * 物品数量
	 */
	@Column(name = "goods_num")
	private Integer goods_num;
	/**
	 * 所属学校ID
	 */
	@Column(name = "school_id")
	private Integer school_id;
	
	
	
}
