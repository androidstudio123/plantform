package cn.crm.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

@Slf4j
public class FTPUtil {

    public String hostname = PropertiesUtil.getValue("FTP_ADDRESS");
    public Integer port = Integer.parseInt(PropertiesUtil.getValue("FTP_PORT"));
    public String username = PropertiesUtil.getValue("FTP_USERNAME");
    public String password = PropertiesUtil.getValue("FTP_PASSWORD");
    public String basepath = PropertiesUtil.getValue("FTP_BASEPATH");
    public FTPClient ftpClient = null;

    /**
     * 上传文件
     * @param pathname ftp服务保存地址
     * @param fileName 上传到ftp的文件名
     * @param inputStream 输入文件流
     * @return
     */
    public boolean uploadFile( String pathname, String fileName,InputStream inputStream){
        boolean flag = false;
        try{
            log.info("{=====================================}","开始上传文件");
            initFtpClient();
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            //
            System.out.println(pathname);
            boolean b = ftpClient.changeWorkingDirectory(pathname);
            //文件夹不存在则创建
            if(!b){
                CreateDirecroty(pathname);
            }
            ftpClient.setBufferSize(1024*2);
            boolean uploadResult = ftpClient.storeFile(fileName, inputStream);
            if(uploadResult){
                flag = true;
                log.info("{=====================================}","上传文件成功");
            }
            log.info("{=====================================}","上传文件失败，请重新上传");
            inputStream.close();
            ftpClient.logout();
        }catch (Exception e) {
            log.info("{=====================================}","上传文件失败");
            flag =  false;
            e.printStackTrace();
        }finally{
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return flag;
    }
    //改变目录路径
    public boolean changeWorkingDirectory(String directory) {
        boolean flag = true;
        try {
            flag = ftpClient.changeWorkingDirectory(directory);
            if (flag) {
                 System.out.println("进入文件夹" + directory + " 成功！");
            } else {
                System.out.println("进入文件夹" + directory + " 失败！开始创建文件夹");

            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return flag;
    }


    //创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
    public boolean CreateDirecroty(String remote) throws IOException {
        boolean success = true;
        String directory = remote + "/";
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!"/".equalsIgnoreCase(directory) && !changeWorkingDirectory(new String(directory))) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            String path = "";
            String paths = "";
            while (true) {
                String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
                path = path + "/" + subDirectory;
                if (!existFile(path)) {
                    if (makeDirectory(subDirectory)) {
                        changeWorkingDirectory(subDirectory);
                    } else {
                        System.out.println("创建目录[" + subDirectory + "]失败");
                        changeWorkingDirectory(subDirectory);
                    }
                } else {
                    changeWorkingDirectory(subDirectory);
                }

                paths = paths + "/" + subDirectory;
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        return success;
    }
    //判断ftp服务器文件是否存在
    public boolean existFile(String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }
    //创建目录
    public boolean makeDirectory(String dir) {
        boolean flag = true;
        try {
            flag = ftpClient.makeDirectory(dir);
            if (flag) {
                System.out.println("创建文件夹" + dir + " 成功！");

            } else {
                System.out.println("创建文件夹" + dir + " 失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 初始化ftp服务器
     */
    public void initFtpClient() {
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");
        try {
            System.out.println("connecting...ftp服务器:"+this.hostname+":"+this.port);
            ftpClient.connect(hostname, port); //连接ftp服务器
            ftpClient.login(username, password); //登录ftp服务器
            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
            if(!FTPReply.isPositiveCompletion(replyCode)){
                System.out.println("connect failed...ftp服务器:"+this.hostname+":"+this.port);
            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws  IOException {
        String basepath = PropertiesUtil.getValue("FTP_BASEPATH");

        FTPUtil ftp =new FTPUtil();
        File file = new File("D:\\1.jpg");
        String fileNames  = file.getName();
        String type = fileNames.indexOf(".") != -1 ? fileNames.substring(fileNames.lastIndexOf(".") + 1, fileNames.length()) : null;
        String videoName = IdGenerator.idGen()+"."+type;
        System.out.println("filename 为："+videoName);
        InputStream is = new FileInputStream(file);
        System.out.println(is);
        boolean res = ftp.uploadFile(basepath, videoName, is);
        System.out.println("文件上传结果："+res);
    }
	
	
}
