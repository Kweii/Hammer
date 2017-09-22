package com.hammer.rpc.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 封装一个方法调用
 * Created by gui on 2017/9/16.
 */
public class Invocation implements Serializable {
    /*方法调用所在接口的名字*/
    private String interfaceName;
    /*被调用方法的名字*/
    private String methodName;
    /*被调用方法入参类型*/
    private Class<?>[] paramTypes;
    /*被调用方法的实参*/
    private Object[] params;
    /*调用方法对应接口版本号*/
    private String version;

    public Invocation() {
    }

    public Invocation(String interfaceName, String methodName, Class<?>[] paramTypes, Object[] params, String version) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.params = params;
        this.version = version;
    }

    public Object invoke(){
        //TODO:通过网络远程调用
        return this.toString();
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public Invocation setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public Invocation setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public Invocation setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
        return this;
    }

    public Object[] getParams() {
        return params;
    }

    public Invocation setParams(Object[] params) {
        this.params = params;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public Invocation setVersion(String version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return "Invocation{" +
                "interfaceName='" + interfaceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", paramTypes=" + Arrays.toString(paramTypes) +
                ", params=" + Arrays.toString(params) +
                ", version='" + version + '\'' +
                '}';
    }
}
