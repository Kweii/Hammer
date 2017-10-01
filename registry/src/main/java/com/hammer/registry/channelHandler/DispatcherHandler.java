package com.hammer.registry.channelHandler;

import com.hammer.rpc.msg.HammerMsg;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by gui on 2017/10/1.
 */
public class DispatcherHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        HammerMsg hammerMsg = (HammerMsg)msg;

    }
}
