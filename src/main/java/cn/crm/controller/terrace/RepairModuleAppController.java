package cn.crm.controller.terrace;

import cn.crm.result.ResultData;
import cn.crm.service.terrace.RepairModuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author MYZ
 * @version V1.0
 * @Description: 手机端查询模块信息
 * @Package cn.crm.controller.terrace
 * @date 2019/3/25 17:37
 */
@Api(description = "手机端模块信息管理")
@RequestMapping("repairAppModule")
@RestController
public class RepairModuleAppController {

    @Autowired
    private RepairModuleService repairModuleService;

    /**
     * 手机端查询模块信息
     *
     * @param request
     * @return
     */
    @GetMapping("findAppModule")
    @ApiOperation(notes = "手机端查询模块信息", value = "手机端查询模块信息")
    public ResultData findAppModule(HttpServletRequest request) {
        return repairModuleService.findAppModule(request);
    }
}
