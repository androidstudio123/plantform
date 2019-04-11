package cn.crm.service.repair;

import cn.crm.entity.repair.SchoolConfigEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;

import javax.servlet.http.HttpServletRequest;

public interface SchoolConfigService extends BaseService<SchoolConfigEntity> {

    //查询所有学校配置信息
    ResultData findSchoolConfig(Integer pageNum, Integer pageSize, String schooleName, Integer status);

    //新增学校配置信息
    ResultData saveSchoolConfig(SchoolConfigEntity schoolConfigEntity,HttpServletRequest request);

    //修改学校配置信息
    ResultData updateSchoolConfig(SchoolConfigEntity schoolConfigEntity);

    //删除学校配置信息
    ResultData deleteSchoolConfig(Integer id);

    //启用禁用学校配置信息
    ResultData changeSchoolConfigStatus(Integer id, Integer status);
    /**
     * @Description : 查询当前登陆人管理的学校信息
     * @param
     * @Return : cn.crm.result.ResultData
     * @Author : MYZ
     * @Date : 2019/3/28 11:04
     */
    ResultData findAllSchoolConfig(HttpServletRequest request);

    /**
     * @Description : 查询所有学校信息
     * @param
     * @Return : cn.crm.result.ResultData
     * @Author : MYZ
     * @Date : 2019/3/28 11:04
     */
    ResultData findAllSchoolConfig();
    //根据经纬度判断学校
    ResultData findSchoolByJWD(Double lat, Double lon,Integer user_id);
}
