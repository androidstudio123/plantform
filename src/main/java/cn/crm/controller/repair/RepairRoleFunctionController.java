package cn.crm.controller.repair;

import cn.crm.service.repair.RepairRoleFunctionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RepairRoleFunctionController
 * @Author HJW
 * @Date 2019/3/21 15:28
 */
@RestController
@RequestMapping(value="/repairrolefunction")
public class RepairRoleFunctionController {

    private RepairRoleFunctionService repairRoleFunctionService;
}
