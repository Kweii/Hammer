package com.hammer.core.proxy;

import java.lang.reflect.Proxy;

/**
 * @Author 桂列华
 * @Date 2017/10/6 8:33.
 * @Email guiliehua@163.com
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
