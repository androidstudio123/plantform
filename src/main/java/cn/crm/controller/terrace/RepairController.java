package cn.crm.controller.terrace;


import cn.crm.entity.SysUserEntity;
import cn.crm.entity.repair.SchoolConfigEntity;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.repair.SchoolConfigService;
import cn.crm.service.terrace.RepairAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;

/**
 * @author MYZ
 * @version V1.0
 * @Description: 微信新用户登录学校授权
 * @Package cn.crm.controller.terrace
 * @date 2019/3/26 15:35
 */
@RestController
@RequestMapping("repair")
@Api(description = "微信新用户登录学校授权")
public class RepairController {
    @Autowired
    private RepairAppService repairAppService;

    @Autowired
    private SchoolConfigService schoolConfigService;

    /**
     * @param lon       经度
     * @param lat       纬度
     * @param school_id 学校id
     * @param request
     * @Description :   新用户登陆需要传递经度,纬度或者收到选择学校id
     * @Return : cn.crm.result.ResultData
     * @Author : MYZ
     * @Date : 2019/3/26 15:38
     */
    @GetMapping("updateUser")
    @ApiOperation(notes = "新用户登陆需要传递经度,纬度或者选择学校id", value = "新用户登陆需要传递经度,纬度或者选择学校id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lon", value = "经度", dataType = "Double", required = false, paramType = "query"),
            @ApiImplicitParam(name = "lat", value = "纬度", dataType = "Double", required = false, paramType = "query"),
            @ApiImplicitParam(name = "school_id", value = "学校id", dataType = "Integer", required = false, paramType = "query")
    })
    public ResultData updateUser(Double lon, Double lat, Integer school_id, HttpServletRequest request) {
        return repairAppService.updateUser(lon, lat, school_id, request);
    }

    /**
     * @param request
     * @Description :  用于判断登陆用户是否为第一次登录
     * @Return : cn.crm.result.ResultData
     * @Author : MYZ
     * @Date : 2019/3/27 11:31
     */
    @GetMapping("judgeSchoolId")
    @ApiOperation(notes = "用于判断登陆用户是否为第一次登录", value = "用于判断登陆用户是否为第一次登录")
    public ResultData judgeSchoolId(HttpServletRequest request) {
        SysUserEntity userEntity = (SysUserEntity) request.getSession().getAttribute("userEntity");
        //判断用户信息是否为空
        if (userEntity == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage());
        }
        //获取学校id
        Integer school_id = userEntity.getSchool_id();
        SchoolConfigEntity school = schoolConfigService.findByPrimaryKey(school_id);
        if (school_id == null) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
        }
        return new ResultData(ResultCode.ALREADYEXISTS.getCode(), false, ResultCode.ALREADYEXISTS.getMessage(),school);
    }

}
