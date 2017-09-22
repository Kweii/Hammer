package com.hammer.core.springExt.bean;

/**
 * Created by gui on 2017/9/16.
 */
public class ProviderBean {
    /*接口名称*/
    private String interfaze;

    /*对接口类bean的引用*/
    private String ref;

    /*接口发布版本*/
    private String alias;

    public String buildKey(){
        return this.interfaze+"-"+this.alias;
    }

    public String getInterfaze() {
        return interfaze;
    }

    public ProviderBean setInterfaze(String interfaze) {
        this.interfaze = interfaze;
        return this;
    }

    public String getRef() {
        return ref;
    }

    public ProviderBean setRef(String ref) {
        this.ref = ref;
        return this;
    }

    public String getAlias() {
        return alias;
    }

    public ProviderBean setAlias(String alias) {
        this.alias = alias;
        return this;
    }
}
