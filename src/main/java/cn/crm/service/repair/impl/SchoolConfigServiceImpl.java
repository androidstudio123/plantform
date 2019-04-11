package cn.crm.service.repair.impl;


import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.SysAdminUsergroupEntity;
import cn.crm.entity.SysUserEntity;
import cn.crm.entity.SysUsergroupEntity;
import cn.crm.entity.repair.RepairAreaEntity;
import cn.crm.entity.repair.RepairRoleEntity;
import cn.crm.entity.repair.SchoolConfigEntity;
import cn.crm.mapper.repair.RepairAreaMapper;
import cn.crm.mapper.repair.RepairRoleMapper;
import cn.crm.mapper.repair.SchoolConfigMapper;
import cn.crm.mapper.sys.SysAdminUsergroupMapper;
import cn.crm.mapper.sys.SysUserMapper;
import cn.crm.mapper.sys.SysUsergroupMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.repair.SchoolConfigService;
import cn.crm.util.AdminEntityUtil;
import cn.crm.util.LatLonUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Service
public class SchoolConfigServiceImpl extends BaseServiceImpl<SchoolConfigEntity> implements SchoolConfigService {

    @Autowired
    private SchoolConfigMapper schoolConfigMapper;

    @Autowired
    private SysUsergroupMapper sysUsergroupMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RepairAreaMapper repairAreaMapper;

    @Autowired
    private SysAdminUsergroupMapper sysAdminUsergroupMapper;

    @Autowired
    private RepairRoleMapper repairRoleMapper;
    /**
     * 查询所有学校配置列表
     *
     * @return
     */
    @Override
    public ResultData findSchoolConfig(Integer pageNum, Integer pageSize, String schoolName, Integer status) {
        ResultData resultData = null;
        Example example = new Example(SchoolConfigEntity.class);
        if(schoolName == null || schoolName == ""){
            PageHelper.startPage(pageNum, pageSize);
            List<SchoolConfigEntity> schoolConfigEntities = schoolConfigMapper.selectAll();
            resultData = new ResultData(20000, true, "查询成功", schoolConfigEntities, schoolConfigEntities.size());
            return resultData;
        }else{
            Example.Criteria criteria = example.createCriteria();
            criteria.andLike("school_name", "%" + schoolName + "%");
            criteria.andEqualTo("school_state", status);
            List<SchoolConfigEntity> schoolConfigEntities1 = schoolConfigMapper.selectByExample(example);
            List<SchoolConfigEntity> schoolConfigEntities = schoolConfigMapper.selectByExample(example);
            resultData = new ResultData(20000, true, "查询成功", schoolConfigEntities, schoolConfigEntities1.size());
        }
        return resultData;
    }

    /**
     * @param
     * @Description : 查询当前人管理的学校信息
     * @Return : cn.crm.result.ResultData
     * @Author : MYZ
     * @Date : 2019/3/28 11:04
     */
    @Override
    public ResultData findAllSchoolConfig(HttpServletRequest request) {
        //获取登陆用户信息
        SysAdminEntity sysAdminEntity = (SysAdminEntity) request.getSession().getAttribute(AdminEntityUtil.ADMIN_SESSION_KEY);
        //判断是否为空
        if (sysAdminEntity == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage(), null);
        }
        //获取登陆人id
        Integer admin_id = sysAdminEntity.getAdmin_id();
        //查询对应的学校id
        List<Integer> schoolId = sysUsergroupMapper.findSchoolId(admin_id);
        Example example = new Example(SchoolConfigEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("school_id", schoolId);
        criteria.andEqualTo("school_state", 1);
        //调用查询方法
        List<SchoolConfigEntity> schoolConfigEntities = schoolConfigMapper.selectByExample(example);
        return new ResultData(ResultCode.SUCCESS.getCode(), false, ResultCode.SUCCESS.getMessage(), schoolConfigEntities);
    }

