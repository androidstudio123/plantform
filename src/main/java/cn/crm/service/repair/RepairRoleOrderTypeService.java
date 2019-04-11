package cn.crm.service.repair;
import cn.crm.entity.repair.RepairRoleOrderTypeEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;

/**
 * TODO 在此加入类描述
 * @author hzg
 * @version  2019-03-22 10:36:04
 */
public interface RepairRoleOrderTypeService extends BaseService<RepairRoleOrderTypeEntity> {


    /**
     * 根据用户查询此用户所负责的工单类型
     * @param userId 用户ID
     * @return
     */
    ResultData findOrderTypeByUser(Integer userId);
}
