package cn.crm.mapper.repair;

import cn.crm.entity.repair.SchoolConfigEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

@Component
public interface SchoolConfigMapper extends Mapper<SchoolConfigEntity>,MySqlMapper<SchoolConfigEntity>{


/**
 * @Description : 根据经度纬度查询对应学校id
 * @param lon	 经度
 * @param lat	纬度
 * @Return : java.lang.Integer
 * @Author : MYZ
 * @Date : 2019/3/26 17:23
 */
public  Integer  findSchoolId(@Param("lon")Double lon,@Param("lat") Double lat);


}

