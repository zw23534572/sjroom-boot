package github.sjroom.secrity.config;

import github.sjroom.secrity.filter.JWTAccessDeniedHandler;
import github.sjroom.secrity.filter.JWTAuthenticationEntryPoint;
import github.sjroom.secrity.filter.JWTAuthenticationFilter;
import github.sjroom.secrity.filter.JWTAuthorizationFilter;
import github.sjroom.core.utils.CollectionUtil;
import github.sjroom.secrity.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableConfigurationProperties(WebSecurityProperties.class)
@EnableWebSecurity
//添加annotation 支持,包括（prePostEnabled，securedEnabled...）
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Resource
	private UserDetailsService userDetailsService;
	@Autowired
	private WebSecurityProperties webSecurityProperties;
	//忽略需要认证的路径
	private List<String> noAuthentication = new ArrayList<>(16);

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordEncoder() {
			@Override
			public String encode(CharSequence rawPassword) {
				return MD5Util.md5(((String) rawPassword));
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				log.info("rawPassword:{}", (String) rawPassword);
				log.info("MD5Util.md5((String)rawPassword) rawPassword:{}", MD5Util.md5((String) rawPassword));
				log.info("encodedPassword:{}", encodedPassword);
				return encodedPassword.equals(MD5Util.md5((String) rawPassword));
			}
		};
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");//修改为添加而不是设置，* 最好改为实际的需要，我这是非生产配置，所以粗暴了一点
		configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
		configuration.addAllowedHeader("*");//这里很重要，起码需要允许 Access-Control-Allow-Origin
		// The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("token", "Cache-Control", "X-User-Agent", "Content-Type"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(this.noAuthentication.toArray(new String[this.noAuthentication.size()]));
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/api/**").authenticated()
			.anyRequest().permitAll()
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(new JWTAuthenticationEntryPoint())
			.accessDeniedHandler(new JWTAccessDeniedHandler());
		// 认证
		http.addFilterAt(new JWTAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
		// 授权
		http.addFilterAfter(new JWTAuthorizationFilter(authenticationManager(), userDetailsService), SessionManagementFilter.class);
		// 禁用缓存
		http.headers().cacheControl();
		// 开启跨域
		http.cors();
		// 由于使用的是JWT，我们这里不需要csrf
		http.csrf().disable();
		// 基于token，所以不需要session
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@PostConstruct
	public void afterPropertiesSet() {
		List<String> noAuthentication = this.webSecurityProperties.getNoAuthentication();
		if (!CollectionUtil.isEmpty(noAuthentication)) {
			this.noAuthentication = noAuthentication;
		}

		log.info("Load WebSecurityProperties: [forceOut: {}, validateAble: {}].",
			webSecurityProperties.isForceOut(), webSecurityProperties.isValidateAble());
	}
}
