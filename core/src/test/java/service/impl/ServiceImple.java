package service.impl;

import service.Service;

/**
 * @Author 桂列华
 * @Date 2017/10/21 16:15.
 * @Email guiliehua@163.com
 */
public class ServiceImple implements Service {
    public String sayHello(String name) {
        return "hello "+name;
    }
}
