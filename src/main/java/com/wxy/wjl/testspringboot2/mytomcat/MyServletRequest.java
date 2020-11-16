package com.wxy.wjl.testspringboot2.mytomcat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * function: 自定义Request相当于HttpServletRequest
 */
public class MyServletRequest {

    // 请求方式
    private String method;
    // 请求URL
    private String url;
    // 携带参数
    private String[] paramArray;

    public MyServletRequest(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String[] split = bufferedReader.readLine().split(" ");
            if (split.length == 3) {
                this.method = split[0];
                String allUrl = split[1];
                if (allUrl.contains("?")) {
                    this.url = allUrl.substring(0, allUrl.indexOf("?"));
                    String params = allUrl.substring(allUrl.indexOf("?")+1);
                    paramArray = params.split("&");
                } else {
                    this.url = allUrl;
                }
                if (allUrl.endsWith("ico")) {
                    return;
                }
            }

            // 注：split[2] 是 协议：HTTP/1.1
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String[] getParamArray() {
        return paramArray;
    }

}