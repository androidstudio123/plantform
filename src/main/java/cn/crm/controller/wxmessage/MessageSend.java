package cn.crm.controller.wxmessage;


import cn.crm.util.PropertiesUtil;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MessageSend
 * @Author HJW
 * @Date 2019/4/4 11:13
 */

public class MessageSend {


    public  void returnMessage(String openId) {
        String appid = PropertiesUtil.getValue("APPID");
        String secret= PropertiesUtil.getValue("APPSECRET");
        Map<String, Object> map2 = new HashMap<>();
        String url1 = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> exchange = restTemplate.exchange(url1, HttpMethod.GET, null, String.class);
        Map map = JSON.parseObject(exchange.getBody(), Map.class);
        String access_token = (String) map.get("access_token");
        String postUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
        JSONObject data = new JSONObject();
        data.put("touser", "om9rnwB8jTfYiEA1Jm1YgELuTKVw");   // openid
        data.put("template_id", "GiKSLqFqzVUt2JOi_po47-LG-F6-WmODo_gUKJG8NJQ");
        data.put("url", "http://www.baidu.com");
        JSONObject first = new JSONObject();
        first.put("提醒", "你有一个新的工单");
        first.put("color", "#173177");
        JSONObject keyword1 = new JSONObject();
        keyword1.put("value", "20190409");
        keyword1.put("color", "#173177");
        JSONObject keyword2 = new JSONObject();
        keyword2.put("value", "老张");
        keyword2.put("color", "#173177");
        JSONObject keyword3 = new JSONObject();
        keyword3.put("value", "32000.00");
        keyword3.put("color", "#173177");
        JSONObject keyword4 = new JSONObject();
        keyword3.put("value", "商场消费");
        keyword3.put("color", "#173177");
        JSONObject keyword5 = new JSONObject();
        keyword3.put("value", "20191112");
        keyword3.put("color", "#173177");
        JSONObject remark = new JSONObject();
        remark.put("value", "请务必还款");
        remark.put("color", "#173177");
        data.put("first",first);
        data.put("keyword1",keyword1);
        data.put("keyword2",keyword2);
        data.put("keyword3",keyword3);
        data.put("keyword3",keyword4);
        data.put("keyword3",keyword5);
        data.put("remark",remark);
        String s = "";
        try {
             s = HttpClientUtils.sendPostJsonStr(postUrl, data.toJSONString());
            System.out.println("返回的结果为:"+s);

        } catch (IOException e) {
            e.printStackTrace();
        }
        com.alibaba.fastjson.JSONObject result = JSON.parseObject(s);
        int errcode = result.getIntValue("errcode");
        System.out.println(errcode);

        if(errcode == 0){
            // 发送成功
            System.out.println("发送成功");
        } else {
            // 发送失败
            System.out.println("发送失败");
        }

    }
}
