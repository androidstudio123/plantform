package cn.crm.service.sys;

import cn.crm.result.ResultData;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
* @Description: TODO  后台登录服务层接口
* @param {NAME}
* @Author:MYZ
*/

public interface SysLoginService {

    /**
     * 用户登录
     * @param admin_name  用户名
     * @param admin_password  密码
     * @param imageCode 验证码
     * @return
     */
    public ResultData login (String admin_name, String admin_password, String imageCode, HttpServletRequest request);
}
