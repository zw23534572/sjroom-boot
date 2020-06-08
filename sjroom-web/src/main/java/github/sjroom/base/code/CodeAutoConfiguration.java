package github.sjroom.base.code;

import github.sjroom.base.env.DopProperties;
import github.sjroom.base.env.ServerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * code 自动化配置
 *
 * @author manson.zhou
 */
@Configuration
@AutoConfigureAfter(ServerInfo.class)
public class CodeAutoConfiguration {

    @Autowired
    private ServerInfo serverInfo;

    @Bean
    @ConditionalOnMissingBean(CodeTranslator.class)
    public CodeTranslator codeTranslator(DopProperties dopProperties) {
        return new SimpleCodeTranslator(serverInfo, dopProperties);
    }
}
