package com.hammer.provider.server;

import com.hammer.rpc.codec.fastjson.FastJsonDecoder;
import com.hammer.rpc.codec.fastjson.FastJsonEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * @Author 桂列华
 * @Date 2017/10/7 17:49.
 * @Email guiliehua@163.com
 */
public class ProviderServer {
    private static final Logger logger = LogManager.getLogger(ProviderServer.class);

    public static void main(String[] args){
        try {
            start();
        } catch (UnknownHostException e) {
            logger.error(e);
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }

    public static void start() throws UnknownHostException, InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(5);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(50);

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 500)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new FastJsonDecoder(1024 * 1024 * 1024, FastJsonEncoder.HAMMER_MSG_LENGTH_POS_IDX, FastJsonEncoder.TEMP_LENGTH_PLACE_HOLDER.length));
                        pipeline.addLast(new FastJsonEncoder());
                        pipeline.addLast(new ReadTimeoutHandler(1000, TimeUnit.MILLISECONDS));
                        pipeline.addLast(null);
                    }
                });

        ChannelFuture future = serverBootstrap.bind(InetAddress.getLocalHost(), 9526).sync();
        logger.info("starting at:"+InetAddress.getLocalHost().getHostAddress());
        future.channel().closeFuture().sync();
    }
}
