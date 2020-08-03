package com.wxy.wjl.testspringboot2.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 安全问题修复 XSS and CSRF
 */
@Slf4j
public class XssAndCsrfFilter implements Filter {

    private String[] verifyRefererArray = null;

    private String[] xssInvalidCharacterArray = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        verifyRefererArray=filterConfig.getInitParameter("refererConfig").split(",");
        xssInvalidCharacterArray=filterConfig.getInitParameter("xssInvalidCharacter").split(",");
    }
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest httpRequest, ServletResponse httpResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)httpRequest;
        HttpServletResponse response=(HttpServletResponse)httpResponse;

        if(!verifyReferer(request)){
            response.setStatus(400);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print("Suspected XSS attack!");
            return;
        }
        RequestWrapper requestWrapper = new RequestWrapper(request);
        if(!checkXss(requestWrapper)){
            response.setStatus(400);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print("Suspected CSRF attack!");
            return;
        }
        filterChain.doFilter(requestWrapper,httpResponse);
    }

    /**
     * 验证referer
     * @param request
     * @return
     */
    private boolean verifyReferer(HttpServletRequest request){

        String referer = request.getHeader("Referer");
        log.info("referer = " + referer);
        for (String verifyReferer : verifyRefererArray) {
            if (StringUtils.isEmpty(verifyReferer) || StringUtils.isEmpty(referer) || referer.trim().contains(verifyReferer)) {
                return true;
            }
        }
        log.info("Suspected CSRF attack! referer =  "+referer);
        return false;
    }

    /**
     * 检测非法字符
     * @param requestWrapper
     * @return
     */
    private boolean checkXss(RequestWrapper requestWrapper) throws IOException{

        String requestPra = requestWrapper.getBody();

        for(String xssInvalidChar:xssInvalidCharacterArray){
            if(StringUtils.isNotBlank(xssInvalidChar) && requestPra.contains(xssInvalidChar)){
                log.info("Suspected XSS attack! The request parameter contains illegal characters! parameter =  "+requestPra+" ,illegal character ="+xssInvalidChar);
                return false;
            }
        }
        return true;
    }

}
