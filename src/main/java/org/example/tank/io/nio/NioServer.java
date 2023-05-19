package org.example.tank.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/18 15:23
 * Version 1.0
 * @description
 **/
public class NioServer {
    public static void main(String[] args) throws IOException {
        // 带了channel基本上都是非阻塞的
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.socket().bind(new InetSocketAddress("localhost",8888));
        channel.configureBlocking(false);

        System.out.printf("nio server start ,listen on :",channel.getLocalAddress());

        Selector open = Selector.open();
        channel.register(open, SelectionKey.OP_ACCEPT);

        while (true){
            open.select();
            Set<SelectionKey> selectionKeys = open.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                iterator.remove();
                handle(next);
            }
        }
    }

    // BypteBuffer 只有一个指针，无论是写还是读，就是靠一个指针来回移动
    // Netty BypteBufa 有两个指针，一个读，一个写，零拷贝
    private static void handle(SelectionKey key) {
        if (key.isAcceptable()){
            try {
                ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                SocketChannel accept = channel.accept();
                accept.configureBlocking(false);
                channel.register(key.selector(), SelectionKey.OP_READ);
            }catch (IOException e) {
                e.printStackTrace();
            }finally {

            }
        }else if (key.isReadable()) {
            SocketChannel sc = null;
            try{
                sc = (SocketChannel) key.channel();
                ByteBuffer buff = ByteBuffer.allocate(512);
                buff.clear();
                int len = sc.read(buff);

                if (len != -1) {
                    System.out.println(new String(buff.array(),0,len));
                }
                ByteBuffer bufferToWrite = ByteBuffer.wrap("HelloClient".getBytes());
                sc.write(bufferToWrite);
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                if (sc != null) {
                    try {
                        sc.close();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
