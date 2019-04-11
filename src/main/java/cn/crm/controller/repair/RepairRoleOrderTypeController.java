package cn.crm.controller.repair;


import cn.crm.service.repair.RepairRoleOrderTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value="/repairRoleOrderType")
public class RepairRoleOrderTypeController {


	@Autowired
	private RepairRoleOrderTypeService repairRoleOrderTypeService;

}
