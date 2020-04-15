package com.wxy.wjl.testspringboot2.Controller;



import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static com.wxy.wjl.testspringboot2.Controller.SocketHandler.close;
import static com.wxy.wjl.testspringboot2.Controller.SocketHandler.isSocketClosed;


/**
 * @author zhoujian
 * 自定义封装的连接的客户端
 */
@Slf4j
@Data
public class ClientSocket implements Runnable{

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private String key;
    private String message;

    @Override
    public void run() {
        //每5秒进行一次客户端连接，判断是否需要释放资源
        while (true){
            try {
                TimeUnit.SECONDS.sleep(5);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isSocketClosed(this)){
                log.info("客户端已关闭,其Key值为：{}", this.getKey());
                //关闭对应的服务端资源
                close(this);
                break;
            }
        }
    }
}
