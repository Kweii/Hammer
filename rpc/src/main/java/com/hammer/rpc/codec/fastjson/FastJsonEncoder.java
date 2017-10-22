package com.hammer.rpc.codec.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.hammer.rpc.codec.AbstractEncoder;
import com.hammer.rpc.msg.HammerMsg;
import com.hammer.rpc.msg.body.MsgBody;
import com.hammer.rpc.msg.header.MsgHeader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by gui on 2017/9/23.
 */
public class FastJsonEncoder extends AbstractEncoder<HammerMsg> {
    /*长度位置的临时占位符*/
    public static final byte[] TEMP_LENGTH_PLACE_HOLDER = new byte[4];
    /*HammerMsg的“长度”位置*/
    public static final int HAMMER_MSG_LENGTH_POS_IDX = 4;

    public static final String ENCODE_CHAR_SET = "UTF-8";

    @Override
    protected void encode(ChannelHandlerContext ctx, HammerMsg hammerMsg, ByteBuf sendBuffer) throws Exception {
        if (hammerMsg==null || hammerMsg.getHeader()==null){
            throw new RuntimeException("the hammerMsg is null");
        }

        MsgHeader header = hammerMsg.getHeader();
        MsgBody body = hammerMsg.getBody();

        encodeHeader(header, sendBuffer);
        encodeBody(body, sendBuffer);
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
        encodeObj(body, sendBuffer);
        sendBuffer.setInt(HAMMER_MSG_LENGTH_POS_IDX, sendBuffer.readableBytes());

    }

    private void encodeObj(Object obj, ByteBuf sendBuffer){
        /*记住obj长度值应该写入的位置*/
        int lengthPosIdx = sendBuffer.writerIndex();
        /*暂时占位obj长度位置*/
        sendBuffer.writeBytes(TEMP_LENGTH_PLACE_HOLDER);
        byte[] objBytes = new byte[0];
        if (obj != null){

            /*编码obj*/
            objBytes = JSONObject.toJSONBytes(obj);
            /*写入obj*/
            sendBuffer.writeBytes(objBytes);

        }
        /*写入obj的长度*/
        sendBuffer.setInt(lengthPosIdx, objBytes.length);

    }

}
