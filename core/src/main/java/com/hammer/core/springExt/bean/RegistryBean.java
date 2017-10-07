package com.hammer.core.springExt.bean;

/**
 * @Author 桂列华
 * @Date 2017/10/6 8:33.
 * @Email guiliehua@163.com
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
