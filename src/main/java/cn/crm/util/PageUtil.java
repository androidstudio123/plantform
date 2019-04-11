package cn.crm.util;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
/**
 * 
 * @ClassName:  PageUtil   
 * @Description:TODO 分页工具类  
 * @author: hzg 
 * @date: 2018年10月11日 上午9:34:08
 */
public class PageUtil {
	
	/**
	 * 
	 * @Title: startPage   
	 * @Description: TODO   查询list之前调用此方法，会自动分页   
	 * @return: void      
	 * @throws
	 */
	public static void startPage(){
	   	 HttpServletRequest request = CommonUtil.getHttpRequest();
	        Integer pageNum = CommonUtil.valueOf(request.getParameter("pageNum"), 1);
	        Integer pageSize = CommonUtil.valueOf(request.getParameter("pageSize"), 10);
	        PageHelper.startPage(pageNum, pageSize);
	        String orderField = request.getParameter("sort");
	        String orderDirection = request.getParameter("order");
	        if (StringUtil.isNotEmpty(orderField)) {
	            PageHelper.orderBy(orderField);
	            if (StringUtil.isNotEmpty(orderDirection)) {
	                PageHelper.orderBy(orderField + " " + orderDirection);
	            }
	        }
	   }

	/**
	 * 排序进行分页查询
	 * @param orderField   排序字段
	 * @param orderDirection  排序方式
	 */
	public static void startPage(String orderField,String orderDirection){
		HttpServletRequest request = CommonUtil.getHttpRequest();
		Integer pageNum = CommonUtil.valueOf(request.getParameter("pageNum"), 1);
		Integer pageSize = CommonUtil.valueOf(request.getParameter("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		if (StringUtil.isNotEmpty(orderField)) {
			PageHelper.orderBy(orderField);
			if (StringUtil.isNotEmpty(orderDirection)) {
				PageHelper.orderBy(orderField + " " + orderDirection);
			}
		}
	}

}
