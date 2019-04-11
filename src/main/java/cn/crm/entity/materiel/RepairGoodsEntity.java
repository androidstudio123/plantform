package cn.crm.entity.materiel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


@Data
@Table(name="repair_goods")
@ApiModel(description = "库存物品类")
public class RepairGoodsEntity {
		

	/**
	 * 主键ID
	 */
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@ApiModelProperty(value="主键ID")
	private Integer id;
	/**
	 * 物品名称
	 */
	@Column(name = "goods_name")
	@ApiModelProperty(value="物品名称")
	private String goods_name;

	/**
	 * 物品类型id
	 */
	@Column(name = "goods_type_id")
	@ApiModelProperty(value="物品类型id")
	private Integer goods_type_id;
	/**
	 * 生产厂家id
	 */
	@Column(name = "producer_id")
	@ApiModelProperty(value="生产厂家id")
	private Integer producer_id;
	/**
	 * 库名称id
	 */
	@Column(name = "repertory_name_id")
	@ApiModelProperty(value="库名称id")
	private Integer repertory_name_id;
	/**
	 * 库类型id
	 */
	@Column(name = "repertory_type_id")
	@ApiModelProperty(value="库类型id")
	private Integer repertory_type_id;
	/**
	 * 数量
	 */
	@Column(name = "num")
	@ApiModelProperty(value="数量")
	private Integer num;

	/**
	 * 库名称
	 */
	@ApiModelProperty(value="库名称")
	@Transient
	private String repertory_name;

	/**
	 * 生产厂家名称
	 */
	@ApiModelProperty(value="生产厂家名称")
	@Transient
	private String producer_name;


	/**
	 * 库类型名称
	 */
	@ApiModelProperty(value="库类型名称")
	@Transient
	private String repertory_type_name;

	/**
	 * 物品分类名称
	 */
	@ApiModelProperty(value="物品分类名称")
	@Transient
	private String goods_type_name;




}
