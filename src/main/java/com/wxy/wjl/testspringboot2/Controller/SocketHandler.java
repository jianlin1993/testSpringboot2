package com.wxy.wjl.testspringboot2.Controller;


import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import com.wxy.wjl.testspringboot2.config.SocketPool;

import static com.wxy.wjl.testspringboot2.config.SocketPool.add;
import static com.wxy.wjl.testspringboot2.config.SocketPool.remove;


/**“Socket是应用层与TCP/IP协议族通信的中间软件抽象层，它是一组接口，提供一套调用TCP/IP协议的API。在设计模式中，Socket其实就是一个门面模式，它把复杂的TCP/IP协议族隐藏在Socket接口后面，对用户来说，一组简单的接口就是全部，让Socket去组织数据，以符合指定的协议。”
 * Socket操作处理类
 */
@Slf4j
public class SocketHandler{


    /**
     * 将连接的Socket注册到Socket池中
     * @param socket
     * @return
     */
    public static ClientSocket register(Socket socket){
        ClientSocket clientSocket = new ClientSocket();
        clientSocket.setSocket(socket);
        try {
            clientSocket.setInputStream(new DataInputStream(socket.getInputStream()));
            clientSocket.setOutputStream(new DataOutputStream(socket.getOutputStream()));
            byte[] bytes = new byte[1024];
            clientSocket.getInputStream().read(bytes);
            clientSocket.setKey(new String(bytes, "utf-8"));
            add(clientSocket);
            return clientSocket;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向指定客户端发送信息
     * @param clientSocket
     * @param message
     */
    public static void sendMessage(ClientSocket clientSocket, String message){
        try {
            clientSocket.getOutputStream().write(message.getBytes("utf-8"));
            //clientSocket.getOutputStream().writeUTF(message);
        } catch (IOException e) {
            log.error("发送信息异常：{}", e);
            close(clientSocket);
        }
    }

    /**
     * 获取指定客户端的上传信息
     * @param clientSocket
     * @return
     */
    public static String onMessage(ClientSocket clientSocket){
        byte[] bytes = new byte[1024];
        try {
            clientSocket.getInputStream().read(bytes);
            String msg = new String(bytes, "utf-8");
            return msg;
        } catch (IOException e) {
            e.printStackTrace();
            close(clientSocket);
        }
        return null;
    }

    /**
     * 指定Socket资源回收
     * @param clientSocket
     */
    public static void close(ClientSocket clientSocket){
        log.info("进行资源回收");
        if (clientSocket != null){
            log.info("开始回收socket相关资源，其Key为{}", clientSocket.getKey());
            remove(clientSocket.getKey());
            Socket socket = clientSocket.getSocket();
            try {
                socket.shutdownInput();
                socket.shutdownOutput();
            } catch (IOException e) {
                log.error("关闭输入输出流异常，{}", e);
            }finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log.error("关闭socket异常{}", e);
                }
            }
        }
    }


    /**
     * 发送数据包，判断数据连接状态
     * @param clientSocket
     * @return
     */
    public static boolean isSocketClosed(ClientSocket clientSocket){
        try {
            clientSocket.getSocket().sendUrgentData(1);
            return false;
        } catch (IOException e) {
            return true;
        }
    }

}
