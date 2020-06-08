package github.sjroom.base.web.json;

import github.sjroom.base.context.servlet.ControllerCallContextFilter;
import github.sjroom.base.context.servlet.DopWebMvcConfigurer;
import github.sjroom.core.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 *
 */
@Slf4j
@Configuration
public class JsonConfiguration {
	@Bean
	public MappingJackson2HttpMessageConverter jacksonConverter() {
		return new JsonHttpMessageConverter(JsonUtil.mapper);
	}

	@Bean
	@ConditionalOnClass(MappingJackson2HttpMessageConverter.class)
	public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
		return jacksonConverter();
	}

	@Bean
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
	public FilterRegistrationBean controllerCallContextFilter1() {
		FilterRegistrationBean<ControllerCallContextFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new ControllerCallContextFilter());
		registrationBean.addUrlPatterns(DopWebMvcConfigurer.ApiMapping);
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return registrationBean;
	}
}
