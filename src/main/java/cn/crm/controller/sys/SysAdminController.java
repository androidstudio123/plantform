package cn.crm.controller.sys;

import cn.crm.entity.*;
import cn.crm.logaop.SystemLog;
import cn.crm.mapper.sys.SysAdminMapper;
import cn.crm.mapper.sys.SysUsergroupMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.room.SysRoomgroupService;
import cn.crm.service.sys.SysAdminRoomgroupService;
import cn.crm.service.sys.SysAdminService;
import cn.crm.service.user.SysAdminUsergroupService;
import cn.crm.service.user.SysUsergroupService;
import cn.crm.util.PasswordUtil;
import cn.crm.util.StringUtils;
import cn.crm.vo.SysUsergroupVo;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统管理
 * 1.查询 查询当前管理员下级
 * 2.新增 只能增加下级管理员
 * 3.删除 有下级先删除下级
 * 4.修改 只能修改下级管理员的上级，本级不能修改
 */
@RestController
@RequestMapping(value = "/sysAdminController")
@Api(description = "系统管理员")
public class SysAdminController {

    @Autowired
    private SysAdminService sysAdminService;
    @Autowired
    private SysUsergroupService sysUsergroupService;

    @Autowired
    private SysRoomgroupService sysRoomgroupService;

    @Autowired
    private SysAdminUsergroupService sysAdminUsergroupService;
    @Autowired
    private SysAdminRoomgroupService sysAdminRoomgroupService;
    @Autowired
    private SysAdminMapper sysAdminMapper;

    /**
     * 查询管理员需要分配的角色
     */
    @GetMapping(value = "/findRoleByAdminAuth")
    @ApiOperation(value = "添加管理员时选择角色", notes = "添加管理员时选择角色")
    @ResponseBody
    public ResultData findRoleByAdminAuth(HttpServletRequest request, HttpServletResponse response) {
        SysAdminEntity sysAdminEntity1 = (SysAdminEntity) request.getSession().getAttribute("sysAdminEntity");
        if (sysAdminEntity1.getAdmin_id() == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage(), null);
        }

        List<SysRoleEntity> roleByAdminAuth = sysAdminService.findRoleByAdminAuth(sysAdminEntity1.getAdmin_id());
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), roleByAdminAuth);
    }

    /**
     * 查询管理员父级
     */
    @GetMapping(value = "/findParentAdmin")
    @ApiOperation(value = "查找管理员父级", notes = "查找管理员父级")
    @ApiImplicitParam(name = "flag", value = "查找标识(1:查询包括自己在内的所有下级,2:查询所有上级)", required = true, dataType = "string", paramType = "query")
    @ResponseBody
    public ResultData findParentAdmin(HttpServletRequest request, HttpServletResponse response, Integer flag) {
        SysAdminEntity sysAdminEntity1 = (SysAdminEntity) request.getSession().getAttribute("sysAdminEntity");
        List<SysAdminEntity> lists = new ArrayList<>();
        Integer admin_id = sysAdminEntity1.getAdmin_id();
        if (flag == 1) { //  查找当前管理员和管理员下级
            List<SysAdminEntity> byParentId = sysAdminService.findByParentId(sysAdminEntity1.getAdmin_id());
            //递归调用方法查询子级
            List<SysAdminEntity> sysAdminEntities = new ArrayList<>();
            this.findChildAdmin(byParentId, sysAdminEntities);
            sysAdminEntities.add(sysAdminEntity1);
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), sysAdminEntities);
        } else if (flag == 2) { //查找所有父级
            List<SysAdminEntity> allAdmin = sysAdminService.queryAll();
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), allAdmin);
        }
        return null;
    }

    /**
     * 根据管理员id查询管理员
     */
    @GetMapping(value = "/findAdminById")
    @ApiOperation(value = "根据管理员id查找管理员详情", notes = "根据管理员id查找管理员详情")
    @ApiImplicitParam(name = "adminId", value = "管理员id", required = true, dataType = "string", paramType = "query")
    @ResponseBody
    public ResultData findAdminById(HttpServletRequest request,
                                    HttpServletResponse response, Integer adminId) {
        Map<String, Object> map = new HashMap();
//        List<Integer> roomGroupIdsByAdminId = sysAdminRoomgroupService.findRoomGroupIdsByAdminId(adminId, 1);
        List<Integer> userGroupIdsByAdminid = sysAdminUsergroupService.findUserGroupIdsByAdminid(adminId, 1);
//        map.put("roomGroup", roomGroupIdsByAdminId );
        map.put("userGroup", userGroupIdsByAdminid);
        map.put("adminInfo", sysAdminMapper.selectByPrimaryKey(adminId));
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), map);
    }

    /**
     * 根据id删除管理员
     * 管理员存在下级 不允许删除
     */
    @PostMapping(value = "/deleteAdminById")
    @ApiOperation(value = "根据管理员删除管理员", notes = "删除管理员")
    @ApiImplicitParam(name = "adminId", value = "管理员id", required = true, dataType = "string", paramType = "query")
    @ResponseBody
    public ResultData deleteAdminById(HttpServletRequest request,
                                      HttpServletResponse response, Integer adminId) {

        SysAdminEntity sysAdminEntity1 = (SysAdminEntity) request.getSession().getAttribute("sysAdminEntity");
        //判断此管理员是否有下级
        List<SysAdminEntity> byParentId = sysAdminService.findByParentId(adminId);
        if (byParentId.size() > 0) {
            return new ResultData(ResultCode.FOREIGNNODEERROR.getCode(), false, ResultCode.FOREIGNNODEERROR.getMessage());
        }
        String delete = sysAdminService.delete(adminId);
        if ("success".equals(delete)) {
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
        }
        return new ResultData(ResultCode.ERROR.getCode(), false, ResultCode.ERROR.getMessage());
    }


