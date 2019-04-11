package cn.crm.entity;

import javax.persistence.*;

import lombok.Data;

/**
 * 房间类型
 */

@Data
@Table(name="sys_room_type")
public class SysRoomTypeEntity{
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator = "Mysql")
	/**
	 * 房间类型id
	 */
	@Column(name = "roomType_id")
	private Integer roomType_id;
	/**
	 * 房间类型名称
	 */
	@Column(name = "roomType_name")
	private String roomType_name;
	/**
	 * 房间类型说明
	 */
	@Column(name = "roomType_desc")
	private String roomType_desc;

}
