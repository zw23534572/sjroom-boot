package github.sjroom.base.logger.interceptor;

import github.sjroom.base.logger.contants.LogConstants;
import github.sjroom.core.context.ContextConstants;
import github.sjroom.core.utils.StringUtil;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogMdcInterceptorAdapter extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestId = request.getHeader(ContextConstants.CALL_REQUEST_ID);
		if (StringUtil.isBlank(requestId)) {
			requestId = StringUtil.getUUID();
		}
		ThreadContext.put(LogConstants.MDC_REQUEST_ID_KEY, requestId);
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
		ThreadContext.remove(LogConstants.MDC_REQUEST_ID_KEY);
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}
}
