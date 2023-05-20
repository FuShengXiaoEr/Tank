package org.example.nettycodec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.example.nettychatroom.ServerFrame;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/18 22:18
 * Version 1.0
 * @description
 **/
public class Server {
    // 保存channel,使用一个线程维护
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public void start() {
        System.out.println("Server start……");
        // 负责接客
        NioEventLoopGroup boosGroup = new NioEventLoopGroup(2);
        // 负责服务
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(2);
        try {
            // server启动辅助类
            ServerBootstrap b = new ServerBootstrap();
            ChannelFuture sync = b.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new TaskMsgDecoder())
                                    .addLast(new MyChildHandler());
                        }
                    }).bind(8888).sync();
            System.out.println("server bind port……");
            sync.channel().closeFuture().sync();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

class MyChildHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        TaskMsg tm = (TaskMsg) msg;
        System.out.printf(tm.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 每一个channel在启动的时候被加入
        Server.clients.add(ctx.channel());
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
