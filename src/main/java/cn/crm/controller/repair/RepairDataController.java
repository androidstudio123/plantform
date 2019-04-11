package cn.crm.controller.repair;

import cn.crm.service.repair.RepairDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RepairDataController
 * @Author HJW
 * @Date 2019/3/21 15:25
 */
@RestController
@RequestMapping(value="/repairdata")
public class RepairDataController {

    @Autowired
    private RepairDataService repairDataService;
}
