package com.wxy.wjl.testspringboot2.socket;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.wxy.wjl.testspringboot2.socket.SocketHandler.register;

@Slf4j
@Data
@Component
@NoArgsConstructor
/**
 * 启动socket监听
 */
public class SocketServer {

   // @Value("${port}")
    private Integer port;
    private boolean started;
    private ServerSocket serverSocket;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args){
        new SocketServer().start(8068);
    }

    public void start(){
        start(null);
    }



    public void start(Integer port){
        log.info("port: {}, {}", this.port, port);
        try {
            serverSocket =  new ServerSocket(port == null ? this.port : port);
            started = true;
            log.info("Socket服务已启动，占用端口： {}", serverSocket.getLocalPort());
        } catch (IOException e) {
            log.error("端口冲突,异常信息：{}", e);
            System.exit(0);
        }

        while (started){
            try {
                Socket socket = serverSocket.accept();
                socket.setKeepAlive(true);
                ClientSocket register = register(socket);
                log.info("客户端已连接，其Key值为：{}", register.getKey());

                SocketHandler.sendMessage(register, "success-aaaaa.");
                if (register != null){
                    executorService.submit(register);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}