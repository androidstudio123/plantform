package cn.crm.service.user.impl;


import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.SysAdminUsergroupEntity;
import cn.crm.entity.SysUserEntity;
import cn.crm.entity.SysUserRoomEntity;
import cn.crm.entity.repair.RepairRoleEntity;
import cn.crm.mapper.repair.RepairRoleMapper;
import cn.crm.mapper.sys.SysAdminUsergroupMapper;
import cn.crm.mapper.sys.SysUserMapper;
import cn.crm.mapper.sys.SysUserRoomMapper;
import cn.crm.mapper.sys.SysUsergroupMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.user.SysUserService;
import cn.crm.util.*;
import cn.crm.util.slideVerification.validate.StringUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.reflections.Reflections.log;

/**
 * @param {NAME} 1 查询用户信息
 *               2 根据id查询用户信息
 *               3 新增用户信息
 *               4 修改用户信息
 *               5 删除用户信息
 * @Description: TODO  用户信息服务层实现类
 * @Author:MYZ
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserEntity> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoomMapper sysUserRoomMapper;

    @Autowired
    private SysAdminUsergroupMapper sysAdminUsergroupMapper;
    @Autowired
    private SysUsergroupMapper sysUsergroupMapper;
    @Autowired
    private RepairRoleMapper repairRoleMapper;

    /**
     * 查询用户组下的用户信息
     *
     * @param user_name    根据用户名模糊查询
     * @param userGroup_id 用户组id
     * @return
     */
    @Override
    public ResultData findUser(String user_name, Integer userGroup_id, Integer admin_id) {
        List<Integer> list = new ArrayList<>();
        //判断用户组id是否为空
        if (userGroup_id == null) {
            //调用分页
            PageUtil.startPage("user_createTime", "desc");
            //默认查询该管理员下所有用户信息
            List<SysUserEntity> allUser = sysUserMapper.findAllUser(admin_id, user_name);
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), new PageInfo<SysUserEntity>(allUser));
        } else {
            //查询当前登录管理的用户组id
            List<Integer> userGroupId = sysAdminUsergroupMapper.findUserGroupIds(admin_id);
            //递归获取传递过来的用户组id的所有子级
            list.add(userGroup_id);
            list = getNextChildId(userGroup_id, list);
            userGroupId.retainAll(list);
            //创建查询条件
            Example example = new Example(SysUserEntity.class);
            Example.Criteria criteria = example.createCriteria();
            //判断用户名是否为空
            if (StringUtils.isNotEmpty(user_name)) {
                criteria.andLike("user_name", "%" + user_name + "%");
            }
            criteria.andIn("userGroup_id", userGroupId);
            //根据注册时间倒叙分页查询,默认不传一页十条
            PageUtil.startPage("user_createTime", "desc");
            //调用查询方法
            List<SysUserEntity> sysUserEntities = sysUserMapper.selectByExample(example);
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), new PageInfo<SysUserEntity>(sysUserEntities));
        }
    }

    /**
     * 根据学校id查询用户信息
     *
     * @param schoolId
     * @return
     */
    @Override
    public List<SysUserEntity> findUserBySchoolId(Integer schoolId) {
        PageUtil.startPage("user_createTime", "desc");
        List<SysUserEntity> userBySchoolId = sysUserMapper.findUserBySchoolId(schoolId);
        return userBySchoolId;
    }

    /**
     * 根据当前用户组ID获取下级用户组ID
     *
     * @param userGroup_id 当前用户组id
     * @param ids          用户组ID的集合
     * @return
     */
    private List<Integer> getNextChildId(Integer userGroup_id, List<Integer> ids) {
        List<Integer> nextUserGroupId = sysUsergroupMapper.findNextUserGroupId(userGroup_id);
        if (nextUserGroupId == null || nextUserGroupId.size() == 0) {
            return ids;
        } else {
            ids.addAll(nextUserGroupId);
            for (Integer integer : nextUserGroupId) {
                getNextChildId(integer, ids);
            }
        }
        return ids;
    }

    /**
     * 根据用户id查询这条用户详细信息
     * MYZ
     *
     * @param user_id
     * @return
     */
    @Override
    public ResultData findUserByUserId(Integer user_id) {
        //判断传递用户id是否为空
        if (user_id == null) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage(), null);
        }
        //调用查询接口
        SysUserEntity sysUserEntity = sysUserMapper.selectByPrimaryKey(user_id);
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), sysUserEntity);
    }

    /**
     * 新增用户信息
     * MYZ
     *
     * @param sysUserEntity 用户信息实体类
     * @return
     */
    @Override
    public ResultData addUser(SysUserEntity sysUserEntity) {
        //判断传入的实体类信息是否为空
        if (sysUserEntity == null) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
        }
        //获取用户的用户名
        String user_name = sysUserEntity.getUser_name();
        //获取用户的学号
        Integer user_number = sysUserEntity.getUser_number();
        //获取分组id
        Integer userGroup_id = sysUserEntity.getUserGroup_id();
        //判断用户名是否为空
        if (StringUtils.isEmpty(user_name)) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
        }
        //判断分组id是否为空
        if (userGroup_id == null) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
        }
        //根据得到的用户名查询数据库是否有重名的信息
        Example example = new Example(SysUserEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.orEqualTo("user_name", user_name);
        criteria.orEqualTo("user_number", user_number);
        //调用查询接口
        List<SysUserEntity> sysUserEntities = sysUserMapper.selectByExample(example);
        //判断查询信息是否为空
        if (sysUserEntities.size() > 0 && sysUserEntities != null) {
            return new ResultData(ResultCode.ALREADYEXISTS.getCode(), false, ResultCode.ALREADYEXISTS.getMessage());
        }
        //设置新增时间
        sysUserEntity.setUser_createTime(new Date());
        //调用新增接口
        int insert = sysUserMapper.insert(sysUserEntity);
        //判断返回结果
        if (insert > 0) {
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
        }
        return new ResultData(ResultCode.ERROR.getCode(), false, ResultCode.ERROR.getMessage());
    }

    /**
     * 修改用户信息
     * MYZ
     *
     * @param sysUserEntity 用户信息实体类
     * @return
     */
    @Override
    public ResultData updateUser(SysUserEntity sysUserEntity, HttpServletRequest request) {
        //判断传入的实体类信息是否为空
        if (sysUserEntity == null) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
        }
        //获取用户的用户名
        String user_name = sysUserEntity.getUser_name();
        //获取用户的id
        Integer user_id = sysUserEntity.getUser_id();
        //判断用户名是否为空
        if (StringUtils.isEmpty(user_name)) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
        }
        //根据得到的用户名查询数据库是否有重名的信息
        Example example = new Example(SysUserEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("user_name", user_name);
        criteria.andNotEqualTo("user_id", user_id);
        List<SysUserEntity> sysUserEntities = sysUserMapper.selectByExample(example);
        //判断数据库是否存在重名的信息
        if (sysUserEntities.size() > 0 && sysUserEntities != null) {
            return new ResultData(ResultCode.ALREADYEXISTS.getCode(), false, ResultCode.ALREADYEXISTS.getMessage());
        }
        //设置时间
        sysUserEntity.setUser_createTime(new Date());
        //调用修改方法
        int i = sysUserMapper.updateByPrimaryKeySelective(sysUserEntity);
        //判断返回结果
        if (i > 0) {
            HttpSession session = request.getSession();
            session.setAttribute("userEntity", sysUserEntity);
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
        }
        return new ResultData(ResultCode.ERROR.getCode(), false, ResultCode.ERROR.getMessage());
    }

    /**
     * 根据用户id删除用户信息
     * MYZ
     *
     * @param user_id 用户id
     * @return
     */
    @Override
    @Transactional
    public ResultData deleteUser(Integer user_id) {
        //判断传过来的id是否为空
        if (user_id == null) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
        }
        //调用删除接口,删除用户信息
        int i1 = sysUserMapper.deleteByPrimaryKey(user_id);
        //判断返回结果
        if (i1 > 0) {
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
        }
        return new ResultData(ResultCode.RETRY.getCode(), false, ResultCode.RETRY.getMessage());
    }

    /**
     * 上传用户头像
     * MYZ
     *
     * @param request
     * @param file    上传文件
     * @return
     */
    @Override
    public ResultData uploadUserAvatar(HttpServletRequest request,MultipartFile file) {
        String targetPath = PropertiesUtil.getValue("AVATAR_IMAGES");
        String url = IoUploadUtil.upload(file, targetPath);
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), url);
    }


    /**
     * 用于导出用户信息到excel(不分页)
     * MYZ
     *
     * @param userGroup_id 用户组id
     * @return
     */
    @Override
    public List<SysUserEntity> finduser(HttpServletRequest request,Integer admin_id, Integer userGroup_id, String user_name) {
        //判断用户组id是否为空
        if (userGroup_id == null) {
//            //从session中获取用户信息
//            SysAdminEntity sysAdminEntity1 = (SysAdminEntity) request.getSession().getAttribute("sysAdminEntity");
//            //判断获取信息是否为空
//            if (sysAdminEntity1 == null) {
//                return null;
//            }
            //默认查询该管理员下所有用户信息
            List<SysUserEntity> allUser = sysUserMapper.findAllUser(admin_id, user_name);
            return allUser;
        } else {
            //创建查询条件
            Example example = new Example(SysUserEntity.class);
            Example.Criteria criteria = example.createCriteria();
            //默认查询该用户组的用户信息
            criteria.andEqualTo("userGroup_id", userGroup_id);
            //判断用户名是否为空
            if (StringUtils.isNotEmpty(user_name)) {
                criteria.andLike("user_name", "%" + user_name + "%");
            }
            //调用查询方法
            List<SysUserEntity> sysUserEntities = sysUserMapper.selectByExample(example);
            return sysUserEntities;
        }
    }

    @Override
    public ResultData distributionRole(Integer id, Integer role_id) {
        SysUserEntity sysUserEntity = sysUserMapper.selectByPrimaryKey(id);
        if (sysUserEntity == null) {
            return new ResultData(20001, false, "网络错误请重试");
        }
        RepairRoleEntity repairRoleEntity = repairRoleMapper.selectByPrimaryKey(role_id);
        if (repairRoleEntity == null) {
            return new ResultData(20002, false, "角色名无效，请重新选择");
        }
        sysUserEntity.setRole_id(role_id);
        int result = sysUserMapper.updateByPrimaryKeySelective(sysUserEntity);
        if (result > 0) {
            return new ResultData(20000, true, "授权角色成功");
        }
        return new ResultData(20003, false, "授权角色失败，请重试");
    }

    /**
     * @param file        Excel文件
     * @param userGroupId 用户分组ID
     * @param falg        如果有重复是否覆盖 1 覆盖 2不覆盖
     * @return
     */
    @Override
    @Transactional
    public ResultData improtUserExcel(MultipartFile file, Integer userGroupId, Integer falg) {

        ImportParams params = new ImportParams();
        params.setHeadRows(1);
        params.setTitleRows(1);
        //是否验证表格数据
        params.setNeedVerfiy(true);
        List<SysUserEntity> list;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), SysUserEntity.class, params);
        } catch (Exception e) {
            log.error("读取用户Excel失败", e);
            return new ResultData(ResultCode.RETRY.getCode(), false, ResultCode.RETRY.getMessage(), e.toString());
        }
        Integer count = 0;
        StringBuffer msg = new StringBuffer();
        for (SysUserEntity entity : list) {
            entity.setUserGroup_id(userGroupId);
            Integer user_number = entity.getUser_number();
            SysUserEntity userEntity = this.findUserByUserNumber(user_number);
            if (null != userEntity) {
                if (falg == 1) {
                    Integer user_id = userEntity.getUser_id();
                    entity.setUser_id(user_id);
                    count += sysUserMapper.updateByPrimaryKeySelective(entity);
                } else {

                    msg.append(user_number).append("、");
                    continue;
//                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                    return new ResultData(ResultCode.ALREADYEXISTS.getCode(), false, ResultCode.ALREADYEXISTS.getMessage());
                }
            } else {
                entity.setUser_createTime(new Date());


                count += sysUserMapper.insertSelective(entity);
            }
        }
        if (msg.length() > 1) {
            msg.deleteCharAt(msg.length() - 1);
        }
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), msg.toString());
//       }else {
//           return new ResultData(ResultCode.ALREADYEXISTS.getCode(), false, ResultCode.ALREADYEXISTS.getMessage());
//       }
    }


    /**
     * 根据学号、工号查询用户基本信息
     *
     * @param user_number
     * @return
     */
    @Override
    public SysUserEntity findUserByUserNumber(Integer user_number) {
        Example example = new Example(SysUserEntity.class);
        example.createCriteria().andEqualTo("user_number", user_number);
        List<SysUserEntity> sysUserEntities = sysUserMapper.selectByExample(example);
        return sysUserEntities.get(0);
    }


    /**
     * 修改手机号
     *
     * @param userPhone
     * @return
     */
    @Override
    public ResultData updatePhone(String userPhone, HttpServletRequest request) {
        //从session中获取用户信息
        SysUserEntity userEntity = (SysUserEntity) request.getSession().getAttribute("userEntity");
        //判断用户信息是否为空
        if (userEntity == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage());
        }
        userEntity.setUser_phone(userPhone);
        int i = sysUserMapper.updateByPrimaryKeySelective(userEntity);
        if (i > 0) {
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
        }
        return new ResultData(ResultCode.ERROR.getCode(), false, ResultCode.ERROR.getMessage());
    }

    @Override
    public ResultData distributionExport(Integer user_id, Integer export_role_id) {
        //根据id查询管理员信息
        SysUserEntity sysUserEntity = sysUserMapper.selectByPrimaryKey(user_id);
        sysUserEntity.setExport_role_id(export_role_id);
        int i = sysUserMapper.updateByPrimaryKeySelective(sysUserEntity);
        if(i>0){
            return new ResultData(20000,true,"分配成功");
        }
        return new ResultData(20001,false,"分配失败，请重试");
    }
}
