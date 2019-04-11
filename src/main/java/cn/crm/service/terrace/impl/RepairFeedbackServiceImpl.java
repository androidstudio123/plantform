package cn.crm.service.terrace.impl;


import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.SysUserEntity;
import cn.crm.entity.terrace.RepairFeedbackEntity;
import cn.crm.mapper.sys.SysUserMapper;
import cn.crm.mapper.terrace.RepairFeedbackMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.terrace.RepairFeedbackService;
import cn.crm.util.AdminEntityUtil;
import cn.crm.util.PageUtil;
import cn.crm.util.StringUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * TODO 在此加入类描述  反馈信息服务层接口实现类
 *
 * @author hzg
 * @version 2019-03-28 15:13:17
 * @copyright
 */

@Service
public class RepairFeedbackServiceImpl extends BaseServiceImpl<RepairFeedbackEntity> implements RepairFeedbackService {

    @Autowired
    private RepairFeedbackMapper repairFeedbackMapper;

    @Autowired
    private SysUserMapper sysUserMapper;


    /**
     * 查询反馈信息
     *
     * @param user_name 根据用户名模糊查询
     * @return
     */
    @Override
    public ResultData findAllFeedback(String user_name, HttpServletRequest request) {
        //获取登陆用户信息
        SysAdminEntity sysAdminEntity = (SysAdminEntity) request.getSession().getAttribute(AdminEntityUtil.ADMIN_SESSION_KEY);
        //判断是否为空
        if (sysAdminEntity == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage());
        }
        //获取登录人id
        Integer admin_id = sysAdminEntity.getAdmin_id();
        //查询所有用户id
        List<Integer> userIds = sysUserMapper.finduser(admin_id);
        //判断返回数据是否为空
        if (userIds == null || userIds.size() == 0) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage(), null);
        }
        //查询对应的反馈内容
        Example example = new Example(RepairFeedbackEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("user_id", userIds);
        //判断名字是否为空
        if (StringUtils.isNotEmpty(user_name)) {
            criteria.andLike("user_name", "%" + user_name + "%");
        }
        PageUtil.startPage("feedback_time", "desc");
        //调用查询接口
        List<RepairFeedbackEntity> repairFeedbackEntities = repairFeedbackMapper.selectByExample(example);
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), new PageInfo<RepairFeedbackEntity>(repairFeedbackEntities));
    }


    /**
     * 新增反馈信息
     *
     * @param repairFeedbackEntity 反馈信息实体类
     * @return
     */
    @Override
    public ResultData addFeeback(RepairFeedbackEntity repairFeedbackEntity, HttpServletRequest request) {
        //获取用户信息
        SysUserEntity userEntity = (SysUserEntity) request.getSession().getAttribute("userEntity");
        //判断获取信息是否为空
        if (userEntity == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage());
        }
        //获取用户id
        Integer user_id = userEntity.getUser_id();
        //判断实体类是否为空
        if (repairFeedbackEntity == null) {
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
        }
        //设置参数
        repairFeedbackEntity.setFeedback_time(new Date());
        repairFeedbackEntity.setUser_id(user_id);
        repairFeedbackEntity.setUser_name(userEntity.getUser_name());
        //调用保存方法
        int insert = repairFeedbackMapper.insert(repairFeedbackEntity);
        //判断返回结果
        if (insert > 0) {
            return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage());
        }
        return new ResultData(ResultCode.ERROR.getCode(), false, ResultCode.ERROR.getMessage());
    }

}
