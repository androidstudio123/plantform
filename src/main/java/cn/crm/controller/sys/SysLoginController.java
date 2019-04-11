package cn.crm.controller.sys;

import cn.crm.logaop.SystemLog;
import cn.crm.result.ResultData;
import cn.crm.service.sys.SysLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName {NAME}
 * @Description TODO  后台登录控制层实现类
 * @Author MYZ
 * @Date 2019/3/14 0014 10:28
 */
@Api(description = "后台登录界面")
@RestController
@RequestMapping(value = "/syslogin")
public class SysLoginController {


    @Autowired
    private SysLoginService sysLoginService;

    /**
     * 用户登录
     *
     * @param admin_name     用户名
     * @param admin_password 密码
     * @param imageCode      验证码
     * @return
     */
    @SystemLog(module = "后台登录",methods = "用户登录")
    @PostMapping("login")
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "admin_name", value = "用户名", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "admin_password", value = "用户密码", required = true, dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "imageCode", value = "验证码", required = true, dataType = "string", paramType = "query")
    })
    public ResultData login(String admin_name, String admin_password, String imageCode, HttpServletRequest request) {
        return sysLoginService.login(admin_name, admin_password, imageCode, request);
    }




}
