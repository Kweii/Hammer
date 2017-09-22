package com.hammer.core.proxy;

import java.lang.reflect.Proxy;

/**
 * Created by gui on 2017/9/16.
 */
public class ProxyFactory {
    public static ProxyFactory getInstance(){
        return Holder.proxyFactory;
    }

    public <T> T newProxyInstance(Class clazz){
        Object proxy = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, HammerInvocationHandler.getInstance());
        return (T)proxy;
    }

    private static class Holder{
        private static final ProxyFactory proxyFactory = new ProxyFactory();
    }
}
