package com.wxy.wjl.testspringboot2.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 构建可重复读的流
 */
public class InputStreamFactory {

    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private byte[]                buffer                = new byte[1024];

    public InputStreamFactory(InputStream input) throws IOException {
        int len;
        while ((len = input.read(buffer)) > -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        byteArrayOutputStream.flush();
    }

    public InputStream newInputStream() {
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    public static void main(String[] args) {
        InputStream inputStream=null;
        InputStreamFactory inputStreamFactory = null;
        try {
            inputStreamFactory = new InputStreamFactory(inputStream);
        } catch (IOException e) {
        }
    }

}
