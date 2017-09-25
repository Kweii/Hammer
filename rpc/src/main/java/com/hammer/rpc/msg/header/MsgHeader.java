package com.hammer.rpc.msg.header;

import com.hammer.rpc.msg.common.MsgEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gui on 2017/9/23.
 */
public class MsgHeader {
    private int crcCode = 0xABC001;
    private int length = -1;
    private MsgEnum msgEnum;
    private byte priority;
    private Map<String, Object> attachment = new HashMap<String, Object>();

    public MsgHeader() {
    }

    public MsgHeader(int length, MsgEnum msgEnum, byte priority, Map<String, Object> attachment) {
        this.length = length;
        this.msgEnum = msgEnum;
        this.priority = priority;
        this.attachment = attachment;
    }

    public int getCrcCode() {
        return crcCode;
    }

    public MsgHeader setCrcCode(int crcCode) {
        this.crcCode = crcCode;
        return this;
    }

    public int getLength() {
        return length;
    }

    public MsgHeader setLength(int length) {
        this.length = length;
        return this;
    }

    public MsgEnum getMsgEnum() {
        return msgEnum;
    }

    public MsgHeader setMsgEnum(MsgEnum msgEnum) {
        this.msgEnum = msgEnum;
        return this;
    }

    public byte getPriority() {
        return priority;
    }

    public MsgHeader setPriority(byte priority) {
        this.priority = priority;
        return this;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public MsgHeader setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
        return this;
    }
}
