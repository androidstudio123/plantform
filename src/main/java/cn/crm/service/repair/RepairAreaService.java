package cn.crm.service.repair;


import cn.crm.entity.repair.RepairAreaEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;


public interface RepairAreaService extends BaseService<RepairAreaEntity> {

    //查询报修区域
    ResultData findRepairArea(Integer pageNum, Integer pageSize,Integer schoole_id);
    //新增报修区域
    ResultData saveRepairArea(RepairAreaEntity repairAreaEntity);
    //编辑报修区域
    ResultData updateReapirArea(RepairAreaEntity repairAreaEntity);
    //删除报修区域
    ResultData deleteReapirArea(Integer id);

    /**
     * 根据维修区域ID,查询此维修区域的全名
     * @param areaId  维修区域ID
     * @return
     */
    String findAreaNameById(Integer areaId);
}
