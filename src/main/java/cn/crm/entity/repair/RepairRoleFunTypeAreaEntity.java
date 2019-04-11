package cn.crm.entity.repair;

import lombok.Data;

import javax.persistence.Column;
import java.util.List;


@Data
public class RepairRoleFunTypeAreaEntity {
	@Column(name = "role_id")
	private Integer role_id;
	@Column(name = "role_name")
	private String role_name;
	@Column(name = "f_id")
	private String f_id;
	@Column(name = "f_name")
	private String f_name;
	@Column(name = "t_id")
	private String t_id;
	@Column(name = "t_name")
	private String t_name;
	@Column(name = "a_id")
	private String a_id;
	@Column(name = "a_name")
	private String a_name;
	@Column(name = "role_flag")
	private Integer role_flag;
	@Column(name = "is_default")
	private Integer is_default;


}
