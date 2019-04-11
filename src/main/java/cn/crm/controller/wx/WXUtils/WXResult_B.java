package cn.crm.controller.wx.WXUtils;

import lombok.Data;

import java.util.List;

@Data
public class WXResult_B {

    private String openid;
    private String nickname;
    private String sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private List privilege;

}
