package com.hammer.core.springExt.parser;

import com.hammer.core.context.HammerContext;
import com.hammer.core.proxy.ProxyFactory;
import com.hammer.core.springExt.bean.ConsumerBean;
import com.hammer.core.springExt.bean.ProviderBean;
import com.hammer.core.springExt.bean.RegistryBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import static com.hammer.core.constant.SpringConstant.*;

/**
 * Created by gui on 2017/9/16.
 */
public class HammerBeanDefinitionParser implements BeanDefinitionParser {
    private Logger logger = LogManager.getLogger(HammerBeanDefinitionParser.class);

    /*要解析的类*/
    private Class beanClass;

    public HammerBeanDefinitionParser(Class beanClass){
        this.beanClass = beanClass;
    }

    /**
     * 解析hammer配置文件
     * @param element
     * @param parserContext
     * @return
     */
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        registerHammerUtilsIfNecessary(parserContext);

        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setLazyInit(false);
        if (this.beanClass.equals(ProviderBean.class)){
            beanDefinition.setBeanClass(ProviderBean.class);

            String interfaze = element.getAttribute("interface");
            String ref = element.getAttribute("ref");
            String alias = element.getAttribute("alias");

            ProviderBean providerBean = new ProviderBean();
            providerBean.setInterfaze(interfaze).setRef(ref).setAlias(alias);
            String beanId = providerBean.buildKey();

            beanDefinition.getPropertyValues().addPropertyValue("interfaze", interfaze);
            beanDefinition.getPropertyValues().addPropertyValue("ref", ref);
            beanDefinition.getPropertyValues().addPropertyValue("alias", alias);
            parserContext.getRegistry().registerBeanDefinition(beanId, beanDefinition);

        }else if (this.beanClass.equals(ConsumerBean.class)){
            String beanId = element.getAttribute("id");
            String interfaze = element.getAttribute("interface");
            String alias = element.getAttribute("alias");

            Class interfazeClass = null;
            try {
                interfazeClass = Class.forName(interfaze);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("can not find class="+interfaze);
            }
            /*设置动态代理信息*/
            beanDefinition.setFactoryBeanName(PROXY_FACTORY_BEAN_NAME);
            beanDefinition.setFactoryMethodName(PROXY_FACTORY_METHOD_NAME);
            ConstructorArgumentValues constructorArguments = new ConstructorArgumentValues();
            constructorArguments.addIndexedArgumentValue(0, interfazeClass);
            beanDefinition.setConstructorArgumentValues(constructorArguments);

            parserContext.getRegistry().registerBeanDefinition(beanId, beanDefinition);

        }else if (this.beanClass.equals(RegistryBean.class)){
            String ip = element.getAttribute("ip");
            int port = Integer.parseInt(element.getAttribute("port"));
            beanDefinition.setBeanClass(RegistryBean.class);
            beanDefinition.getPropertyValues().addPropertyValue("ip", ip);
            beanDefinition.getPropertyValues().addPropertyValue("port", port);

            parserContext.getRegistry().registerBeanDefinition(REGISTRY_BEAN_ID, beanDefinition);
            logger.info(String.format("登记注册中心完成, ip=%s, port=%d", ip, port));
        }

        return beanDefinition;
    }

    /**
     * 向spring context中注册ProxyFactory、HammerContext等
     * @param parserContext
     */
    private void registerHammerUtilsIfNecessary(ParserContext parserContext){
        /*只向spring context注册一次*/
        if (!HammerContext.isInitialized()){
            synchronized (HammerBeanDefinitionParser.class){
                if (!HammerContext.isInitialized()){
                    RootBeanDefinition factoryBeanDefinition = new RootBeanDefinition();
                    factoryBeanDefinition.setLazyInit(false);
                    factoryBeanDefinition.setBeanClass(ProxyFactory.class);
                    factoryBeanDefinition.setInitMethodName(PROXY_FACTORY_INIT_METHOD_NAME);

                    parserContext.getRegistry().registerBeanDefinition(PROXY_FACTORY_BEAN_NAME, factoryBeanDefinition);
                    HammerContext.setInitialized(true);

                    RootBeanDefinition hammerContextBeanDefinition = new RootBeanDefinition();
                    hammerContextBeanDefinition.setLazyInit(false);
                    hammerContextBeanDefinition.setBeanClass(HammerContext.class);
                    hammerContextBeanDefinition.setInitMethodName(HAMMER_CONTEXT_INIT_METHOD_NAME);
                    parserContext.getRegistry().registerBeanDefinition("hammerContext", hammerContextBeanDefinition);

                }
            }
        }
    }

}
