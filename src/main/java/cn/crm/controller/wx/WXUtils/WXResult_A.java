package cn.crm.controller.wx.WXUtils;

import lombok.Data;

@Data
public class WXResult_A {

    private String access_token;
    private Integer expires_in;
    private String refresh_token;
    private String openid;
    private String scope;

}
