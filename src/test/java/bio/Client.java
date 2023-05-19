package bio;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/18 14:29
 * Version 1.0
 * @description
 **/
public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost",8888);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("hello");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
