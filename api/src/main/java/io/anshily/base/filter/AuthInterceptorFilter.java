package io.anshily.base.filter;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by skyADMIN on 16/7/4.
 */
@Component
@WebFilter
public class AuthInterceptorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        if (request.getMethod().equals("POST")){
            ServletRequest servletRequestWrapper = new ReprocessHttpServletRequest(request);
            chain.doFilter(servletRequestWrapper,res);
        }else {
            chain.doFilter(req,res);
        }
    }
    @Override
    public void init(FilterConfig filterConfig) {}
    @Override
    public void destroy() {}

}
