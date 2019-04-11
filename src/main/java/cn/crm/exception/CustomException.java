package cn.crm.exception;

/**
 * 自定义异常
 */
public class CustomException  extends  RuntimeException{

    //无参构造方法
    public CustomException(){
        super();
    }

    //有参的构造方法
    public CustomException(String message){
        super(message);

    }






}
