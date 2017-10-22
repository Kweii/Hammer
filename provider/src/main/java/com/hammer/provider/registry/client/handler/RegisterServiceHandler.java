package com.hammer.provider.registry.client.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Author 桂列华
 * @Date 2017/10/8 11:28.
 * @Email guiliehua@163.com
 */
public class RegisterServiceHandler extends ChannelHandlerAdapter {
    private static Logger logger = LogManager.getLogger(RegisterServiceHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    }
}
