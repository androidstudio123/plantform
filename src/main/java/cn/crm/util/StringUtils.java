package cn.crm.util;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
	
	
	 
	 public static boolean isNotEmpty(final CharSequence cs) {
	        return !isEmpty(cs) &&  !"null".equals(cs);
	 }
	 
	
     
}
