package cn.crm.service.repair.impl;


import cn.crm.entity.repair.RepairRoleOrderTypeEntity;
import cn.crm.mapper.repair.RepairRoleOrderTypeMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.repair.RepairRoleOrderTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO 在此加入类描述
 * @copyright {@link hzg}
 * @author hzg
 * @version  2019-03-22 10:36:04
 * @see cn.yr.service.impl.RepairRoleOrderType
 */

@Service
public class RepairRoleOrderTypeServiceImpl extends BaseServiceImpl<RepairRoleOrderTypeEntity> implements RepairRoleOrderTypeService {
	
	@Autowired
	private RepairRoleOrderTypeMapper repairRoleOrderTypeMapper;


    /**
     * 根据用户查询此用户所负责的工单类型
     * @param userId 用户ID
     * @return
     */
    @Override
    public ResultData findOrderTypeByUser(Integer userId) {
        try {
            List<Integer> orderTypes = repairRoleOrderTypeMapper.findOrderTypeByUser(userId);
            return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),orderTypes);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage(),null);
        }
    }
}
