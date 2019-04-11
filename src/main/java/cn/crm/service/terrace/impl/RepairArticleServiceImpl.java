package cn.crm.service.terrace.impl;


import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.SysUserEntity;
import cn.crm.entity.terrace.RepairArticleEntity;
import cn.crm.mapper.sys.SysUserMapper;
import cn.crm.mapper.sys.SysUsergroupMapper;
import cn.crm.mapper.terrace.RepairArticleMapper;
import cn.crm.mapper.terrace.RepairSchoolArticleMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.terrace.RepairArticleService;
import cn.crm.util.*;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crm.service.BaseServiceImpl;
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
 * TODO 在此加入类描述  文章信息服务层实现类
 *
 * @author MYZ
 * @version 2019-03-25 10:00:30
 */

@Service
public class RepairArticleServiceImpl extends BaseServiceImpl<RepairArticleEntity> implements RepairArticleService {

    @Autowired
    private RepairArticleMapper repairArticleMapper;

    @Autowired
    private SysUsergroupMapper sysUsergroupMapper;

    @Autowired
    private RepairSchoolArticleMapper repairSchoolArticleMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 查询文章信息
     *
     * @param typeId 可根据类型id查询
     * @return
     */
    @Override
    public ResultData findAllArticle(String article_title, Integer typeId, HttpServletRequest request) {
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
        if (schoolId == null || schoolId.size() == 0) {
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
        }
        //根据学校id查询对应类型id
        List<Integer> articleTypeId = repairSchoolArticleMapper.findArticleTypeId(schoolId);
        //根据类型查询文章信息
        Example example = new Example(RepairArticleEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("article_typeId", articleTypeId);
        //根据类型id查询
        if (typeId != null) {
            criteria.andEqualTo("article_typeId", typeId);
        }
        //根据标题模糊差
        if (StringUtils.isNotEmpty(article_title)) {
            criteria.andLike("article_title", "%" + article_title + "%");
        }
        //分页根据发布时间倒序
        PageUtil.startPage("publish_time", "desc");
        //调用查询方法
        List<RepairArticleEntity> repairArticleEntities = repairArticleMapper.selectByExample(example);
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), new PageInfo<RepairArticleEntity>(repairArticleEntities));
    }

    /**
     * 根据文章id查询一条文章信息
     *
     * @param id 文章id
     * @return
     */
    @Override
    public RepairArticleEntity findArticleById(Integer id) {
        //判断传递id是否为空
        if (id == null) {
            return null;
        }
        //调用查询方法
        RepairArticleEntity repairArticleEntity = repairArticleMapper.selectByPrimaryKey(id);
        return repairArticleEntity;
    }

    /**
     * 新增文章信息
     *
     * @param repairArticleEntity 文章实体类
     * @return
     */
    @Override
    public ResultData addArticle(RepairArticleEntity repairArticleEntity, HttpServletRequest request) {
        //获取登陆用户信息
        SysAdminEntity sysAdminEntity = (SysAdminEntity) request.getSession().getAttribute(AdminEntityUtil.ADMIN_SESSION_KEY);
        //判断是否为空
        if (sysAdminEntity == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage(), null);
        }
        //判断文章信息是否为空
        if (repairArticleEntity == null) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
        }
        //设置发布人id
        repairArticleEntity.setPublish_userId(sysAdminEntity.getAdmin_id());
        repairArticleEntity.setPublish_userName(sysAdminEntity.getAdmin_name());
        repairArticleEntity.setPublish_time(new Date());
        //调用保存方法
        int insert = repairArticleMapper.insert(repairArticleEntity);
        //判断返回结果
        if (insert > 0) {
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
        }
        return new ResultData(ResultCode.ERROR.getCode(), true, ResultCode.ERROR.getMessage());
    }

    /**
     * 修改文章信息
     *
     * @param repairArticleEntity 文章实体类
     * @return
     */
    @Override
    public ResultData updateArticle(RepairArticleEntity repairArticleEntity, HttpServletRequest request) {
        //获取登陆用户信息
        SysAdminEntity sysAdminEntity = (SysAdminEntity) request.getSession().getAttribute(AdminEntityUtil.ADMIN_SESSION_KEY);
        //判断是否为空
        if (sysAdminEntity == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage(), null);
        }
        //判断文章信息是否为空
        if (repairArticleEntity == null) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
        }
        //设置发布人id
        repairArticleEntity.setUpdate_userId(sysAdminEntity.getAdmin_id());
        repairArticleEntity.setUpdate_userName(sysAdminEntity.getAdmin_name());
        repairArticleEntity.setUpdate_time(new Date());
        //调用修改方法
        int i = repairArticleMapper.updateByPrimaryKeySelective(repairArticleEntity);
        //判断返回结果
        if (i > 0) {
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
        }
        return new ResultData(ResultCode.ERROR.getCode(), true, ResultCode.ERROR.getMessage());
    }

    /**
     * 删除一条文章信息
     *
     * @param id 文章id
     * @return
     */
    @Override
    public ResultData deleteArticle(Integer id) {
        //判断id是否为空
        if (id == null) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
        }
        //调用删除方法
        int i = repairArticleMapper.deleteByPrimaryKey(id);
        //判断返回结果
        if (i > 0) {
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
        }
        return new ResultData(ResultCode.ERROR.getCode(), true, ResultCode.ERROR.getMessage());
    }

    /**
     * 上传轮播图
     *
     * @param request
     * @param file
     * @return
     */
    @Override
    public ResultData uploadMRotationchart(HttpServletRequest request, MultipartFile file) {
        String targetPath = PropertiesUtil.getValue("ROTATION_CHART_URL");
        String url = IoUploadUtil.upload(file, targetPath);
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), url);

    }

        /**
         * 手机端查询文章信息
         *
         * @param request
         * @param typeId  文章类型查询
         * @return
         */
        @Override
        public ResultData findAppArticle (HttpServletRequest request, Integer typeId){
            SysUserEntity userEntity = (SysUserEntity) request.getSession().getAttribute("userEntity");
            //判断信息是否为空
            if (userEntity == null) {
                return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage(), null);
            }
            //获取用户id查询是否有学校
            SysUserEntity sysUserEntity = sysUserMapper.selectByPrimaryKey(userEntity.getUser_id());
            //获取学校id
            Integer school_id = sysUserEntity.getSchool_id();
            //根据学校id查询对应类型id
            List<Integer> articleTypeId = repairSchoolArticleMapper.findAppArticleTypeId(school_id);
            //判断文章类型id是否为空
            if (articleTypeId.size() > 0 && articleTypeId != null) {
                //根据类型查询文章信息
                Example example = new Example(RepairArticleEntity.class);
                Example.Criteria criteria = example.createCriteria();
                example.setOrderByClause("publish_time  desc");
                criteria.andIn("article_typeId", articleTypeId);
                criteria.andEqualTo("article_typeId", typeId);
                List<RepairArticleEntity> repairArticleEntities = repairArticleMapper.selectByExample(example);
                return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), repairArticleEntities);
            }
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), null);
        }

    }


