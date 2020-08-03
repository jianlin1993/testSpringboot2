package com.wxy.wjl.testspringboot2.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 安全扫描问题修复  增加请求头
 */
public class HttpHeadFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest httpRequest, ServletResponse httpResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletResponse response=(HttpServletResponse)httpResponse;
        response.setHeader("X-Frame-Options","SAMEORIGIN");
        response.setHeader("Strict-Transport-Security","max-age=600; includeSubDomains; always");
        response.setHeader("X-Content-Type-Options","nosniff");
        response.setHeader("X-XSS-Protection","1; mode=block");

        filterChain.doFilter(httpRequest,httpResponse);
    }

}