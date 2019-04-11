package cn.crm.controller.terrace;

import cn.crm.result.ResultData;
import cn.crm.service.terrace.RepairArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Decipriton:手机端查询文章信息
 * @author MYZ
 * @version V1.0
 * @Package cn.crm.controller.terrace
 * @date 2019/3/25 17:37
 */
@RestController
@RequestMapping("repairAppArticle")
@Api(description = "手机端文章信息管理")
public class RepairArticleAppController {
    @Autowired
    private RepairArticleService repairArticleService;

    /**
     * 手机端查询文章信息
     *
     * @param request
     * @param typeId  文章类型
     * @return
     */
    @GetMapping("findAppArticle")
    @ApiOperation(notes = "手机端查询文章信息(根据文章类型查询)", value = "手机端查询文章信息(根据文章类型查询)")
    @ApiImplicitParam(name = "typeId", value = "文章类型", dataType = "int", required = true, paramType = "query")
    public ResultData findAppArticle(HttpServletRequest request, Integer typeId) {
        return repairArticleService.findAppArticle(request, typeId);
    }
}
