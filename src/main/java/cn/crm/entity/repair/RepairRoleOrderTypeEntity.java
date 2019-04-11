package cn.crm.entity.repair;

import javax.persistence.Table;
import javax.persistence.Column;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
/**
 * TODO 在此加入类描述
 * @copyright {@link hzg}
 * @author hzg
 * @version  2019-03-22 10:36:04
 * @see cn.yr.entity.RepairRoleOrderType
 */

@Data
@Table(name="repair_role_order_type")
public class RepairRoleOrderTypeEntity{
		

	/**
	 * 报修角色id
	 */
	@Column(name = "role_id")
	private Integer role_id;
	/**
	 * 工单类型id
	 */
	@Column(name = "order_type_id")
	private Integer order_type_id;
	
	
	
}
