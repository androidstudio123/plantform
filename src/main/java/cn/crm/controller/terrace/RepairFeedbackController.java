package cn.crm.controller.terrace;


import cn.crm.entity.terrace.RepairFeedbackEntity;
import cn.crm.result.ResultData;
import cn.crm.service.terrace.RepairFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO 在此加入类描述  反馈信息控制层实现类
 *
 * @author hzg
 * @version 2019-03-28 15:13:17
 * @copyright
 */
@RestController
@RequestMapping(value = "/repairfeedback")
@Api(description = "反馈信息管理")
public class RepairFeedbackController {


    @Autowired
    private RepairFeedbackService repairFeedbackService;

    /**
     * 查询反馈信息
     *
     * @param user_name 根据用户名模糊查询
     * @return
     */
    @GetMapping("findAllFeedback")
    @ApiOperation(notes = "查询反馈信息(根据用户名模糊查询)", value = "查询反馈信息(根据用户名模糊查询)")
    @ApiImplicitParam(name = "user_name", value = "用户名", dataType = "String", required = false, paramType = "query")
    public ResultData findAllFeedback(String user_name, HttpServletRequest request) {
        return repairFeedbackService.findAllFeedback(user_name, request);
    }

    /**
     * 新增反馈信息
     *
     * @param repairFeedbackEntity 反馈信息实体类
     * @return
     */
    @PostMapping("addFeeback")
    @ApiOperation(notes = "新增反馈信息", value = "新增反馈信息")
    public ResultData addFeeback(@RequestBody RepairFeedbackEntity repairFeedbackEntity, HttpServletRequest request) {
        return repairFeedbackService.addFeeback(repairFeedbackEntity, request);
    }

}
