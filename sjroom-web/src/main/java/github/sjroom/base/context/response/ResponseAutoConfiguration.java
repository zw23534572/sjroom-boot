package github.sjroom.base.context.response;

import github.sjroom.base.code.CodeTranslator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: manson.zhou
 * @Date: 2019/7/29 12:00
 * @Desc: ****
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ResponseAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ResponseMessageResolver defaultResponseMessageResolver(CodeTranslator codeTranslator) {
        return new DefaultResponseMessageResolver(codeTranslator);
    }
}
