package com.hammer.core.springExt.bean;

/**
 * Created by gui on 2017/9/16.
 */
public class ConsumerBean {
    private String id;

    private String interfaze;

    private String alias;

    private boolean async;

    private String callBack;

    public String getId() {
        return id;
    }

    public String buildKey(){
        return this.interfaze+"-"+this.alias;
    }

    public ConsumerBean setId(String id) {
        this.id = id;
        return this;
    }

    public String getInterfaze() {
        return interfaze;
    }

    public ConsumerBean setInterfaze(String interfaze) {
        this.interfaze = interfaze;
        return this;
    }

    public String getAlias() {
        return alias;
    }

    public ConsumerBean setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public boolean isAsync() {
        return async;
    }

    public ConsumerBean setAsync(boolean async) {
        this.async = async;
        return this;
    }

    public String getCallBack() {
        return callBack;
    }

    public ConsumerBean setCallBack(String callBack) {
        this.callBack = callBack;
        return this;
    }
}
