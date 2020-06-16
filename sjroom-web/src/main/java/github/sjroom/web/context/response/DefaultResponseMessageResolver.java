package github.sjroom.web.context.response;


import github.sjroom.web.code.CodeTranslator;
import github.sjroom.core.code.IResultCode;
import github.sjroom.core.exception.FrameworkException;
import github.sjroom.core.utils.JsonUtil;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Katrel.Zhou
 * @date 2019/5/28 9:49
 * @desc 手动对response进行resolver, 适用于鉴权等服务
 */
@AllArgsConstructor
public class DefaultResponseMessageResolver implements ResponseMessageResolver {

	private final static String STATE_CODE = "stateCode";
	private final static String STATE_MSG = "stateMsg";
	private final CodeTranslator codeTranslator;

	@Override
	public void successResolve(HttpServletRequest request, HttpServletResponse response, Object data) {
		resolve(request, response, data);
	}

	@Override
	public void failResolve(HttpServletRequest request, HttpServletResponse response, String code, String message,
							Object... args) {
		Map<String, Object> result = new HashMap<>();
		CodeTranslator.TranslatedInfo translatedInfo = codeTranslator.translation(code, message, args);
		result.put(STATE_CODE, translatedInfo.getCode());
		result.put(STATE_MSG, translatedInfo.getMessage());
		resolve(request, response, result);
	}

	@Override
	public void failResolve(HttpServletRequest request, HttpServletResponse response, IResultCode resultCode,
							Object... args) {
		failResolve(request, response, resultCode.getCode(), resultCode.getMsg(), args);
	}

	private void resolve(HttpServletRequest request, HttpServletResponse response, Object data) {
		try (PrintWriter writer = response.getWriter()) {
			writer.print(JsonUtil.toJson(data));
			writer.flush();
		} catch (IOException e) {
			throw new FrameworkException(e);
		}
	}
}

