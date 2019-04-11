package cn.crm.service.repair.impl;

import cn.crm.entity.repair.DefaultFunctionEntity;
import cn.crm.mapper.repair.DefaultFunctionMapper;
import cn.crm.mapper.repair.UserRoleGroupSchoolVoMapper;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.repair.DefaultFunctionService;
import cn.crm.service.repair.UserRoleGroupSchoolVoService;
import cn.crm.util.PageUtil;
import cn.crm.vo.UserRoleGroupSchoolVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * TODO 在此加入类描述
 * @author hzg
 * @version  2019-03-26 09:19:29
 */

@Service
public class UserRoleGroupSchoolVoServiceImpl extends BaseServiceImpl<UserRoleGroupSchoolVo> implements UserRoleGroupSchoolVoService {
	
	@Autowired
	private UserRoleGroupSchoolVoMapper userRoleGroupSchoolVoMapper;


	@Override
	public ResultData findUserRoleGroupSchool(Integer admin_id,Integer user_group_id, String user_name, Integer pageNum, Integer pageSize) {
	    if(user_name == ""){
	        user_name = null;
        }
		PageUtil.startPage();
		List<UserRoleGroupSchoolVo> userRoleGroupSchool = userRoleGroupSchoolVoMapper.findUserRoleGroupSchool(admin_id,user_group_id, user_name, pageNum, pageSize);
		PageInfo<UserRoleGroupSchoolVo> userRoleGroupSchoolVoPageInfo = new PageInfo<>(userRoleGroupSchool);
		return new ResultData(20000,true,"查询成功",userRoleGroupSchoolVoPageInfo);
	}
}
