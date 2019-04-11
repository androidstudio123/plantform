package cn.crm.service.materiel.impl;

import cn.crm.entity.materiel.RepairGoodsEntity;
import cn.crm.entity.materiel.RepairGoodsExportEntity;
import cn.crm.mapper.materiel.RepairGoodsExportMapper;
import cn.crm.mapper.materiel.RepairGoodsMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.materiel.RepairGoodsExportService;
import cn.crm.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class RepairGoodsExportServiceImpl extends BaseServiceImpl<RepairGoodsExportEntity> implements RepairGoodsExportService {
	
	@Autowired
	private RepairGoodsExportMapper repairGoodsExportMapper;


	@Override
	public Integer selectCount(Integer flag) {
		Example example = new Example(RepairGoodsExportEntity.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("flag",flag);
		int res = repairGoodsExportMapper.selectCountByExample(example);
		return res;
	}
	@Autowired
    private RepairGoodsMapper repairGoodsMapper;


    /**
     * 从仓库里出货物
     * @param exportEntity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData saveExportGoods(RepairGoodsExportEntity exportEntity) {
        if(exportEntity == null){
            return new ResultData(ResultCode.ERROR.getCode(),false,"出库物品为空",null);
        }
        Integer goods_id = exportEntity.getGoods_id();
        RepairGoodsEntity repairGoodsEntity = repairGoodsMapper.selectByPrimaryKey(goods_id);
        Integer num = repairGoodsEntity.getNum();    //当前要出库的物品剩余数量
        Integer goods_num = exportEntity.getGoods_num();  //要领的物品数量
        if(goods_num > num){
            return new ResultData(ResultCode.ERROR.getCode(),false,"领取物品的数量超出了库存限制!",null);
        }
        //将此条出库记录进行保存
        exportEntity.setExport_no(IdGenerator.idGen());  //出库单号
        int insert = repairGoodsExportMapper.insert(exportEntity);
        if(insert < 0){
            return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage(),null);
        }
        //消减库存
        repairGoodsEntity.setNum(num - goods_num);
        //执行更新库存表操作
        int i = repairGoodsMapper.updateByPrimaryKeySelective(repairGoodsEntity);
        if(insert < 0){
            return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage(),null);
        }
        return new ResultData(ResultCode.SUCCESS.getCode(),false,ResultCode.SUCCESS.getMessage(),null);
    }

    /**
     * 根据维修人员查询出库记录
     * @param userId    用户ID
     * @param goodsTypeId  物品分类ID
     * @param repertoryTypeId  库类型ID
     * @param repertoryNameId   库名称ID
     * @param flag  维修类型标识 0维修 1换新
     * @return
     */
    @Override
    public ResultData findByUser(Integer userId, Integer goodsTypeId, Integer repertoryTypeId, Integer repertoryNameId, Integer flag) {
        if(userId == null){
            return new ResultData(ResultCode.ERROR.getCode(),false,"用户ID不能为空",null);
        }
        Example example = new Example(RepairGoodsExportEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goods_type_id",goodsTypeId);
        criteria.andEqualTo("repertory_type_id",repertoryTypeId);
        criteria.andEqualTo("repertory_name_id",repertoryNameId);
        criteria.andEqualTo("flag",flag);
        criteria.andEqualTo("user_id",userId);
        List<RepairGoodsExportEntity> repairGoodsExportEntities = repairGoodsExportMapper.selectByExample(example);
        return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),repairGoodsExportEntities);
    }
}
