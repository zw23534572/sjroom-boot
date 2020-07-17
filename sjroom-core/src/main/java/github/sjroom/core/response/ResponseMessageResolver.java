package github.sjroom.core.response;

import github.sjroom.core.exception.FrameworkException;
import github.sjroom.core.utils.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Manson.zhou
 * @date 2019/5/28 9:49
 * @desc 手动对response进行resolver, 适用于鉴权等服务
 */
public class ResponseMessageResolver {

	public static void successResolve(HttpServletRequest request, HttpServletResponse response, Object data) {
		resolve(request, response, data);
	}

	public static void failResolve(HttpServletRequest request, HttpServletResponse response, String code,
								   Object... args) {
		resolve(request, response, RespVo.failure(code, args));
	}

	private static void resolve(HttpServletRequest request, HttpServletResponse response, Object data) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		try (PrintWriter writer = response.getWriter()) {
			JsonUtil.writeValue(writer, data);
			writer.flush();
		} catch (IOException e) {
			throw new FrameworkException(e);
		}
	}
}

