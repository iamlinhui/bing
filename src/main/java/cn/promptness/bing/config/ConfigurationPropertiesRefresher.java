package cn.promptness.bing.config;

import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author : Lynn
 * @date : 2019-05-27 22:49
 */
@Component
public class ConfigurationPropertiesRefresher implements ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(ConfigurationPropertiesRefresher.class);

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @ApolloConfigChangeListener(value = {"application", "bootstrap", "business"})
    public void onChange(ConfigChangeEvent changeEvent) {
        for (String key : changeEvent.changedKeys()) {
            ConfigChange change = changeEvent.getChange(key);
            logger.info("found change - {}", change.toString());
        }
        refreshGatewayProperties(changeEvent);
    }

    /**
     * 更新SpringApplicationContext对象，并更新路由
     *
     * @param changeEvent
     */
    private void refreshGatewayProperties(ConfigChangeEvent changeEvent) {

        logger.info("refreshing properties!");

        applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));

        logger.info("configuration properties refreshed!");
    }

}
