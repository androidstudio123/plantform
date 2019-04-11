package cn.crm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**
 * @ClassName {NAME}
 * @Description TODO
 * @Author MYZ
 * @Date 2019/3/15 0015 10:19
 */
@Data
@ApiModel(description = "报修管理组合实体类")
public class OrderAreaTypeUserVo {

    /**
     * 管理员id
     */
    @Column(name = "order_id")
    @ApiModelProperty(value = "订单id")
    private Integer order_id;

    /**
     * 用户组id
     */
    @Column(name = "order_no")
    @ApiModelProperty(value = "订单编号")
    private String order_no;
    /**
     * 用户组id
     */
    @Column(name = "order_status")
    @ApiModelProperty(value = "订单状态")
    private Integer order_status;
    @Column(name = "create_time")
    @ApiModelProperty(value = "报修时间")
    private Date create_time;
    @Column(name = "start_time")
    @ApiModelProperty(value = "接单时间")
    private Date start_time;
    @Column(name = "finish_time")
    @ApiModelProperty(value = "完成时间")
    private Date finish_time;
    @Column(name = "order_desc")
    @ApiModelProperty(value = "订单描述")
    private String order_desc;

    @Column(name = "a_name")
    @ApiModelProperty(value = "订单描述")
    private String a_name;

    @Column(name = "bx_name")
    @ApiModelProperty(value = "订单描述")
    private String bx_name;
    /**
     * 描述
     */
    @Column(name = "area_id")
    @ApiModelProperty(value = "区域id")
    private Integer area_id;

    @Column(name = "area_name")
    @ApiModelProperty(value = "区域名称")
    private String area_name;
    /**
     * userGroup_createTime
     */
    @Column(name = "parent_id")
    @ApiModelProperty(value = "上级id")
    private Integer parent_id;
    /**
     * 创建人id
     */
    @Column(name = "school_id")
    @ApiModelProperty(value = "学校id")
    private Integer school_id;
    /**
     * userGroup_updateTime
     */
    @Column(name = "school_name")
    @ApiModelProperty(value = "学校名称")
    private String school_name;
    /**
     * 修改人id
     */
    @Column(name = "type")
    @ApiModelProperty(value = "区域类型")
    private Integer type;
    /**
     * 父级id
     */
    @Column(name = "type_id")
    @ApiModelProperty(value = "工单类型id")
    private Integer type_id;
    /**
     * 父级id
     */
    @Column(name = "type_name")
    @ApiModelProperty(value = "工单类型名称")
    private String type_name;
    /**
     * 父级id
     */
    @Column(name = "baox_name")
    @ApiModelProperty(value = "报修人抿成")
    private String baox_name;
    /**
     * 父级id
     */
    @Column(name = "baox_nick")
    @ApiModelProperty(value = "报修人昵称")
    private String baox_nick;
    /**
     * 父级id
     */
    @Column(name = "weix_name")
    @ApiModelProperty(value = "维修人名称")
    private String weix_name;
    /**
     * 父级id
     */
    @Column(name = "weix_nick")
    @ApiModelProperty(value = "维修人昵称")
    private String weix_nick;
    @Column(name = "rank")
    @ApiModelProperty(value = "评价星际")
    private Integer rank;
    @Column(name = "comment_content")
    @ApiModelProperty(value = "评价内容")
    private String comment_content;

}
