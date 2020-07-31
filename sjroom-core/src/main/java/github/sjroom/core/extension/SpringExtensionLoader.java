package github.sjroom.core.extension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * spring bean loader
 *
 * @author Katrel.Zhou
 * @version 1.0.0
 * @date 2019/5/17 17:44
 */
@Configuration
public class SpringExtensionLoader implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(SpringExtensionLoader.class);

    private static ApplicationContext applicationContext;

    public static <T> T getSpringBean(String beanName, Class<T> clazz) {
        Object obj = applicationContext.getBean(beanName);
        if(null == obj) {
            return null;
        }
        return clazz.cast(obj);
    }

    public static <T> T getSpringBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static <T> Map<String, T> getSpringBeansOfType(Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz);
    }

    public static <T> Map<String, T> getSpringBeansOfType(ApplicationContext applicationContext, Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringExtensionLoader.applicationContext = applicationContext;
        if (logger.isInfoEnabled()) {
            logger.info("Aware bean \"{}\" to bean of type SpringExtensionLoader. ", applicationContext);
        }
    }

}
