package github.sjroom.secrity.config;

import github.sjroom.secrity.filter.JWTAccessDeniedHandler;
import github.sjroom.secrity.filter.JWTAuthenticationEntryPoint;
import github.sjroom.secrity.filter.JWTAuthenticationFilter;
import github.sjroom.secrity.filter.JWTAuthorizationFilter;
import github.sjroom.core.utils.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties(WebSecurityProperties.class)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserDetailsService userDetailsService;
    @Autowired
    private WebSecurityProperties webSecurityProperties;
    //忽略需要认证的路径
    private List<String> noAuthentication = new ArrayList<>(16);


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
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