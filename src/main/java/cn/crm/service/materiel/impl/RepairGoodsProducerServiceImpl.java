package cn.crm.service.materiel.impl;

import cn.crm.entity.materiel.RepairGoodsImportEntity;
import cn.crm.entity.materiel.RepairGoodsProducerEntity;
import cn.crm.mapper.materiel.RepairGoodsImportMapper;
import cn.crm.mapper.materiel.RepairGoodsProducerMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.materiel.RepairGoodsImportService;
import cn.crm.service.materiel.RepairGoodsProducerService;
import cn.crm.util.PageUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


@Service
public class RepairGoodsProducerServiceImpl extends BaseServiceImpl<RepairGoodsProducerEntity> implements RepairGoodsProducerService {
	
	@Autowired
	private RepairGoodsProducerMapper repairGoodsProducerMapper;


	@Override
	public ResultData findRepairGoodsProducerAll(String producer_name, Integer pageNum, Integer pageSize) {
		if(pageNum == null || pageNum < 0){
			pageNum = 1;
		}
		if(pageSize == null || pageSize < 0){
			pageSize = 10;
		}
		if(producer_name != null && producer_name != ""){
			PageUtil.startPage();
			Example example = new Example(RepairGoodsProducerEntity.class);
			example.createCriteria().andLike("producer_name","%"+producer_name+"%");
			List<RepairGoodsProducerEntity> repairGoodsProducerEntities = repairGoodsProducerMapper.selectByExample(example);
			PageInfo<RepairGoodsProducerEntity> repairGoodsProducerEntityPageInfo = new PageInfo<>(repairGoodsProducerEntities);
			return new ResultData(20000,true,"查询成功",repairGoodsProducerEntityPageInfo);
		}
		PageUtil.startPage();
		PageInfo<RepairGoodsProducerEntity> repairGoodsProducerEntityPageInfo = new PageInfo<>(repairGoodsProducerMapper.selectAll());
		return new ResultData(20000,true,"查询成功",repairGoodsProducerEntityPageInfo);
	}

	@Override
	public ResultData saveRepairGoodsProducer(RepairGoodsProducerEntity repairGoodsProducerEntity) {
		if(repairGoodsProducerEntity == null){
			return new ResultData(20001,false,"生产厂家信息不能为空，请输入生产厂家信息");
		}
		Example example = new Example(RepairGoodsProducerEntity.class);
		example.createCriteria().andEqualTo("producer_name",repairGoodsProducerEntity.getProducer_name());
		List<RepairGoodsProducerEntity> repairGoodsProducerEntities = repairGoodsProducerMapper.selectByExample(example);
		if(repairGoodsProducerEntities.size()>0){
			return new ResultData(20003,false,"生产厂家已存在，请勿重复添加");
		}
		int i = repairGoodsProducerMapper.insertSelective(repairGoodsProducerEntity);
		if(i>0){
			return new ResultData(20000,true,"新增成功");
		}
		return new ResultData(20002,false,"网络错误，请重试");
	}

	@Override
	public ResultData updateRepairGoodsProducer(RepairGoodsProducerEntity repairGoodsProducerEntity) {
		if(repairGoodsProducerEntity == null){
			return new ResultData(20001,false,"生产厂家信息不能为空，请输入生产厂家信息");
		}
		Example example = new Example(RepairGoodsProducerEntity.class);
		example.createCriteria().andEqualTo("producer_name",repairGoodsProducerEntity.getProducer_name());
		List<RepairGoodsProducerEntity> repairGoodsProducerEntities = repairGoodsProducerMapper.selectByExample(example);
		RepairGoodsProducerEntity repairGoodsProducerEntity1 = repairGoodsProducerMapper.selectByPrimaryKey(repairGoodsProducerEntity.getProducer_id());
		if(repairGoodsProducerEntities.size()>0 && !repairGoodsProducerEntity1.getProducer_name().equals(repairGoodsProducerEntity.getProducer_name())){
			return new ResultData(20003,false,"生产厂家已存在，请勿修改成已存在的生产厂家");
		}
		int i = repairGoodsProducerMapper.updateByPrimaryKeySelective(repairGoodsProducerEntity);
		if(i>0){
			return new ResultData(20000,true,"编辑成功");
		}
		return new ResultData(20002,false,"网络错误，请重试");
	}

	@Override
	public ResultData deleteRepairGoodsProducer(Integer producer_id) {
		if(producer_id == null || producer_id < 0){
			return new ResultData(20001,false,"请选择生产厂家");
		}
		int i = repairGoodsProducerMapper.deleteByPrimaryKey(producer_id);
		if(i>0){
			return new ResultData(20000,true,"删除成功");
		}
		return new ResultData(20002,false,"网络错误，请重试");
	}

	/**
	 * 根据库名称,库类型以及物品类型查询对应的生产厂家(手机端出库使用)
	 * @param repertoryNameId 库名称ID
	 * @param repertoryTypeId 库类型ID
	 * @param goodsTypeId  物品分类ID
	 * @return
	 */
    @Override
    public ResultData findByCondition(Integer repertoryNameId, Integer repertoryTypeId, Integer goodsTypeId) {
    	if(repertoryNameId == null || repertoryTypeId == null || goodsTypeId == null){
			return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage(),null);
		}
		List<RepairGoodsProducerEntity> byCondition = repairGoodsProducerMapper.findByCondition(repertoryNameId, repertoryTypeId, goodsTypeId);
		return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),byCondition);
    }
}
