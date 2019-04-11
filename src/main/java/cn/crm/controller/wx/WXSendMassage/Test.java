package cn.crm.controller.wx.WXSendMassage;

public class Test {

    public static void main(String[] args) {


        WeChatMsgSend swx = new WeChatMsgSend();
        try {
            String token = swx.getToken("wx720c4fd0b7fe2e04","Z4dtXbkcbNayC9CJOTLHW52jEhP_R77qDFPZc99lv9Y");
            String postdata = swx.createpostdata("18338512245|18538221574|15518308079", "text", 16, "content","这是一条测试信息");
            String resp = swx.post("utf-8", WeChatMsgSend.CONTENT_TYPE,(new WeChatUrlData()).getSendMessage_Url(), postdata, token);
            System.out.println("获取到的token======>" + token);
            System.out.println("请求数据======>" + postdata);
            System.out.println("发送微信的响应数据======>" + resp);
        }catch (Exception e) {
            e.getStackTrace();
        }



    }

}
