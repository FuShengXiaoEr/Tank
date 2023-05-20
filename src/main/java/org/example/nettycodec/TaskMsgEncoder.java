package org.example.nettycodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author XiZhuangBaoTu
 * Date 2023/5/20 11:02
 * Version 1.0
 * @description
 **/
public class TaskMsgEncoder extends MessageToByteEncoder<TaskMsg> {
    @Override
    protected void encode(ChannelHandlerContext ctx, TaskMsg msg, ByteBuf out) {
        out.writeInt(msg.x);
        out.writeInt(msg.y);
        System.out.println("encode"+out);
    }
}
