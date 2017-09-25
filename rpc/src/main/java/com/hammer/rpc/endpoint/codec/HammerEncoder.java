package com.hammer.rpc.endpoint.codec;

import com.alibaba.fastjson.JSONObject;
import com.hammer.rpc.msg.HammerMsg;
import com.hammer.rpc.msg.body.MsgBody;
import com.hammer.rpc.msg.header.MsgHeader;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by gui on 2017/9/23.
 */
public class HammerEncoder extends MessageToMessageEncoder<HammerMsg> {
    /*长度位置的临时占位符*/
    private static final byte[] TEMP_LENGTH_PLACE_HOLDER = new byte[4];
    /*HammerMsg的“长度”位置*/
    public static final int HAMMER_MSG_LENGTH_POS_IDX = 4;

    public static final String ENCODE_CHAR_SET = "UTF-8";

    public void testEncode(ChannelHandlerContext context, HammerMsg hammerMsg, List<Object> out) throws Exception{
        this.encode(context, hammerMsg, out);
    }


    @Override
    protected void encode(ChannelHandlerContext context, HammerMsg hammerMsg, List<Object> out) throws Exception {
        if (hammerMsg==null || hammerMsg.getHeader()==null){
            throw new RuntimeException("the hammerMsg is null");
        }

        MsgHeader header = hammerMsg.getHeader();
        MsgBody body = hammerMsg.getBody();
        ByteBuf sendBuffer = Unpooled.buffer();

        encodeHeader(header, sendBuffer);
        encodeBody(body, sendBuffer);

        out.add(sendBuffer);

    }

    private void encodeHeader(MsgHeader header, ByteBuf sendBuffer) throws UnsupportedEncodingException {
        /*写入crcCode*/
        sendBuffer.writeInt(header.getCrcCode());
        /*暂时占位消息长度位置*/
        sendBuffer.writeBytes(TEMP_LENGTH_PLACE_HOLDER);
        /*写入消息类型*/
        sendBuffer.writeByte(header.getMsgEnum().getValue());
        /*写入消息优先级*/
        sendBuffer.writeByte(header.getPriority());
        /*写入消息头附件长度*/
        sendBuffer.writeInt(header.getAttachment().size());

        /*准备写入附件信息*/
        String key = null;
        byte[] keyBytes = null;
        Object value = null;
        for (Map.Entry<String, Object> entry : header.getAttachment().entrySet()){
            key = entry.getKey();
            /*编码key*/
            keyBytes = key.getBytes(ENCODE_CHAR_SET);
            /*写入key的长度*/
            sendBuffer.writeInt(keyBytes.length);
            /*写入key*/
            sendBuffer.writeBytes(keyBytes);
            /*写入value信息*/
            value = entry.getValue();
            encodeObj(value, sendBuffer);
        }
    }

    private void encodeBody(MsgBody body, ByteBuf sendBuffer){
        if (body != null){
            encodeObj(body, sendBuffer);

        }else{
            sendBuffer.writeInt(0);
            sendBuffer.setInt(HAMMER_MSG_LENGTH_POS_IDX, sendBuffer.readableBytes());
        }
    }

    private void encodeObj(Object obj, ByteBuf sendBuffer){
        /*记住obj长度值应该写入的位置*/
        int lengthPosIdx = sendBuffer.writerIndex();
        /*暂时占位obj长度位置*/
        sendBuffer.writeBytes(TEMP_LENGTH_PLACE_HOLDER);

        /*编码obj*/
        byte[] objBytes = JSONObject.toJSONBytes(obj);
        /*写入obj*/
        sendBuffer.writeBytes(objBytes);

        /*写入obj的长度*/
        sendBuffer.setInt(lengthPosIdx, objBytes.length);
    }
}
