package cn.crm.entity.materiel;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Table(name="repair_goods_repertory_type")
public class RepairGoodsRepertoryTypeEntity {
		

	/**
	 * 主键ID
	 */
	@Column(name = "repertory_type_id")
	@Id
	private Integer repertory_type_id;
	/**
	 * 生产厂家名称
	 */
	@Column(name = "repertory_type_name")
	private String repertory_type_name;
	
	
	
}