//    /**
//     * 根据管理员id修改管理员信息
//     * userGroups  用户组
//     * roomGroups  用户组
//     */
//    @PostMapping(value = "/updateAdminById")
//    @ApiOperation(value = "修改管理员信息", notes = "修改管理员信息")
//    @ResponseBody
//    public ResultData updateAdminById(HttpServletRequest request, HttpServletResponse response,
//                                      @RequestBody SysAdminEntity sysAdminEntity, String userGroups, String roomGroups) {
//        //会话是否过期
//        SysAdminEntity sysAdminEntity1 = (SysAdminEntity) request.getSession().getAttribute("sysAdminEntity");
//        if (sysAdminEntity1 == null) {
//            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage(), null);
//        }
//        //修改管理之前删除数据
//        sysAdminUsergroupService.deleteByExample(new SysAdminUsergroupEntity(sysAdminEntity.getAdmin_id()));
//        sysAdminRoomgroupService.deleteByExample(new SysAdminRoomgroupEntity(sysAdminEntity.getAdmin_id()));
//        String save = sysAdminService.update(sysAdminEntity);
//        if ("success".equals(save)) {
//            //获取新增的主键
//            Integer adPK = sysAdminEntity.getAdmin_id();
//            if (!StringUtils.isEmpty(userGroups)) {
//                //用户组授权
//                Arrays.stream(userGroups.split(",")).forEach(e -> {
//                    sysAdminUsergroupService.save(new SysAdminUsergroupEntity(adPK, Integer.parseInt(e)));
//                });
//            }
//            if (!StringUtils.isEmpty(roomGroups)) {
//                //房间组授权
//                Arrays.stream(roomGroups.split(",")).forEach(e -> {
//                    sysAdminRoomgroupService.save(new SysAdminRoomgroupEntity(adPK, Integer.parseInt(e)));
//                });
//            }
//            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), sysAdminService.findByPrimaryKey(sysAdminEntity1.getAdmin_id()));
//        }
//        return new ResultData(ResultCode.ERROR.getCode(), false, ResultCode.ERROR.getMessage(), null);
//    }

