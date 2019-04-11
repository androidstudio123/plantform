package cn.crm.controller.sys;

import cn.crm.entity.SysFuncEntity;
import cn.crm.entity.SysRoleFuncEntity;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.sys.SysRoleFuncService;
import cn.crm.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

/**
 * 角色对应资源列表
 */
@RestController
@RequestMapping("/sysRoleFun")
@Api(description = "角色授权资源")
public class SysRoleFunController {

    @Autowired
    private SysRoleFuncService sysRoleFuncService;


    /**
     * 角色授权资源
     *
     * @return
     */

    @PostMapping("/addFunByRoleid")
    @ApiOperation("角色授权资源")
    public ResultData addFunByRoleid(String text) {
        return sysRoleFuncService.addFunByRoleid(text);
    }



    /**
     * 根据角色id查询所有资源
     *
     * @param roleId
     * @return
     */
    @GetMapping("/findFunByRoleid")
    @ApiOperation("根据角色id查询所有资源")
    @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "string", paramType = "query")
    @ResponseBody
    public ResultData findFunByRoleid(Integer roleId) {
        List<SysFuncEntity> funs = sysRoleFuncService.findFunByRoleid(roleId);
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), funs);
    }

}
