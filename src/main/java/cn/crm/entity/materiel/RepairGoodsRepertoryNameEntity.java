package cn.crm.entity.materiel;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Table(name="`repair_goods_ repertory_name`")
public class RepairGoodsRepertoryNameEntity {
		

	/**
	 * 主键ID
	 */
	@Column(name = "repertory_name_id")
	@Id
	private Integer repertory_name_id;
	/**
	 * 库名称
	 */
	@Column(name = "repertory_name")
	private String repertory_name;
	
	
	
}
