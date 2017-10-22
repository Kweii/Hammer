package com.hammer.rpc.msg.body;

import com.hammer.rpc.msg.body.vo.ServiceVO;

import java.util.List;

/**
 * @Author 桂列华
 * @Date 2017/10/1 17:37.
 * @Email guiliehua@163.com
 */
public class RegisterInvocation implements MsgBody {
    public RegisterInvocation() {
    }

    List<ServiceVO> serviceVOList;

    public RegisterInvocation(List<ServiceVO> serviceVOList) {
        this.serviceVOList = serviceVOList;
    }

    public List<ServiceVO> getServiceVOList() {
        return serviceVOList;
    }

    public RegisterInvocation setServiceVOList(List<ServiceVO> serviceVOList) {
        this.serviceVOList = serviceVOList;
        return this;
    }
}
