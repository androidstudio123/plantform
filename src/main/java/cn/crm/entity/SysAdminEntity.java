package cn.crm.entity;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 后台管理员
 */
@Data
@Table(name="sys_admin")
public class SysAdminEntity implements Serializable{


	private static final long serialVersionUID = -7622077072641192849L;

	/**
	 * 管理员id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator = "Mysql")
	@Column(name = "admin_id")
	private Integer admin_id;
	/**
	 * 昵称
	 */
	@Column(name = "admin_nickname")
	private String admin_nickname;
	/**
	 * 登陆账号
	 */
	@Column(name = "admin_name")
	private String admin_name;
	/**
	 * 密码
	 */
	@Column(name = "admin_password")
	private String admin_password;
	/**
	 * 创建人
	 */
	@Column(name = "admin_createName")
	private String admin_createName;
	/**
	 * 创建时间
	 */
	@Column(name = "admin_createTime")
	private java.util.Date admin_createTime;
	/**
	 * 修改人
	 */
	@Column(name = "admin_updateName")
	private String admin_updateName;
	/**
	 * 修改时间
	 */
	@Column(name = "admin_updateTime")
	private java.util.Date admin_updateTime;
	/**
	 * 父级id
	 */
	@Column(name = "admin_parentId")
	private Integer admin_parentId;
	/**
	 * 创建人id
	 */
	@Column(name = "admin_createId")
	private Integer admin_createId;
	/**
	 * 修改人id
	 */
	@Column(name = "admin_updateId")
	private Integer admin_updateId;
	/**
	 * 密码盐
	 */
	@Column(name = "admin_salt")
	private String admin_salt;
	/**
	 * 角色id
	 */
	@Column(name = "role_id")
	private Integer role_id;

	/**
	 * 管理员状态 0 正常 1 冻结 2 已删除
	 */
	@Column(name = "admin_status")
	private Integer admin_status;

	/**
	 * 管理员手机
	 */
	@Column(name = "admin_phone")
	private String admin_phone;


	/**
	 * 角色名称
	 */
	@Column(name = "role_name")
	private String role_name;

	/**
	 * 管理员邮箱
	 */
	@Column(name = "admin_mail")
	private String admin_mail;
	/**
	 * 管理员工号
	 */
	@Column(name = "admin_no")
	private String admin_no;

	/**
	 * 出库角色ID
	 */
	@Column(name = "export_role_id")
	private Integer export_role_id;

	/**
	 * 用户组
	 */
	private List<SysUsergroupEntity> usergroupEntities;

	/**
	 * 房间组
	 */
	private List<SysRoomgroupEntity> roomgroupEntities;








}
