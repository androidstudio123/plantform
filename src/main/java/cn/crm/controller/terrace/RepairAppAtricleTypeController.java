package cn.crm.controller.terrace;

import cn.crm.result.ResultData;
import cn.crm.service.terrace.RepairArticleTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author MYZ
 * @version V1.0
 * @Description: 手机端查询文章类型
 * @Package cn.crm.controller.terrace
 * @date 2019/3/26 14:19
 */
@Api(description = "手机端文章类型管理")
@RestController
@RequestMapping("repairAppArticleType")
public class RepairAppAtricleTypeController {

    @Autowired
    private RepairArticleTypeService repairArticleTypeService;

    /**
     * 手机端查询文章类型
     *
     * @param request
     * @return
     */
    @GetMapping("findAppArticleType")
    @ApiOperation(notes = "手机端查询文章类型",value = "手机端查询文章类型")
    public ResultData findAppArticleType(HttpServletRequest request) {
       return  repairArticleTypeService.findAppArticleType(request);
    }
}
