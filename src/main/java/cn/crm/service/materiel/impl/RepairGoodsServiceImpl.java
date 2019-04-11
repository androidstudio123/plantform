package cn.crm.service.materiel.impl;

import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.materiel.*;
import cn.crm.mapper.materiel.RepairGoodsImportMapper;
import cn.crm.mapper.materiel.RepairGoodsMapper;
import cn.crm.mapper.materiel.RepertoryExportRoleConfigMapper;
import cn.crm.mapper.materiel.RepertoryExportRoleMapper;
import cn.crm.mapper.sys.SysAdminMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.materiel.RepairGoodsImportService;
import cn.crm.service.materiel.RepairGoodsService;
import cn.crm.service.materiel.RepairRepertoryAdminConfigService;
import cn.crm.util.AdminEntityUtil;
import cn.crm.util.PageUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Service
public class RepairGoodsServiceImpl extends BaseServiceImpl<RepairGoodsEntity> implements RepairGoodsService {
	
	@Autowired
	private RepairGoodsMapper repairGoodsMapper;

	@Autowired
    private RepairRepertoryAdminConfigService repairRepertoryAdminConfigService;

	@Autowired
    private RepertoryExportRoleMapper repertoryExportRoleMapper;

	@Autowired
	private RepertoryExportRoleConfigMapper roleConfigMapper;

	@Autowired
    private SysAdminMapper sysAdminMapper;



    /**
     * 根据当前登录管理员信息,查询其管理的库存
     * @param request
     * @param repertoryNameId  库ID
     * @return
     */
    @Override
    public ResultData findAllGoodsByAdmin(HttpServletRequest request, Integer repertoryNameId) {
        //1.获取当前登录管理员信息
        SysAdminEntity sysAdminEntity = (SysAdminEntity) request.getSession().getAttribute("sysAdminEntity");
        if(sysAdminEntity == null){
            return new ResultData(ResultCode.OUTDATE.getCode(),false,ResultCode.OUTDATE.getMessage(),null);
        }
        //2.判断是否是admin登录
        if("admin".equals(sysAdminEntity.getAdmin_name())){ //admin登录,则查询所有库存信息
            PageUtil.startPage();
            List<RepairGoodsEntity> allGoods = repairGoodsMapper.findAllGoods(repertoryNameId);
            PageInfo<RepairGoodsEntity> repairGoodsEntityPageInfo = new PageInfo<>(allGoods);
            return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),repairGoodsEntityPageInfo);
        }else{  //普通管理员登录,则根据权限查询
            //查询当前管理员所负责的库与库类型
            List<RepertoryExportRoleConfigEntity> byAdmin = this.findByAdmin(sysAdminEntity);
            if(byAdmin == null || byAdmin.size() <= 0){
                //如果此用户不是admin,并且没有任何库管权限,则直接返回空数据
                return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),null);
            }
            //去库存表中查询与之对应的物品
            PageUtil.startPage();
            List<RepairGoodsEntity> goodsEntityList = repairGoodsMapper.findAllGoodsByAdmin(repertoryNameId, byAdmin);
            PageInfo<RepairGoodsEntity> repairGoodsEntityPageInfo = new PageInfo<>(goodsEntityList);
            return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),repairGoodsEntityPageInfo);
        }
    }

    /**
     * 根据当前登录管理员信息,统计物品数量
     * @param goodsName   物品名称
     * @param goodsTypeId  物品分类ID
     * @param producerId  生产厂家ID
     * @param repertoryNameId  库名称ID
     * @param repertoryTypeId 库类型ID
     * @return
     */
    @Override
    public ResultData countGoodsByAdmin(String goodsName, Integer goodsTypeId, Integer producerId,
                                        Integer repertoryNameId, Integer repertoryTypeId,HttpServletRequest request) {
        //1.获取当前登录管理员信息
        SysAdminEntity sysAdminEntity = (SysAdminEntity) request.getSession().getAttribute("sysAdminEntity");
        if(sysAdminEntity == null){
            return new ResultData(ResultCode.OUTDATE.getCode(),false,ResultCode.OUTDATE.getMessage(),null);
        }
        //2.判断当前是admin登录还是普通管理员登录
        if("admin".equals(sysAdminEntity.getAdmin_name())){
            Integer num = repairGoodsMapper.countGoods(goodsName, goodsTypeId, producerId, repertoryNameId, repertoryTypeId);
            return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),num);
        }else{ //不是admin,那么就根据他的库管权限统计
            //查询当前管理员所负责的库与库类型
            List<RepertoryExportRoleConfigEntity> configByAdmin = this.findByAdmin(sysAdminEntity);
            if(configByAdmin == null || configByAdmin.size() <= 0){
                //如果此用户不是admin,并且没有任何库管权限,则直接返回空数据
                return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),0);
            }
            Integer num = repairGoodsMapper.countGoodsByAdmin(goodsName, goodsTypeId, producerId, repertoryNameId, repertoryTypeId, configByAdmin);
            return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),num);
        }
    }

    /**
     * 根据库名称,库类型,生产厂家以及物品类型查询对应的物品(手机端出库使用)
     * @param repertoryNameId  库名称ID
     * @param repertoryTypeId 库类型ID
     * @param goodsTypeId  物品类型ID
     * @param producerId  生产厂家ID
     * @return
     */
    @Override
    public ResultData findByCondition(Integer repertoryNameId, Integer repertoryTypeId, Integer producerId ,Integer goodsTypeId) {
        if(repertoryNameId == null || repertoryTypeId == null || goodsTypeId == null || producerId == null){
            return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage(),null);
        }
        Example example = new Example(RepairGoodsEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goods_type_id",goodsTypeId);
        criteria.andEqualTo("producer_id",producerId);
        criteria.andEqualTo("repertory_name_id",repertoryNameId);
        criteria.andEqualTo("repertory_type_id",repertoryTypeId);
        List<RepairGoodsEntity> goodsEntityList = repairGoodsMapper.selectByExample(example);
        return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),goodsEntityList);
    }

    /**
     * 根据管理员查询其管理的库与库类型
     * @param admin  管理员实体对象
     * @return
     */
    public List<RepertoryExportRoleConfigEntity> findByAdmin(SysAdminEntity admin){
        //查询出此管理员下的所有子级管理员
        Set<Integer> set = new HashSet<Integer>();
        List<Integer> list = new ArrayList<Integer>();
        list.add(admin.getAdmin_id());
        AdminEntityUtil.getAllChildAdminId(set,list);
        set.add(admin.getAdmin_id());
        //查询对应的出库角色信息
        List<RepertoryExportRoleEntity> exportRoleByAdmin = repertoryExportRoleMapper.findExportRoleByAdminId(set);
        if(exportRoleByAdmin == null || exportRoleByAdmin.size() == 0){
            return null;
        }
        //根据其对应的出库角色信息查询其管理的库信息
        List<RepertoryExportRoleConfigEntity> configs = roleConfigMapper.findByExportRoleId(exportRoleByAdmin);
        return configs;
    }
}
