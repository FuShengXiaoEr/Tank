package org.example.nettycodec;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;
import org.example.nettychatroom.ClientFrame;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/19 21:34
 * Version 1.0
 * @description
 **/
public class Client {
    private Channel channel = null;
    public  void connect() {
        System.out.println("Client start……");
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    channel = socketChannel;
                    socketChannel.pipeline()
                            .addLast(new TaskMsgEncoder())
                            .addLast(new MyHandler());
                }
            });
            ChannelFuture future = bootstrap.connect("localhost", 8888).sync();
            System.out.println("connect to server");
            // 阻塞，等待关闭
            future.channel().closeFuture().sync();
            System.out.println("interrupt");
        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public void send(String text) {
        channel.writeAndFlush(Unpooled.copiedBuffer(text.getBytes()));
    }

    public void closeConnect() {
        send("__bye__");
        channel.close();
    }

    static class MyHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ByteBuf buf = null;
            try {
                buf = (ByteBuf) msg;
                byte[] bytes = new byte[buf.readableBytes()];
                buf.getBytes(buf.readerIndex(), bytes);
                String sr = new String(bytes);
//                System.out.println(sr);
                // 传给ClientFrame
                ClientFrame.INSTANCE.updateText(sr);
            }finally {
                if (buf!= null) {
                    ReferenceCountUtil.release(buf);
                }
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            // 关闭连接
            ctx.close();
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(new TaskMsg(5,8));
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.connect();
    }
}
