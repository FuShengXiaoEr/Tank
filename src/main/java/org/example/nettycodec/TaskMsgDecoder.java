package org.example.nettycodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/20 11:07
 * Version 1.0
 * @description
 **/
public class TaskMsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 8) return;
        int x = in.readInt();
        int y = in.readInt();

        out.add(new TaskMsg(x,y));
    }
}
