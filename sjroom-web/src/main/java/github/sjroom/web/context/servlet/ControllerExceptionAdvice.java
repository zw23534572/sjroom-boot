package github.sjroom.web.context.servlet;

import github.sjroom.core.exception.BusinessException;
import github.sjroom.core.utils.CollectionUtil;
import github.sjroom.core.utils.Exceptions;
import github.sjroom.core.utils.JsonUtil;
import github.sjroom.core.utils.ObjectUtil;
import github.sjroom.web.logger.contants.LogConstants;
import github.sjroom.web.utils.Util2Web;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * mvc 基础的异常拦截和处理器
 */

@Slf4j
@ToString
@Configuration
@RestControllerAdvice
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ControllerExceptionAdvice implements InitializingBean {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Map<String, Object> handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		Map<String, Object> values = new HashMap<>();

		Integer httpStatus = null;
		String code = null;
		// xReqId
		Optional.ofNullable(ThreadContext.get(LogConstants.MDC_REQUEST_ID_KEY))
			.ifPresent(v -> values.put(LogConstants.MDC_REQUEST_ID_KEY, v));
		values.put(LogConstants.ERROR_MESSAGE, ex.getMessage());
		values.put(LogConstants.ERROR_DATE_TIME, new Date());

		// 本地api异常
		if (ex instanceof BusinessException) {
			BusinessException aeEx = (BusinessException) ex;
			code = aeEx.getCode();
			Optional.ofNullable(aeEx.getValues())
				.filter(vs -> !CollectionUtil.isEmpty(vs))
				.ifPresent(vs -> values.putAll(vs));
			httpStatus = aeEx.getHttpStatus();
		} else if (ex instanceof MethodArgumentNotValidException) {
			httpStatus = HttpServletResponse.SC_BAD_REQUEST;
			code = Optional.ofNullable(((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors())
				.map(errors -> errors.stream().findFirst().map(FieldError::getDefaultMessage).get())
				.orElse(String.valueOf(httpStatus));
			// 断言异常
		} else if (ex instanceof IllegalArgumentException
			|| ex instanceof ValidationException // 验证器异常
			|| ex instanceof ServletException) {  // servlet异常等
			// 404 处理
			httpStatus = (ex instanceof NoHandlerFoundException) ? HttpServletResponse.SC_NOT_FOUND : HttpServletResponse.SC_BAD_REQUEST;
			code = String.valueOf(httpStatus);
			values.put(LogConstants.ERROR_DETAIL_MESSAGE, ex.getMessage());
			values.put(LogConstants.ERROR_MESSAGE, "参数校验失败，请确认后再提交");
		}

		// code不存在,代表未知(不处理)异常
		if (StringUtils.isBlank(code)) {
//			code = InnerError;
			// api 服务的message需要友好
			if (DopWebMvcConfigurer.isApiRequest(request)) {
				values.put(LogConstants.ERROR_MESSAGE, "抱歉，系统服务中断，请稍后再试。如多次尝试失败，请与系统管理员联系");
			}
		}

		values.put(LogConstants.ERROR_CODE, code);
		if (httpStatus == null) {
			httpStatus = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}

		response.setStatus(httpStatus);

		logException(request, httpStatus, response, ex);
		fillDebug(request, ex, values);
		return values;
	}

	/**
	 * 将url中的/替换成.后，再设置相应的logger(可动态设置),可以在后台打印出相应的日志
	 * 例如:api/erp-web/user/info ===>   mvc.api.erp-web.user.info = debug
	 *
	 * @param request
	 * @param response
	 * @param ex
	 */
	protected void logException(HttpServletRequest request, int httpStatus, HttpServletResponse response, Exception ex) {
		Logger logger = Util2Web.getLogger(request);
		// 500 按error级别输出，400按warn级别输出
		boolean bizError = httpStatus >= HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		LoggerPrint logPrinter = bizError ? logger::error : logger::warn;
//		if (logger.isTraceEnabled()) {
//			logPrinter.print("[trace.enabled].request={}", UtilJson.toString(Util2Web.getRequestInfo(request)), ex);
//		} else
		if (logger.isDebugEnabled()) {
			logPrinter.print("[debug.enabled].query={}, params={}", request.getQueryString(), JsonUtil.toJson(request.getParameterMap()), ex);
		} else if (logger.isInfoEnabled() && bizError) {// 非bizError,强制按warn输出,降低日志量
			logPrinter.print("[info.enabled].params={}, ex:", JsonUtil.toJson(request.getParameterMap()), ex);
		} else if (logger.isWarnEnabled()) {
			logPrinter.print("[warn.enabled].ex={}", "name:" + ex.getClass().getSimpleName() + ",message:" + ex.getMessage());
		}
//		else if (logger.isErrorEnabled()) {
//			logPrinter.print("[error.enabled].ex={}", "name:" + ex.getClass().getSimpleName());
//		}
	}

	private interface LoggerPrint {
		/**
		 * 打印日志
		 *
		 * @param format
		 * @param arguments
		 */
		void log(String format, Object... arguments);

		default void print(String format, Object... arguments) {
			this.log(format, arguments);
		}
	}

	protected void fillDebug(HttpServletRequest request, Exception ex, Map<String, Object> values) {
		if (ObjectUtil.isNotEmpty((request.getHeader("stackTrace")))) {
			values.put("stackTrace", Exceptions.getStackTraceAsString(ex));
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("FrameworkMvcExceptionAdvice initialization is complete， param : {}", this);
	}


}
