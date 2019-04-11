package cn.crm.service.materiel.impl;

import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.materiel.RepairGoodsEntity;
import cn.crm.entity.materiel.RepairGoodsRepertoryTypeEntity;
import cn.crm.entity.materiel.RepairGoodsTypeEntity;
import cn.crm.entity.materiel.RepairRepertoryAdminConfigEntity;
import cn.crm.mapper.materiel.RepairGoodsMapper;
import cn.crm.mapper.materiel.RepairGoodsTypeMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.materiel.RepairGoodsService;
import cn.crm.service.materiel.RepairGoodsTypeService;
import cn.crm.service.materiel.RepairRepertoryAdminConfigService;
import cn.crm.util.PageUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
public class RepairGoodsTypeServiceImpl extends BaseServiceImpl<RepairGoodsTypeEntity> implements RepairGoodsTypeService {
	
	@Autowired
	private RepairGoodsTypeMapper repairGoodsTypeMapper;

	@Autowired
	private RepairGoodsMapper repairGoodsMapper;
	@Override
	public ResultData findRepairGoodsType(String goods_type_name) {
		if(goods_type_name == "" || goods_type_name == null){
			PageUtil.startPage();
			List<RepairGoodsTypeEntity> repairGoodsTypeEntities = repairGoodsTypeMapper.selectAll();
			return new ResultData(20000,false,"查询成功",new PageInfo<>(repairGoodsTypeEntities));
		}
		PageUtil.startPage();
		Example example = new Example(RepairGoodsTypeEntity.class);
		example.createCriteria().andLike("goods_type_name","%"+goods_type_name+"%");
		List<RepairGoodsTypeEntity> repairGoodsTypeEntities = repairGoodsTypeMapper.selectByExample(example);
		return new ResultData(20000,false,"查询成功",new PageInfo<>(repairGoodsTypeEntities));
	}

	@Override
	public ResultData saveRepairGoodsType(RepairGoodsTypeEntity repairGoodsTypeEntity) {
		if(repairGoodsTypeEntity == null){
			return new ResultData(20001,false,"信息不能为空");
		}
		Example example = new Example(RepairGoodsTypeEntity.class);
		example.createCriteria().andEqualTo("goods_type_name",repairGoodsTypeEntity.getGoods_type_name());
		List<RepairGoodsTypeEntity> repairGoodsTypeEntities = repairGoodsTypeMapper.selectByExample(example);
		if(repairGoodsTypeEntities.size()>0){
			return new ResultData(20003,false,"商品类型已存在，请勿重复添加");
		}
		int i = repairGoodsTypeMapper.insertSelective(repairGoodsTypeEntity);
		if(i>0){
			return new ResultData(20000,true,"执行成功");
		}
		return new ResultData(20002,false,"网络错误请重试");
	}

	@Override
	public ResultData updateRepairGoodsType(RepairGoodsTypeEntity repairGoodsTypeEntity) {
		if(repairGoodsTypeEntity == null){
			return new ResultData(20001,false,"信息不能为空");
		}
		Example example = new Example(RepairGoodsTypeEntity.class);
		example.createCriteria().andEqualTo("goods_type_name",repairGoodsTypeEntity.getGoods_type_name());
		List<RepairGoodsTypeEntity> repairGoodsTypeEntities = repairGoodsTypeMapper.selectByExample(example);
		RepairGoodsTypeEntity repairGoodsTypeEntity1 = repairGoodsTypeMapper.selectByPrimaryKey(repairGoodsTypeEntity.getGoods_type_id());
		if(repairGoodsTypeEntities.size()>0 && !repairGoodsTypeEntity1.getGoods_type_name().equals(repairGoodsTypeEntity.getGoods_type_name())){
			return new ResultData(20003,false,"物品类型已存在，请勿修改成该物品类型");
		}
		int i = repairGoodsTypeMapper.updateByPrimaryKeySelective(repairGoodsTypeEntity);
		if(i>0){
			return new ResultData(20000,true,"执行成功");
		}
		return new ResultData(20002,false,"网络错误请重试");
	}

	@Override
	public ResultData deleteRepairGoodsType(Integer goods_type_id) {
		if(goods_type_id == null || goods_type_id < 0){
			return new ResultData(20001,false,"请选择物料类型");
		}
		Example example = new Example(RepairGoodsEntity.class);
		example.createCriteria().andEqualTo("goods_type_id",goods_type_id);
		List<RepairGoodsEntity> repairGoodsEntities = repairGoodsMapper.selectByExample(example);
		if(repairGoodsEntities.size()>0){
			return new ResultData(20003,false,"该类型正在被使用，请勿删除");
		}
		int i = repairGoodsTypeMapper.deleteByPrimaryKey(goods_type_id);
		if(i>0){
			return new ResultData(20000,true,"执行成功");
		}
		return new ResultData(20002,false,"网络错误请重试");
	}

	/**
	 * 根据库名称与库类型查询对应的物品分类(手机端出库使用)
	 * @param repertoryNameId  库名称ID
	 * @param repertoryTypeId  库类型ID
	 * @return
	 */
    @Override
    public ResultData findByCondition(Integer repertoryNameId, Integer repertoryTypeId) {
    	if(repertoryNameId == null || repertoryTypeId == null){
			return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),null);
		}
		List<RepairGoodsTypeEntity> byCondition = repairGoodsTypeMapper.findByCondition(repertoryNameId, repertoryTypeId);
		return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),byCondition);
    }
}
