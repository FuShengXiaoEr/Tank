package org.example.tank.netty;

import java.io.*;
import java.net.Socket;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/18 23:00
 * Version 1.0
 * @description
 **/
public class NettyClient {
    public static void main(String[] args) throws IOException {
        Socket s =new Socket("localhost",8888);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        bw.write("mashibing");
        bw.flush();
        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String str = reader.readLine();
        System.out.printf(str);
        bw.close();

    }
}
