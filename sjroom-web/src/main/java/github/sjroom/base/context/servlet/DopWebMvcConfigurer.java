package github.sjroom.base.context.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Auther: smj
 * @Date: 2019/07/29 18:47
 * @Version 1.8
 * @Description: springboot项目的基础WebMvcConfigurer实现
 */
@Slf4j
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class DopWebMvcConfigurer implements WebMvcConfigurer {


    public final static String ApiMapping = "/api/**";
    public final static String RmiMapping = "/rmi/**";
    public final static Pattern ApiPattern = Pattern.compile("^\\/api\\/.*", Pattern.CASE_INSENSITIVE);
    public final static Pattern RmiPattern = Pattern.compile("^\\/rmi\\/.*", Pattern.CASE_INSENSITIVE);


    public static boolean isApiRequest(HttpServletRequest request) {
        return ApiPattern.matcher(request.getServletPath()).matches();
    }

    public static boolean isRmiRequest(HttpServletRequest request) {
        return RmiPattern.matcher(request.getServletPath()).matches();
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(ApiMapping);
    }


}