    /**
     * @param
     * @Description : 查询所有学校信息
     * @Return : cn.crm.result.ResultData
     * @Author : MYZ
     * @Date : 2019/3/28 11:04
     */
    @Override
    public ResultData findAllSchoolConfig() {
        Example example = new Example(SchoolConfigEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("school_state", 1);
        //调用查询方法
        List<SchoolConfigEntity> schoolConfigEntities = schoolConfigMapper.selectByExample(example);
        return new ResultData(ResultCode.SUCCESS.getCode(), false, ResultCode.SUCCESS.getMessage(), schoolConfigEntities);
    }

    /**
     * 新增学校配置
     *
     * @param schoolConfigEntity
     * @return
     */
    @Override
    public ResultData saveSchoolConfig(SchoolConfigEntity schoolConfigEntity, HttpServletRequest request) {
        //从session中获取用户信息
        SysAdminEntity sysAdminEntity1 = (SysAdminEntity) request.getSession().getAttribute("sysAdminEntity");
        //判断获取信息是否为空
        if (sysAdminEntity1 == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage());
        }
        ResultData resultData = null;
        if (schoolConfigEntity == null) {
            resultData = new ResultData(20002, false, "学校配置信息不能为空，请输入学校配置信息");
            return resultData;
        }
        //获取传递学校名称
        String school_name = schoolConfigEntity.getSchool_name();
        //根据学校名查询数据库是否存在
        Example example = new Example(SchoolConfigEntity.class);
        example.createCriteria().andEqualTo("school_name", school_name);
        List<SchoolConfigEntity> schoolConfigEntities = schoolConfigMapper.selectByExample(example);
        //判断是否为空
        if (schoolConfigEntities.size() > 0 && schoolConfigEntities != null) {
            return new ResultData(ResultCode.ALREADYEXISTS.getCode(), false, ResultCode.ALREADYEXISTS.getMessage());
        }
        int result = schoolConfigMapper.insertSelective(schoolConfigEntity);
        if (result < 1) {
            resultData = new ResultData(20000, true, "新增失败");
            return resultData;
        }
        RepairAreaEntity repairAreaEntity = new RepairAreaEntity();
        repairAreaEntity.setSchool_id(schoolConfigEntity.getSchool_id());
        repairAreaEntity.setArea_name(schoolConfigEntity.getSchool_name());
        repairAreaEntity.setParent_id(0);
        int i = repairAreaMapper.insertSelective(repairAreaEntity);
        if (i < 1) {
            return new ResultData(20002, false, "添加区域组失败");
        }
        SysUsergroupEntity sysUsergroupEntity = new SysUsergroupEntity();
        sysUsergroupEntity.setUserGroup_parentId(0);
        sysUsergroupEntity.setUserGroup_name(schoolConfigEntity.getSchool_name());
        sysUsergroupEntity.setUserGroup_createTime(new Date());
        sysUsergroupEntity.setSchool_id(schoolConfigEntity.getSchool_id());
        int insert = sysUsergroupMapper.insert(sysUsergroupEntity);
        if (insert < 1) {
            return new ResultData(20003, false, "新增用户组失败");
        }
        Integer userGroupId = sysUsergroupEntity.getUserGroup_id();
        // 当前登录adminID 以及其所有上级ID
        Set<Integer> adminIds = new HashSet<>();
        adminIds.add(sysAdminEntity1.getAdmin_id());
        Integer parentId = sysAdminEntity1.getAdmin_parentId() == null ? 0 : sysAdminEntity1.getAdmin_parentId();
        if (parentId > 0) {
            adminIds.add(parentId);
            AdminEntityUtil.getAllParentAdminId(adminIds, parentId);
        }
        List<SysAdminUsergroupEntity> list = new ArrayList<>();
        //定义state状态为1
        int state = 1;
        for (Integer adminId : adminIds) {
            if (adminId == null || adminId == 0) {
                continue;
            }
            //创建一个管理员和用户组中间表实体类
            SysAdminUsergroupEntity sysAdminUsergroupEntity = new SysAdminUsergroupEntity(adminId, userGroupId, state);
            list.add(sysAdminUsergroupEntity);
        }
        //判断list
        if (list.size() > 0) {
            //调用新增方法
            int insert1 = sysAdminUsergroupMapper.insertList(list);
            //判断返回结果
            if (insert1 > 0) {
                return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
            }
            return new ResultData(ResultCode.RETRY.getCode(), false, ResultCode.RETRY.getMessage());
        }
        return new ResultData(ResultCode.ERROR.getCode(), false, ResultCode.ERROR.getMessage());
    }


