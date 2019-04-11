package cn.crm.entity.repair;

import javax.persistence.Table;
import javax.persistence.Column;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Table(name="order_comment")
public class OrderCommentEntity{
		

	/**
	 * 工单评价ID
	 */
	@Column(name = "comment_id")
	private Integer comment_id;
	/**
	 * 评价人id
	 */
	@Column(name = "commentator_id")
	private Integer commentator_id;

	/**
	 * 评论内容
	 */
	@Column(name = "comment_content")
	private String comment_content;
	/**
	 * 评论时间
	 */
	@Column(name = "comment_time")
	private java.util.Date comment_time;
	/**
	 * 评论的工单ID
	 */
	@Column(name = "order_id")
	private Integer order_id;
	/**
	 * 工单编号
	 */
	@Column(name = "order_no")
	private String order_no;


	/**
	 * 1好评 2中评 3差评"
	 */
	private Integer rank;
	
	
	
}
