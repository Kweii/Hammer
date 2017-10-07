package com.hammer.core.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author 桂列华
 * @Date 2017/10/6 8:33.
 * @Email guiliehua@163.com
 */
public class HammerInvocationHandler implements InvocationHandler {
    private HammerInvocationHandler(){}

    /**
     * 获取单例实例
     * @return
     */
    public static HammerInvocationHandler getInstance(){
        return Holder.invocationHandler;
    }

    /**
     * 接口类方法调用拦截规则
     * @param proxy 代理类本身
     * @param method 被调用方法
     * @param args 方法实参
     * @return 方法返回值
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class declaringClazz = method.getDeclaringClass();
        /*如果传进来的是一个接口，远程代理*/
        if (declaringClazz.isInterface()){
            String version = "1.0";
//            Invocation invocation = new Invocation(declaringClazz.getName(), method.getName(), method.getParameterTypes(), args, version);
//
//            return invocation.invoke();
            return "xxxx";
        }

        /*如果传进来是一个已实现的具体类，不支持*/
        throw new IllegalArgumentException("Hammer does not support publishing class service");
    }

    @Override
    public String toString() {
        return "HammerInvocationHandler{}";
    }

    /**
     * 内部类实现单例模式
     */
    private static class Holder{
        private static final HammerInvocationHandler invocationHandler = new HammerInvocationHandler();
    }


}
