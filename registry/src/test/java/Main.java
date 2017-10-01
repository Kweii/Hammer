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
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;


/**
 * Created by gui on 2017/10/1.
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(5);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(50);

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 500)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new FastJsonDecoder(1024*1024*4, FastJsonEncoder.HAMMER_MSG_LENGTH_POS_IDX, FastJsonEncoder.TEMP_LENGTH_PLACE_HOLDER.length))
                                .addLast(new FastJsonEncoder())
                                .addLast(new ReadTimeoutHandler(1000, TimeUnit.MILLISECONDS));
                    }
                });

        ChannelFuture future = serverBootstrap.bind(InetAddress.getLocalHost(), 1015).sync();
        logger.info("starting");
        future.channel().closeFuture().sync();
    }
}
