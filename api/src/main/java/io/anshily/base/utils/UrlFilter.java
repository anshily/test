package io.anshily.base.utils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UrlFilter implements Filter {

	public void destroy() {

	}
	
	public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
//		String message = request.getRequestURI();
		String message2 = request.getQueryString();
		// 后台头部和尾部不用过滤
			/*if (message != null || message.endsWith("/")){
				boolean b1 = message.endsWith("/");
				boolean b2 = message.endsWith("/commons/403.jsp");
				boolean b3 = message.endsWith("/commons/404.jsp");
				boolean b4 = message.endsWith("/commons/500.jsp");
				if(b1 || b2 || b3 || b4){
					filterChain.doFilter(req, res);
					return;
				}else {
					response.sendRedirect(request.getContextPath() + "/jsp/common/404.jsp");
					return;
				}
			}*/
			
			if(message2!=null){
				boolean ba = message2.indexOf("<")>0;
				boolean bb = message2.indexOf(">")>0;
				boolean bc = message2.indexOf("\"")>0;
				boolean bd = message2.indexOf("\\")>0;
				boolean be = message2.indexOf("/")>0;
				boolean bf = message2.indexOf("{")>0;
				boolean bl = message2.indexOf("}")>0;
				boolean bh = message2.indexOf(";")>0;
				boolean bi = message2.indexOf("(")>0;
				boolean bj = message2.indexOf(")")>0;
				boolean bk = message2.indexOf("+")>0;
				boolean bm = message2.indexOf("'")>0;
				boolean bn = message2.indexOf("..")>0;
				if(ba || bb || bc || bd || be || bf || bh || bi || bj || bk || bl || bm || bn || WebUtils.sqlValidate(message2)){
					response.sendRedirect(request.getContextPath() + "/jsp/common/404.jsp");
					return;
				}else {
					filterChain.doFilter(req, res);
					return;
				}
			}else {
				filterChain.doFilter(req, res);
				return;
			}
				
	}
	
	public void init(FilterConfig arg0) throws ServletException {

	}
	
}
