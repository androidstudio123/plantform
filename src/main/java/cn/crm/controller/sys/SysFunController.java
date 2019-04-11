package cn.crm.controller.sys;


import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.SysFuncEntity;
import cn.crm.mapper.sys.SysFuncMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.sys.SysFuncService;
import cn.crm.util.AdminEntityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * 系统菜单导航资源
 * <p>
 * 1.加载 ：根据当前登录用户所拥有的权限去查询
 * 2.新增 ：选择上级
 * 3.删除 ：下面有子节点不允许删
 * 4.修改 ：修改子菜单所属上级
 */
@RestController
@RequestMapping(value = "/sysFunController")
@Api(description = "系统资源导航")
public class

SysFunController {

    @Autowired
    private SysFuncService sysFuncService;
    @Autowired
    private SysFuncMapper sysFuncMapper;

    /**
     * 查询所有菜单导航(不根据权限查询)
     */
    @GetMapping("/findAllFun")
    @ApiOperation(value = "查询所有菜单", notes = "查询所有菜单")
    public ResultData findAllFun(HttpServletResponse response, HttpServletRequest request) {
        return new ResultData(20000, true, "查询成功", sysFuncMapper.selectAll());
    }

    /**
     * 查询所有菜单
     */
    @GetMapping("/findAllSysFun")
    @ApiOperation(value = "查询所有(根据登录权限查询)菜单导航信息", notes = "查询所有(根据登录权限查询)菜单导航信息")
    public ResultData findAllSysFun(HttpServletResponse response, HttpServletRequest request) {
        //获取登录用户id
        SysAdminEntity adminEntity = (SysAdminEntity) request.getSession().getAttribute("sysAdminEntity");
        //获取当前登陆用户的parent_id
        Integer admin_parentId = adminEntity.getAdmin_parentId();
        //判断admin_parentId是否为0//p
        if (admin_parentId == 0){
            //0为最上级查询所有节点
            Example example = new Example(SysFuncEntity.class);
            example.setOrderByClause("fun_sort desc");
            return new ResultData(20000,true,"查询成功",sysFuncService.queryExampleForList(example));
        }
        Integer role_id = adminEntity.getRole_id();
        List<SysFuncEntity> allSysFun;
        if(role_id > 0){
            allSysFun = sysFuncService.findAllSysFun(role_id);
        }else{
            allSysFun = Collections.EMPTY_LIST;
        }
        return new ResultData(20000,true,"查询成功",allSysFun);
    }

    /**
     * 修改 菜单导航信息
     * <p>
     * /sysFunController/updateSysFun
     */
    @PostMapping("/updateSysFun")
    @ApiOperation(value = "修改 菜单导航信息", notes = "修改 菜单导航信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "funId", value = "节点id", required = true, dataType = "form"),
            @ApiImplicitParam(name = "parentId", value = "本节点父类id", required = true, dataType = "form")
    })
    public ResultData updateSysFun(HttpServletRequest request, Integer funId, Integer parentId) {
        SysFuncEntity sysFuncEntity = new SysFuncEntity();
        sysFuncEntity.setFun_id(funId);
        sysFuncEntity.setFun_parentId(parentId);
        String result = sysFuncService.update(sysFuncEntity);
        if ("success".equals(result)) {
            return new ResultData(ResultCode.SUCCESS.getCode(), true, "修改成功", null);
        }
        return new ResultData(ResultCode.ERROR.getCode(), false, "修改失败", null);
    }

    /**
     * 删除 菜单导航信息
     *
     * @param fun_id
     * @return
     */
    @GetMapping("/deleteFunById")
    @ApiOperation(value = "删除 菜单导航信息", notes = "删除 菜单导航信息")
    @ApiImplicitParam(name = "fun_id", value = " 菜单导航id", required = true, dataType = "int", paramType = "query")
    public ResultData deleteFunById(HttpServletRequest request, Integer fun_id) {
        //判断是否有下级
        Example example = new Example(SysFuncEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("fun_parentId", fun_id);
        List<SysFuncEntity> sysFuncEntities = sysFuncService.queryExampleForList(example);
        if (sysFuncEntities.size() != 0) {
            return new ResultData(ResultCode.ERROR.getCode(), false, "此菜单下有下级，不能完成删除", null);

        }
        String delete = sysFuncService.delete(fun_id);
        if ("success".equals(delete)) {
            return new ResultData(ResultCode.SUCCESS.getCode(), true, "删除成功", null);
        }
        return new ResultData(ResultCode.ERROR.getCode(), false, "删除失败，请检查网络", null);
    }

    /**
     * 根据 菜单导航id查询 菜单导航信息
     *
     * @param fun_id
     * @return
     */
    @GetMapping("/findSysFunByid")
    @ApiOperation(value = "根据 菜单导航id查询 菜单导航信息", notes = "根据 菜单导航id查询 菜单导航信息")
    @ApiImplicitParam(name = "fun_id", value = " 菜单导航id", required = true, dataType = "int", paramType = "query")
    public ResultData findRoleByid(Integer fun_id) {
        final SysFuncEntity byPrimaryKey = sysFuncService.findByPrimaryKey(fun_id);
        return new ResultData(20000, true, "查询成功", byPrimaryKey);
    }

    /**
     * 新增 菜单导航信息
     */
    @PostMapping("/addSysFun")
    @ApiOperation(value = "新增 菜单导航信息", notes = "新增 菜单导航信息")
    public ResultData addSysFun(HttpServletRequest request, @RequestBody SysFuncEntity sysFuncEntity) {
        String save = sysFuncService.save(sysFuncEntity);
        if ("success".equals(save)) {
            return new ResultData(ResultCode.SUCCESS.getCode(), true, "新增成功", null);
        }
        return new ResultData(20001, false, "新增失败，请检查网络", null);
    }

}
