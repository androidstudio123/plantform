package cn.crm.exception;


import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



//@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


	/**
	 * 全局异常捕捉
	 * @param ex
	 * @return
	 */
    @ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public ResultData exceptionHandler(Exception ex) {
		if(ex instanceof UnauthorizedException){
//			log.error("未授权异常",ex.getMessage());
			return new ResultData(ResultCode.FORBIDDEN.getCode(),false,ResultCode.FORBIDDEN.getMessage());
		}
//		log.error("其他异常",ex.getMessage());
		return new ResultData(ResultCode.RETRY.getCode(),false,ResultCode.RETRY.getMessage());
	}

}