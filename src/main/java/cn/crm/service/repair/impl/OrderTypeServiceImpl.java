package cn.crm.service.repair.impl;


import cn.crm.entity.repair.OrderTypeEntity;
import cn.crm.mapper.repair.OrderTypeMapper;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.repair.OrderTypeService;
import cn.crm.util.PageUtil;
import com.alibaba.druid.sql.PagerUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


@Service
public class OrderTypeServiceImpl extends BaseServiceImpl<OrderTypeEntity> implements OrderTypeService {
	
	@Autowired
	private OrderTypeMapper orderTypeMapper;


	@Override
	public ResultData findOrderType(Integer pageNum,Integer pageSize,String type_name) {
		PageHelper.startPage(1,10);
		Example example = new Example(OrderTypeEntity.class);
		if(type_name == "" || type_name == null){
			List<OrderTypeEntity> orderTypeEntities = orderTypeMapper.selectAll();
			PageInfo<OrderTypeEntity> orderTypeEntityPageInfo = new PageInfo<>(orderTypeEntities);
			return new ResultData(20000,true,"查询成功",orderTypeEntityPageInfo);
		}
		example.createCriteria().andLike("type_name","%"+type_name+"%");
		List<OrderTypeEntity> orderTypeEntities = orderTypeMapper.selectByExample(example);
		PageInfo<OrderTypeEntity> orderTypeEntityPageInfo = new PageInfo<>(orderTypeEntities);
		return new ResultData(20000,true,"查询工单类型",orderTypeEntityPageInfo);
	}

	@Override
	public ResultData saveOrderType(OrderTypeEntity orderTypeEntity) {
		if(orderTypeEntity == null){
			return new ResultData(20001,false,"请输入工单类型信息");
		}
		int result = orderTypeMapper.insertSelective(orderTypeEntity);
		if(result>0){
			return new ResultData(20000,true,"新增成功");
		}
		return new ResultData(20002,false,"新增失败，请重试");
	}

	@Override
	public ResultData updateOrderType(OrderTypeEntity orderTypeEntity) {
		if(orderTypeEntity == null){
			return new ResultData(20001,false,"请输入工单类型信息");
		}
		int result = orderTypeMapper.updateByPrimaryKeySelective(orderTypeEntity);
		if(result>0){
			return new ResultData(20000,true,"编辑成功");
		}
		return new ResultData(20002,false,"编辑失败，请重试");
	}

	@Override
	public ResultData deleteOrderType(Integer id) {
		if(id<0 || id == null){
			return new ResultData(20001,false,"请选择工单类型");
		}
		int result = orderTypeMapper.deleteByPrimaryKey(id);
		if(result>0){
			return new ResultData(20000,true,"删除成功");
		}
		return new ResultData(20002,false,"删除失败，请重试");
	}
}
