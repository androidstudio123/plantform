package cn.crm.entity.repair;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

import lombok.Data;


@Data
@Table(name="repair_function")
public class RepairFunctionEntity{
		

	/**
	 * 主键ID
	 */
	@Id
	@Column(name = "function_id")
	private Integer function_id;
	/**
	 * 功能名称
	 */
	@Column(name = "function_name")
	private String function_name;
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;
	/**
	 * 功能图标
	 */
	@Column(name = "function_icon")
	private String function_icon;

	/**
	 * 前端路由
	 */
	@Column(name = "url")
	private String url;

	/**
	 * 功能编号
	 */
	@Column(name = "function_no")
	private String function_no;

	/**
	 * 标识 0填单报修 1图标 2按钮
	 */
	private Integer flag;

	
	
}
