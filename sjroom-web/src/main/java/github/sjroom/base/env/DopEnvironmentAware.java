package github.sjroom.base.env;

import github.sjroom.core.exception.FrameworkException;
import github.sjroom.core.utils.StringUtil;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

/**
 * 当前系统环境变量及配置文件
 *
 * @author Katrel.Zhou
 * @version 1.0.0
 * @date 2019/5/17 17:44
 */
public class DopEnvironmentAware implements EnvironmentAware {

    private Environment environment;
    private String serverName;
    private DopProperties dopProperties;

    public DopEnvironmentAware(DopProperties dopProperties) {
        this.dopProperties = dopProperties;
    }

    public String getServerName() {
        return this.serverName;
    }

    public String getProperty(String key) {
        return this.environment.getProperty(key);
    }

    public <T> T getProperty(String key, Class<T> targetType) {
        return this.environment.getProperty(key, targetType);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    public DopProperties getDopProperties() {
        return this.dopProperties;
    }

    @PostConstruct
    public void postConstruct() {
        String propertyServerName = this.environment.getProperty("dop.server.short-name");
        if (StringUtil.isBlank(propertyServerName)) {
            throw new FrameworkException("No server name was found, please configure it with property key dop.server.short-name = ${shortName}");
        }
        this.serverName = propertyServerName.toLowerCase();
    }

}
