package cn.crm.mapper.repair;

import cn.crm.util.IdGenerator;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Map;

/**
 * @author MYZ
 * @version V1.0
 * @Description:
 * @Package cn.crm.mapper.repair
 * @date 2019/3/29 16:07
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DefaultFunctionMapperTest {

    @Autowired
    HttpServletRequest request; //这里可以获取到request

    @Test
   public void aaa(){
      String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx807f7cafabba1b54&secret=758c66e4f63f02b23d05ac0c49f9af40";
      RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        Map map = JSON.parseObject(exchange.getBody(), Map.class);
        String access_token = (String)map.get("access_token");
        System.out.println("获取到的token为:"+ map.get("access_token"));
        String url1 = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi";
        ResponseEntity<String> exchange1 = restTemplate.exchange(url1, HttpMethod.GET, null, String.class);
        Map map1 = JSON.parseObject(exchange1.getBody(), Map.class);
        String ticket = (String) map1.get("ticket");
        String noncestr = IdGenerator.idGen();
        String timestamp = System.currentTimeMillis() + "";
        System.out.println("ticket------------------"+ticket);
        String sign = "jsapi_ticket=" + ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;
    }

    @Test
    public void test() throws FileNotFoundException {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();

        String path2 = ResourceUtils.getURL("classpath:").getPath();
        System.out.println("path" + path);
        System.out.println("path2" + path2);
    }
}