package cn.crm.service.repair;



import cn.crm.entity.repair.RepairGoodsEntity;
import cn.crm.entity.repair.RepairRoleFunTypeAreaEntity;
import cn.crm.service.BaseService;

import java.util.List;


public interface RepairRoleFunAreaService extends BaseService<RepairRoleFunTypeAreaEntity> {


    List<RepairRoleFunTypeAreaEntity> findRepairRole(String roleName);
}
