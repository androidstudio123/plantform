package cn.crm.shiro.matcher;

/**
 * 登陆类型枚举类
 */
public enum LoginType {

    PASSWORD("password"), //密码登陆
    NOPASSWD("nopassword"); //免密登陆
    private String code;
    private LoginType(String code){
        this.code = code;

    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
