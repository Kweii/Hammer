package com.hammer.core.springExt.parser;

import com.hammer.core.springExt.bean.ConsumerBean;
import com.hammer.core.springExt.bean.ProviderBean;
import com.hammer.core.springExt.bean.RegistryBean;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @Author 桂列华
 * @Date 2017/10/6 8:33.
 * @Email guiliehua@163.com
 */
public class HammerNamespaceHandler extends NamespaceHandlerSupport {
    public void init() {
        registerBeanDefinitionParser("provider", new HammerBeanDefinitionParser(ProviderBean.class));
        registerBeanDefinitionParser("consumer", new HammerBeanDefinitionParser(ConsumerBean.class));
        registerBeanDefinitionParser("registry", new HammerBeanDefinitionParser(RegistryBean.class));
    }
}
