package cn.crm.util.slideVerification.validate;

public class StringUtils {
    public static boolean isEmpty(String templateFiletype) {
        return templateFiletype == null || templateFiletype.equals("");
    }
    public static boolean isNotEmpty(String  cs) {
        return !isEmpty(cs) &&  !"null".equals(cs);
    }
}
