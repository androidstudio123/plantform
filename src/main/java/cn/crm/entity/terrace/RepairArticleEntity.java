package cn.crm.entity.terrace;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.util.Date;

/**
 * TODO 在此加入类描述  文章实体类
 * @author MYZ
 * @version  2019-03-25 10:00:30
 */

@Data
@Table(name="repair_article")
@ApiModel(description = "文章类")
public class RepairArticleEntity{
		

	/**
	 * 文章id
	 */
	@Id
	@Column(name = "id")
	@ApiModelProperty(value = "文章id")
	private Integer id;
	/**
	 * 文章标题
	 */
	@Column(name = "article_title")
	@ApiModelProperty(value = "文章标题")
	private String article_title;
	/**
	 * 文章简介
	 */
	@ApiModelProperty(value = "文章简介")
	@Column(name="article_desc")
	private String article_desc;
	/**
	 * 文章内容
	 */
	@Column(name = "article_content")
	@ApiModelProperty(value = "文章内容")
	private String article_content;
	/**
	 * 文章类型id
	 */
	@Column(name = "article_typeId")
	@ApiModelProperty(value = "文章类型id")
	private Integer article_typeId;
	/**
	 * 轮播图路径
	 */
	@Column(name = "article_url")
	@ApiModelProperty(value = "轮播图路径")
	private String article_url;
	/**
	 * 文章标识:0,普通文章 1 轮播图 2公告
	 */
	@Column(name = "flag")
	@ApiModelProperty(value = "文章标识:0,普通文章 1 轮播图 2公告")
	private Integer flag;
	/**
	 * 文章发布时间
	 */
	@Column(name = "publish_time")
	@ApiModelProperty(value = "文章发布时间")
	private Date publish_time;
	/**
	 * 文章发布人id
	 */
	@Column(name = "publish_userId")
	@ApiModelProperty(value = "文章发布人id")
	private Integer publish_userId;
	/**
	 * 发布人名字
	 */
	@Column(name = "publish_userName")
	@ApiModelProperty(value = "发布人名字")
	private String publish_userName;

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



}
