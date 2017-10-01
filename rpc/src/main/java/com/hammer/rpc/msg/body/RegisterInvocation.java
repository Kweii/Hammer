package com.hammer.rpc.msg.body;

import com.hammer.rpc.msg.body.vo.ServiceVO;

import java.util.List;

/**
 * @Author 桂列华
 * @Date 2017/10/1 17:37.
 * @Email guiliehua@163.com
 */
public class RegisterInvocation implements MsgBody {
    List<ServiceVO> serviceVOList;
}
