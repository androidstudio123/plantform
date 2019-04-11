//package cn.crm.httpsession;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import cn.crm.util.StringUtils;
//import org.springframework.session.web.http.CookieHttpSessionIdResolver;
//import org.springframework.session.web.http.CookieSerializer;
//import org.springframework.session.web.http.CookieSerializer.CookieValue;
//import org.springframework.session.web.http.DefaultCookieSerializer;
//import org.springframework.session.web.http.HttpSessionIdResolver;
//
//
//public class CookieHeaderHttpSessionIdResolver implements HttpSessionIdResolver   {
//
//	private static final String WRITTEN_SESSION_ID_ATTR = CookieHttpSessionIdResolver.class
//			.getName().concat(".WRITTEN_SESSION_ID_ATTR");
//
//	private CookieSerializer cookieSerializer = new DefaultCookieSerializer();
//
//
//	private String headerName = "token";
//
//	@Override
//	public List<String> resolveSessionIds(HttpServletRequest request) {
//		List<String> readCookieValues = this.cookieSerializer.readCookieValues(request);
////		if(readCookieValues.size() == 0) {
//			String header = request.getHeader(headerName);
//			if(StringUtils.isNotBlank(header))
//				readCookieValues.add(header);
////		}
//		return readCookieValues;
//	}
//
//	@Override
//	public void setSessionId(HttpServletRequest request, HttpServletResponse response,
//                             String sessionId) {
//		if (sessionId.equals(request.getAttribute(WRITTEN_SESSION_ID_ATTR))) {
//			return;
//		}
//		request.setAttribute(WRITTEN_SESSION_ID_ATTR, sessionId);
//		this.cookieSerializer
//				.writeCookieValue(new CookieValue(request, response, sessionId));
//	}
//
//	@Override
//	public void expireSession(HttpServletRequest request, HttpServletResponse response) {
//		this.cookieSerializer.writeCookieValue(new CookieValue(request, response, ""));
//	}
//
//	/**
//	 * Sets the {@link CookieSerializer} to be used.
//	 *
//	 * @param cookieSerializer the cookieSerializer to set. Cannot be null.
//	 */
//	public void setCookieSerializer(CookieSerializer cookieSerializer) {
//		if (cookieSerializer == null) {
//			throw new IllegalArgumentException("cookieSerializer cannot be null");
//		}
//		this.cookieSerializer = cookieSerializer;
//	}
//
//
//}
