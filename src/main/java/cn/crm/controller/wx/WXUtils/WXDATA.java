package cn.crm.controller.wx.WXUtils;

import cn.crm.util.PropertiesUtil;
import net.sf.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;


/**
 * 微信获取数据
 */

public class WXDATA {


    /**
     * 获取微信用户信息
     * @param access_token
     * @param openid
     * @return
     */
    public static WXResult_B getWXUserInfo(String access_token, String openid) {
        WXResult_B  wxResult_b = null;
        RestTemplate restTemplate  = new RestTemplate();
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        converterList.remove(1);    //移除StringHttpMessageConverter
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        converterList.add(1, converter);    //convert顺序错误会导致失败
        restTemplate.setMessageConverters(converterList);
        String url = String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN", access_token, openid);
        ResponseEntity<String> results = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        System.out.println(String.format("请求微信接口返回的结果数据为:=%s",results));
        if(results.getStatusCode().value()==200&results.getBody()!=null){
            String jsonRes = results.getBody();
            wxResult_b =  (WXResult_B) JSONObject.toBean(JSONObject.fromObject(jsonRes),WXResult_B.class);
            System.out.println(String.format("微信返回的用户信息为:=%s",wxResult_b.toString()));
        }
        return wxResult_b;
    }

    /**
     * 获取accesstoken
     * @param code
     * @return
     */
    public static WXResult_A getAccessToken(String code) {
        WXResult_A myJson = null;
        RestTemplate restTemplate  = new RestTemplate();
        String APPID = PropertiesUtil.getValue("APPID");
        String SECRET = PropertiesUtil.getValue("APPSECRET");
        String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code&appid=%s&secret=%s&code=%s&scope=snsapi_base", APPID, SECRET,code);

//        https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx807f7cafabba1b54&secret=758c66e4f63f02b23d05ac0c49f9af40
        ResponseEntity<String> results = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        System.out.println(String.format("请求微信接口返回的结果数据为:=%s",results));
        if(results.getStatusCode().value()==200&results.getBody()!=null){
            String jsonRes = results.getBody();
            myJson = com.alibaba.fastjson.JSONObject.parseObject(jsonRes, WXResult_A.class);
//            myJson = (WXResult_A)JSONObject.toBean(JSONObject.fromObject(jsonRes),WXResult_A.class);
            System.out.println(String.format("微信返回的用户信息为:=%s",myJson.toString()));
        }
        return myJson;
    }


    public static void main(String[] args) {
        String code = "061t3O7e16Y0Rz0mMA4e13M48e1t3O7z";
        WXResult_A accessToken = WXDATA.getAccessToken(code);
        System.out.println(accessToken);
        WXDATA.getWXUserInfo(accessToken.getAccess_token(),accessToken.getOpenid());


    }
}
