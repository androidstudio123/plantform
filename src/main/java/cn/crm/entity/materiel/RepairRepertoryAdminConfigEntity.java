package cn.crm.entity.materiel;

import lombok.Data;

import javax.persistence.*;


@Data
@Table(name="repair_repertory_admin_config")
public class RepairRepertoryAdminConfigEntity {
		

	/**
	 * 主键ID
	 */
	@Column(name = "repertory_config_id")
	@Id
//	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer repertory_config_id;
	/**
	 * 管理员id
	 */
	@Column(name = "admin_id")
	private Integer admin_id;
    /**
     * 库名称id
     */
    @Column(name = "repertory_name_id")
    private Integer repertory_name_id;
    /**
     * 库类型id
     */
    @Column(name = "repertory_type_id")
    private Integer repertory_type_id;
    /**
     * 分组编号
     */
    @Column(name = "group_no")
    private String group_no;
	
	
	
}
