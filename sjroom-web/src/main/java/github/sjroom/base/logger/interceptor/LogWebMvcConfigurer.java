package github.sjroom.base.logger.interceptor;

import github.sjroom.base.context.servlet.DopWebMvcConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LogWebMvcConfigurer implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 注册拦截器
		InterceptorRegistration registrationBean = registry.addInterceptor(new LogMdcInterceptorAdapter());
		// 配置拦截的路径
		registrationBean.addPathPatterns(DopWebMvcConfigurer.ApiMapping, DopWebMvcConfigurer.RmiMapping);
		// 配置不拦截的路径
		registrationBean.excludePathPatterns("/static/**", "/templates/**");
	}
}
