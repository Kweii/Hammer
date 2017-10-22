package com.hammer.core.context;

import com.hammer.core.constant.SpringConstant;
import com.hammer.core.springExt.bean.ConsumerBean;
import com.hammer.core.springExt.bean.ProviderBean;
import com.hammer.core.springExt.bean.RegistryBean;
import com.hammer.provider.registry.client.RegistryClient;
import com.hammer.rpc.msg.body.vo.ServiceVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * @Author 桂列华
 * @Date 2017/10/6 8:33.
 * @Email guiliehua@163.com
 */
public class HammerContext implements ApplicationListener<ContextRefreshedEvent>{
    private static Logger logger = LogManager.getLogger(HammerContext.class);
    private static boolean initialized = false;
    private static ApplicationContext applicationContext;
    private static Set<ProviderBean> providers = new HashSet<ProviderBean>();
    private static Set<ConsumerBean> consumers = new HashSet<ConsumerBean>();

    private HammerContext(){}

    public static HammerContext getContext(){
        return Holder.instance;
    }

    public static boolean isInitialized() {
        return initialized;
    }

    public static void setInitialized(boolean initialized) {
        HammerContext.initialized = initialized;
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    public void onApplicationEvent(ContextRefreshedEvent refreshedEvent) {
        applicationContext = refreshedEvent.getApplicationContext();
        if (applicationContext.getParent() == null){
            logger.info("spring初始化完成，准备注册服务");
            RegistryBean registry = applicationContext.getBean(SpringConstant.REGISTRY_BEAN_ID, RegistryBean.class);
            try {

                RegistryClient.registerService(registry.getIp(), registry.getPort(), getServiceVOs());
                logger.info("注册服务完成");
                CountDownLatch latch = new CountDownLatch(1);
                latch.await();
            } catch (InterruptedException e) {
                logger.error(e);
            }

        }
    }

    public static void registerProvider(ProviderBean providerBean){
        providers.add(providerBean);
    }

    public static Set<ProviderBean> getProviders(){
        return providers;
    }

    public static void initConsumer(ConsumerBean consumerBean){
        consumers.add(consumerBean);
    }

    public static Set<ConsumerBean> getConsumers(){
        return consumers;
    }

    private static List<ServiceVO> getServiceVOs() {
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.error(e);
        }
        int port = 9528;
        List<ServiceVO> serviceVOList = new LinkedList<ServiceVO>();
        ServiceVO serviceVO = null;
        for (ProviderBean providerBean : providers){
            serviceVO = providerBean.convert();
            serviceVO.setIp(ip)
                    .setPort(port);

            serviceVOList.add(serviceVO);
        }

        return serviceVOList;
    }

    private static class Holder {
        private static final HammerContext instance = new HammerContext();
    }
}
