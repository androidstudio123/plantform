package cn.crm.service.repair;



import cn.crm.entity.repair.RepairRoleAreaEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;
import org.springframework.stereotype.Component;

public interface RepairRoleAreaService extends BaseService<RepairRoleAreaEntity> {


    /**
     * 根据用户查询此用户所负责的区域
     * @param userId  用户ID
     * @return
     */
    ResultData findRoleAreaByUser(Integer userId);
}
