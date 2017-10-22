package com.hammer.registry.handler;

import com.hammer.registry.persistence.RegistryService;
import com.hammer.registry.persistence.impl.redis.RegistryServiceImpl;
import com.hammer.rpc.msg.HammerMsg;
import com.hammer.rpc.msg.body.RegisterInvocation;
import com.hammer.rpc.msg.body.vo.ServiceVO;
import com.hammer.rpc.msg.enums.MsgEnum;
import com.hammer.rpc.msg.header.MsgHeader;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author 桂列华
 * @Date 2017/10/6 8:33.
 * @Email guiliehua@163.com
 */
public class DispatcherHandler extends ChannelHandlerAdapter {
    private static Logger logger = LogManager.getLogger(DispatcherHandler.class);

    @Resource
    private RegistryService registryService = new RegistryServiceImpl();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("注册中心收到请求");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        HammerMsg hammerMsg = (HammerMsg)msg;
        MsgHeader header = hammerMsg.getHeader();
        logger.info(header);
        switch (header.getMsgEnum()){
            case REGISTER_REQ:
                handleRegister(ctx, (RegisterInvocation)hammerMsg.getBody());
                break;
            case SERVICE_DISCOVERY_REQ:



        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause);
    }

    private void handleRegister(ChannelHandlerContext ctx, RegisterInvocation registerInvocation){
        List<ServiceVO> serviceVOList = registerInvocation.getServiceVOList();
        if (serviceVOList!=null && !serviceVOList.isEmpty()){
            for (ServiceVO serviceVO : serviceVOList){
                registryService.register(serviceVO);
            }
        }
        MsgHeader header = new MsgHeader();
        header.setMsgEnum(MsgEnum.REGISTER_RESP);
        HammerMsg msg = new HammerMsg(header, null);
        ctx.writeAndFlush(msg);
    }

    private void handleServiceDiscovery(ChannelHandlerContext ctx, )

}
