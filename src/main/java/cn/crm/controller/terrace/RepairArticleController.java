package cn.crm.controller.terrace;


import cn.crm.entity.terrace.RepairArticleEntity;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.terrace.RepairArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO 在此加入类描述 文章信息控制层实现类
 *
 * @author MYZ
 * @version 2019-03-25 10:00:30
 * @copyright
 */
@Api(description = "文章信息管理")
@RestController
@RequestMapping(value = "/repairarticle")
public class RepairArticleController {


    @Autowired
    private RepairArticleService repairArticleService;


    /**
     * 查询文章信息
     *
     * @param typeId 可根据类型id查询
     * @return
     */
    @GetMapping("findArticle")
    @ApiOperation(notes = "查询文章信息(可根据类型id查询)", value = "查询文章信息(可根据类型id查询)")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "typeId", value = "文章类型id", dataType = "int", required = false, paramType = "query"),
    @ApiImplicitParam(name = "keyword", value = "文章标题", dataType = "String", required = false, paramType = "query")
    })
    public ResultData findArticle(String  keyword, Integer typeId,HttpServletRequest request) {
         return repairArticleService.findAllArticle(keyword,typeId,request);
    }

    /**
     * 根据文章id查询一条文章信息
     *
     * @param id 文章id
     * @return
     */
    @GetMapping("findArticleById")
    @ApiOperation(notes = "根据文章id查询一条文章信息", value = "根据文章id查询一条文章信息")
    @ApiImplicitParam(name = "id", value = "文章id", dataType = "int", required = true, paramType = "query")
    public ResultData findArticleById(Integer id) {
        RepairArticleEntity articleEntity = repairArticleService.findArticleById(id);
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), articleEntity);
    }

    /**
     * 新增文章信息
     *
     * @param repairArticleEntity 文章实体类
     * @return
     */
    @PostMapping("addArticle")
    @ApiOperation(notes = "新增文章信息", value = "新增文章信息")
    public ResultData addArticle(@RequestBody RepairArticleEntity repairArticleEntity, HttpServletRequest request) {
        return repairArticleService.addArticle(repairArticleEntity, request);
    }

    /**
     * 修改文章信息
     *
     * @param repairArticleEntity 文章实体类
     * @return
     */
    @PostMapping("updateArticle")
    @ApiOperation(notes = "修改文章信息", value = "修改文章信息")
    public ResultData updateArticle(@RequestBody RepairArticleEntity repairArticleEntity, HttpServletRequest request) {
        return repairArticleService.updateArticle(repairArticleEntity, request);
    }

    /**
     * 删除一条文章信息
     *
     * @param id 文章id
     * @return
     */
    @GetMapping("deleteArticle")
    @ApiOperation(notes = "删除一条文章信息", value = "删除一条文章信息")
    @ApiImplicitParam(name = "id", value = "文章id", dataType = "int", required = true, paramType = "query")
    public ResultData deleteArticle(Integer id) {
        return repairArticleService.deleteArticle(id);
    }

    /**
     * 上传轮播图
     * @param request
     * @param file  上传文件
     * @return
     */
    @PostMapping("uploadMRotationchart")
    @ApiOperation(value = "上传轮播图", notes = "上传轮播图")
    @ApiImplicitParam(name = "file", value = "上传文件", required = true)
    public ResultData  uploadMRotationchart(HttpServletRequest request, MultipartFile file){
        return repairArticleService.uploadMRotationchart(request,file);
    }


}
