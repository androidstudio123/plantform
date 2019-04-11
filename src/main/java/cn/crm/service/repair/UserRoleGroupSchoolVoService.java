package cn.crm.service.repair;

import cn.crm.entity.repair.DefaultFunctionEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;
import cn.crm.vo.UserRoleGroupSchoolVo;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import java.util.List;


/**
 * TODO 在此加入类描述
 * @copyright {@link hzg}
 * @author hzg
 * @version  2019-03-26 09:19:29
 * @see cn.yr.service.DefaultFunction
 */
public interface UserRoleGroupSchoolVoService extends BaseService<UserRoleGroupSchoolVo> {
    ResultData findUserRoleGroupSchool(Integer admin_id,Integer user_group_id, String user_name, Integer pageNum, Integer pageSize);

}
