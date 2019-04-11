package cn.crm.service.repair.impl;

import cn.crm.entity.repair.DefaultFunctionEntity;
import cn.crm.mapper.repair.DefaultFunctionMapper;
import cn.crm.mapper.repair.OrderAreaTypeUserVoMapper;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.repair.DefaultFunctionService;
import cn.crm.service.repair.OrderAreaTypeUserVoService;
import cn.crm.util.PageUtil;
import cn.crm.vo.OrderAreaTypeUserVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO 在此加入类描述
 * @author hzg
 * @version  2019-03-26 09:19:29
 */

@Service
public class OrderAreaTypeUserVoServiceImpl extends BaseServiceImpl<OrderAreaTypeUserVo> implements OrderAreaTypeUserVoService {
	
	@Autowired
	private OrderAreaTypeUserVoMapper orderAreaTypeUserVoMapper;


	@Override
	public ResultData findOrderManager(Integer order_status, String order_no, Integer pageNum, Integer pageSize) {
		if(order_no == ""){
			order_no = null;
		}
		PageUtil.startPage();
		List<OrderAreaTypeUserVo> orderManager = orderAreaTypeUserVoMapper.findOrderManager(order_status, order_no, pageNum, pageSize);
		PageInfo<OrderAreaTypeUserVo> orderAreaTypeUserVoPageInfo = new PageInfo<>(orderManager);
		return new ResultData(20000,true,"查询成功",orderAreaTypeUserVoPageInfo);
	}
}
