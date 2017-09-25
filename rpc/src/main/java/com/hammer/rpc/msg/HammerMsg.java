package com.hammer.rpc.msg;

import com.hammer.rpc.msg.body.MsgBody;
import com.hammer.rpc.msg.header.MsgHeader;

/**
 * Created by gui on 2017/9/23.
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
