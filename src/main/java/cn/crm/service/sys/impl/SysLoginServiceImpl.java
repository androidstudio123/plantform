package cn.crm.service.sys.impl;

import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.sys.SysLoginService;
import cn.crm.shiro.matcher.EasyTypeToken;
import cn.crm.util.StringUtils;
import cn.crm.util.UserEntityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName {NAME}
 * @Description TODO  后台登录服务层实现类
 * @Author MYZ
 * @Date 2019/3/14 0014 10:24
 */
@Service
public class SysLoginServiceImpl implements SysLoginService {
    /**
     * 用户登录
     *
     * @param admin_name     用户名
     * @param admin_password 密码
     * @param imageCode      验证码
     * @return
     */
    @Override
    public ResultData login(String admin_name, String admin_password, String imageCode, HttpServletRequest request) {
//        //判断传入的验证码是否为空
//        if (StringUtils.isEmpty(imageCode)) {
//            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, ResultCode.EMPTYPARAMS.getMessage());
//        }
//        //获取session中的验证码信息
//        String codeStr = (String) request.getSession().getAttribute("imageCode");
//        //判断验证码是否正确
//        if (null == codeStr || !imageCode.equals(codeStr.toLowerCase())) {
//            return new ResultData(ResultCode.ERROR.getCode(), false, ResultCode.ERROR.getMessage());
//        }
        Map<String, Object> map = new HashMap<String, Object>();
        Subject sub = SecurityUtils.getSubject();
        EasyTypeToken token = new EasyTypeToken(admin_name, admin_password);
        try {
            sub.login(token);
            map.put("msg", "true");
            map.put("admin", UserEntityUtil.getUserFromSession());
        } catch (LockedAccountException e) {
            token.clear();
            return new ResultData(ResultCode.LOGINERROR_FREEZE.getCode(), false, ResultCode.LOGINERROR_FREEZE.getMessage());
        } catch (ExcessiveAttemptsException e) {
            token.clear();
            return new ResultData(ResultCode.LOGINERROR_LOCK.getCode(), false, ResultCode.LOGINERROR_LOCK.getMessage());
        } catch (AuthenticationException e) {
            token.clear();
            return new ResultData(ResultCode.LOGINERROR_NONENTITY.getCode(), false, ResultCode.LOGINERROR_NONENTITY.getMessage());
        }
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), map);
    }

}
