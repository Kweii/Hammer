package com.hammer.registry.persistence;

import com.hammer.rpc.msg.body.vo.ServiceVO;

/**
 * @Author 桂列华
 * @Date 2017/10/1 21:25.
 * @Email guiliehua@163.com
 */
public interface RegistryService {
    /**
     *
     * @param serviceVO
     * @return
     */
    public void register(ServiceVO serviceVO);

}
