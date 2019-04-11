package cn.crm.mapper.terrace;


import cn.crm.entity.terrace.RepairSchoolArticleEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * TODO 在此加入类描述  学校文章类型中间表mapper
 *
 * @author MYZ
 * @version 2019-03-25 10:01:40
 */
@Component
public interface RepairSchoolArticleMapper extends Mapper<RepairSchoolArticleEntity>, MySqlMapper<RepairSchoolArticleEntity> {

    /**
     * 根据学校ids查询对应文章类型ids
     *
     * @param school_id
     * @return
     */
    public List<Integer> findArticleTypeId(@Param("school_id") List<Integer> school_id);

    /**
     * 根据学校id查询对应文章类型ids
     *
     * @param school_id
     * @return
     */
    public List<Integer> findAppArticleTypeId(@Param("school_id") Integer school_id);


}

