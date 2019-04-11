package cn.crm.entity.materiel;

import javax.persistence.*;

import lombok.Data;

@Data
@Table(name="repair_goods_import")
public class RepairGoodsImportEntity{
		

	/**
	 * 主键ID
	 */
	@Column(name = "import_id")
	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer import_id;
	/**
	 * 物品id
	 */
	@Column(name = "goods_id")
	private Integer goods_id;
	/**
	 * 物品名称
	 */
	@Column(name = "goods_name")
	private String goods_name;
	/**
	 * 物品类型ID
	 */
	@Column(name = "goods_type_id")
	private Integer goods_type_id;
	/**
	 * 新购入的物品数量
	 */
	@Column(name = "goods_num")
	private Integer goods_num;
	/**
	 * 录入时间
	 */
	@Column(name = "import_time")
	private java.util.Date import_time;
	/**
	 * 录入的管理员ID
	 */
	@Column(name = "admin_id")
	private Integer admin_id;
	/**
	 * 录入的管理员名字
	 */
	@Column(name = "admin_name")
	private String admin_name;
	/**
	 * 录入的管理员手机
	 */
	@Column(name = "admin_phone")
	private String admin_phone;
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;
	/**
	 * 所属库ID
	 */
	@Column(name = "repertory_name_id")
	private Integer repertory_name_id;
	/**
	 * 所属库类型ID
	 */
	@Column(name = "repertory_type_id")
	private Integer repertory_type_id;
	/**
	 * 生产厂家id
	 */
	@Column(name = "producer_id")
	private Integer producer_id;
	/**
	 * 生产厂家名称
	 */
	@Column(name = "producer_name")
	private String producer_name;
	/**
	 * 所属库名称
	 */
	@Column(name = "repertory_name")
	private String repertory_name;
	/**
	 * 物料分类名称
	 */
	@Column(name = "goods_type_name")
	private String goods_type_name;
	/**
	 * 所属库类型名称
	 */
	@Column(name = "repertory_type_name")
	private String repertory_type_name;
	/**
	 * 状态
	 */
	@Column(name = "state")
	private Integer state;
	/**
	 * 物品单价
	 */
	@Column(name = "money")
	private Double money;
	/**
	 * 入库单号
	 */
	@Column(name = "goods_no")
	private String goods_no;

	@Transient
    private Integer flag;
	
	
	
}
