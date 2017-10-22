package com.hammer.rpc.msg.body;

import java.util.Arrays;

/**
 * @Author 桂列华
 * @Date 2017/10/6 8:33.
 * @Email guiliehua@163.com
 */
public class ServiceInvocation implements MsgBody {

    private String chainId;
    /*方法调用所在接口的名字*/
    private String interfaceName;
    /*被调用方法的名字*/
    private String methodName;
    /*被调用方法入参类型*/
    private Class<?>[] paramTypes;
    /*被调用方法的实参*/
    private Object[] params;
    /*调用方法对应接口版本号*/
    private String alias;

    public ServiceInvocation() {
    }

    public ServiceInvocation(String chainId) {
        this.chainId = chainId;
    }

    public ServiceInvocation(String chainId, String interfaceName, String methodName, Class<?>[] paramTypes, Object[] params, String alias) {
        this.chainId = chainId;
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.params = params;
        this.alias = alias;
    }

    public Object forward(){
        //TODO:通过网络远程调用
        return this.toString();
    }

    public String getChainId() {
        return chainId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public ServiceInvocation setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public ServiceInvocation setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public ServiceInvocation setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
        return this;
    }

    public Object[] getParams() {
        return params;
    }

    public ServiceInvocation setParams(Object[] params) {
        this.params = params;
        return this;
    }

    public String getAlias() {
        return alias;
    }

    public ServiceInvocation setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    @Override
    public String toString() {
        return "ServiceInvocation{" +
                "interfaceName='" + interfaceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", paramTypes=" + Arrays.toString(paramTypes) +
                ", params=" + Arrays.toString(params) +
                ", alias='" + alias + '\'' +
                '}';
    }
}
