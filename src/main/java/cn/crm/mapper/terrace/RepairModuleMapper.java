package cn.crm.mapper.terrace;


import cn.crm.entity.terrace.RepairModuleEntity;
import cn.crm.vo.ModuleVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * TODO 在此加入类描述  模块mapper
 *
 * @author MYZ
 * @version 2019-03-25 10:00:12
 */
@Component
public interface RepairModuleMapper extends Mapper<RepairModuleEntity>, MySqlMapper<RepairModuleEntity> {
    /**
     * 查询模块信息
     *
     * @param schoolIds
     * @param module_status
     * @param module_name
     * @return
     */
    public List<ModuleVo> findAllModule(@Param("schoolIds") List<Integer> schoolIds,@Param("module_name")  String module_name, @Param("module_status") Integer module_status);

    /**
     * 根据id查询信息
     * @param id
     * @return
     */
    public ModuleVo findModuleById (@Param("id") Integer id);

}

