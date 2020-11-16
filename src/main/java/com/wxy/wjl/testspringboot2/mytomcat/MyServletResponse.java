package com.wxy.wjl.testspringboot2.mytomcat;


import java.io.OutputStream;

/**
 *
 * function: 自定义容器Servlet父类
 */
public class MyServletResponse {

    private OutputStream outputStream;

    // 添加Response响应头
    public static final String RESPONSE_HEADER=
            "HTTP/1.1 200 \r\n"
                    + "Content-Type: text/html\r\n"
                    + "\r\n";

    public MyServletResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

}
