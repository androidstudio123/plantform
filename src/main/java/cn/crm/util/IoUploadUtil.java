package cn.crm.util;

import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.reflections.Reflections.log;

/**
 * @author MYZ
 * @version V1.0
 * @Description:
 * @Package cn.crm.util
 * @date 2019/4/3 16:47
 */
public class IoUploadUtil {

    public static String upload(MultipartFile file, String path) {
        if (cn.crm.util.StringUtils.isBlank(file.getOriginalFilename())) {
            return null;
        }
        InputStream is = null;
        //源文件类型
        String sourceFileName = file.getOriginalFilename();
        String type = sourceFileName.indexOf(".") != -1 ? sourceFileName.substring(sourceFileName.lastIndexOf(".") + 1, sourceFileName.length()) : null;
        if (!type.equalsIgnoreCase("jpg") && !type.equalsIgnoreCase("png") && !type.equalsIgnoreCase("gif") && !type.equalsIgnoreCase("jpeg")) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String format = sdf.format(new Date());
        //获取项目路径
        File directory = new File("");//参数为空
        String author = directory.getAbsolutePath();
        //保存文件名和路径
        String saveName = IdGenerator.idGen() + "." + type;
        //相对路径
        String avatar_images = path + format;
        //图片存放路径
//        String targetPath = author + "/src/main/resources/static" + avatar_images;
        String targetPath = author + "/static" + avatar_images;
        //获取文件夹路径
        File file1 = new File(targetPath);
        //如果文件夹不存在则创建
        if (!file1.exists() && !file1.isDirectory()) {
            boolean mkdir = file1.mkdirs();
        }
        try {
            is = file.getInputStream();
            FileOutputStream fos = new FileOutputStream(targetPath + "/" + saveName);
            byte[] b = new byte[1024];
            int length;
            while ((length = is.read(b)) != -1) {
                fos.write(b, 0, length);
            }
            is.close();
            fos.close();
            String url = avatar_images + "/" + saveName;
            return url;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }


    }
}
