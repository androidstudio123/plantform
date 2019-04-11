package cn.crm.controller.sys;


import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.SysLogEntity;
import cn.crm.entity.SysRoleEntity;
import cn.crm.mapper.sys.SysRoleMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.sys.SysRoleService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 角色
 */
@RestController
@RequestMapping(value = "/sysRoleController")
@Api(description = "系统管理员角色")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleMapper sysRoleMapper;


    /**
     * 查询所有角色
     *
     * @return
     */
    @GetMapping("/FindAllRole")
    @ApiOperation(value = "查询所有角色", notes = "查询所有角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role_isActive", value = "角色状态", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "role_name", value = "角色名称", required = true, dataType = "int", paramType = "query")
    })
    public ResultData findAllSysRole(HttpServletRequest request, Integer role_isActive, String role_name) {
        //取值
        SysAdminEntity sysAdminEntity = (SysAdminEntity) request.getSession().getAttribute("sysAdminEntity");
        if (sysAdminEntity == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage(), null);
        }
        //获取登陆人id
        Integer admin_id = sysAdminEntity.getAdmin_id();
        List<SysRoleEntity> sysRoleEntities = sysRoleService.findAllSysRole(role_isActive, role_name, admin_id);
        return new ResultData(ResultCode.SUCCESS.getCode(), true, "查询成功", new PageInfo<SysRoleEntity>(sysRoleEntities));
    }

    /**
     * 修改角色信息
     *
     * @param sysRoleEntity
     * @return
     */
    @PostMapping("updateRole")
    @ResponseBody
    @ApiOperation(value = "修改角色信息", notes = "修改角色信息")
    public ResultData updateRole(HttpServletRequest request, @RequestBody SysRoleEntity sysRoleEntity) {
        //取值
        SysAdminEntity sysAdminEntity = (SysAdminEntity) request.getSession().getAttribute("sysAdminEntity");
//        if(sysAdminEntity==null){
//            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage(), null);
//        }
        return sysRoleService.updateRole(sysRoleEntity);
    }

    /**
     * 删除角色信息
     *
     * @param role_id
     * @return
     */
    @GetMapping("deleteRole")
    @ResponseBody
    @ApiOperation(value = "删除角色信息", notes = "删除角色信息")
    @ApiImplicitParam(name = "role_id", value = "角色id", required = true, dataType = "int", paramType = "query")
    public ResultData deleteRole(Integer role_id) {
        return sysRoleService.deleteRole(role_id);
    }

    /**
     * 根据角色id查询角色信息
     *
     * @param role_id
     * @return
     */
    @GetMapping("findRoleByid")
    @ResponseBody
    @ApiOperation(value = "根据角色id查询角色信息", notes = "根据角色id查询角色信息")
    @ApiImplicitParam(name = "role_id", value = "角色id", required = true, dataType = "int", paramType = "query")
    public ResultData findRoleByid(Integer role_id) {
        SysRoleEntity roleEntity = sysRoleService.findRoleByid(role_id);
        return new ResultData(20000, true, "查询成功", roleEntity);
    }

    /**
     * 新增角色信息
     *
     * @param sysRoleEntity
     * @return
     */
    @PostMapping("addRole")
    @ResponseBody
    @ApiOperation(value = "新增角色信息", notes = "新增角色信息")
    public ResultData addRole(HttpServletRequest request, @RequestBody SysRoleEntity sysRoleEntity) {
        return sysRoleService.addRole(sysRoleEntity,request);
    }
}
