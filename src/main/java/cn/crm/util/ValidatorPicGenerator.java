package cn.crm.util;
import cn.crm.util.slideVerification.validate.VerifyImageUtil;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 滑块验证图片工具类
 * @author  nhy
 * @Date  2019/03/06
 */
public class ValidatorPicGenerator {
    public Map<String,byte[]> generator() throws  Exception {
        Map<String, byte[]> pictureMap;
        File templateFile;  //模板图片
        File targetFile;  //
        Random random = new Random();
        int templateNo = random.nextInt(4) + 1;
        int targetNo = random.nextInt(20) + 1;
        //随机生成底片
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("static/templates/" + templateNo + ".png");
        templateFile = new File(templateNo + ".png");
        FileUtils.copyInputStreamToFile(stream, templateFile);
        //随机生成切片
        stream = this.getClass().getClassLoader().getResourceAsStream("static/targets/" + targetNo + ".jpg");
        targetFile = new File(targetNo + ".jpg");
        FileUtils.copyInputStreamToFile(stream, targetFile);

        // 转换成bufferimage
        BufferedImage bi = ImageIO.read(templateFile);
        BufferedImage bis = ImageIO.read(targetFile);
        List<BufferedImage> images = new ArrayList<>();

        //图片输出到桌面
        pictureMap = VerifyImageUtil.pictureTemplatesCut(templateFile, targetFile, "png", "jpg");
        byte[] oriCopyImages = pictureMap.get("oriCopyImage");
        byte[] newImages = pictureMap.get("newImage");

        int x_axie = VerifyImageUtil.getX();
        int y_axie = VerifyImageUtil.getY();
        //把图片保存到 list中
        images.add(bis);
        images.add(bis);
        return  pictureMap;
    }
}
