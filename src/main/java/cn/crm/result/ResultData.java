package cn.crm.result;

import lombok.Data;

/**
 * 统一返回结果
 * @author  nhy
 */
@Data
public class ResultData {
    private  Integer code;
    private boolean flag;
    private String message;
    private Object data;
    private Integer total;
    public ResultData() {
    }

    public ResultData(Integer code, boolean flag, String message) {
        this.code = code;
        this.flag = flag;
        this.message = message;
    }

    public ResultData(Integer code, boolean flag, String message, Object data) {
        this.code = code;
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public ResultData(Integer code,boolean flag,String message,Object data,Integer total){
        this.code = code;
        this.flag = flag;
        this.message = message;
        this.data = data;
        this.total = total;
    }
}
