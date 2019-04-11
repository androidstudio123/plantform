package cn.crm.logaop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
@Documented  
public @interface SystemLog {
	
	String module()  default "";  //模块名称 
	String methods()  default "";  //请求方法
    String description()  default "";  //描述
}
