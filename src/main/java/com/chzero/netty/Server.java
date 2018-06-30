package com.chzero.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author CHZERO
 * @version 1.0
 * @date 2018-06-30 14:35
 * @email 827348260@qq.com
 * @description
 */
public class Server{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ServerSocket serverSocket;

    public Server(int port){
        try{
            this.serverSocket = new ServerSocket(port);
            this.logger.info("服务端启动成功. 端口[{}]", port);
        }catch (IOException e){
            this.logger.info("服务端启动失败");
            this.logger.info("失败日志 : {}", e.getMessage());
        }
    }

    public void start(){
       new Thread(Server.this::doStart).start();
    }

    private void doStart(){
        while (true){
            try{
                Socket client = this.serverSocket.accept();
                new ClientHandler(client).start();
            }catch (IOException e){
                this.logger.info("服务端异常");
                this.logger.info("异常日志 : {}", e.getMessage());
            }
        }
    }
}
