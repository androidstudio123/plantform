package cn.crm.service.repair.impl;

import cn.crm.entity.repair.OrderCommentEntity;
import cn.crm.mapper.repair.OrderCommentMapper;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.repair.OrderCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderCommentServiceImpl extends BaseServiceImpl<OrderCommentEntity> implements OrderCommentService {
	
	@Autowired
	private OrderCommentMapper orderCommentMapper;
	

}
