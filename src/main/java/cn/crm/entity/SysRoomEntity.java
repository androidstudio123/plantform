package cn.crm.entity;

import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 房间
 */
@Data
@Table(name="sys_room")
@ApiModel("房间对象")
public class SysRoomEntity{
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator = "Mysql")
	/**
	 * 房间id
	 */
	@Column(name = "room_id")
	@ApiModelProperty(value = "ID，新增是不要需要，修改时必填")
	private Integer room_id;
	/**
	 * 房间名称
	 */
	@ApiModelProperty(value = "房间名称",required = true)
	@Column(name = "room_name")
	private String room_name;
	/**
	 * 房间锁sn号
	 */
	@ApiModelProperty(value = "房间锁sn号",required = true)
	@Column(name = "room_sn")
	private String room_sn;
	/**
	 * 房间锁imei
	 */
	@ApiModelProperty(value = "房间锁imei",required = true)
	@Column(name = "room_imei")
	private String room_imei;
	/**
	 * 房间类型
	 */
	@ApiModelProperty(value = "房间类型ID",required = true)
	@Column(name = "roomType_id")
	private Integer roomType_id;
	/**
	 * 房间类型名称
	 */
	@ApiModelProperty(value = "房间类型名称",required = true)
	@Column(name = "roomType_name")
	private String roomType_name;
	/**
	 * 房间组id
	 */
	@ApiModelProperty(value = "房间组id",required = true)
	@Column(name = "roomGroup_id")
	private Integer roomGroup_id;
	
	
	
}
