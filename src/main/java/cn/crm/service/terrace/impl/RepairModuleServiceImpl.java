package cn.crm.service.terrace.impl;

import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.SysUserEntity;
import cn.crm.entity.terrace.RepairModuleEntity;
import cn.crm.entity.terrace.RepairSchoolModuleEntity;
import cn.crm.mapper.sys.SysUserMapper;
import cn.crm.mapper.sys.SysUsergroupMapper;
import cn.crm.mapper.terrace.RepairModuleMapper;
import cn.crm.mapper.terrace.RepairSchoolModuleMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.terrace.RepairModuleService;
import cn.crm.util.*;
import cn.crm.vo.ModuleVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.reflections.Reflections.log;

/**
 * TODO 在此加入类描述  模块服务层实现类
 *
 * @author MYZ
 * @version 2019-03-25 10:00:12
 */

@Service
public class RepairModuleServiceImpl extends BaseServiceImpl<RepairModuleEntity> implements RepairModuleService {

    @Autowired
    private RepairModuleMapper repairModuleMapper;

    @Autowired
    private SysUsergroupMapper sysUsergroupMapper;
    @Autowired
    private RepairSchoolModuleMapper repairSchoolModuleMapper;

    @Autowired
    private SysUserMapper sysUserMapper;


    /**
     * 查询模块信息
     *
     * @param module_name   可根据模块名模糊查询
     * @param module_status 根据状态查询
     * @return
     */
    @Override
    public ResultData findAllModule(String module_name, Integer module_status, HttpServletRequest request) {
        //获取登陆用户信息
        SysAdminEntity sysAdminEntity = (SysAdminEntity) request.getSession().getAttribute(AdminEntityUtil.ADMIN_SESSION_KEY);
        //判断是否为空
        if (sysAdminEntity == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage());
        }
        //获取登陆人id
        Integer admin_id = sysAdminEntity.getAdmin_id();
        //查询对应的学校id
        List<Integer> schoolId = sysUsergroupMapper.findSchoolId(admin_id);
        //判断查询是否为空
        if (schoolId.size() == 0 && schoolId == null) {
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
        }
//        //根据学校id查询对应模块id
//        List<Integer> moduleId = repairSchoolModuleMapper.findModuleId(schoolId);
//        Example example = new Example(RepairModuleEntity.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andIn("id", moduleId);
//        //判断模块名称是否为空
//        if (StringUtils.isEmpty(module_name) && module_status == null) {
//            //根据权重分页降序
//            PageUtil.startPage("module_rank", "desc");
//            List<RepairModuleEntity> repairModuleEntities = repairModuleMapper.selectByExample(example);
//            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), new PageInfo<RepairModuleEntity>(repairModuleEntities));
//        }
//        //不为空根据模块名称模糊查询
//        if (StringUtils.isNotEmpty(module_name)) {
//            criteria.andLike("module_name", "%" + module_name + "%");
//        }
//        //不为空根据模块状态查询
//        if (module_status != null) {
//            criteria.andEqualTo("module_status", module_status);
//        }
        PageUtil.startPage("module_rank", "desc");
        List<ModuleVo> repairModuleEntities = repairModuleMapper.findAllModule(schoolId, module_name, module_status);
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), new PageInfo<ModuleVo>(repairModuleEntities));
    }

    /**
     * 查询模块信息
     *
     * @return
     */
    @Override
    public ResultData findModule(HttpServletRequest request) {
        //获取登陆用户信息
        SysAdminEntity sysAdminEntity = (SysAdminEntity) request.getSession().getAttribute(AdminEntityUtil.ADMIN_SESSION_KEY);
        //判断是否为空
        if (sysAdminEntity == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage());
        }
        //获取登陆人id
        Integer admin_id = sysAdminEntity.getAdmin_id();
        //查询对应的学校id
        List<Integer> schoolId = sysUsergroupMapper.findSchoolId(admin_id);
        //判断查询是否为空
        if (schoolId.size() == 0 && schoolId == null) {
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
        }
        //根据学校id查询对应模块id
        List<Integer> moduleId = repairSchoolModuleMapper.findModuleId(schoolId);
        Example example = new Example(RepairModuleEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", moduleId);
        List<RepairModuleEntity> repairModuleEntities = repairModuleMapper.selectByExample(example);
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), repairModuleEntities);
    }

    /**
     * 根据模块id查询一个模块信息
     *
     * @param id 模块id
     * @return
     */
    @Override
    public ResultData findModuleById(Integer id) {
        //调用查询方法
        ModuleVo moduleVo = repairModuleMapper.findModuleById(id);
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), moduleVo);

    }

    /**
     * 新增模块信息
     *
     * @param repairModuleEntity 模块实体类
     * @return
     */
    @Override
    @Transactional
    public ResultData addModule(RepairModuleEntity repairModuleEntity, HttpServletRequest request) {
        //获取登陆用户信息
        SysAdminEntity sysAdminEntity = (SysAdminEntity) request.getSession().getAttribute(AdminEntityUtil.ADMIN_SESSION_KEY);
        //判断是否为空
        if (sysAdminEntity == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage(), null);
        }
        //判断模块信息是否为空
        if (repairModuleEntity == null) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
        }
        //设置发布人id
        repairModuleEntity.setCreate_userId(sysAdminEntity.getAdmin_id());
        repairModuleEntity.setCreate_userName(sysAdminEntity.getAdmin_name());
        repairModuleEntity.setCreate_time(new Date());
        //设置状态
        repairModuleEntity.setModule_status(1);
        repairModuleEntity.setModule_rank(0);
        //调用保存方法
        int insert = repairModuleMapper.insert(repairModuleEntity);
        int insert1 = 0;
        //判断返回结果
        if (insert > 0) {
            //同时插入数据到学校和模块中间表
            //获取新增模块id
            Integer id = repairModuleEntity.getId();
            //获取学校ids
            String schoolIds = repairModuleEntity.getSchoolIds();
            //判断学校ids是否为空
            if (StringUtils.isEmpty(schoolIds)) {
                return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
            }
            //遍历得到学校id
            String[] split = schoolIds.split(",");
            for (String schoolId : split) {
                //创建中间表实体类
                RepairSchoolModuleEntity repairSchoolModuleEntity = new RepairSchoolModuleEntity();
                //插入中间表
                repairSchoolModuleEntity.setModule_id(id);
                repairSchoolModuleEntity.setSchool_id(Integer.valueOf(schoolId));
                insert1 = repairSchoolModuleMapper.insert(repairSchoolModuleEntity);
            }
            if (insert1 > 0) {
                return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
            }
            return new ResultData(ResultCode.ERROR.getCode(), true, ResultCode.ERROR.getMessage());
        }
        return new ResultData(ResultCode.ERROR.getCode(), true, ResultCode.ERROR.getMessage());
    }

    /**
     * 修改模块信息
     *
     * @param repairModuleEntity 模块实体类
     * @return
     */
    @Override
    @Transactional
    public ResultData updateModule(RepairModuleEntity repairModuleEntity, HttpServletRequest request) {
        //获取登陆用户信息
        SysAdminEntity sysAdminEntity = (SysAdminEntity) request.getSession().getAttribute(AdminEntityUtil.ADMIN_SESSION_KEY);
        //判断是否为空
        if (sysAdminEntity == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage(), null);
        }
        //判断文章信息是否为空
        if (repairModuleEntity == null) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
        }
        //获取学校ids
        String schoolIds = repairModuleEntity.getSchoolIds();
        //设置修改人id
        repairModuleEntity.setUpdate_user_id(sysAdminEntity.getAdmin_id());
        repairModuleEntity.setUpdate_user_name(sysAdminEntity.getAdmin_name());
        repairModuleEntity.setUpdate_time(new Date());
        //调用修改方法
        int insert = repairModuleMapper.updateByPrimaryKeySelective(repairModuleEntity);
        //判断返回结果
        if (insert > 0) {
            //同时修改学校和模块中间表数据
            //获取修改模块id
            Integer id = repairModuleEntity.getId();
            //查询中间表是否有数据
            Example example = new Example(RepairSchoolModuleEntity.class);
            example.createCriteria().andEqualTo("module_id", id);
            List<RepairSchoolModuleEntity> repairSchoolModuleEntities = repairSchoolModuleMapper.selectByExample(example);
            //判断集合是否为空
            if (repairSchoolModuleEntities.size() > 0 && repairSchoolModuleEntities != null) {
                //删除中间表数据
                int i = repairSchoolModuleMapper.deleteByExample(example);
                int insert1 = 0;
                //判断返回结果
                if (i > 0) {
                    //判断ids是否为空
                    if (StringUtils.isEmpty(schoolIds)) {
                        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
                    }
                    //遍历得到学校id
                    String[] split = schoolIds.split(",");
                    for (String schoolId : split) {
                        //创建中间表实体类
                        RepairSchoolModuleEntity repairSchoolModuleEntity = new RepairSchoolModuleEntity();
                        //插入中间表
                        repairSchoolModuleEntity.setModule_id(id);
                        repairSchoolModuleEntity.setSchool_id(Integer.valueOf(schoolId));
                        insert1 = repairSchoolModuleMapper.insert(repairSchoolModuleEntity);
                    }
                    if (insert1 > 0) {
                        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
                    }
                    return new ResultData(ResultCode.ERROR.getCode(), true, ResultCode.ERROR.getMessage());
                }
                return new ResultData(ResultCode.ERROR.getCode(), true, ResultCode.ERROR.getMessage());
            }
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
        }
        return new ResultData(ResultCode.ERROR.getCode(), true, ResultCode.ERROR.getMessage());
    }

    /**
     * 删除一个模块信息
     *
     * @param id 模块id
     * @return
     */
    @Override
    @Transactional
    public ResultData deleteModule(Integer id) {
        //判断id是否为空
        if (id == null) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
        }
        //根据id查询模块状态
        RepairModuleEntity repairModuleEntity = repairModuleMapper.selectByPrimaryKey(id);
        Integer module_status = repairModuleEntity.getModule_status();
        //判断状态
        if (module_status == 1) {
            return new ResultData(ResultCode.ERROR_RESOURCE.getCode(), false, ResultCode.ERROR_RESOURCE.getMessage());
        }
        //调用删除方法
        int i = repairModuleMapper.deleteByPrimaryKey(id);
        //判断返回结果
        if (i > 0) {
            //同时删除中间表对应数据
            Example example = new Example(RepairSchoolModuleEntity.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("module_id", id);
            List<RepairSchoolModuleEntity> repairSchoolModuleEntities = repairSchoolModuleMapper.selectByExample(example);
            //判断查询结果
            if (repairSchoolModuleEntities.size() > 0 && repairSchoolModuleEntities != null) {
                int i1 = repairSchoolModuleMapper.deleteByExample(example);
                if (i1 > 0) {
                    return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
                }
                return new ResultData(ResultCode.ERROR.getCode(), true, ResultCode.ERROR.getMessage());
            }
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
        }
        return new ResultData(ResultCode.ERROR.getCode(), true, ResultCode.ERROR.getMessage());
    }

    /**
     * 上传模块图标
     *
     * @param request
     * @param file
     * @return
     */
    @Override
    public ResultData uploadModuleImage(HttpServletRequest request, MultipartFile file) {
        String targetPath = PropertiesUtil.getValue("MODULE_IMAGE_URL");
        String url = IoUploadUtil.upload(file, targetPath);
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), url);
    }

    /**
     * 手机端查询模块信息
     *
     * @param request
     * @return
     */
    @Override
    public ResultData findAppModule(HttpServletRequest request) {
        SysUserEntity userEntity = (SysUserEntity) request.getSession().getAttribute("userEntity");
        //判断信息是否为空
        if (userEntity == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage(), null);
        }
        //获取用户id查询是否有学校
        SysUserEntity sysUserEntity = sysUserMapper.selectByPrimaryKey(userEntity.getUser_id());
        //获取学校id
        Integer school_id = sysUserEntity.getSchool_id();
        //根据学校id查询对应得模块id
        List<Integer> moduleIds = repairSchoolModuleMapper.findAppModuleId(school_id);
        //查询获取模块id信息是否为空
        if (moduleIds.size() > 0 && moduleIds != null) {
            //根据模块id查询模块信息
            Example example = new Example(RepairModuleEntity.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andIn("id", moduleIds);
            criteria.andEqualTo("module_status", 1);
            List<RepairModuleEntity> repairModuleEntities = repairModuleMapper.selectByExample(example);
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), new PageInfo<RepairModuleEntity>(repairModuleEntities));
        }
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), null);
    }
}


