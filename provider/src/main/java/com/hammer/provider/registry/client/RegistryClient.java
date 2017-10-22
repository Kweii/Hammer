package com.hammer.provider.registry.client;

import com.hammer.provider.registry.client.handler.RegisterServiceHandler;
import com.hammer.rpc.codec.fastjson.FastJsonDecoder;
import com.hammer.rpc.codec.fastjson.FastJsonEncoder;
import com.hammer.rpc.msg.HammerMsg;
import com.hammer.rpc.msg.body.MsgBody;
import com.hammer.rpc.msg.body.RegisterInvocation;
import com.hammer.rpc.msg.body.vo.ServiceVO;
import com.hammer.rpc.msg.enums.MsgEnum;
import com.hammer.rpc.msg.header.MsgHeader;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * @Author 桂列华
 * @Date 2017/10/7 18:19.
 * @Email guiliehua@163.com
 */
public class RegistryClient {
    private static Logger logger = LogManager.getLogger(RegistryClient.class);

    public static void registerService(String host, int port, List<ServiceVO> serviceVOList) throws InterruptedException {
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new FastJsonDecoder(1024 * 1024 * 1024, FastJsonEncoder.HAMMER_MSG_LENGTH_POS_IDX, FastJsonEncoder.TEMP_LENGTH_PLACE_HOLDER.length));
                        pipeline.addLast(new FastJsonEncoder());
                        //pipeline.addLast(new ReadTimeoutHandler(60000, TimeUnit.MILLISECONDS));
                        pipeline.addLast(new RegisterServiceHandler());
                    }
                });

        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            if (future.awaitUninterruptibly(5000)){
                MsgHeader header = new MsgHeader();
                header.setMsgEnum(MsgEnum.REGISTER_REQ);
                MsgBody body = new RegisterInvocation(serviceVOList);
                HammerMsg msg = new HammerMsg(header, body);
                future.channel().writeAndFlush(msg);
            }

        } finally {
            //worker.shutdownGracefully();
        }
    }
}
