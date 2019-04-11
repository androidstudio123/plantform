package cn.crm.util.slideVerification.validate;

import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 测试生成的滑快
 *
 * @author  xiaoning
 */
public class TestGenerator {

    public List<BufferedImage> generator() throws  Exception {
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
        BufferedImage bis = ImageIO.read(templateFile);
        List<BufferedImage> images = new ArrayList<>();
        //把图片保存到 list中
        images.add(bi);
        images.add(bis);

        //图片输出到桌面
        pictureMap = VerifyImageUtil.pictureTemplatesCut(templateFile, targetFile, "png", "jpg");
        byte[] oriCopyImages = pictureMap.get("oriCopyImage");
        byte[] newImages = pictureMap.get("newImage");
        FileOutputStream fout = new FileOutputStream("C:/Users/Administrator/Desktop/oriCopyImage.png");
        //将字节写入文件
        try {
            fout.write(oriCopyImages);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fout.close();

        FileOutputStream newImageFout = new FileOutputStream("C:/Users/Administrator/Desktop/newImage.png");
        //将字节写入文件
        newImageFout.write(newImages);
        newImageFout.close();
        return  images;
    }


    public static void main(String[] args)  throws  Exception{
        new TestGenerator().generator();
        int x = VerifyImageUtil.getX();
        System.out.println(x);
    }
}
