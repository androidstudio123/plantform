package cn.crm.entity;

import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统日志表
 */
@Data
@Table(name="sys_log")
@ApiModel(description = "日志实体类")
public class SysLogEntity{
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator = "Mysql")
	/**
	 * 日志id
	 */
	@Column(name = "log_id")
	@ApiModelProperty(value = "日志id")
	private Integer log_id;
	/**
	 * 请求ip
	 */
	@Column(name = "log_ip")
	@ApiModelProperty(value = "请求ip")
	private String log_ip;
	/**
	 * 请求模块
	 */
	@Column(name = "log_fun")
	@ApiModelProperty(value = "请求模块")
	private String log_fun;
	/**
	 * 请求方法
	 */
	@Column(name = "log_method")
	@ApiModelProperty(value = "请求方法")
	private String log_method;
	/**
	 * 时间
	 */
	@Column(name = "log_time")
	@ApiModelProperty(value = "时间")
	private java.util.Date log_time;
	/**
	 * 操作人
	 */
	@Column(name = "log_user")
	@ApiModelProperty(value = "操作人")
	private String log_user;
	/**
	 * 请求参数
	 */
	@Column(name = "log_param")
	@ApiModelProperty(value = "请求参数")
	private String log_param;
	/**
	 * 请求结果
	 */
	@Column(name = "log_result")
	@ApiModelProperty(value = "请求结果")
	private String log_result;
	
	
	
}
