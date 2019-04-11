package cn.crm.entity.terrace;

import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * TODO 在此加入类描述  文章类型实体类
 * @copyright
 * @author MYZ
 * @version  2019-03-25 10:01:09
 */

@Data
@Table(name="repair_article_type")
@ApiModel(description = "文章类型类")
public class RepairArticleTypeEntity{
		

	/**
	 * 文章类型id
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "JDBC")
	@ApiModelProperty(value = "文章类型id")
	private Integer id;
	/**
	 * 文章类型名称
	 */
	@Column(name = "articleType_title")
	@ApiModelProperty(value = "文章类型名称")
	private String articleType_title;

	/**
	 * 文章类型创建时间
	 */
	@Column(name = "create_time")
	@ApiModelProperty(value = "文章类型创建时间")
	private Date create_time;
	/**
	 * 文章类型创建人id
	 */
	@Column(name = "create_userId")
	@ApiModelProperty(value = "文章类型创建人id")
	private Integer create_userId;
	/**
	 * 文章类型创建人名字
	 */
	@Column(name = "create_userName")
	@ApiModelProperty(value = "文章类型创建人名字")
	private String create_userName;

	/**
	 * 文章修改时间
	 */
	@Column(name = "update_time")
	@ApiModelProperty(value = "文章修改时间")
	private Date update_time;
	/**
	 * 文章修改人id
	 */
	@Column(name = "update_userId")
	@ApiModelProperty(value = "文章修改人id")
	private Integer update_userId;
	/**
	 * 修改人名字
	 */
	@Column(name = "update_userName")
	@ApiModelProperty(value = "修改人名字")
	private String update_userName;

	/**
	 * 文章类型状态:0 禁用 1启用
	 */
	@Column(name = "articleType_state")
	@ApiModelProperty(value = "文章类型状态:0 禁用 1启用")
	private Integer articleType_state;
	/**
	 * 学校ids
	 */
	@Transient
	@ApiModelProperty(value = "学校ids")
	private  String schoolIds;
	
	
	
}
