package com.hammer.rpc.msg.body.vo;

import com.hammer.rpc.common.Constant;

/**
 * @Author 桂列华
 * @Date 2017/10/1 17:25.
 * @Email guiliehua@163.com
 */
public class ServiceVO {
    /*服务对应接口*/
    private String interfaze;
    /*服务对应别名*/
    private String alias;
    /*服务提供者对应IP*/
    private  String ip;
    /*对应端口*/
    private int port;
    /*是否支持动态别名*/
    private boolean supportDynamicAlias;
    /*提供动态别名时当前节点的本职服务对应的最大阈值，言外之意就是当前节点的值大于该阈值时将不支持动态别名（连最基本的额的本职工作都没有做好，凭什么让你做其他的）*/
    private float threshold;

    public String commonServiceGroup(){
        return Constant.COMMON_SERVICE_REGISTER_GROUP_PREFIX + interfaze + Constant.SEPARATOR + alias;
    }
    public String commonServiceKey(){
        return ip + Constant.SEPARATOR + port;
    }

    public String dynamicServiceGroup(){
        return Constant.DYNAMIC_SERVICE_REGISTER_GROUP_PREFIX + interfaze;
    }

    public String dynamicServiceKey(){
        return ip + Constant.SEPARATOR + port;
    }

    public String getInterfaze() {
        return interfaze;
    }

    public ServiceVO setInterfaze(String interfaze) {
        this.interfaze = interfaze;
        return this;
    }

    public String getAlias() {
        return alias;
    }

    public ServiceVO setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public ServiceVO setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public int getPort() {
        return port;
    }

    public ServiceVO setPort(int port) {
        this.port = port;
        return this;
    }

    public boolean isSupportDynamicAlias() {
        return supportDynamicAlias;
    }

    public ServiceVO setSupportDynamicAlias(boolean supportDynamicAlias) {
        this.supportDynamicAlias = supportDynamicAlias;
        return this;
    }

    public float getThreshold() {
        return threshold;
    }

    public ServiceVO setThreshold(float threshold) {
        this.threshold = threshold;
        return this;
    }
}
