package cn.crm.service.materiel;



import cn.crm.entity.materiel.RepairGoodsEntity;
import cn.crm.entity.materiel.RepairGoodsImportEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;
import com.sun.org.apache.regexp.internal.RE;

import javax.servlet.http.HttpServletRequest;

public interface RepairGoodsService extends BaseService<RepairGoodsEntity> {


    /**
     * 根据当前登录管理员信息,查询其管理的库存
     * @param request
     * @param repertoryNameId  库ID
     * @return
     */
    ResultData findAllGoodsByAdmin(HttpServletRequest request, Integer repertoryNameId);

    /**
     * 根据当前登录管理员信息,统计物品数量
     * @param goodsName   物品名称
     * @param goodsTypeId  物品分类ID
     * @param producerId  生产厂家ID
     * @param repertoryNameId  库名称ID
     * @param repertoryTypeId 库类型ID
     * @return
     */
    ResultData countGoodsByAdmin(String goodsName, Integer goodsTypeId, Integer producerId,
                                 Integer repertoryNameId, Integer repertoryTypeId,HttpServletRequest request);

    /**
     * 根据库名称,库类型,生产厂家以及物品类型查询对应的物品(手机端出库使用)
     * @param repertoryNameId  库名称ID
     * @param repertoryTypeId 库类型ID
     * @param goodsTypeId  物品类型ID
     * @param producerId  生产厂家ID
     * @return
     */
    ResultData findByCondition(Integer repertoryNameId, Integer repertoryTypeId,Integer producerId, Integer goodsTypeId);
}
