package org.linker.foundation.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author RWM
 * @date 2018/2/8
 */
public class SpringBeanHelper implements ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(SpringBeanHelper.class);

    protected static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringBeanHelper.ctx = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        try {
            return ctx.getBean(clazz);
        } catch (NoSuchBeanDefinitionException e) {
            log.warn("no qualifying bean of type: {}", clazz);
            return null;
        }
    }
}
