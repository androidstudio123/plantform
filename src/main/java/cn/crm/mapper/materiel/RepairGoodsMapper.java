package cn.crm.mapper.materiel;

import cn.crm.entity.materiel.RepairGoodsEntity;
import cn.crm.entity.materiel.RepairGoodsImportEntity;
import cn.crm.entity.materiel.RepairRepertoryAdminConfigEntity;
import cn.crm.entity.materiel.RepertoryExportRoleConfigEntity;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;


public interface RepairGoodsMapper extends Mapper<RepairGoodsEntity>,MySqlMapper<RepairGoodsEntity>{

    /**
     * 根据当前登录管理员信息,查询其管理的库存(适用于普通管理员)
     * @param repertoryNameId  库名称ID
     * @param configByAdmin    用户所拥有的权限
     * @return
     */
    List<RepairGoodsEntity> findAllGoodsByAdmin(@Param("repertoryNameId") Integer repertoryNameId, @Param("configByAdmin") List<RepertoryExportRoleConfigEntity> configByAdmin);

    /**
     * 查询所有库存信息,不再已权限区分(针对于admin管理员)
     * @return
     */
    List<RepairGoodsEntity> findAllGoods(Integer repertoryNameId );

    /**
     * 根据当前登录管理员信息,统计物品数量(针对admin管理员)
     * @param goodsName   物品名称
     * @param goodsTypeId  物品分类ID
     * @param producerId  生产厂家ID
     * @param repertoryNameId  库名称ID
     * @param repertoryTypeId 库类型ID
     * @return
     */
    Integer countGoods(@Param("goodsName") String goodsName, @Param("goodsTypeId") Integer goodsTypeId, @Param("producerId") Integer producerId,
                       @Param("repertoryNameId")Integer repertoryNameId, @Param("repertoryTypeId") Integer repertoryTypeId);

    /**
     * 根据当前登录管理员信息,统计物品数量(针对普通管理员)
     * @param goodsName   物品名称
     * @param goodsTypeId  物品分类ID
     * @param producerId  生产厂家ID
     * @param repertoryNameId  库名称ID
     * @param repertoryTypeId 库类型ID
     * @param repertoryTypeId 库类型ID
     * @return
     */
    Integer countGoodsByAdmin(@Param("goodsName") String goodsName, @Param("goodsTypeId") Integer goodsTypeId, @Param("producerId") Integer producerId,
                       @Param("repertoryNameId")Integer repertoryNameId, @Param("repertoryTypeId") Integer repertoryTypeId,
                              @Param("configByAdmin") List<RepertoryExportRoleConfigEntity> configByAdmin);
}

