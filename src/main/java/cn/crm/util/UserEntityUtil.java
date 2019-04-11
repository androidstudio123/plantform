package cn.crm.util;

import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import java.text.SimpleDateFormat;
import java.util.Date;


public class UserEntityUtil {
    /**
     * 用户信息key
     */
    public static final String USER_SESSION_KEY = "sysAdminEntity";
    public static final String USER_SESSION_LOGOUT_LOGOUT_KEY = "USER_SESSION_LOGOUT_LOGOUT_KEY";

    /**
     * 从session中获取当前登录的用户信息
     */
    public static SysAdminEntity getUserFromSession() {
        Session session = SecurityUtils.getSubject().getSession();
        return (SysAdminEntity) session.getAttribute(UserEntityUtil.USER_SESSION_KEY);
    }

    public static String convertDateToString(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 获取微信登录的用户
     * @return
     */
    public static SysUserEntity getRepairUserFromSession(){
        Session session = SecurityUtils.getSubject().getSession();
        return (SysUserEntity) session.getAttribute("userEntity");
    }

}
