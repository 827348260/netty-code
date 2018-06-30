package com.chzero.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author CHZERO
 * @version 1.0
 * @date 2018-06-30 14:46
 * @email 827348260@qq.com
 * @description
 */
public class ClientHandler{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Socket socket;

    private static final int MAX_DATA_SIZE = 1024;

    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    public void start(){
        new Thread(ClientHandler.this::doStart).start();
    }

    private void doStart(){
        try{
            InputStream inputStream = this.socket.getInputStream();
            while (true){
                byte[] data = new byte[MAX_DATA_SIZE];
                int length;
                while ((length = inputStream.read(data)) != -1){
                    String message = new String(data, 0, length);
                    this.logger.info("客户端传来信息: {}", message);
                    this.socket.getOutputStream().write(data);
                }
            }
        }catch (IOException e){
            this.logger.info("客户端异常");
            this.logger.info("异常日志 : {}", e.getMessage());
        }
    }

}
