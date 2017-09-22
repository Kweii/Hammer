package com.hammer.core.springExt.parser;

import com.hammer.core.springExt.bean.ConsumerBean;
import com.hammer.core.springExt.bean.ProviderBean;
import com.hammer.core.springExt.bean.RegistryBean;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by gui on 2017/9/16.
 */
public class HammerNamespaceHandler extends NamespaceHandlerSupport {
    public void init() {
        registerBeanDefinitionParser("provider", new HammerBeanDefinitionParser(ProviderBean.class));
        registerBeanDefinitionParser("consumer", new HammerBeanDefinitionParser(ConsumerBean.class));
        registerBeanDefinitionParser("registry", new HammerBeanDefinitionParser(RegistryBean.class));
    }
}
