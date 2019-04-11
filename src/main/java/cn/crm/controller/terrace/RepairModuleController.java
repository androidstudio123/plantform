package cn.crm.controller.terrace;


import cn.crm.entity.terrace.RepairModuleEntity;
import cn.crm.result.ResultData;
import cn.crm.service.terrace.RepairModuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * TODO 在此加入类描述
 *
 * @author MYZ
 * @version 2019-03-25 10:00:12
 */
@Api(description = "模块信息管理")
@RestController
@RequestMapping(value = "/repairmodule")
public class RepairModuleController {


    @Autowired
    private RepairModuleService repairModuleService;


    /**
     * 查询模块信息
     *
     * @param keyword 可根据模块名模糊查询
     * @param status      根据状态查询
     * @return
     */
    @GetMapping("findAllModule")
    @ApiOperation(notes = "查询模块信息(可根据模块名模糊查询/状态查询)", value = "查询模块信息(可根据模块名模糊查询/状态查询)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "模块名", dataType = "string", required = false, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "模块状态", dataType = "int", required = false, paramType = "query")
    })
    public ResultData findAllModule(String keyword, Integer status, HttpServletRequest request) {
        return repairModuleService.findAllModule(keyword, status, request);
    }

    /**
     * 查询模块信息
     *
     * @return
     */
    @GetMapping("findModule")
    @ApiOperation(notes = "查询所有模块信息", value = "查询所有模块信息")
    public ResultData findModule(HttpServletRequest request) {
        return repairModuleService.findModule(request);
    }

    /**
     * 根据模块id查询一个模块信息
     *
     * @param id 模块id
     * @return
     */
    @GetMapping("findModuleById")
    @ApiOperation(notes = "根据模块id查询一个模块信息", value = "根据模块id查询一个模块信息")
    @ApiImplicitParam(name = "id", value = "模块id", dataType = "int", required = true, paramType = "query")
    public ResultData findModuleById(Integer id) {
        return repairModuleService.findModuleById(id);
    }

    /**
     * 新增模块信息
     *
     * @param repairModuleEntity 模块实体类
     * @return
     */
    @PostMapping("addModule")
    @ApiOperation(notes = "新增模块信息", value = "新增模块信息")
    public ResultData addModule(@RequestBody RepairModuleEntity repairModuleEntity, HttpServletRequest request) {
        return repairModuleService.addModule(repairModuleEntity, request);
    }

    /**
     * 修改模块信息
     *
     * @param repairModuleEntity 模块实体类
     * @return
     */
    @PostMapping("updateModule")
    @ApiOperation(notes = "修改模块信息", value = "修改模块信息")
    public ResultData updateModule(@RequestBody RepairModuleEntity repairModuleEntity, HttpServletRequest request) {
        return repairModuleService.updateModule(repairModuleEntity, request);
    }

    /**
     * 删除一个模块信息
     *
     * @param id 模块id
     * @return
     */
    @GetMapping("deleteModule")
    @ApiOperation(notes = "删除一个模块信息", value = "删除一个模块信息")
    @ApiImplicitParam(name = "id", value = "模块id", dataType = "int", required = true, paramType = "query")
    public ResultData deleteModule(Integer id) {
        return repairModuleService.deleteModule(id);
    }


    /**
     * 上传模块图标
     *
     * @param request
     * @param file
     * @return
     */
    @PostMapping("uploadModuleImage")
    @ApiOperation(value = "上传模块图标", notes = "上传模块图标")
    @ApiImplicitParam(name = "file", value = "上传文件", required = true)
    public ResultData uploadModuleImage(HttpServletRequest request, MultipartFile file) {
        return repairModuleService.uploadModuleImage(request, file);
    }

}
