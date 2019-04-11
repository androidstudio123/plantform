package cn.crm.util.slideVerification.safety.smmetric;


import cn.crm.util.slideVerification.safety.nusum.Base64Util;

/**
 * DES加密工具
 */
public class DESUtil extends BaseSmmetric {

    /**
     * 加密
     * 将加密后的内容转换成base64
     * @param source  需要加密的字符串
     * @return
     * @throws Exception
     */
    public static String encrypt(String source) throws Exception {
        setType("DES");
        byte[] endSource = getEncrypt(source);
        return Base64Util.byteToBase64(endSource);
    }

    /**
     * 解密
     * @param source 需要解密的base64
     * @return
     * @throws Exception
     */
    public static String decrypt(String source) throws Exception {
        setType("DES");
        byte[] bytes = getDecrypt(source);
        return new String(bytes);
    }

}
