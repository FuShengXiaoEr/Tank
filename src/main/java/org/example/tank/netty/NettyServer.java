package org.example.tank.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/18 22:18
 * Version 1.0
 * @description
 **/
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        // 负责接客
        NioEventLoopGroup boosGroup = new NioEventLoopGroup(2);
        // 负责服务
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(2);
        // server启动辅助类
        ServerBootstrap b = new ServerBootstrap();

        b.group(boosGroup,workerGroup);
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

class MyChildHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        System.out.printf(buf.toString());
        ctx.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
