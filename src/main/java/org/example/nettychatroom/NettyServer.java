package org.example.nettychatroom;

import com.sun.security.ntlm.Server;
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

import java.util.Date;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/18 22:18
 * Version 1.0
 * @description
 **/
public class NettyServer {
    // 保存channel,使用一个线程维护
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static void main(String[] args) throws InterruptedException {
        // 负责接客
        NioEventLoopGroup boosGroup = new NioEventLoopGroup(2);
        // 负责服务
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(2);
        // server启动辅助类
        ServerBootstrap b = new ServerBootstrap();

        b.group(boosGroup, workerGroup);
        // 异步全双工
        b.channel(NioServerSocketChannel.class);
        // netty帮忙处理了accept的过程
        b.childHandler(new MyChildInitializer());
        ChannelFuture sync = b.bind(8888).sync();
        sync.channel().closeFuture().sync();
        boosGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}

class MyChildInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new MyChildHandler());
    }
}

class MyChildHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        NettyServer.clients.writeAndFlush(msg);
//        ctx.writeAndFlush(msg);
        ByteBuf buf = null;

        buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.getBytes(buf.readerIndex(), bytes);
        String s = new String(bytes);
        if ("__bye__".equals(s)) {
            System.out.printf("client ready exit");
            NettyServer.clients.remove(ctx.channel());
            ctx.close();
        } else {
            NettyServer.clients.writeAndFlush(buf);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 每一个channel在启动的时候被加入
        NettyServer.clients.add(ctx.channel());
    }
}
