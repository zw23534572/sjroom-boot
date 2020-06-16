package github.sjroom.web.json;

import github.sjroom.core.utils.JsonUtil;
import github.sjroom.web.context.servlet.ControllerCallContextFilter;
import github.sjroom.web.context.servlet.DopWebMvcConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 *
 */
@Slf4j
@Configuration
public class JsonConfiguration {
	@Bean
	public JsonHttpMessageConverter jacksonConverter() {
		return new JsonHttpMessageConverter(JsonUtil.mapper);
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