//
//    /**
//     * 增加管理员
//     * userGroups  用户组
//     * roomGroups  用户组
//     */
//    @PostMapping(value = "/saveAdminInfo")
//    @ApiOperation(value = "添加管理员", notes = "添加管理员")
//    @ResponseBody
//    public ResultData saveAdminInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody SysAdminEntity sysAdminEntity, String userGroups, String roomGroups) {
//        //会话是否过期
//        SysAdminEntity sysAdminEntity1 = (SysAdminEntity) request.getSession().getAttribute("sysAdminEntity");
//        if (sysAdminEntity1 == null) {
//            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage(), null);
//        }
//        //判断管理员名称是否存在
//        Example example = new Example(SysAdminEntity.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("admin_name", sysAdminEntity.getAdmin_name());
//        List<SysAdminEntity> sysAdminEntities = sysAdminService.queryExampleForList(example);
//        if (sysAdminEntities.size() > 0) {
//            return new ResultData(ResultCode.ALREADYEXISTS.getCode(), false, ResultCode.ALREADYEXISTS.getMessage(), null);
//        }
//        // 加密
//        new PasswordUtil().encryptPassword(sysAdminEntity);
//        String save = sysAdminService.save(sysAdminEntity);
//        if ("success".equals(save)) {
//            // 获取新增的主键
//            Integer adPK = sysAdminEntity.getAdmin_id();
//            if (!StringUtils.isEmpty(userGroups)) {
//                //用户组授权
//                Arrays.stream(userGroups.split(",")).forEach(e -> {
//                    sysAdminUsergroupService.save(new SysAdminUsergroupEntity(adPK, Integer.parseInt(e)));
//                });
//            }
//            if (!StringUtils.isEmpty(roomGroups)) {
//                // 房间组授权
//                Arrays.stream(roomGroups.split(",")).forEach(e -> {
//                    sysAdminRoomgroupService.save(new SysAdminRoomgroupEntity(adPK, Integer.parseInt(e)));
//                });
//            }
//            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), sysAdminService.findByPrimaryKey(sysAdminEntity.getAdmin_id()));
//        }
//        return new ResultData(ResultCode.ERROR.getCode(), true, ResultCode.ERROR.getMessage(), null);
//    }


    /**
     * 根据管理员id修改管理员信息
     * userGroups  用户组
     */
    @PostMapping(value = "/updateAdminById")
    @ApiOperation(value = "修改管理员信息", notes = "修改管理员信息")
    @Transactional
    public ResultData updateAdminById(HttpServletRequest request, HttpServletResponse response,
                                      @RequestBody SysAdminEntity sysAdminEntity, String userGroups) {
        //会话是否过期
        SysAdminEntity sysAdminEntity1 = (SysAdminEntity) request.getSession().getAttribute("sysAdminEntity");
        if (sysAdminEntity1 == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage(), null);
        }
        //修改管理之前删除数据
        sysAdminUsergroupService.deleteByExample(new SysAdminUsergroupEntity(sysAdminEntity.getAdmin_id()));
        //获取传递过来的父级id
        Integer admin_parentId = sysAdminEntity.getAdmin_parentId();
        Integer admin_id = sysAdminEntity.getAdmin_id();
        //判断传递的父级id是否等于当前修改的管理员id
        if (admin_id.equals(admin_parentId)) {
            return new ResultData(ResultCode.ADMINADD_ERROR.getCode(), false, ResultCode.ADMINADD_ERROR.getMessage());
        }
        String save = sysAdminService.update(sysAdminEntity);
        if ("success".equals(save)) {
            //获取新增的主键
            Integer adPK = sysAdminEntity.getAdmin_id();
            if (!StringUtils.isEmpty(userGroups)) {
                // 用户组授权
                List<SysAdminUsergroupEntity> sysAdminUsergroupEntities = JSONArray.parseArray(userGroups, SysAdminUsergroupEntity.class);
                sysAdminUsergroupEntities.forEach(e -> {
                    e.setAdmin_id(adPK);
                    sysAdminUsergroupService.save(e);
                });
            }
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), sysAdminService.findByPrimaryKey(sysAdminEntity1.getAdmin_id()));
        }
        return new ResultData(ResultCode.ERROR.getCode(), false, ResultCode.ERROR.getMessage(), null);
    }


    /**
     * 增加管理员
     * userGroups  用户组
     */
    @PostMapping(value = "/saveAdminInfo")
    @ApiOperation(value = "添加管理员", notes = "添加管理员")
    @ResponseBody
    @Transactional
    public ResultData saveAdminInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody SysAdminEntity sysAdminEntity, String userGroups, String roomGroups) {
        //会话是否过期
        SysAdminEntity sysAdminEntity1 = (SysAdminEntity) request.getSession().getAttribute("sysAdminEntity");
        if (sysAdminEntity1 == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage(), null);
        }
        //判断管理员名称是否存在
        Example example = new Example(SysAdminEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("admin_name", sysAdminEntity.getAdmin_name());
        List<SysAdminEntity> sysAdminEntities = sysAdminService.queryExampleForList(example);
        if (sysAdminEntities.size() > 0) {
            return new ResultData(ResultCode.ALREADYEXISTS.getCode(), false, ResultCode.ALREADYEXISTS.getMessage(), null);
        }
        // 加密
        new PasswordUtil().encryptPassword(sysAdminEntity);
        String save = sysAdminService.save(sysAdminEntity);
        if ("success".equals(save)) {
            // 获取新增的主键
            Integer adPK = sysAdminEntity.getAdmin_id();
            if (!StringUtils.isEmpty(userGroups)) {
                // 用户组授权
                List<SysAdminUsergroupEntity> sysAdminUsergroupEntities = JSONArray.parseArray(userGroups, SysAdminUsergroupEntity.class);
                sysAdminUsergroupEntities.forEach(e -> {
                    e.setAdmin_id(adPK);
                    sysAdminUsergroupService.save(e);
                });
            }
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), sysAdminService.findByPrimaryKey(sysAdminEntity.getAdmin_id()));
        }
        return new ResultData(ResultCode.ERROR.getCode(), true, ResultCode.ERROR.getMessage(), null);
    }


    /**
     * 获取所有管理员
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/getAllAuthAdmin")
    @ApiOperation(value = "加载管理员", notes = "加载管理员")
    @ApiImplicitParam(name = "username", value = "用户名", required = false, dataType = "string", paramType = "query")
    @ResponseBody
    public ResultData getAllAuthAdmin(HttpServletRequest request, HttpServletResponse response, String admin_name) {
        SysAdminEntity sysAdminEntity = (SysAdminEntity) request.getSession().getAttribute("sysAdminEntity");
        if (sysAdminEntity == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage(), null);
        }
        List<SysAdminEntity> byParentId = sysAdminService.findByParentId(sysAdminEntity.getAdmin_id());
        //递归调用方法查询子级
        List<SysAdminEntity> sysAdminEntities = new ArrayList<>();
        this.findChildAdmin(byParentId, sysAdminEntities);
        if (StringUtils.isNotBlank(admin_name)) {
            sysAdminEntities = sysAdminEntities.stream().filter(e -> e.getAdmin_name().contains(admin_name)).collect(Collectors.toList());
        }
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), sysAdminEntities);
    }

    /**
     * 查找子级菜单
     *
     * @param newArray
     * @param entitys
     * @return
     */
    private List findChildAdmin(List<SysAdminEntity> newArray, List<SysAdminEntity> entitys) {
        newArray.forEach(item -> {
            List<SysAdminEntity> entities = sysAdminService.findByParentId(item.getAdmin_id());
            if (entities.size() > 0) {
                entitys.add(item);
                findChildAdmin(entities, entitys);
            } else {
                entitys.add(item);
            }
        });
        return entitys;
    }

    /**
     * 修改用户密码
     *
     * @param admin_password 密码
     * @return
     */
    @SystemLog(module = "用户管理", methods = "修改用户密码")
    @PostMapping("updatePassword")
    @ApiOperation(value = "修改用户密码", notes = "修改用户密码")
    @ApiImplicitParam(name = "admin_password", value = "用户密码", required = true, dataType = "string", paramType = "query")
    public ResultData updatePassword(String admin_password) {
        return sysAdminService.updatePassword(admin_password);
    }

    /**
     * 重置密码
     *
     * @param admin_id
     * @param admin_password
     * @return
     */
    @SystemLog(module = "用户管理", methods = "重置用户密码")
    @PostMapping("resstingPassword")
    @ApiOperation(value = "重置用户密码", notes = "重置用户密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "admin_id", value = "用户id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "admin_password", value = "用户密码", required = true, dataType = "string", paramType = "query")
    })
    public ResultData resstingPassword(HttpServletRequest request, Integer admin_id, String admin_password) {
        //判断重置密码不能为空
        if (StringUtils.isBlank(admin_password)) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage(), null);
        }
        //获取管理员信息
        SysAdminEntity sysAdminEntity = sysAdminService.findByPrimaryKey(admin_id);
        sysAdminEntity.setAdmin_id(admin_id);
        sysAdminEntity.setAdmin_name(sysAdminEntity.getAdmin_name());
        sysAdminEntity.setAdmin_password(admin_password);
        new PasswordUtil().encryptPassword(sysAdminEntity);
        String update = sysAdminService.update(sysAdminEntity);
        if ("success".equals(update)) {
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), null);
        }
        return new ResultData(ResultCode.ERROR.getCode(), false, ResultCode.ERROR.getMessage(), null);
    }


    /**
     * 根据管理员id查询对应的用户组信息
     * sysAdminController/findUsergroupByParentId
     *
     * @return
     */

    @Autowired
    private SysUsergroupMapper sysUsergroupMapper;

    @GetMapping("/findUsergroupByParentId")
    @ApiOperation(notes = "父级id", value = "根据管理员id查询对应的用户组信息")
    public ResultData findUsergroupByParentId(Integer parentId, HttpServletRequest request) {
        //查询管理员下级
        List<SysAdminEntity> entities = sysAdminService.findByParentId(parentId);
        List<SysUsergroupVo> usergroup = sysUsergroupMapper.findUsergroupsByAdaminId(parentId);
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), usergroup);
    }

    @PostMapping("distributionExport")
    public ResultData distributionExport(Integer admin_id,Integer export_role_id){
        ResultData resultData = sysAdminService.distributionExport(admin_id,export_role_id);
        return resultData;
    }


}
