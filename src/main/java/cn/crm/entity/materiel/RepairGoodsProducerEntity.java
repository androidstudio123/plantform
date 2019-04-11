package cn.crm.entity.materiel;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Table(name="repair_goods_producer")
public class RepairGoodsProducerEntity {
		

	/**
	 * 主键ID
	 */
	@Column(name = "producer_id")
	@Id
	private Integer producer_id;
	/**
	 * 生产厂家名称
	 */
	@Column(name = "producer_name")
	private String producer_name;
	
	
	
}
