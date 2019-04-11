package cn.crm.entity.repair;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
/**
 * TODO 在此加入类描述
 * @copyright {@link hzg}
 * @author hzg
 * @version  2019-03-26 09:19:29
 * @see cn.yr.entity.DefaultFunction
 */

@Data
@Table(name="default_function")
public class DefaultFunctionEntity{
		

	/**
	 * 默认功能id
	 */
	@Id
	@Column(name = "function_id")
	private Integer function_id;
	/**
	 * 默认功能名称
	 */
	@Column(name = "function_name")
	private String function_name;
	/**
	 * 默认功能图标
	 */
	@Column(name = "function_icon")
	private String function_icon;
	/**
	 * 跳转路径
	 */
	@Column(name = "function_url")
	private String function_url;
	
	
	
}
