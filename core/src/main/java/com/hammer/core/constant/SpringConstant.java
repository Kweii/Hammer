package com.hammer.core.constant;

/**
 * Created by gui on 2017/9/23.
 */
public class SpringConstant {
    /*ProxyFactory在spring context中的名字*/
    public static final String PROXY_FACTORY_BEAN_NAME = "com.hammer.core.proxy.ProxyFactory";

    /*ProxyFactory的实例化方法*/
    public static final String PROXY_FACTORY_INIT_METHOD_NAME = "getInstance";

    /*ProxyFactory的生成代理类的工厂方法*/
    public static final String PROXY_FACTORY_METHOD_NAME = "newProxyInstance";


    public static final String HAMMER_CONTEXT_INIT_METHOD_NAME = "getContext";

    public static final String REGISTRY_BEAN_ID = "com.hammer.core.springExt.bean.RegistryBean";
}
