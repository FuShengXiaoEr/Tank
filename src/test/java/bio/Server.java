package bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/18 14:27
 * Version 1.0
 * @description
 **/
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket();
        socket.bind(new InetSocketAddress("localhost", 8888));
        boolean start = true;
        while (start) {
            Socket accept = socket.accept();
            new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                    String s = reader.readLine();
                    System.out.printf("server accept:" + s);
                    reader.close();
                    accept.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        socket.close();
    }
}
