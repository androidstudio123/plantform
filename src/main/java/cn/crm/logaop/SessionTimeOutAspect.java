package cn.crm.logaop;

import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义登录过期aop
 */
//@Aspect
//@Component
@Slf4j
public class SessionTimeOutAspect {
    public SessionTimeOutAspect() {
    }
    @Pointcut("execution(public * cn.crm.controller..*.*(..))&&!execution(* cn.crm.controller..*.login*(..))")
    public void controllerPointcut(){
    }
    @Pointcut("controllerPointcut()")
    public void sessionTimeOutPointcut(){
    }
    @Around("sessionTimeOutPointcut()")
    public Object sessionTimeOutAdvice(ProceedingJoinPoint pjp) throws IOException {
        Object result = null;
        String targetName = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();
        System.out.println("----------------执行方法-----------------");
        System.out.println("类名："+targetName+" 方法名："+methodName);
        HttpServletResponse response = null;
        for (Object param : pjp.getArgs()) {
            if (param instanceof HttpServletResponse) {
                response = (HttpServletResponse) param;
            }
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        if(session.getAttribute("sysAdminEntity")!=null){
            try {
                result = pjp.proceed();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return result;
        } else{
            System.out.println("Session已超时，正在跳转回登录页面");
            this.responseOutWithJson(response,new ResultData(ResultCode.OUTDATE.getCode(),false,ResultCode.OUTDATE.getMessage(),null));
        }
        return result;


    }


    /**
     * 以JSON格式输出
     * @param response
     */
    protected void responseOutWithJson(HttpServletResponse response,
                                       Object responseObject) {
        //将实体对象转换为JSON Object转换
        JSONObject responseJSONObject = JSONObject.fromObject(responseObject);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(responseJSONObject.toString());
            log.debug("返回是\n");
            log.debug(responseJSONObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
