package cn.crm.mapper.repair;

import cn.crm.entity.repair.DefaultFunctionEntity;
import cn.crm.result.ResultData;
import cn.crm.vo.UserRoleGroupSchoolVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;


/**
 * TODO 在此加入类描述
 * @copyright {@link hzg}
 * @author hzg
 * @version  2019-03-26 09:19:29
 */

public interface UserRoleGroupSchoolVoMapper extends Mapper<UserRoleGroupSchoolVo>,MySqlMapper<UserRoleGroupSchoolVo>{

    List<UserRoleGroupSchoolVo> findUserRoleGroupSchool(@Param("admin_id")Integer admin_id,@Param("user_group_id")Integer user_group_id, @Param("user_name")String user_name, Integer pageNum, Integer pageSize);

}

