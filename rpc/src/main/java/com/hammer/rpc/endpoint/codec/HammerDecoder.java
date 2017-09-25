package com.hammer.rpc.endpoint.codec;

import com.alibaba.fastjson.JSONObject;
import com.hammer.rpc.msg.HammerMsg;
import com.hammer.rpc.msg.body.*;
import com.hammer.rpc.msg.common.MsgEnum;
import com.hammer.rpc.msg.header.MsgHeader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gui on 2017/9/23.
 */
public class HammerDecoder extends LengthFieldBasedFrameDecoder {
    public HammerDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    public Object decode(ByteBuf rcvBuffer) throws Exception {
        return this.decode(null, rcvBuffer);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf rcvBuffer) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, rcvBuffer);
        if (frame == null){
            return null;
        }

        MsgHeader header = decodeHeader(rcvBuffer);
        MsgBody body = decodeBody(rcvBuffer, header.getMsgEnum());

        return new HammerMsg(header, body);
    }

    /**
     * 解码消息头
     * @param rcvBuffer
     * @return
     * @throws UnsupportedEncodingException
     */
    private MsgHeader decodeHeader(ByteBuf rcvBuffer) throws UnsupportedEncodingException {
        MsgHeader header = new MsgHeader();
        /*解码crcCode*/
        header.setCrcCode(rcvBuffer.readInt());
        /*解码消息长度*/
        header.setLength(rcvBuffer.readInt());
        /*解码消息类型*/
        byte msgEnumVale = rcvBuffer.readByte();
        MsgEnum msgEnum = MsgEnum.getByValue(msgEnumVale);
        header.setMsgEnum(msgEnum);
        /*解码消息优先级*/
        header.setPriority(rcvBuffer.readByte());
        /*解码附件长度*/
        int attachSize = rcvBuffer.readInt();

        if (attachSize==0){
            return header;
        }

        /*准备解码附件*/
        Map<String, Object> attach = new HashMap<String, Object>(attachSize);
        int keySize=-1, valueSize=-1;
        byte[] keyBytes=null, valueBytes=null;
        String key = null;
        Object value = null;
        for(int attachIdx=0; attachIdx<attachSize; attachIdx++){
            /*******解码附件key开始*******/
            keySize = rcvBuffer.readInt();
            keyBytes = new byte[keySize];
            rcvBuffer.readBytes(keyBytes);
            key = new String(keyBytes, HammerEncoder.ENCODE_CHAR_SET);
            /*******解码附件key结束*******/

            /*******解码附件value开始*******/
            valueSize = rcvBuffer.readInt();
            valueBytes = new byte[valueSize];
            rcvBuffer.readBytes(valueBytes);
            value = JSONObject.parseObject(valueBytes, Object.class);
            /*******解码附件value结束*******/

            attach.put(key, value);

        }

        return header;
    }

    /**
     * 解码消息体
     * @param rcvBuffer
     * @param msgEnum
     * @return
     */
    private MsgBody decodeBody(ByteBuf rcvBuffer, MsgEnum msgEnum){
        int bodySize = rcvBuffer.readInt();
        if (bodySize == 0){
            return null;
        }
        MsgBody body = null;

        byte[] bodyBytes = new byte[bodySize];
        rcvBuffer.readBytes(bodyBytes);
        Class targetClazz = getMsgBodyClazz(msgEnum);

        body = JSONObject.parseObject(bodyBytes, targetClazz);
        return body;

    }

    /**
     * 获取消息体的类型
     * @param msgEnum
     * @return
     */
    private Class getMsgBodyClazz(MsgEnum msgEnum){
        Class targetClazz = null;
        switch (msgEnum){
            case HANDSHAKE_SYNC:
                targetClazz = HandShakeInvocation.class;
                break;
            case HANDSHAKE_ACK_ACCEPT:
                targetClazz = HandShakeResp.class;
                break;
            case HANDSHAKE_ACK_REFUSE:
                targetClazz = HandShakeResp.class;
                break;
            case HEARTBEAT_REQ:
                targetClazz = HearBeatInvocation.class;
                break;
            case HEARTBEAT_RESP:
                targetClazz = HeartBeatResp.class;
                break;
            case REGISTER_REQ:
                targetClazz = RegisterInvocation.class;
                break;
            case REGISTER_RESP:
                targetClazz = RegisterResp.class;
                break;
            case SERVICE_MSG_REQ:
                targetClazz = ServiceInvocation.class;
                break;
            case SERVICE_MSG_RESP:
                targetClazz = ServiceResp.class;
                break;
            case UNKNOWN:
                targetClazz = Object.class;
                break;
        }

        return targetClazz;
    }
}
