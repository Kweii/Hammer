package com.hammer.rpc.msg.enums;

/**
 * @Author 桂列华
 * @Date 2017/10/6 8:33.
 * @Email guiliehua@163.com
 */
public enum MsgEnum {
    UNKNOWN((byte)0, "未知类型消息"),

    HANDSHAKE_SYNC((byte)11, "握手请求消息"),
    HANDSHAKE_ACK_REFUSE((byte)12, "握手拒绝消息"),
    HANDSHAKE_ACK_ACCEPT((byte)13, "握手接受消息"),

    HEARTBEAT_REQ((byte)21, "心跳请求消息"),
    HEARTBEAT_RESP((byte)22, "心跳回复消息"),

    REGISTER_REQ((byte)31, "注册请求消息"),
    REGISTER_RESP((byte)32, "注册响应消息"),

    SERVICE_DISCOVERY_REQ((byte)41, "服务发现消息"),
    SERVICE_DISCOVERY_RESP((byte)42, "服务发现响应消息"),

    SERVICE_MSG_REQ((byte)91, "业务请求消息"),
    SERVICE_MSG_RESP((byte)92, "业务响应消息");

    private byte value;
    private String show;

    MsgEnum(byte value, String show){
        this.value = value;
        this.show = show;
    }


    public static MsgEnum getByValue(byte value){
        for (MsgEnum msgEnum : MsgEnum.values()){
            if (msgEnum.value == value){
                return msgEnum;
            }
        }

        return UNKNOWN;
    }

    public byte getValue() {
        return value;
    }

    public String getShow() {
        return show;
    }

}
