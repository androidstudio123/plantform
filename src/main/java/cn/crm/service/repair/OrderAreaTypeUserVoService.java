package cn.crm.service.repair;

import cn.crm.entity.repair.DefaultFunctionEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;
import cn.crm.vo.OrderAreaTypeUserVo;
import org.apache.ibatis.annotations.Param;


/**
 * TODO 在此加入类描述
 * @copyright {@link hzg}
 * @author hzg
 * @version  2019-03-26 09:19:29
 */
public interface OrderAreaTypeUserVoService extends BaseService<OrderAreaTypeUserVo> {
    ResultData findOrderManager(Integer order_status, String order_no, Integer pageNum, Integer pageSize);

}
