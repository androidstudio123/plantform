package cn.crm.mapper.terrace;


import cn.crm.entity.terrace.RepairSchoolModuleEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * TODO 在此加入类描述
 *
 * @author hzg
 * @version 2019-03-25 10:01:25
 * @copyright {@link hzg}
 * @see cn.crm.service.RepairSchoolModule
 */
@Component
public interface RepairSchoolModuleMapper extends Mapper<RepairSchoolModuleEntity>, MySqlMapper<RepairSchoolModuleEntity> {

    /**
     * 根据学校ids查询对应模块ids
     *
     * @param school_id
     * @return
     */
    public List<Integer> findModuleId(@Param("school_id") List<Integer> school_id);

    /**
     * 根据学校id查询对应模块ids
     *
     * @param school_id
     * @return
     */
    public List<Integer> findAppModuleId(@Param("school_id") Integer school_id);


}

