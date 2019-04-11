package cn.crm.controller.sys;

import cn.crm.entity.SysLogEntity;
import cn.crm.logaop.SystemLog;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.sys.SysLogService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @ClassName {NAME}  日志控制层实现类
 * @Description TODO
 * @Author MYZ
 * @Date 2019/3/18 0018 11:48
 */
@RestController
@RequestMapping("/syslog")
@Api(description = "日志管理")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 查询日志信息
     *
     * @param log_user 用户名
     * @param log_ip   请求地址
     * @param flag     用于判断根据用户名查询还是根据请求地址查询 flag:1 根据用户名 2 根据请求地址
     * @return
     */
    @GetMapping("findAllLog")
    @ApiOperation(value = "查询日志信息(可根据用户名查询或请求地址查询)", notes = "查询日志信息(可根据用户名查询或请求地址查询)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "log_user", value = "用户名", dataType = "string", required = false, paramType = "query"),
            @ApiImplicitParam(name = "log_ip", value = "请求地址", dataType = "string", required = false, paramType = "query"),
            @ApiImplicitParam(name = "flag", value = "用于判断根据用户名查询还是根据请求地址查询 flag:1 根据用户名 2 根据请求地址", dataType = "int", required = false, paramType = "query")
    })
    public ResultData findAllLog(String log_user, String log_ip, Integer flag) {
        List<SysLogEntity> logEntityList = sysLogService.findAllLog(log_user, log_ip, flag);
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), new PageInfo<SysLogEntity>(logEntityList));
    }
}
