package com.wxy.wjl.testspringboot2.job.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @Description:
 * @Author: liAo
 * @Date: 2020/2/8
 * @Modified by :
 * @Modified Description :
 */
public class UrlUtil {
    /**
     * @description: 约束工具类
     * @author: liAo
     * @date: 2020/2/8
     * @param:
     * @return:
     * @throws:
     * @modified by:
     * @modified Description:
    */
    private UrlUtil(){}

    /**
     * @description: 截取URL中的？之后的部分 参数
     * @author: Leo
     * @date: 2020/4/9
     * @param:
     * @return:
     * @throws:
     * @modified by:
     * @modified description:
    */
    public static HashMap<String, String> getParameter(String strUrl) throws UnsupportedEncodingException {
        String strAllParam = null;
        String[] arrSplit;
        HashMap<String, String> map = new HashMap();

        strUrl = strUrl.trim();
        arrSplit = strUrl.split("[?]");
        if (strUrl.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }

        // 无参数
        if (StringUtils.isBlank(strAllParam)) {
            return map;
        }
        arrSplit = strAllParam.split("&");
        for (int i = 0;i < arrSplit.length; i++) {
            String tmp = arrSplit[i];
            if (StringUtils.isBlank(tmp)) {
                continue;
            }
            String[] tmpSplit = tmp.split("=");
            if (tmpSplit.length != 2 || StringUtils.isBlank(tmpSplit[0])) {
                continue;
            }
            map.put(tmpSplit[0], URLEncoder.encode(tmpSplit[1], "UTF-8"));
        }

        return map;
    }

    /**
     * @description: 获取主机ip
     * @author: liAo
     * @date: 2020/2/8
     * @param:
     * @return:
     * @throws:
     * @modified by:
     * @modified Description:
    */
    public static String getHost(String reqUrl, Logger msgLog) throws MalformedURLException {
        msgLog.info("Try to get: " + reqUrl + " host name");
        java.net.URL  url = new  java.net.URL(reqUrl);
        return url.getHost();
    }

    /**
     * @description: 获取主机端口
     * @author: liAo
     * @date: 2020/2/9
     * @param:
     * @return:
     * @throws:
     * @modified by:
     * @modified Description:
     */
    public static int getPort(String reqUrl, Logger msgLog) {
        java.net.URL  url = null;
        try {
            url = new  java.net.URL(reqUrl);
        } catch (MalformedURLException e) {
            msgLog.error("Try to get: " + reqUrl + " host name");
            msgLog.error("Failed to get IP address，", e);
        }
        return url.getPort();
    }

    /**
     * @description: 获取主机 ip和端口
     * @author: liAo
     * @date: 2020/2/9
     * @param:
     * @return:
     * @throws:
     * @modified by:
     * @modified Description:
     */
    public static String getHostPort(String reqUrl, Logger msgLog) {
        java.net.URL  url = null;
        try {
            url = new  java.net.URL(reqUrl);
        } catch (MalformedURLException e) {
            msgLog.error("Try to get: " + reqUrl + " host name");
            msgLog.error("Failed to get IP address and port，", e);
        }
        return url.getHost() + ":" +  url.getPort();
    }

    /**
     * @description: 获取url
     * @author: liAo
     * @date: 2020/2/9
     * @param:
     * @return:
     * @throws:
     * @modified by:
     * @modified Description:
     */

    public static String getProtocolHostPort(String reqUrl, Logger msgLog) throws MalformedURLException {
        msgLog.info("Try to get: " + reqUrl + " host name");
        java.net.URL  url = new  java.net.URL(reqUrl);
        return url.getProtocol() + "://" + url.getHost() + ":" +  url.getPort();
    }

    /**
     * @description: 测试类
     * @author: liAo
     * @date: 2020/2/9
     * @param:
     * @return:
     * @throws:
     * @modified by:
     * @modified Description:
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
//        String reqUrl = "http://localhost:10688/ops/";
        String reqUrl = "http://localhost:9084/ops/";
        java.net.URL  url = null;
        try {
            url = new  java.net.URL(reqUrl);
        } catch (MalformedURLException e) {
            System.out.println(e);
        }
        String t = url.getProtocol() + "://" + url.getHost() + ":" +  url.getPort();
        System.out.println(t);

        // 测试 getParameter
        reqUrl = "http://10.0.0.5:8182/egw/sendmessage/sdpLoginOrRefreshToken.dom";
        System.out.println("reqUrl=[" + reqUrl + "] " +  "getParameter(reqUrl))=[" + getParameter(reqUrl) + "]");

        reqUrl = "http://10.0.0.5:8382/ccs/timer/ecInfoProc.dom?ec_sce_id=13";
        System.out.println("reqUrl=[" + reqUrl + "] " +  "getParameter(reqUrl))=[" + getParameter(reqUrl) + "]");
    }

}
