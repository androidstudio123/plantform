package cn.crm.controller.user;


import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.SysUsergroupEntity;
import cn.crm.logaop.SystemLog;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.user.SysUsergroupService;
import cn.crm.vo.SysUsergroupVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * TODO 在此加入类描述   用户组控制层实现
 *
 * @author MYZ
 * @version 2019-03-13 15:22:22
 * @copyright
 */
@RestController
@RequestMapping(value = "/sysusergroup")
@Api(description = "用户组管理")
public class SysUsergroupController {


    @Autowired
    private SysUsergroupService sysUsergroupService;





    /**
     * 根据管理员id查询对应的用户组信息
     * MYZ
     *
     * @param admin_id 管理员id
     * @return
     */

    @GetMapping("findUsergroup")
    @ApiOperation(notes = "根据管理员id查询对应的用户组信息", value = "根据管理员id查询对应的用户组信息")
    public ResultData findUsergroup(Integer admin_id, HttpServletRequest request, Integer id) {
        //从session中获取用户信息
        SysAdminEntity sysAdminEntity1 = (SysAdminEntity) request.getSession().getAttribute("sysAdminEntity");
        //判断获取信息是否为空
        if (sysAdminEntity1 == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage());
        }
        //获取管理人员id
        Integer admin_id1 = sysAdminEntity1.getAdmin_id();
        //调用查询方法
        List<SysUsergroupVo> sysUsergroupVoList = sysUsergroupService.findUsergroup(admin_id1);
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), sysUsergroupVoList);
    }


    /**
     * 根据用户组id查询这条用户组详细信息
     * MYZ
     *
     * @param userGroup_id 用户组id
     * @return
     */
    @GetMapping("findUsergroupByUsergroupId")
    @ApiOperation(notes = "根据用户组id查询这条用户组详细信息", value = "根据用户组id查询这条用户组详细信息")
    @ApiImplicitParam(name = "userGroup_id", value = "用户组id", dataType = "int", required = true, paramType = "query")
    public ResultData findUsergroupByUsergroupId(Integer userGroup_id) {
        return sysUsergroupService.findUsergroupByUsergroupId(userGroup_id);
    }

    /**
     * 新增用户组
     * MYZ
     *
     * @param sysUsergroupEntity 用户组实体类
     * @return
     */
    @PostMapping("addUsergroup")
    @SystemLog(module = "用户组管理", methods = "新增用户组")
    @ApiOperation(notes = "新增用户组", value = "新增用户组")
    public ResultData addUsergroup(@RequestBody SysUsergroupEntity sysUsergroupEntity, HttpServletRequest request) {
        return sysUsergroupService.addUsergroup(sysUsergroupEntity, request);
    }

    /**
     * 修改用户组
     * MYZ
     *
     * @param sysUsergroupEntity 用户组实体类
     * @return
     */
    @PostMapping("updateUsergroup")
    @SystemLog(module = "用户组管理", methods = "修改用户组")
    @ApiOperation(notes = "修改用户组", value = "修改用户组")
    public ResultData updateUsergroup(@RequestBody SysUsergroupEntity sysUsergroupEntity, HttpServletRequest request) {
        return sysUsergroupService.updateUsergroup(sysUsergroupEntity, request);
    }

    /**
     * 根据用户组id删除用户组
     * MYZ
     *
     * @param userGroup_id 用户组id
     * @return
     */
    @GetMapping("deleteUser")
    @SystemLog(module = "用户组管理", methods = "删除用户组")
    @ApiOperation(notes = "根据用户组id删除用户组", value = "根据用户组id删除用户组")
    @ApiImplicitParam(name = "userGroup_id", value = "用户组id", dataType = "int", required = true, paramType = "query")
    public ResultData deleteUser(Integer userGroup_id) {
        return sysUsergroupService.deleteUser(userGroup_id);
    }

    /**
     * 查询用户管理左边的分组架构
     * @return
     */
    @GetMapping("findUserManagerGroup")
    @SystemLog(module = "查询用户管理左边的分组架构", methods = "查询用户管理左边的分组架构")
    public ResultData findUserManagerGroup(){
        ResultData resultData = sysUsergroupService.findUserManagerGroup();
        return resultData;
    }

}
