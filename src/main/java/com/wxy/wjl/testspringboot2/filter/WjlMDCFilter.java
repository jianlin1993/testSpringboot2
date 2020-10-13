package com.wxy.wjl.testspringboot2.filter;

import com.wxy.wjl.testspringboot2.utils.MDCUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * MDC ( Mapped Diagnostic Contexts )，它是一个线程安全的存放诊断日志的容器。
 * 在filter中添加每个请求的唯一流水来跟踪日志
 * 他是使用ThreadLocal实现的 线程独立的变量
 * final ThreadLocal<Map<String, String>> copyOnThreadLocal = new ThreadLocal();
 */
public class WjlMDCFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest httpRequest, ServletResponse httpResponse, FilterChain filterChain)
            throws IOException, ServletException {
        MDCUtils.setTraceId();

        filterChain.doFilter(httpRequest,httpResponse);
    }
}
