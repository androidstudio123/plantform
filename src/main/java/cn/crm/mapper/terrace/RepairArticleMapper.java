package cn.crm.mapper.terrace;


import cn.crm.entity.terrace.RepairArticleEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * TODO 在此加入类描述  文章信息mapper
 * @author MYZ
 * @version  2019-03-25 10:00:30
 */
@Component
public interface RepairArticleMapper extends Mapper<RepairArticleEntity>, MySqlMapper<RepairArticleEntity> {




}

