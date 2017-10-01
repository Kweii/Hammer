package com.hammer.registry.server;

import com.hammer.registry.channelInitializer.RegistryChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by gui on 2017/10/1.
 */
public class RegistryServer {
    private static final Logger logger = LogManager.getLogger(RegistryServer.class);

    public static void start() throws UnknownHostException, InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(5);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(50);

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 500)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new RegistryChannelInitializer<SocketChannel>());

        ChannelFuture future = serverBootstrap.bind(InetAddress.getLocalHost(), 1015).sync();
        logger.info("starting at:"+InetAddress.getLocalHost().getHostAddress());
        future.channel().closeFuture().sync();
    }
}