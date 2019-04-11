package cn.crm.controller.sys;

import cn.crm.entity.SysRoleFuncEntity;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.sys.SysRoleFuncService;
import cn.crm.util.slideVerification.validate.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

/**
 * 管理员对应菜单导航
 *  1 授权: 管理员拥有角色 ,角色对应资源
 *  2 修改授权 : 清空中间表 重新添加
 */

@RestController
@Api(description = "角色资源")
@RequestMapping(value = "/sysRoleFun")
public class SysAdminFunController {

    @Autowired
    private SysRoleFuncService sysRoleFunService;



    /**
     * 给角色添加资源
     * @param roleId 角色id
     * @param funIds 资源id数组
     * @return
     */
    @PostMapping("/addSysRoleFun")
    @ApiOperation(value = "给角色添加资源信息", notes = "给角色添加资源信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "funIds", value = "资源id(可以传多个资源)", required = true, dataType = "string", paramType = "query")
    })
    public ResultData addRoleToResource(Integer roleId, String funIds) {
        //参数不能为空
        if(StringUtils.isEmpty(funIds)){
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(),false,ResultCode.EMPTYPARAMS.getMessage(),null);
        }
        //删除权限
         String result = sysRoleFunService.delete(new Example(SysRoleFuncEntity.class).createCriteria().andEqualTo("role_id", roleId));
        if("error".equals(result)){
            new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage(),null);
        }
        //循环添加
        String[] split = funIds.split(",");
        for (int i = 0;i < split.length; i++) {
            String funid = split[i];
            String save = sysRoleFunService.save(new SysRoleFuncEntity(roleId, Integer.parseInt(funid)));
        }
        return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),null);
    }


}
