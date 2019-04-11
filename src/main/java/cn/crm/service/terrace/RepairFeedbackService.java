package cn.crm.service.terrace;


import cn.crm.entity.terrace.RepairFeedbackEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;

import javax.servlet.http.HttpServletRequest;


/**
 * TODO 在此加入类描述  反馈信息服务层接口
 *
 * @author hzg
 * @version 2019-03-28 15:13:17
 * @copyright
 */
public interface RepairFeedbackService extends BaseService<RepairFeedbackEntity> {

    /**
     * 查询反馈信息
     *
     * @param user_name 根据用户名模糊查询
     * @return
     */
    public ResultData findAllFeedback(String user_name, HttpServletRequest request);

    /**
     * 新增反馈信息
     * @param repairFeedbackEntity  反馈信息实体类
     * @return
     */
    public ResultData addFeeback(RepairFeedbackEntity repairFeedbackEntity,HttpServletRequest request);
}
