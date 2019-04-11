package cn.crm.entity;

import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 房间组
 */

@Data
@Table(name="sys_roomgroup")
@ApiModel(description = "房间分组")
public class SysRoomgroupEntity{
		

	/**
	 * 房间组id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator = "Mysql")
	@ApiModelProperty(value = "ID,新增不用填写，修改必填")
	@Column(name = "roomGroup_id")
	private Integer roomGroup_id;
	/**
	 * 房间组名称
	 */
	@ApiModelProperty(value = "分组名字",required = true)
	@Column(name = "roomGroup_name")
	private String roomGroup_name;
	/**
	 * 描述
	 */
	@ApiModelProperty("描述")
	@Column(name = "roomGroup_desc")
	private String roomGroup_desc;
	/**
	 * 上级id
	 */
	@ApiModelProperty(value = "上级id",required = true)
	@Column(name = "roomGroup_parentId")
	private Integer roomGroup_parentId;
	
	
	
}
