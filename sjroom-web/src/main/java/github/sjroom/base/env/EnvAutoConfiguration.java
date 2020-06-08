package github.sjroom.base.env;

import github.sjroom.core.extension.SpringExtensionLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 平台环境配置
 *
 * @author Katrel.Zhou
 * @version 1.0.0
 * @date 2019/5/20 10:30
 */
@Configuration
@EnableConfigurationProperties(DopProperties.class)
public class EnvAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SpringExtensionLoader extensionLoader() {
        return new SpringExtensionLoader();
    }

    @Bean(name = "dopEnvironment")
    @ConditionalOnMissingBean
    public DopEnvironmentAware environmentAware(DopProperties dopProperties) {
        return new DopEnvironmentAware(dopProperties);
    }

    @Bean
    @Primary
    public ServerInfo envServerInfo(ServerProperties serverProperties){
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setServerProperties(serverProperties);
        return serverInfo;
    }
}
