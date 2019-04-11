package cn.crm.service.terrace.impl;

import cn.crm.entity.SysUserEntity;
import cn.crm.entity.SysUsergroupEntity;
import cn.crm.entity.repair.RepairRoleEntity;
import cn.crm.mapper.repair.RepairRoleMapper;
import cn.crm.mapper.repair.SchoolConfigMapper;
import cn.crm.mapper.sys.SysUserMapper;
import cn.crm.mapper.sys.SysUsergroupMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.terrace.RepairAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author MYZ
 * @version V1.0
 * @Description: 微信新用户登录学校授权
 * @Package cn.crm.service.terrace.impl
 * @date 2019/3/26 17:49
 */
@Service
public class RepairAppServiceImpl implements RepairAppService {
    @Autowired
    private SchoolConfigMapper schoolConfigMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RepairRoleMapper repairRoleMapper;
    @Autowired
    private SysUsergroupMapper sysUsergroupMapper;

    /**
     * @param lon       经度
     * @param lat       纬度
     * @param school_id 学校id
     * @param request
     * @Description :   新用户登陆需要传递经度,纬度或者收到选择学校id
     * @Return : cn.crm.result.ResultData
     * @Author : MYZ
     * @Date : 2019/3/26 15:38
     */
    @Override
    public ResultData updateUser(Double lon, Double lat, Integer school_id, HttpServletRequest request) {
        //从session中获取用户信息
        SysUserEntity userEntity = (SysUserEntity) request.getSession().getAttribute("userEntity");
        //判断用户信息是否为空
        if (userEntity == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage());
        }
        //判断所有参数都是否为空
        if (lon == null && lat == null && school_id == null) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
        }
        //判断传递经纬度是否为空
        if (lon == null || lat == null) {
            //根据学校id查询对应的分组id并且父类id为0
            Integer userGroupId = sysUsergroupMapper.findUserGroupId(school_id);
            userEntity.setUserGroup_id(userGroupId);
            userEntity.setSchool_id(school_id);
            if (userEntity.getRole_id() == null || userEntity.getRole_id() < 0) {
                //为用户设置默认角色
                Example example = new Example(RepairRoleEntity.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("is_default", 0);
                RepairRoleEntity repairRoleEntity = repairRoleMapper.selectOneByExample(example);
                userEntity.setRole_id(repairRoleEntity.getRole_id());
            }
            //调用修改方法
            int i = sysUserMapper.updateByPrimaryKeySelective(userEntity);
            if (i > 0) {
                HttpSession session = request.getSession();
                session.setAttribute("userEntity", userEntity);
                return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
            }
            return new ResultData(ResultCode.ERROR.getCode(), true, ResultCode.ERROR.getMessage());
        }
        //根据传递过来的经纬度,查询数据库对应的学校信息
        Integer schoolId = schoolConfigMapper.findSchoolId(lon, lat);
        //判断学校id是否为空
        if (schoolId != null) {
            //根据学校id查询对应的分组id并且父类id为0
            Integer userGroupId = sysUsergroupMapper.findUserGroupId(school_id);
            userEntity.setUserGroup_id(userGroupId);
            userEntity.setSchool_id(schoolId);
            if (userEntity.getRole_id() == null || userEntity.getRole_id() < 1) {
                //为用户设置默认角色
                Example example = new Example(RepairRoleEntity.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("is_default", 0);
                RepairRoleEntity repairRoleEntity = repairRoleMapper.selectOneByExample(example);
                userEntity.setRole_id(repairRoleEntity.getRole_id());
            }
            //调用修改方法
            int i = sysUserMapper.updateByPrimaryKeySelective(userEntity);
            if (i > 0) {
                return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
            }
            return new ResultData(ResultCode.ERROR.getCode(), true, ResultCode.ERROR.getMessage(), schoolId);
        }
        return new ResultData(ResultCode.SEEKSDD_FAILED.getCode(), true, ResultCode.SEEKSDD_FAILED.getMessage());
    }
}


