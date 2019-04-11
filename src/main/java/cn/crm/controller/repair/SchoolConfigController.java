package cn.crm.controller.repair;


import cn.crm.entity.repair.SchoolConfigEntity;
import cn.crm.logaop.SystemLog;
import cn.crm.result.ResultData;
import cn.crm.service.repair.SchoolConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/schoolconfig")
@Api(description = "学校配置信息管理")
public class SchoolConfigController {


    @Autowired
    private SchoolConfigService schoolConfigService;

    /**
     * 查询所有学校配置
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("findSchoolConfig")
    @SystemLog(methods = "查询所有学校配置", module = "查询所有学校配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "当前页数", value = "pageNum", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "每页多少条", value = "pageSize", dataType = "int", paramType = "query")
    })
    public ResultData findSchoolConfig(Integer pageNum, Integer pageSize, String schoolName, Integer status) {
        ResultData resultData = schoolConfigService.findSchoolConfig(pageNum, pageSize, schoolName, status);
        return resultData;
    }

    /**
     * 根据经纬度查询学校配置信息
     * @param lat
     * @param lon
     * @param user_id
     * @return
     */
    @GetMapping("findSchoolConfigByJWD")
    @SystemLog(methods = "根据经纬度查询所有学校配置", module = "根据经纬度查询所有学校配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "当前页数", value = "pageNum", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "每页多少条", value = "pageSize", dataType = "int", paramType = "query")
    })
    public ResultData findSchoolConfigByJWD(Double lat, Double lon, Integer user_id) {
        ResultData resultData = schoolConfigService.findSchoolByJWD(lat,lon,user_id);
        return resultData;
    }

    /**
     * @param
     * @Description : 查询当前用户管理的学校信息
     * @Return : cn.crm.result.ResultData
     * @Author : MYZ
     * @Date : 2019/3/28 11:04
     */
    @GetMapping("findAllSchoolConfig")
    @ApiOperation(notes = "查询当前用户管理的学校信息(不分页)", value = "查询当前用户管理的学校信息(不分页)")
    public ResultData findAllSchoolConfig(HttpServletRequest request) {
        return schoolConfigService.findAllSchoolConfig(request);
    }

    /**
     * @param
     * @Description : 查询所有学校信息
     * @Return : cn.crm.result.ResultData
     * @Author : MYZ
     * @Date : 2019/3/28 11:04
     */
    @GetMapping("findAppAllSchoolConfig")
    @ApiOperation(notes = "查询所有学校配置(不分页)", value = "查询所有学校配置(不分页)")
    public ResultData findAppAllSchoolConfig() {
        return schoolConfigService.findAllSchoolConfig();
    }

    /**
     * 新增学校配置信息
     *
     * @param schoolConfigEntity
     * @return
     */
    @PostMapping("saveSchoolConfig")
    @SystemLog(methods = "新增学校配置", module = "新增学校配置")
    @ApiOperation(notes = "学校配置信息", value = "学校配置信息")
    public ResultData saveSchoolConfig(@RequestBody SchoolConfigEntity schoolConfigEntity,HttpServletRequest request) {
        ResultData resultData = schoolConfigService.saveSchoolConfig(schoolConfigEntity,request);
        return resultData;
    }

    /**
     * 编辑学校配置信息
     *
     * @param schoolConfigEntity
     * @return
     */
    @PostMapping("updateSchoolConfig")
    @SystemLog(methods = "修改学校配置", module = "修改学校配置")
    @ApiImplicitParam(name = "学校配置信息", value = "schoolConfigEntity", dataType = "SchoolConfigEntity", paramType = "form")
    public ResultData updateSchoolConfig(@RequestBody SchoolConfigEntity schoolConfigEntity) {
        ResultData resultData = schoolConfigService.updateSchoolConfig(schoolConfigEntity);
        return resultData;
    }

    /**
     * 删除学校配置信息
     *
     * @param id
     * @return
     */
    @PostMapping("deleteSchoolConfig")
    @SystemLog(methods = "删除学校配置", module = "删除学校配置")
    @ApiImplicitParam(name = "配置信息id", value = "id", dataType = "int", paramType = "form")
    public ResultData deleteSchoolConfig(Integer id) {
        ResultData resultData = schoolConfigService.deleteSchoolConfig(id);
        return resultData;
    }

    /**
     * 启用禁用学校配置信息
     *
     * @param id
     * @param status
     * @return
     */
    @PostMapping("changeSchoolConfigStatus")
    @SystemLog(methods = "启用禁用学校配置信息", module = "启用禁用学校配置信息")
    @ApiImplicitParam(name = "学校配置id", value = "id", dataType = "int", paramType = "query")
    public ResultData changeSchoolConfigStatus(Integer id, Integer status) {
        ResultData resultData = schoolConfigService.changeSchoolConfigStatus(id, status);
        return resultData;
    }



}
