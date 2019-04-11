package cn.crm.entity.materiel;

import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



@Data
@Table(name="repair_goods_export")
@ApiModel(description = "出库记录类")
public class RepairGoodsExportEntity{
		

	/**
	 * 主键ID
	 */
	@Column(name = "export_id")
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@ApiModelProperty(value="出库记录ID")
	private Integer export_id;
	/**
	 * 物品名称
	 */
	@Column(name = "goods_name")
	@ApiModelProperty(value="物品名称")
	private String goods_name;
	/**
	 * 物品ID
	 */
	@Column(name = "goods_id")
	@ApiModelProperty(value="物品ID")
	private Integer goods_id;
	/**
	 * 领出的物品数量
	 */
	@Column(name = "goods_num")
	@ApiModelProperty(value="领出的物品数量")
	private Integer goods_num;
	/**
	 * 领出的时间
	 */
	@Column(name = "export_time")
	@ApiModelProperty(value="领出的时间")
	private java.util.Date export_time;
	/**
	 * 领用人ID
	 */
	@Column(name = "user_id")
	@ApiModelProperty(value="领用人ID")
	private Integer user_id;
	/**
	 * 领用人姓名
	 */
	@Column(name = "user_name")
	@ApiModelProperty(value="领用人姓名")
	private String user_name;
	/**
	 * 领用人电话
	 */
	@Column(name = "user_phone")
	@ApiModelProperty(value="领用人电话")
	private String user_phone;
	/**
	 * 耗材使用的维修区域
	 */
	@Column(name = "area_id")
	@ApiModelProperty(value="耗材使用的维修区域")
	private Integer area_id;
	/**
	 * 备注
	 */
	@Column(name = "remark")
	@ApiModelProperty(value="备注")
	private String remark;
	/**
	 * 维修费用
	 */
	@Column(name = "repair_money")
	@ApiModelProperty(value="维修费用")
	private Double repair_money;
	/**
	 * 维修原因
	 */
	@Column(name = "repair_reason")
	@ApiModelProperty(value="维修原因")
	private String repair_reason;
	/**
	 * 物品分类ID
	 */
	@Column(name = "goods_type_id")
	@ApiModelProperty(value="物品分类ID")
	private Integer goods_type_id;
	/**
	 * 物品类型名称
	 */
	@Column(name = "goods_type_name")
	@ApiModelProperty(value="物品类型名称")
	private String goods_type_name;
	/**
	 * 库类型ID
	 */
	@Column(name = "repertory_type_id")
	@ApiModelProperty(value="库类型ID")
	private Integer repertory_type_id;
	/**
	 * 库类型名称
	 */
	@Column(name = "repertory_type_name")
	@ApiModelProperty(value="库类型名称")
	private String repertory_type_name;
	/**
	 * 故障地点名称
	 */
	@Column(name = "area_name")
	@ApiModelProperty(value="故障地点名称")
	private String area_name;
	/**
	 * 库名称ID
	 */
	@Column(name = "repertory_name_id")
	@ApiModelProperty(value="库名称ID")
	private Integer repertory_name_id;
	/**
	 * 库名称
	 */
	@Column(name = "repertory_name")
	@ApiModelProperty(value="库名称")
	private String repertory_name;
	/**
	 * 生产厂家ID
	 */
	@Column(name = "producer_id")
	@ApiModelProperty(value="生产厂家ID")
	private Integer producer_id;
	/**
	 * 生产厂家名称
	 */
	@Column(name = "producer_name")
	@ApiModelProperty(value="生产厂家名称")
	private String producer_name;
	/**
	 * 类型标识 0维修 1换新
	 */
	@Column(name = "flag")
	@ApiModelProperty(value="类型标识 0维修 1换新")
	private Integer flag;

	/**
	 * 出库单号
	 */
	@Column(name = "export_no")
	@ApiModelProperty(value="出库单号")
	private String export_no;

	@ApiModelProperty(value="出库单号")
	@Transient
	private Integer count_num;

	@ApiModelProperty(value="出库单号")
	@Transient
	private Integer sum_num;

	
}
