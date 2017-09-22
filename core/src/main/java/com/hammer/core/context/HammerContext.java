package com.hammer.core.context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by gui on 2017/9/16.
 */
public class HammerContext implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent>{
    Logger logger = LogManager.getLogger(HammerContext.class);

    private static boolean initialized = false;

    private static ApplicationContext applicationContext;

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

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.info("设置spring context");
        applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

    }


    private static class Holder {
        private static final HammerContext instance = new HammerContext();
    }
}
