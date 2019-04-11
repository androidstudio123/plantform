package cn.crm.controller.terrace;


import cn.crm.entity.terrace.RepairArticleTypeEntity;
import cn.crm.result.ResultData;
import cn.crm.service.terrace.RepairArticleTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO 在此加入类描述  文章类型控制层实现类
 *
 * @author MYZ
 * @version 2019-03-25 10:01:09
 */
@Api(description = "文章类型管理")
@RestController
@RequestMapping(value = "/repairarticletype")
public class RepairArticleTypeController {


    @Autowired
    private RepairArticleTypeService repairArticleTypeService;

    /**
     * @param request
     * @param keyword  文章类型名
     * @param status  文章类型状态
     * @Description :  查询文章类型(可根据类型状态和类型名查询)
     * @Return : cn.crm.result.ResultData
     * @Author : MYZ
     * @Date : 2019/3/27 14:37
     */
    @GetMapping("findAllArticleType")
    @ApiOperation(notes = "查询文章类型信息(可根据类型状态和类型名查询)", value = "查询文章类型信息(可根据类型状态和类型名查询)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword",value = "文章类型名",dataType = "String",required =false ,paramType = "query"),
            @ApiImplicitParam(name = "status",value = "文章类型状态",dataType = "Integer",required = false,paramType = "query")
    })
    public ResultData findAllArticleType(HttpServletRequest request,String keyword, Integer status) {
        return repairArticleTypeService.findAllArticleType(request,keyword,status);
    }

    /**
     * @param request
     * @Description :  查询文章类型
     * @Return : cn.crm.result.ResultData
     * @Author : MYZ
     * @Date : 2019/3/27 14:37
     */
    @GetMapping("findArticleType")
    @ApiOperation(notes = "查询文章类型信息", value = "查询文章类型信息")
    public ResultData findArticleType(HttpServletRequest request){
        return repairArticleTypeService.findArticleType(request);
    }

    /**
     * 根据文章类型id查询一个类型信息
     *
     * @param id 文章类型id
     * @return
     */
    @GetMapping("findArticleTypeById")
    @ApiOperation(notes = "根据文章类型id查询一个类型信息", value = "根据文章类型id查询一个类型信息")
    @ApiImplicitParam(name = "id", value = "文章类型id", dataType = "int", required = true, paramType = "query")
    public ResultData findArticleTypeById(Integer id) {
        return repairArticleTypeService.findArticleTypeById(id);
    }

    /**
     * 新增文章类型信息
     *
     * @param repairArticleTypeEntity 文章类型实体类
     * @return
     */
    @PostMapping("addArticleType")
    @ApiOperation(notes = "新增文章类型信息", value = "新增文章类型信息")
    public ResultData addArticleType(@RequestBody RepairArticleTypeEntity repairArticleTypeEntity, HttpServletRequest request) {
        return repairArticleTypeService.addArticleType(repairArticleTypeEntity, request);
    }

    /**
     * 修改文章类型信息
     *
     * @param repairArticleTypeEntity 文章类型实体类
     * @return
     */
    @PostMapping("updateArticleType")
    @ApiOperation(notes = "修改文章类型信息", value = "修改文章类型信息")
    public ResultData updateArticleType(@RequestBody RepairArticleTypeEntity repairArticleTypeEntity, HttpServletRequest request) {
        return repairArticleTypeService.updateArticleType(repairArticleTypeEntity, request);
    }

    /**
     * 删除一个文章类型信息
     *
     * @param id 文章类型id
     * @return
     */
    @GetMapping("deleteArticleType")
    @ApiOperation(notes = "删除一个文章类型信息", value = "删除一个文章类型信息")
    @ApiImplicitParam(name = "id", value = "文章类型id", dataType = "int", required = true, paramType = "query")
    public ResultData deleteArticleType(Integer id) {
        return repairArticleTypeService.deleteArticleType(id);
    }


}
