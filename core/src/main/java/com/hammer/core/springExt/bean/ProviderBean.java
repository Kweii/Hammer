package com.hammer.core.springExt.bean;

/**
 * @Author 桂列华
 * @Date 2017/10/6 8:33.
 * @Email guiliehua@163.com
 */
public class ProviderBean {
    /*接口名称*/
    private String interfaze;

    /*对接口类bean的引用*/
    private String ref;

    /*接口发布版本*/
    private String alias;

    private boolean supportDynamicAlias;

    private float threshold;

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

    public boolean isSupportDynamicAlias() {
        return supportDynamicAlias;
    }

    public ProviderBean setSupportDynamicAlias(boolean supportDynamicAlias) {
        this.supportDynamicAlias = supportDynamicAlias;
        return this;
    }

    public float getThreshold() {
        return threshold;
    }

    public ProviderBean setThreshold(float threshold) {
        this.threshold = threshold;
        return this;
    }
}
