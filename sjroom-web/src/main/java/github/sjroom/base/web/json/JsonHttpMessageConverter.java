package github.sjroom.base.web.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 将消息实体转换成json
 * <p>
 * Created by Zhouwei
 *
 * @date 2018-1-10
 */
public class JsonHttpMessageConverter extends MappingJackson2HttpMessageConverter {

	protected JsonHttpMessageConverter(ObjectMapper objectMapper) {
		super(objectMapper);
	}

	/**
	 * spring mvc 请求返回时，会经过此方法进行实体转换
	 *
	 * @param object        返回实体
	 * @param type
	 * @param outputMessage
	 * @throws IOException
	 * @throws HttpMessageNotWritableException
	 */
	@Override
	protected void writeInternal(Object object, @Nullable Type type, HttpOutputMessage outputMessage)
		throws IOException, HttpMessageNotWritableException {
		super.writeInternal(object, type, outputMessage);
	}

}
