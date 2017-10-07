package com.hammer.registry.channelInitializer;

import com.hammer.registry.channelHandler.DispatcherHandler;
import com.hammer.rpc.codec.fastjson.FastJsonDecoder;
import com.hammer.rpc.codec.fastjson.FastJsonEncoder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Author 桂列华
 * @Date 2017/10/6 8:33.
 * @Email guiliehua@163.com
 */
public class RegistryChannelInitializer<T extends Channel> extends ChannelInitializer {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new FastJsonDecoder(1024 * 1024 * 4, FastJsonEncoder.HAMMER_MSG_LENGTH_POS_IDX,
                                                FastJsonEncoder.TEMP_LENGTH_PLACE_HOLDER.length))
                .addLast(new FastJsonEncoder())
                .addLast(new ReadTimeoutHandler(1000, TimeUnit.MILLISECONDS))
                .addLast(new DispatcherHandler());
    }
}
