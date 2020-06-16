package github.sjroom.web.context;

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
public class ContentConfiguration {
//	@Bean
//	public JsonHttpMessageConverter jacksonConverter() {
//		return new JsonHttpMessageConverter();
//	}


	@Bean
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
	public FilterRegistrationBean controllerCallContextFilter() {
		FilterRegistrationBean<ControllerCallContextFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new ControllerCallContextFilter());
		registrationBean.addUrlPatterns("/api/*");
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return registrationBean;
	}
}
