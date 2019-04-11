package cn.crm.entity.terrace;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * TODO 在此加入类描述  反馈内容实体类
 *
 * @author MYZ
 * @version 2019-03-28 15:13:17
 * @copyright
 */

@Data
@Table(name = "repair_feedback")
@ApiModel(description = "反馈信息实体类")
public class RepairFeedbackEntity {


    /**
     * 反馈内容id
     */
    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "")
    private Integer id;
    /**
     * 反馈内容
     */
    @Column(name = "feedback_content")
    @ApiModelProperty(value = "")
    private String feedback_content;
    /**
     * 反馈人id
     */
    @Column(name = "user_id")
    @ApiModelProperty(value = "")
    private Integer user_id;
    /**
     * 反馈人名称
     */
    @Column(name = "user_name")
    @ApiModelProperty(value = "")
    private String user_name;
    /**
     * 反馈时间
     */
    @Column(name = "feedback_time")
    @ApiModelProperty(value = "")
    private java.util.Date feedback_time;
    /**
     * 反馈类型
     */
    @Column(name = "status")
    @ApiModelProperty(value = "")
    private Integer status;


}
