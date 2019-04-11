package cn.crm.controller.proc;


import cn.crm.util.VerifyUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

/**
 * @program: CRM
 * @description: 返回前段验证码
 * @author: xiaon099
 * @create: 2018-10-25 17:12
 **/
@RestController
@Api(description = "验证码实现")
public class VerifyController {

    /**
     * @desc 图形验证码生成方法
     *
     */
    @RequestMapping("/valicode")
    public void valicode(HttpServletResponse response,HttpSession session) throws Exception{
        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        //Map<String,Object> map = new HashMap<>();
        Object[] objs = VerifyUtil.createImage();
        System.out.println("------------->"+objs[0].toString().toLowerCase());
        if(objs.length!=0){
        	//将验证码存入Session
            session.setAttribute("imageCode",objs[0].toString().toLowerCase());
            //将图片输出给浏览器
            BufferedImage image = (BufferedImage) objs[1];
            response.setContentType("image/png");
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
            if(null != os){
            	os.close();
            }
        }   
    }

}
