package cn.crm.entity.repair;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @ClassName OrderEntity
 * @Author HJW
 * @Date 2019/3/21 15:00
 */

@Data
@Table(name="`order`")
@ApiModel(description = "报修工单类")
public class OrderEntity {

    @ApiModelProperty(value="工单ID")
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer order_id;

    @ApiModelProperty(value="报修人ID")
    private Integer bx_id;

    @ApiModelProperty(value="维修人ID")
    private Integer worker_id;

    @ApiModelProperty(value="工单状态 0待接单 1已接单,维修中 2已完工,待评价 3已评价,待回访  4已回访 5已取消")
    private Integer order_status;

    @ApiModelProperty(value="故障类型ID")
    private Integer fix_type_id;

    @ApiModelProperty(value="故障区域ID")
    private Integer area_id;

    @ApiModelProperty(value="工单编号 年月日8位数字+5位随机数")
    private String order_no;

    @ApiModelProperty(value="故障描述")
    private String order_desc;

    @ApiModelProperty(value="报修人电话")
    private String bx_phone;

    @ApiModelProperty(value="报修人姓名")
    private String bx_name;

    @ApiModelProperty(value="报修时间")
    private Date create_time;

    @ApiModelProperty(value="接单时间")
    private Date start_time;

    @ApiModelProperty(value="完工时间")
    private Date finish_time;

    @ApiModelProperty(value="工单取消时间")
    private Date cancel_time;

    @ApiModelProperty(value="回访时间")
    private Date visiting_time;

    @ApiModelProperty(value="此工单所属的学校")
    private Integer school_id;

    @ApiModelProperty(value="故障区域名称")
    private String area_name;

    @ApiModelProperty(value="取消订单者的ID")
    private Integer canceled_id;

    @ApiModelProperty(value="预约开始时间")
    private Date advance_start_time;

    @ApiModelProperty(value="预约开始时间")
    private Date advance_finish_time;

    @ApiModelProperty(value="房间")
    private String room;

    @ApiModelProperty(value="故障图片地址")
    private String urls;


}
