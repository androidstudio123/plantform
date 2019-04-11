package cn.crm.service.materiel.impl;

import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.materiel.RepairGoodsEntity;
import cn.crm.entity.materiel.RepairGoodsImportEntity;
import cn.crm.mapper.materiel.RepairGoodsImportMapper;
import cn.crm.mapper.materiel.RepairGoodsMapper;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.materiel.RepairGoodsImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
public class RepairGoodsImportServiceImpl extends BaseServiceImpl<RepairGoodsImportEntity> implements RepairGoodsImportService {
	
	@Autowired
	private RepairGoodsImportMapper repairGoodsImportMapper;

	@Autowired
	private RepairGoodsMapper repairGoodsMapper;

	@Override
	public ResultData findRepairGoodsImportAll(Integer admin_id,String goods_name) {
		//获取用户信息
//		SysAdminEntity sysAdminEntity = (SysAdminEntity)request.getSession().getAttribute("sysAdminEntity");
//		if(sysAdminEntity == null){
//			return new ResultData(20008,false,"身份过期，请重试");
//		}
		Example example = new Example(RepairGoodsImportEntity.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("admin_id",admin_id);
		criteria.andLike("goods_name","%"+goods_name+"%");
		List<RepairGoodsImportEntity> repairGoodsImportEntities = repairGoodsImportMapper.selectByExample(example);
		return new ResultData(20000,true,"查询成功",repairGoodsImportEntities);
	}

	@Override
	public ResultData saveRepairGoodsImport(RepairGoodsImportEntity repairGoodsImportEntity) {
		int i = repairGoodsImportMapper.insertSelective(repairGoodsImportEntity);
		if(i>0){
			return new ResultData(20000,true,"执行成功");
		}
		return new ResultData(20001,false,"网络错误，请重试");
	}

	@Override
	public ResultData updateRepairGoodsImport(RepairGoodsImportEntity repairGoodsImportEntity) {
		int i = repairGoodsImportMapper.updateByPrimaryKeySelective(repairGoodsImportEntity);
		if(i>0){
			return new ResultData(20000,true,"执行成功");
		}
		return new ResultData(20001,false,"网络错误，请重试");
	}

	@Override
	public ResultData deleteRepairGoodsImport(Integer import_id) {
		int i = repairGoodsImportMapper.deleteByPrimaryKey(import_id);
		if(i>0){
			return new ResultData(20000,true,"执行成功");
		}
		return new ResultData(20001,false,"网络错误，请重试");
	}

	@Override
	@Transactional
	public ResultData submitRepairGoodsImport(RepairGoodsImportEntity repairGoodsImportEntity, Integer admin_id) {
		if(repairGoodsImportEntity.getFlag() == 0){
			//如果flag是0则只对导入表进行添加数据的操作，state为0
			repairGoodsImportEntity.setState(0);
			repairGoodsImportEntity.setAdmin_id(admin_id);
			repairGoodsImportEntity.setAdmin_name("admin");
			int i = repairGoodsImportMapper.insert(repairGoodsImportEntity);
			if(i>0){
				return new ResultData(20000,false,"执行成功");
			}
			return new ResultData(20003,true,"执行失败，请重试");
		}
		//如果flag为1时，先将数据添加到import表中
		repairGoodsImportEntity.setState(0);
		repairGoodsImportEntity.setAdmin_id(admin_id);
		repairGoodsImportEntity.setAdmin_name("admin");
		int i = repairGoodsImportMapper.insert(repairGoodsImportEntity);
		if(i<1){
			return new ResultData(20006,false,"执行失败，请重试");
		}
		Example example = new Example(RepairGoodsEntity.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("goods_name",repairGoodsImportEntity.getGoods_name());
		criteria.andEqualTo("goods_type_id",repairGoodsImportEntity.getGoods_type_id());
		criteria.andEqualTo("producer_id",repairGoodsImportEntity.getProducer_id());
		criteria.andEqualTo("repertory_name_id",repairGoodsImportEntity.getRepertory_name_id());
		criteria.andEqualTo("repertory_type_id",repairGoodsImportEntity.getRepertory_type_id());
		List<RepairGoodsEntity> repairGoodsEntities = repairGoodsMapper.selectByExample(example);
		if(repairGoodsEntities.size()>0){
			//如果存在此商品直接修改数量
			RepairGoodsEntity repairGoodsEntity = repairGoodsEntities.get(0);
			repairGoodsEntity.setNum(repairGoodsEntity.getNum()+repairGoodsImportEntity.getGoods_num());
			int result = repairGoodsMapper.updateByPrimaryKeySelective(repairGoodsEntity);
			if(result>0){
				return  new ResultData(20000,true,"执行成功");
			}
			return  new ResultData(20003,false,"执行失败");
		}
		//如果商品表中不存在商品新增一条数据
		RepairGoodsEntity repairGoodsEntity = new RepairGoodsEntity();
		repairGoodsEntity.setNum(repairGoodsImportEntity.getGoods_num());
		repairGoodsEntity.setGoods_name(repairGoodsImportEntity.getGoods_name());
		repairGoodsEntity.setGoods_type_id(repairGoodsImportEntity.getGoods_type_id());
		repairGoodsEntity.setProducer_id(repairGoodsImportEntity.getProducer_id());
		repairGoodsEntity.setRepertory_name_id(repairGoodsImportEntity.getRepertory_name_id());
		repairGoodsEntity.setRepertory_type_id(repairGoodsImportEntity.getGoods_type_id());
		int re = repairGoodsMapper.insertSelective(repairGoodsEntity);
		if(re<1){
			return new ResultData(20005,false,"新增到商品表失败，请重试");
		}
		//将入库表中的state改为1
		repairGoodsImportEntity.setState(1);
		int i1 = repairGoodsImportMapper.updateByPrimaryKeySelective(repairGoodsImportEntity);
		if(i1>0){
			return new ResultData(20000,true,"提交成功");
		}
		return new ResultData(20005,false,"网络错误，请重试");
	}

	@Override
	public ResultData submitNotSaveGoodsImport(RepairGoodsImportEntity repairGoodsImportEntity) {
		if(repairGoodsImportEntity.getFlag() == 0){
			//如果flag是0则只对导入表进行编辑操作
			int i = repairGoodsImportMapper.updateByPrimaryKeySelective(repairGoodsImportEntity);
			if(i>0){
				return new ResultData(20000,false,"执行成功");
			}
			return new ResultData(20003,true,"执行失败，请重试");
		}
		//如果flag为1时，先修改import表中的数据
		repairGoodsImportEntity.setState(1);
		int i = repairGoodsImportMapper.updateByPrimaryKeySelective(repairGoodsImportEntity);
		if(i<1){
			return new ResultData(20006,false,"执行失败，请重试");
		}
		Example example = new Example(RepairGoodsEntity.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("goods_name",repairGoodsImportEntity.getGoods_name());
		criteria.andEqualTo("goods_type_id",repairGoodsImportEntity.getGoods_type_id());
		criteria.andEqualTo("producer_id",repairGoodsImportEntity.getProducer_id());
		criteria.andEqualTo("repertory_name_id",repairGoodsImportEntity.getRepertory_name_id());
		criteria.andEqualTo("repertory_type_id",repairGoodsImportEntity.getRepertory_type_id());
		List<RepairGoodsEntity> repairGoodsEntities = repairGoodsMapper.selectByExample(example);
		if(repairGoodsEntities.size()>0){
			//如果存在此商品直接修改数量
			RepairGoodsEntity repairGoodsEntity = repairGoodsEntities.get(0);
			repairGoodsEntity.setNum(repairGoodsEntity.getNum()+repairGoodsImportEntity.getGoods_num());
			int result = repairGoodsMapper.updateByPrimaryKeySelective(repairGoodsEntity);
			if(result>0){
				return  new ResultData(20000,true,"执行成功");
			}
			return  new ResultData(20003,false,"执行失败");
		}
		//如果商品表中不存在商品新增一条数据
		RepairGoodsEntity repairGoodsEntity = new RepairGoodsEntity();
		repairGoodsEntity.setNum(repairGoodsImportEntity.getGoods_num());
		repairGoodsEntity.setGoods_name(repairGoodsImportEntity.getGoods_name());
		repairGoodsEntity.setGoods_type_id(repairGoodsImportEntity.getGoods_type_id());
		repairGoodsEntity.setProducer_id(repairGoodsImportEntity.getProducer_id());
		repairGoodsEntity.setRepertory_name_id(repairGoodsImportEntity.getRepertory_name_id());
		repairGoodsEntity.setRepertory_type_id(repairGoodsImportEntity.getGoods_type_id());
		int re = repairGoodsMapper.insertSelective(repairGoodsEntity);
		if(re<1){
			return new ResultData(20005,false,"新增到商品表失败，请重试");
		}
		//将入库表中的state改为1
		repairGoodsImportEntity.setState(1);
		int i1 = repairGoodsImportMapper.updateByPrimaryKeySelective(repairGoodsImportEntity);
		if(i1>0){
			return new ResultData(20000,true,"提交成功");
		}
		return new ResultData(20005,false,"网络错误，请重试");
	}
}
