package github.sjroom.web.result;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;

@Slf4j
@RestControllerAdvice
public class ResultWrapper implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		Method method = returnType.getMethod();
		if (method.getAnnotation(ApiOperation.class) != null) {
			return true;
		}
		return false;
	}


	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
								  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
								  ServerHttpResponse response) {
		// 如果返回的已经是包装类(是CommonResponse子孙类),则直接返回
		if (body != null && body instanceof Result) {
			return body;
		}

		// 修改返回值类型
		return new Result(body);
	}

}
