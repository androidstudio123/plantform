package cn.crm.service.materiel;



import cn.crm.entity.materiel.RepairGoodsEntity;
import cn.crm.entity.materiel.RepairGoodsImportEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

public interface RepairGoodsImportService extends BaseService<RepairGoodsImportEntity> {

    //查询所有入库信息
    ResultData findRepairGoodsImportAll(Integer admin_id,String goods_name);
    //新增物料信息
    ResultData saveRepairGoodsImport(RepairGoodsImportEntity repairGoodsImportEntity);
    //编辑物料信息
    ResultData updateRepairGoodsImport(RepairGoodsImportEntity repairGoodsImportEntity);
    //删除入库信息
    ResultData deleteRepairGoodsImport(Integer import_id);

    ResultData submitRepairGoodsImport(RepairGoodsImportEntity repairGoodsImportEntity, Integer admin_id);

    ResultData submitNotSaveGoodsImport(RepairGoodsImportEntity repairGoodsImportEntity);
}
