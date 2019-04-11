package cn.crm.service.repair.impl;

import cn.crm.entity.repair.RepairRoleAreaEntity;
import cn.crm.mapper.repair.RepairRoleAreaMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.repair.RepairRoleAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RepairRoleAreaServiceImpl extends BaseServiceImpl<RepairRoleAreaEntity> implements RepairRoleAreaService {
	
	@Autowired
	private RepairRoleAreaMapper repairRoleAreaMapper;

    /**
     * 根据用户查询此用户所负责的区域
     * @param userId  用户ID
     * @return
     */
    @Override
    public ResultData findRoleAreaByUser(Integer userId) {
        try {
            List<Integer> area = repairRoleAreaMapper.findRoleAreaByUser(userId);
            return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),area);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultData(ResultCode.ERROR.getCode(),false,ResultCode.ERROR.getMessage(),null);
        }
    }
}
