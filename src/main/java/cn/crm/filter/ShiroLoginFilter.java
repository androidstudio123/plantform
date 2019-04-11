package cn.crm.filter;

import cn.crm.result.ResultData;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;



public class ShiroLoginFilter extends FormAuthenticationFilter {

	/**
	 * 在访问controller前判断是否登录，返回json，不进行重定向。
	 * 
	 * @param request
	 * @param response
	 * @return true-继续往下执行，false-该filter过滤器已经处理，不继续执行其他过滤器
	 * @throws Exception
	 */
	/**
	 * 这个方法是未登录需要执行的方法
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		
        HttpServletResponse httpResponse = (HttpServletResponse) response;
  
        Subject subject = getSubject(request, response);
        if (subject.getPrincipal() == null) {  
	        //设置响应头
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("application/json");
		//设置返回的数据
		ResultData resultData = new ResultData(20004, false, "用户未登录");
		PrintWriter out = httpResponse.getWriter();
		out.write(JSONObject.toJSONString(resultData));
		//刷新和关闭输出流
		out.flush();
		out.close();
        } else {  
        	//设置响应头
	    	httpResponse.setCharacterEncoding("UTF-8");
	    	httpResponse.setContentType("application/json");
	    	//设置返回的数据
	    	ResultData resultData = new ResultData(20005, false, "用户没有权限");
	    	//写回给客户端
	    	PrintWriter out = httpResponse.getWriter();
	    	out.write(JSONObject.toJSONString(resultData));
	    	//刷新和关闭输出流
	    	out.flush();
	    	out.close();
        }  
        return false;  
	}

	

}
