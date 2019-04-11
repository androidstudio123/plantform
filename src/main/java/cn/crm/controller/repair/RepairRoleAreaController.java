package cn.crm.controller.repair;

import cn.crm.result.ResultData;
import cn.crm.service.repair.RepairRoleAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RepairRoleAreaController
 * @Author HJW
 * @Date 2019/3/21 15:26
 */
@RestController
@RequestMapping(value="/repairrolearea")
@Api(description = "角色所对应的区域相关操作")
public class RepairRoleAreaController {

    private RepairRoleAreaService repairRoleAreaService;


    /**
     * 根据用户查询此用户所负责的区域
     * @param userId  用户ID
     * @return
     */
    @GetMapping("/findRoleAreaByUser")
    @ApiOperation(value = "根据用户查询此用户所负责的区域", notes = "根据用户查询此用户所负责的区域")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "query")
    public ResultData findRoleAreaByUser(Integer userId){
        ResultData resultData = repairRoleAreaService.findRoleAreaByUser(userId);
        return resultData;
    }
}
