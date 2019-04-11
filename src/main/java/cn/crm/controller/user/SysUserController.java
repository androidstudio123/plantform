package cn.crm.controller.user;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.SysAdminUsergroupEntity;
import cn.crm.entity.SysUserEntity;
import cn.crm.entity.SysUsergroupEntity;
import cn.crm.logaop.SystemLog;
import cn.crm.mapper.repair.UserRoleGroupSchoolVoMapper;
import cn.crm.mapper.sys.SysAdminUsergroupMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.repair.UserRoleGroupSchoolVoService;
import cn.crm.service.user.SysAdminUsergroupService;
import cn.crm.service.user.SysUserService;
import cn.crm.service.user.SysUsergroupService;
import cn.crm.util.slideVerification.validate.StringUtils;
import cn.crm.vo.UserRoleGroupSchoolVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.Get;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;


/**
 * TODO 在此加入类描述  用户信息控制层实现
 *
 * @author MYZ
 * @version 2019-03-13 15:21:30
 * @copyright
 */
@RestController
@RequestMapping(value = "/sysuser")
@Api(description = "用户信息管理")
public class SysUserController {


    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private UserRoleGroupSchoolVoService userRoleGroupSchoolVoService;


    /**
     * 查询用户信息
     * MYZ
     *
     * @param user_name    根据用户名模糊查询
     * @param userGroup_id 用户组id
     * @return
     */
    @GetMapping("findUser")
    @ApiOperation(notes = "查询用户信息(可根据用户名模糊查询)", value = "查询用户信息(可根据用户名模糊查询)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名", dataType = "string", required = false, paramType = "query"),
            @ApiImplicitParam(name = "userGroup_id", value = "用户组id", dataType = "int", required = false, paramType = "query")
    })
    public ResultData findUser(HttpServletRequest request, String user_name, Integer userGroup_id) {
        //从session中获取用户信息
        SysAdminEntity sysAdminEntity = (SysAdminEntity) request.getSession().getAttribute("sysAdminEntity");
        //获取登录用户admin_id
        Integer admin_id = sysAdminEntity.getAdmin_id();
        return sysUserService.findUser(user_name, userGroup_id, admin_id);
    }

    /**
     * 根据学校id查询用户信息
     *
     * @param schoolId
     * @return
     */
    @GetMapping("findUserBySchoolId")
    @ApiOperation(notes = "根据学校id查询用户信息", value = "根据学校id查询用户信息")
    @ApiImplicitParam(name = "schoolId", value = "学校id", dataType = "Integer", required = true, paramType = "query")
    public ResultData findUserBySchoolId(Integer schoolId) {
        List<SysUserEntity> userBySchoolId = sysUserService.findUserBySchoolId(schoolId);
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), new PageInfo<SysUserEntity>(userBySchoolId));
    }

    /**
     * 根据用户id查询这条用户详细信息
     * MYZ
     *
     * @param user_id
     * @return
     */
    @GetMapping("findUserByUserId")
    @ApiOperation(notes = "根据用户id查询这条用户详细信息", value = "根据用户id查询这条用户详细信息")
    @ApiImplicitParam(name = "user_id", value = "用户id", dataType = "int", required = true, paramType = "query")
    public ResultData findUserByUserId(Integer user_id) {
        return sysUserService.findUserByUserId(user_id);
    }

    /**
     * 新增用户信息
     * MYZ
     *
     * @param sysUserEntity 用户信息实体类
     * @return
     */
    @PostMapping("addUser")
    @SystemLog(module = "用户管理", methods = "新增用户信息")
    @ApiOperation(notes = "新增用户信息", value = "新增用户信息")
    public ResultData addUser(@RequestBody SysUserEntity sysUserEntity) {
        return sysUserService.addUser(sysUserEntity);
    }

    /**
     * 修改用户信息
     * MYZ
     *
     * @param sysUserEntity 用户信息实体类
     * @return
     */
    @PostMapping("updateUser")
    @SystemLog(module = "用户管理", methods = "修改用户信息")
    @ApiOperation(notes = "修改用户信息", value = "修改用户信息")
    public ResultData updateUser(@RequestBody SysUserEntity sysUserEntity,HttpServletRequest request) {
        return sysUserService.updateUser(sysUserEntity,request);
    }

    /**
     * 根据用户id删除用户信息
     * MYZ
     *
     * @param user_id 用户id
     * @return
     */
    @GetMapping("deleteUser")
    @SystemLog(module = "用户管理", methods = "删除用户信息")
    @ApiOperation(notes = "根据用户id删除用户信息", value = "根据用户id删除用户信息")
    @ApiImplicitParam(name = "user_id", value = "用户id", dataType = "int", required = true, paramType = "query")
    public ResultData deleteUser(Integer user_id) {
        return sysUserService.deleteUser(user_id);
    }

    /**
     * 上传用户头像
     * MYZ
     *
     * @param request
     * @param file    上传文件
     * @return
     */
    @PostMapping("uploadUserAvatar")
    @ApiOperation(notes = "上传用户头像", value = "上传用户头像")
    @ApiImplicitParam(name = "file", value = "用户头像(图片格式)", required = true, paramType = "query")
    public ResultData uploadUserAvatar(HttpServletRequest request, MultipartFile file) {
        return sysUserService.uploadUserAvatar(request, file);
    }


    /**
     * MYZ
     * 导出excle
     */
    @GetMapping("/exportUserExcel")
    @ApiOperation(value = "导出用户信息到excle", notes = "导出用户信息到excle")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_name", value = "用户名称", dataType = "string", required = false, paramType = "query"),
            @ApiImplicitParam(name = "userGroup_id", value = "用户组id", dataType = "int", required = false, paramType = "query")
    })
    public void exportUserExcel(HttpServletResponse response, HttpServletRequest request,Integer admin_id, String user_name, Integer userGroup_id, String ids) {
        List<SysUserEntity> userEntityList = null;
        //判断ids是否为空
        if (StringUtils.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            List<String> list = Arrays.asList(split);
            Example example = new Example(SysUserEntity.class);
            example.createCriteria().andIn("user_id", list);
            userEntityList = sysUserService.queryExampleForList(example);
        } else {
            userEntityList = sysUserService.finduser(request,admin_id,userGroup_id,user_name);
        }
        response.setHeader("Content-Disposition", "attachment;filename=" + "user" + ".xls");  //导出excel表格的名称
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        ExportParams exportParams = new ExportParams("用户管理", "用户");
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, SysUserEntity.class, userEntityList);
        try {
            OutputStream output = response.getOutputStream();
            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
            workbook.write(bufferedOutPut);
            bufferedOutPut.flush();
            bufferedOutPut.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给用户分配角色
     *
     * @param user_id
     * @param role_id
     * @return
     */
    @PostMapping("distributionRole")
    @SystemLog(methods = "给用户分配角色", module = "给用户分配角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "int", required = false, paramType = "query"),
            @ApiImplicitParam(name = "role_id", value = "角色id", dataType = "int", required = false, paramType = "query")
    })
    public ResultData distributionRole(Integer user_id, Integer role_id) {
        ResultData resultData = sysUserService.distributionRole(user_id, role_id);
        return resultData;
    }

    /**
     * 查询用户管理
     *
     * @param user_group_id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("findUserRoleGroupSchool")
    @SystemLog(methods = "查询用户管理", module = "查询用户管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "分组id", value = "user_group_id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "角色id", value = "role_id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "用户名", value = "user_name", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "当前页", value = "pageNum", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "每页多少条数据", value = "pageSize", dataType = "int", paramType = "query")
    })
    public ResultData findUserRoleGroupSchool(HttpServletRequest request,Integer user_group_id, String user_name, Integer pageNum, Integer pageSize) {
        //获取用户信息
        SysAdminEntity sysAdminEntity = (SysAdminEntity)request.getSession().getAttribute("sysAdminEntity");
        if(sysAdminEntity == null){
            return new ResultData(20009,false,"用户登录过期，请重新登录");
        }
        ResultData resultData = userRoleGroupSchoolVoService.findUserRoleGroupSchool(sysAdminEntity.getAdmin_id(),user_group_id,user_name, pageNum, pageSize);
        return resultData;
    }

    /**
     * 返回手机端当前登录用户信息
     *
     * @param request
     * @return
     */
    @GetMapping("queryAppUser")
    @ApiOperation(notes = "返回手机端当前登录用户信息", value = "返回手机端当前登录用户信息")
    public ResultData queryAppUser(HttpServletRequest request) {
        SysUserEntity userEntity = (SysUserEntity) request.getSession().getAttribute("userEntity");
        if (userEntity == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage(), null);
        }
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), userEntity);
    }


    @PostMapping("improtUserExcel")
    @ApiOperation(value = "Excel导入用户信息", notes = "Excel导入用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userGroupId", value = "导入的分组ID", dataType = "int", required = true, paramType = "query"),
            @ApiImplicitParam(name = "falg", value = "是否覆盖重复工号 1是 2否", dataType = "int", required = true, paramType = "query")
    })
    public ResultData improtUserExcel(@RequestParam("file") MultipartFile file, Integer userGroupId, Integer falg, HttpServletRequest request) {
        return sysUserService.improtUserExcel(file, userGroupId, falg);
    }


    /**
     * 修改手机号
     *
     * @param userPhone
     * @return
     */
    @GetMapping("updatePhone")
    @ApiOperation(notes = "修改手机号", value = "修改手机号")
    @ApiImplicitParam(name = "userPhone", value = "手机号", dataType = "String", paramType = "query", required = true)
    public ResultData updatePhone(String userPhone, HttpServletRequest request) {
        return sysUserService.updatePhone(userPhone, request);
    }

    @PostMapping("distributionUserExport")
    public ResultData distributionExport(Integer user_id,Integer export_role_id){
        ResultData resultData = sysUserService.distributionExport(user_id,export_role_id);
        return resultData;
    }
}
