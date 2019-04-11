package cn.crm.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ID生成器
 */
public class IdGenerator {
    public static String idGen() {
        Date  date = new Date() ;
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyyMMdd");
        String result = CoreUtils.randomString(5,true);
        String res = sdf.format(date) + result;
        return res;
    }

    public static void main(String[] args) {
        String a = IdGenerator.idGen();
        System.out.println(a);
    }
}
