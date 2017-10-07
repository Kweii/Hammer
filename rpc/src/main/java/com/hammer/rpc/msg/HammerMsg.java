package com.hammer.rpc.msg;

import com.hammer.rpc.msg.body.MsgBody;
import com.hammer.rpc.msg.header.MsgHeader;


/**
 * @Author 桂列华
 * @Date 2017/10/6 8:33.
 * @Email guiliehua@163.com
 */
public class HammerMsg {
    private MsgHeader header;

    private MsgBody body;

    public HammerMsg() {
    }

    public HammerMsg(MsgHeader header, MsgBody body) {
        this.header = header;
        this.body = body;
    }

    public MsgHeader getHeader() {
        return header;
    }

    public HammerMsg setHeader(MsgHeader header) {
        this.header = header;
        return this;
    }

    public MsgBody getBody() {
        return body;
    }

    public HammerMsg setBody(MsgBody body) {
        this.body = body;
        return this;
    }
}
