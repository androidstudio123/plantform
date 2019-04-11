package cn.crm.entity.terrace;

import javax.persistence.Table;
import javax.persistence.Column;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * TODO 在此加入类描述  学校和模块中间表实体类
 * @copyright
 * @author MYZ
 * @version  2019-03-25 10:01:25
 */

@Data
@Table(name="repair_school_module")
@ApiModel(description = "学校和模块中间表")
public class RepairSchoolModuleEntity{
		

	/**
	 * 模块id
	 */
	@Column(name = "module_id")
	@ApiModelProperty(value = "模块id")
	private Integer module_id;
	/**
	 * 学校id
	 */
	@Column(name = "school_id")
	@ApiModelProperty(value = "学校id")
	private Integer school_id;
	
	
	
}
