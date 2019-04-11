package cn.crm.shiro.realm;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UsernamePasswordLoginTypeToken extends UsernamePasswordToken {

    private static final long serialVersionUID = 7134536615448037793L;
    /**
    *登陆类型
    */
    private String loginType;

    public UsernamePasswordLoginTypeToken(String username, String password, boolean rememberMe, String host, String loginType) {
        super(username, password, rememberMe, host);
        this.loginType = loginType;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}