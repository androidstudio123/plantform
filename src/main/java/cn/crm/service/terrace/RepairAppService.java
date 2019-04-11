package cn.crm.service.terrace;

import cn.crm.result.ResultData;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author MYZ
 * @version V1.0
 * @Description: 微信新用户登录学校授权
 * @Package cn.crm.service.terrace
 * @date 2019/3/26 17:49
 */
public interface RepairAppService {

    /**
     * @param lon     经度
     * @param lat     纬度
     * @param school_id  学校id
     * @param request
     * @Description :   新用户登陆需要传递经度,纬度或者收到选择学校id
     * @Return : cn.crm.result.ResultData
     * @Author : MYZ
     * @Date : 2019/3/26 15:38
     */
    public ResultData updateUser(Double lon, Double lat, Integer school_id, HttpServletRequest request);

}
