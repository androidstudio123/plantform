package cn.crm.entity;

import javax.persistence.*;

import lombok.Data;

/**
 * 功能导航模块
 */
@Data
@Table(name="sys_func")
public class SysFuncEntity{
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator = "Mysql")
	/**
	 * 功能id
	 */
	@Column(name = "fun_id")
	private Integer fun_id;
	/**
	 * 功能名称
	 */
	@Column(name = "fun_name")
	private String fun_name;
	/**
	 * 功能路径地址
	 */
	@Column(name = "fun_url")
	private String fun_url;
	/**
	 * 功能图标
	 */
	@Column(name = "fun_icon")
	private String fun_icon;
	/**
	 * 功能类型
	 */
	@Column(name = "fun_type")
	private Integer fun_type;
	/**
	 * 功能代码
	 */
	@Column(name = "fun_code")
	private String fun_code;
	/**
	 * 排序
	 */
	@Column(name = "fun_sort")
	private Integer fun_sort;
	/**
	 * 功能描述表
	 */
	@Column(name = "fun_desc")
	private String fun_desc;
	/**
	 * 上级id
	 */
	@Column(name = "fun_parentId")
	private Integer fun_parentId;

	/**
	 * 所需要的权限
	 */
	@Column(name = "fun_auth")
	private String fun_auth;
	
}
