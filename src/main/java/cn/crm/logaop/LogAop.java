package cn.crm.logaop;


import cn.crm.entity.SysLogEntity;
import cn.crm.service.sys.SysLogService;
import cn.crm.util.AdminEntityUtil;
import cn.crm.util.CommonUtil;
import org.apache.catalina.session.StandardSessionFacade;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class LogAop {
	private static final Logger log = LoggerFactory.getLogger(LogAop.class);

	@Autowired
	private SysLogService logService;

	/**
	 * Controller 拦截点,前置通知
	 */
	@Pointcut("@annotation(cn.crm.logaop.SystemLog)")
	public void beforeController() {
	}

	/**
	 * 操作异常记录
	 * @param point
	 * @param e
	 * @author hzg
	 * @date 2018.10.12 10点00分
	 * @version 1.0
	 */
	@AfterThrowing(pointcut = "beforeController()", throwing = "e")
	public void doAfterThrowing(JoinPoint point, Throwable e) {
		HttpServletRequest request = CommonUtil.getHttpRequest();
		String requestURI = request.getRequestURI();
		SysLogEntity logForm = new SysLogEntity();
		Map<String, Object> map = null;
		String username = null;
		String ip = null;
		StringBuffer params = null;
		if (!"/login".equals(requestURI)) {
			try {
				Object[] args = point.getArgs();
				if (args.length > 0) {
					params = new StringBuffer();
					for (Object object : args) {
						if (!(object instanceof StandardSessionFacade || object instanceof ShiroHttpServletRequest)) {
							if (params.toString().length() == 0) {
								params.append(String.valueOf(object));
							} else {
								params.append(",").append(String.valueOf(object));
							}
						}
					}
				}
			} catch (Exception e1) {
				params = new StringBuffer("无法获取方法参数");
			}
		}
		try {
			ip = CommonUtil.toIpAddr(request);
		} catch (Exception ee) {
			ip = "无法获取登录用户Ip";
		}
		try {
			map = getControllerMethodDescription(point);
			// tudo 登录名
			username = AdminEntityUtil.getAdminFromSession().getAdmin_name();
			if (StringUtil.isEmpty(username)) {
				username = "无法获取登录用户信息！";
			}
		} catch (Exception ee) {
			username = "无法获取登录用户信息！";
		}
		logForm.setLog_ip(requestURI);
		logForm.setLog_user(username);
		logForm.setLog_fun(String.valueOf(map.get("module")));
		logForm.setLog_method("执行方法:-->" + map.get("methods"));
		logForm.setLog_param(params == null ? "" : params.toString());
		logForm.setLog_result("执行方法:-->" + e);
		logForm.setLog_time(new Date());
		try {
			logService.save(logForm);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * @After在方法执行之后执行
	 */
	@After("beforeController()")
	public void doAfter(JoinPoint point) {
		HttpServletRequest request = CommonUtil.getHttpRequest();
		String requestURI = request.getRequestURI();
//		Object result = null;
//		// 执行方法名
//		String methodName = point.getSignature().getName();
//		String className = point.getTarget().getClass().getSimpleName();
		SysLogEntity logForm = new SysLogEntity();
		Map<String, Object> map = null;
		String username = null;
		StringBuffer params = null;
		Long start = 0L;
		Long end = 0L;
		Long time = 0L;
		String ip = null;

		if (!"/login".equals(requestURI)) {
			try {
				Object[] args = point.getArgs();
				if (args.length > 0) {
					params = new StringBuffer();
					for (Object object : args) {
						if (!(object instanceof StandardSessionFacade || object instanceof ShiroHttpServletRequest)) {
							if (params.toString().length() == 0) {
								params.append(String.valueOf(object));
							} else {
								params.append(",").append(String.valueOf(object));
							}
						}
					}
				}
			} catch (Exception e1) {
				params = new StringBuffer("无法获取方法参数");
			}
		}
		try {
			ip = CommonUtil.toIpAddr(request);
		} catch (Exception e) {
			ip = "无法获取登录用户Ip";
		}
		try {
			// 登录名
			username = AdminEntityUtil.getAdminFromSession().getAdmin_name();;
			if (StringUtil.isEmpty(username)) {
				username = "无法获取登录用户信息！";
			}
		} catch (Exception e) {
			username = "无法获取登录用户信息！";
		}
		// 当前用户
		try {
			map = getControllerMethodDescription(point);
			// 执行方法所消耗的时间
			start = System.currentTimeMillis();
			// result = point.proceed();
			end = System.currentTimeMillis();
			time = end - start;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		try {
			logForm.setLog_ip(requestURI);
			logForm.setLog_user(username);
			logForm.setLog_fun(String.valueOf(map.get("module")));
			logForm.setLog_method("执行方法:-->" + map.get("methods"));
			logForm.setLog_param(params == null ? "" : params.toString());
			logForm.setLog_result(String.valueOf(map.get("result")));
			logForm.setLog_time(new Date());
			logService.save(logForm);
			// *========控制台输出=========*//
//			System.out.println("=====通知开始=====");
//			System.out.println("请求方法:" + className + "." + methodName + "()");
//			System.out.println("参数值：" + (params == null ? "" : params.toString()));
//			System.out.println("方法描述:" + map);
//			System.out.println("请求IP:" + ip);
//			System.out.println("=====通知结束=====");
		} catch (Exception e) {
			// 记录本地异常日志
			log.error("====通知异常====");
			log.error("异常信息:", e);
		}
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 *
	 * @param joinPoint 切点
	 * @return 方法描述
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					map.put("module", method.getAnnotation(SystemLog.class).module());
					map.put("methods", method.getAnnotation(SystemLog.class).methods());
					String de = method.getAnnotation(SystemLog.class).description();
					if (StringUtil.isEmpty(de))
						de = "执行成功!";
					map.put("description", de);
					break;
				}
			}
		}
		return map;
	}

}