    /**
     * 编辑学校配置
     *
     * @param schoolConfigEntity
     * @return
     */
    @Override
    @Transactional
    public ResultData updateSchoolConfig(SchoolConfigEntity schoolConfigEntity) {
        ResultData resultData = null;
        if (schoolConfigEntity == null) {
            resultData = new ResultData(20002, false, "学校配置信息不能为空，请输入学校配置信息");
            return resultData;
        }
        //获取传递学校名称
        String school_name = schoolConfigEntity.getSchool_name();
        Integer school_id = schoolConfigEntity.getSchool_id();
        //根据学校名查询数据库是否存在
        Example example2 = new Example(SchoolConfigEntity.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("school_name", school_name);
        criteria2.andNotEqualTo("school_id", school_id);
        List<SchoolConfigEntity> schoolConfigEntities = schoolConfigMapper.selectByExample(example2);
        //判断是否为空
        if (schoolConfigEntities.size() > 0 && schoolConfigEntities != null) {
            return new ResultData(ResultCode.ALREADYEXISTS.getCode(), false, ResultCode.ALREADYEXISTS.getMessage());
        }
        int result = schoolConfigMapper.updateByPrimaryKeySelective(schoolConfigEntity);
        if (result < 1) {
            resultData = new ResultData(20001, false, "编辑失败");
            return resultData;
        }
        Example example = new Example(RepairAreaEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("school_id", schoolConfigEntity.getSchool_id());
        criteria.andEqualTo("parent_id", 0);
        RepairAreaEntity repairAreaEntity = repairAreaMapper.selectOneByExample(example);
        repairAreaEntity.setArea_name(schoolConfigEntity.getSchool_name());
        int i = repairAreaMapper.updateByPrimaryKeySelective(repairAreaEntity);
        if (i < 1) {
            return new ResultData(20002, false, "编辑区域组失败");
        }
        Example example1 = new Example(SysUsergroupEntity.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("school_id", schoolConfigEntity.getSchool_id());
        criteria1.andEqualTo("userGroup_parentId", 0);
        SysUsergroupEntity sysUsergroupEntity = sysUsergroupMapper.selectOneByExample(example1);
        sysUsergroupEntity.setUserGroup_name(schoolConfigEntity.getSchool_name());
        int i1 = sysUsergroupMapper.updateByPrimaryKeySelective(sysUsergroupEntity);
        if (i1 < 1) {
            return new ResultData(20003, false, "编辑用户组失败");
        }
        resultData = new ResultData(20000, true, "编辑成功");
        return resultData;
    }

    /**
     * 删除学校配置信息
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public ResultData deleteSchoolConfig(Integer id) {
        ResultData resultData = null;
        if (id < 0 || id == null) {
            resultData = new ResultData(20002, false, "请选择学校配置信息");
            return resultData;
        }
        SchoolConfigEntity schoolConfigEntity = schoolConfigMapper.selectByPrimaryKey(id);
        Example example = new Example(RepairAreaEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("school_id", schoolConfigEntity.getSchool_id());
        criteria.andEqualTo("parent_id", 0);
        RepairAreaEntity repairAreaEntity = repairAreaMapper.selectOneByExample(example);
        Example example1 = new Example(RepairAreaEntity.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("parent_id", repairAreaEntity.getArea_id());
        List<RepairAreaEntity> repairAreaEntities = repairAreaMapper.selectByExample(example1);
        if (repairAreaEntities.size() > 0) {
            return new ResultData(20003, false, "该学校下面有分组，请勿删除");
        }
        Example example2 = new Example(SysUsergroupEntity.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("school_id", schoolConfigEntity.getSchool_id());
        criteria2.andEqualTo("userGroup_parentId", 0);
        SysUsergroupEntity sysUsergroupEntity = sysUsergroupMapper.selectOneByExample(example2);
        Example example3 = new Example(SysUsergroupEntity.class);
        Example.Criteria criteria3 = example3.createCriteria();
        criteria3.andEqualTo("userGroup_parentId", sysUsergroupEntity.getUserGroup_id());
        List<SysUsergroupEntity> sysUsergroupEntities = sysUsergroupMapper.selectByExample(example3);
        if (sysUsergroupEntities.size() > 0) {
            return new ResultData(20004, false, "该学校下面有分组，请勿删除");
        }
        int result = schoolConfigMapper.deleteByPrimaryKey(id);
        if (result < 1) {
            resultData = new ResultData(20005, false, "删除失败");
            return resultData;
        }
        int i = repairAreaMapper.deleteByPrimaryKey(repairAreaEntity.getArea_id());
        if (i < 1) {
            return new ResultData(20006, false, "删除失败");
        }
        int i1 = sysUsergroupMapper.deleteByPrimaryKey(sysUsergroupEntity.getUserGroup_id());
        if (i1 < 1) {
            return new ResultData(20007, false, "删除失败");
        }
        //根据用户组id查询用户组和管理员中间表
        Example example4 = new Example(SysAdminUsergroupEntity.class);
        example4.createCriteria().andEqualTo("userGroup_id", sysUsergroupEntity.getUserGroup_id());
        //.调用查询方法
        List<SysAdminUsergroupEntity> sysAdminUsergroupEntities = sysAdminUsergroupMapper.selectByExample(example4);
        //判断查询结果
        if (sysAdminUsergroupEntities.size() > 0 && sysAdminUsergroupEntities != null) {
            //调用删除方法
            int i4 = sysAdminUsergroupMapper.deleteByExample(example4);
            //判断返回结果
            if (i4 > 0) {
                return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
            }
            return new ResultData(ResultCode.ERROR.getCode(), false, ResultCode.ERROR.getMessage());
        }
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
    }

    /**
     * 启用禁用学校配置信息
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    public ResultData changeSchoolConfigStatus(Integer id, Integer status) {
        ResultData resultData = null;
        SchoolConfigEntity schoolConfigEntity = schoolConfigMapper.selectByPrimaryKey(id);
        if (schoolConfigEntity == null) {
            resultData = new ResultData(20001, false, "请选择学校配置信息");
            return resultData;
        }
        schoolConfigEntity.setSchool_state(status);
        int result = schoolConfigMapper.updateByPrimaryKeySelective(schoolConfigEntity);
        if (result > 0) {
            resultData = new ResultData(20000, true, "执行成功");
            return resultData;
        }
        resultData = new ResultData(20002, false, "执行失败，请重试");
        return resultData;
    }

    @Override
    public ResultData findSchoolByJWD(Double lat, Double lon, Integer user_id) {
        Example example = new Example(SchoolConfigEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLessThan("maxLat", lat);
        criteria.andGreaterThan("minlat", lat);
        criteria.andLessThan("maxLon", lon);
        criteria.andGreaterThan("minLon", lon);
        SchoolConfigEntity schoolConfigEntity = schoolConfigMapper.selectOneByExample(example);
        Integer school_id = schoolConfigEntity.getSchool_id();
        //根据school_id查询用户组的信息
        Example example1 = new Example(SysUsergroupEntity.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("school_id", school_id);
        criteria1.andEqualTo("userGroup_parentId", 0);
        SysUsergroupEntity sysUsergroupEntity = sysUsergroupMapper.selectOneByExample(example1);
        //根据user_id 查询用户信息
        SysUserEntity sysUserEntity = sysUserMapper.selectByPrimaryKey(user_id);
        sysUserEntity.setUserGroup_id(sysUsergroupEntity.getUserGroup_id());
        //给用户分配默认角色
        Example example2 = new Example(RepairRoleEntity.class);
        example2.createCriteria().andEqualTo("is_default",0);
        RepairRoleEntity repairRoleEntity = repairRoleMapper.selectOneByExample(example2);
        sysUserEntity.setRole_id(repairRoleEntity.getRole_id());
        //修改用户信息
        int i = sysUserMapper.updateByPrimaryKeySelective(sysUserEntity);
        if (i < 0) {
            return new ResultData(20001, false, "修改失败");
        }
        return new ResultData(20000, true, "查询成功", schoolConfigEntity);
    }
}
