package com.hammer.registry.channelHandler;

import com.hammer.registry.persistence.RegistryService;
import com.hammer.rpc.msg.HammerMsg;
import com.hammer.rpc.msg.body.RegisterInvocation;
import com.hammer.rpc.msg.body.vo.ServiceVO;
import com.hammer.rpc.msg.enums.MsgEnum;
import com.hammer.rpc.msg.header.MsgHeader;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author 桂列华
 * @Date 2017/10/6 8:33.
 * @Email guiliehua@163.com
 */
public class DispatcherHandler extends ChannelHandlerAdapter {
    @Resource
    private RegistryService registryService;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        HammerMsg hammerMsg = (HammerMsg)msg;
        MsgHeader header = hammerMsg.getHeader();

        switch (header.getMsgEnum()){
            case REGISTER_REQ:
                handleRegister(ctx, (RegisterInvocation)hammerMsg.getBody());
                break;



        }

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
}
