package cn.crm.mapper.terrace;


import cn.crm.entity.terrace.RepairArticleTypeEntity;
import cn.crm.vo.ArticleTypeVo;
import cn.crm.vo.ModuleVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * TODO 在此加入类描述  文章类型mapper
 * @author MYZ
 * @version  2019-03-25 10:01:09
 */
@Component
public interface RepairArticleTypeMapper extends Mapper<RepairArticleTypeEntity>, MySqlMapper<RepairArticleTypeEntity> {
    /**
     * 查询文章类型
     * @param schoolIds  学校ids
     * @param articleType_state  文章类型状态
     * @param articleType_title  文章类型标题
     * @return
     */
  public List<ArticleTypeVo> findAllArticleType(@Param("schoolIds") List<Integer>schoolIds ,@Param("articleType_title")String articleType_title,@Param("articleType_state")Integer articleType_state);


    /**
     * 根据id查询信息
     * @param id
     * @return
     */
    public ArticleTypeVo findArticleTypeById (@Param("id") Integer id);

}

