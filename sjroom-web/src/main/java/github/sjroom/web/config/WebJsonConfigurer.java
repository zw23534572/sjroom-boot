package github.sjroom.web.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import github.sjroom.core.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Spring MVC 配置
 */
@Slf4j
@Configuration
public class WebJsonConfigurer implements WebMvcConfigurer {

	@Bean
	public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
		FastJsonHttpMessageConverter messageConverter = new FastJsonHttpMessageConverter();

		FastJsonConfig config = new FastJsonConfig();
		config.setSerializerFeatures(JsonUtil.getSerializerFeatures());
		messageConverter.setFastJsonConfig(config);

		// 将Long整行转换成String
		SerializeConfig serializeConfig = new SerializeConfig();
		serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
		serializeConfig.put(Long.class, ToStringSerializer.instance);
		serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
		config.setSerializeConfig(serializeConfig);

		messageConverter.setDefaultCharset(Charset.forName("UTF-8"));

		List<MediaType> supportedMediaTypes = new ArrayList<>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		messageConverter.setSupportedMediaTypes(supportedMediaTypes);
		return messageConverter;
	}


	/**
	 * 用fastjson来进行json格式转换
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		log.info("MvcConfigurer configureMessageConverters started");
		converters.add(this.fastJsonHttpMessageConverter());
	}

}
