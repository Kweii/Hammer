package com.hammer.registry;

import com.hammer.registry.handler.DispatcherHandler;
import com.hammer.rpc.codec.fastjson.FastJsonDecoder;
import com.hammer.rpc.codec.fastjson.FastJsonEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * @Author 桂列华
 * @Date 2017/10/6 8:33.
 * @Email guiliehua@163.com
 */
public class RegistryServer {
    private static final Logger logger = LogManager.getLogger(RegistryServer.class);

    public static void main(String[] args){
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
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
                        //pipeline.addLast(new ReadTimeoutHandler(600000, TimeUnit.MILLISECONDS));
                        pipeline.addLast(new DispatcherHandler());
                    }
                });

        ChannelFuture future = serverBootstrap.bind(InetAddress.getLocalHost(), 9527).sync();
        logger.info("starting at:"+InetAddress.getLocalHost().getHostAddress());
        future.channel().closeFuture().sync();
    }
}
