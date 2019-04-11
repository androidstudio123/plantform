package cn.crm.entity;

import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;


/**
 * 页面菜单
 */
@Data
@Table(name="sys_resource")
@ApiModel(description = "资源路径")
public class SysResourceEntity{


	/**
	 * 资源路径id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator = "Mysql")
	@Column(name = "res_id")
	private Integer res_id;
	/**
	 * 名称
	 */
	@Column(name = "res_name")
	private String res_name;
	/**
	 * 父类id
	 */
	@Column(name = "res_parentId")
	private Integer res_parentId;
	/**
	 * 资源路径地址
	 */
	@Column(name = "res_url")
	private String res_url;
	/**
	 * 资源类型
	 */
	@Column(name = "res_type")
	private String res_type;
	/**
	 * 图标
	 */
	@Column(name = "res_icon")
	private String res_icon;
	/**
	 * 描述
	 */
	@Column(name = "res_description")
	private String res_description;
	/**
	 * 日期
	 */
	@Column(name = "res_date")
	private java.util.Date res_date = new Date();
	/**
	 * 状态
	 */
	@Column(name = "res_code")
	private String res_code;
	/**
	 * 0 已删除，1 未删除
	 */
	@Column(name = "res_state")
	private Integer res_state;
	/**
	 * 排序
	 */
	@Column(name = "res_sort")
	private Integer res_sort;
	/**
	 * 备注
	 */
	@Column(name = "res_mark")
	private String res_mark;
	
	
	
}
