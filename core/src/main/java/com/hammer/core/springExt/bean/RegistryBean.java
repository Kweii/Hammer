package com.hammer.core.springExt.bean;

/**
 * Created by gui on 2017/9/16.
 */
public class RegistryBean {
    /*注册中心IP*/
    private String ip;
    /*注册中心端口*/
    private int port;

    public String getIp() {
        return ip;
    }

    public RegistryBean setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public int getPort() {
        return port;
    }

    public RegistryBean setPort(int port) {
        this.port = port;
        return this;
    }
}
