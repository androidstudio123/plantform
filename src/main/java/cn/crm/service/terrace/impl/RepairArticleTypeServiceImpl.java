package cn.crm.service.terrace.impl;


import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.SysUserEntity;
import cn.crm.entity.terrace.*;
import cn.crm.mapper.sys.SysUserMapper;
import cn.crm.mapper.sys.SysUsergroupMapper;
import cn.crm.mapper.terrace.RepairArticleTypeMapper;
import cn.crm.mapper.terrace.RepairSchoolArticleMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.terrace.RepairArticleTypeService;
import cn.crm.util.AdminEntityUtil;
import cn.crm.util.PageUtil;
import cn.crm.util.StringUtils;
import cn.crm.vo.ArticleTypeVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crm.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * TODO 在此加入类描述  文章类型服务层
 *
 * @author MYZ
 * @version 2019-03-25 10:01:09
 */

@Service
public class RepairArticleTypeServiceImpl extends BaseServiceImpl<RepairArticleTypeEntity> implements RepairArticleTypeService {

    @Autowired
    private RepairArticleTypeMapper repairArticleTypeMapper;

    @Autowired
    private SysUsergroupMapper sysUsergroupMapper;

    @Autowired
    private RepairSchoolArticleMapper repairSchoolArticleMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * @param request
     * @param articleType_title 文章类型名
     * @param articleType_state 文章类型状态
     * @Description :  查询文章类型(可根据类型状态和类型名查询)
     * @Return : cn.crm.result.ResultData
     * @Author : MYZ
     * @Date : 2019/3/27 14:37
     */
    @Override
    public ResultData findAllArticleType(HttpServletRequest request, String articleType_title, Integer articleType_state) {
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
//        //根据学校id查询对应类型id
//        List<Integer> articleTypeId = repairSchoolArticleMapper.findArticleTypeId(schoolId);
//        Example example = new Example(RepairArticleTypeEntity.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andIn("id" ,articleTypeId);
//        //判断文章类型名是否为空
//        if (StringUtils.isNotEmpty(articleType_title)){
//            criteria.andLike("articleType_title","%"+articleType_title+"%");
//        }
//        //判断状态是否为空
//        if (articleType_state!=null){
//            criteria.andEqualTo("articleType_state",articleType_state);
//        }
        PageUtil.startPage("create_time", "desc");
        List<ArticleTypeVo> repairArticleTypeEntities = repairArticleTypeMapper.findAllArticleType(schoolId, articleType_title, articleType_state);
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), new PageInfo<ArticleTypeVo>(repairArticleTypeEntities));
    }


    /**
     * @param request
     * @Description :  查询文章类型
     * @Return : cn.crm.result.ResultData
     * @Author : MYZ
     * @Date : 2019/3/27 14:37
     */
    @Override
    public ResultData findArticleType(HttpServletRequest request) {
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
        //判断查询是否为空
        if (schoolId.size() == 0 || schoolId == null) {
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), null);
        }
        //根据学校id查询对应类型id
        List<Integer> articleTypeId = repairSchoolArticleMapper.findArticleTypeId(schoolId);
        Example example = new Example(RepairArticleTypeEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", articleTypeId);
        List<RepairArticleTypeEntity> repairArticleTypeEntities = repairArticleTypeMapper.selectByExample(example);
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), repairArticleTypeEntities);
    }

    /**
     * 根据文章类型id查询一个类型信息
     *
     * @param id 文章类型id
     * @return
     */
    @Override
    public ResultData findArticleTypeById(Integer id) {
        //调用查询方法
        ArticleTypeVo repairArticleTypeEntity = repairArticleTypeMapper.findArticleTypeById(id);
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), repairArticleTypeEntity);
    }

    /**
     * 新增文章类型信息
     *
     * @param repairArticleTypeEntity 文章类型实体类
     * @return
     */
    @Override
    @Transactional
    public ResultData addArticleType(RepairArticleTypeEntity repairArticleTypeEntity, HttpServletRequest request) {
        //获取登陆用户信息
        SysAdminEntity sysAdminEntity = (SysAdminEntity) request.getSession().getAttribute(AdminEntityUtil.ADMIN_SESSION_KEY);
        //判断是否为空
        if (sysAdminEntity == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage(), null);
        }
        //判断文章信息是否为空
        if (repairArticleTypeEntity == null) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
        }
        //查询是否有重名
        Example example = new Example(RepairArticleTypeEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("articleType_title", repairArticleTypeEntity.getArticleType_title());
        List<RepairArticleTypeEntity> repairArticleTypeEntities = repairArticleTypeMapper.selectByExample(example);
        if (repairArticleTypeEntities.size() > 0 && repairArticleTypeEntities != null) {
            return new ResultData(ResultCode.ALREADYEXISTS.getCode(), false, ResultCode.ALREADYEXISTS.getMessage());
        }
        //设置发布人id
        repairArticleTypeEntity.setCreate_userId(sysAdminEntity.getAdmin_id());
        repairArticleTypeEntity.setCreate_userName(sysAdminEntity.getAdmin_name());
        repairArticleTypeEntity.setCreate_time(new Date());
        //设置文章类型状态
        repairArticleTypeEntity.setArticleType_state(1);
        //调用保存方法
        int insert = repairArticleTypeMapper.insert(repairArticleTypeEntity);
        int insert1 = 0;
        //判断返回结果
        if (insert > 0) {
            //获取学校ids
            String schoolIds = repairArticleTypeEntity.getSchoolIds();
            //判断学校ids是否为空
            if (StringUtils.isEmpty(schoolIds)) {
                return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
            }
            //同时插入数据到学校和文章类型中间表
            //获取新增文章类型id
            Integer id = repairArticleTypeEntity.getId();
            //遍历得到学校id
            String[] split = schoolIds.split(",");
            for (String schoolId : split) {
                //创建中间表实体类
                RepairSchoolArticleEntity repairSchoolArticleEntity = new RepairSchoolArticleEntity();
                //插入中间表
                repairSchoolArticleEntity.setArticle_id(id);
                repairSchoolArticleEntity.setSchool_id(Integer.valueOf(schoolId));
                insert1 = repairSchoolArticleMapper.insert(repairSchoolArticleEntity);
            }
            if (insert1 > 0) {
                return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
            }
            return new ResultData(ResultCode.ERROR.getCode(), true, ResultCode.ERROR.getMessage());
        }
        return new ResultData(ResultCode.ERROR.getCode(), true, ResultCode.ERROR.getMessage());
    }

    /**
     * 修改文章类型信息
     *
     * @param repairArticleTypeEntity 文章类型实体类
     * @return
     */
    @Override
    @Transactional
    public ResultData updateArticleType(RepairArticleTypeEntity repairArticleTypeEntity, HttpServletRequest request) {
        //获取登陆用户信息
        SysAdminEntity sysAdminEntity = (SysAdminEntity) request.getSession().getAttribute(AdminEntityUtil.ADMIN_SESSION_KEY);
        //判断是否为空
        if (sysAdminEntity == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage(), null);
        }
        //判断文章信息是否为空
        if (repairArticleTypeEntity == null) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
        }
        //查询是否有重名
        Example example2 = new Example(RepairArticleTypeEntity.class);
        Example.Criteria criteria = example2.createCriteria();
        criteria.andEqualTo("articleType_title", repairArticleTypeEntity.getArticleType_title());
        criteria.andNotEqualTo("id", repairArticleTypeEntity.getId());
        List<RepairArticleTypeEntity> repairArticleTypeEntities = repairArticleTypeMapper.selectByExample(example2);
        if (repairArticleTypeEntities.size() > 0 && repairArticleTypeEntities != null) {
            return new ResultData(ResultCode.ALREADYEXISTS.getCode(), false, ResultCode.ALREADYEXISTS.getMessage());
        }
        //设置修改人id
        repairArticleTypeEntity.setUpdate_userId(sysAdminEntity.getAdmin_id());
        repairArticleTypeEntity.setUpdate_userName(sysAdminEntity.getAdmin_name());
        repairArticleTypeEntity.setUpdate_time(new Date());
        //调用修改方法
        int insert = repairArticleTypeMapper.updateByPrimaryKeySelective(repairArticleTypeEntity);
        //判断返回结果
        if (insert > 0) {
            //获取学校ids
            String schoolIds = repairArticleTypeEntity.getSchoolIds();
            //同时修改学校和文章类型中间表数据
            //获取修改文章类型id
            Integer id = repairArticleTypeEntity.getId();
            //查询中间表是否有数据
            Example example = new Example(RepairSchoolArticleEntity.class);
            example.createCriteria().andEqualTo("article_id", id);
            List<RepairSchoolArticleEntity> repairSchoolArticleEntityList = repairSchoolArticleMapper.selectByExample(example);
            //判断集合是否为空
            if (repairSchoolArticleEntityList.size() > 0 && repairSchoolArticleEntityList != null) {
                //删除中间表数据
                int i = repairSchoolArticleMapper.deleteByExample(example);
                int insert1 = 0;
                //判断返回结果
                if (i > 0) {
                    //判断学校ids是否为空
                    if (StringUtils.isEmpty(schoolIds)) {
                        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
                    }
                    //遍历得到学校id
                    String[] split = schoolIds.split(",");
                    for (String schoolId : split) {
                        //创建中间表实体类
                        RepairSchoolArticleEntity repairSchoolArticleEntity = new RepairSchoolArticleEntity();
                        //插入中间表
                        repairSchoolArticleEntity.setArticle_id(id);
                        repairSchoolArticleEntity.setSchool_id(Integer.valueOf(schoolId));
                        insert1 = repairSchoolArticleMapper.insert(repairSchoolArticleEntity);
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
     * 删除一个文章类型信息
     *
     * @param id 文章类型id
     * @return
     */
    @Override
    @Transactional
    public ResultData deleteArticleType(Integer id) {
        //判断id是否为空
        if (id == null) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
        }
        //根据id获取文章类型状态
        RepairArticleTypeEntity repairArticleTypeEntity = repairArticleTypeMapper.selectByPrimaryKey(id);
        Integer articleType_state = repairArticleTypeEntity.getArticleType_state();
        //判断如果状态为1不能删除
        if (articleType_state == 1) {
            return new ResultData(ResultCode.ERROR_RESOURCE.getCode(), false, ResultCode.ERROR_RESOURCE.getMessage());
        }
        //调用删除方法
        int i = repairArticleTypeMapper.deleteByPrimaryKey(id);
        //判断返回结果
        if (i > 0) {
            //同时删除中间表对应数据
            Example example = new Example(RepairSchoolArticleEntity.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("article_id", id);
            List<RepairSchoolArticleEntity> repairSchoolArticleEntityList = repairSchoolArticleMapper.selectByExample(example);
            //判断查询结果
            if (repairSchoolArticleEntityList.size() > 0 && repairSchoolArticleEntityList != null) {
                int i1 = repairSchoolArticleMapper.deleteByExample(example);
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
     * 手机端查询文章类型
     *
     * @param request
     * @return
     */
    @Override
    public ResultData findAppArticleType(HttpServletRequest request) {
        SysUserEntity userEntity = (SysUserEntity) request.getSession().getAttribute("userEntity");
        //判断信息是否为空
        if (userEntity == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage(), null);
        }
        //获取用户id查询是否有学校
        SysUserEntity sysUserEntity = sysUserMapper.selectByPrimaryKey(userEntity.getUser_id());
        //获取学校id
        Integer school_id = sysUserEntity.getSchool_id();
        //根据学校id查询对应得文章类型id
        List<Integer> appArticleTypeIds = repairSchoolArticleMapper.findAppArticleTypeId(school_id);
        //查询获取模块id信息是否为空
        if (appArticleTypeIds.size() > 0 && appArticleTypeIds != null) {
            //根据模块id查询模块信息
            Example example = new Example(RepairArticleTypeEntity.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("articleType_state", 1);
            criteria.andIn("id", appArticleTypeIds);
            List<RepairArticleTypeEntity> repairArticleTypeEntities = repairArticleTypeMapper.selectByExample(example);
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), repairArticleTypeEntities);
        }
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), null);
    }
}

