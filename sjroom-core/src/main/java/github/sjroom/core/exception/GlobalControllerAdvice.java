package github.sjroom.core.exception;

import com.alibaba.fastjson.JSON;
import github.sjroom.core.RespVo;
import github.sjroom.core.code.BaseErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * GlobalControllerAdvice
 *
 * @author Tom.Zeng
 * @date 2019/3/23 16:54
 */
@Aspect
@Order(1)
@Component
@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

	private static final String TIME = "_start_time_";

	@Autowired
	private HttpServletRequest request;

	@Pointcut(
		"execution(* *..controller..*(..)) && "
			+ "(@within(org.springframework.stereotype.Controller)"
			+ " || @within(org.springframework.web.bind.annotation.RestController) )"
			+ " && (@annotation(org.springframework.web.bind.annotation.RequestMapping)"
			+ " || @annotation(org.springframework.web.bind.annotation.GetMapping) "
			+ " || @annotation(org.springframework.web.bind.annotation.PostMapping) "
			+ " || @annotation(org.springframework.web.bind.annotation.PutMapping) "
			+ " || @annotation(org.springframework.web.bind.annotation.DeleteMapping) "
			+ " || @annotation(org.springframework.web.bind.annotation.PatchMapping))"
	)
	public void pointcut() {
	}

	@Before(value = "pointcut()")
	public void before(JoinPoint joinPoint) {
		request.setAttribute(TIME, System.currentTimeMillis());
		log.info(">>>>> {}, param={}", request.getServletPath(), JSON.toJSONString(joinPoint.getArgs()));
	}

	@AfterReturning(value = "pointcut()", returning = "result")
	public void finish(Object result) {
		log.info("<<<<< {} << {}ms << {}", request.getServletPath(), time(), JSON.toJSONString(result));
	}

	@ExceptionHandler(Throwable.class)
	public RespVo handlerThrowable(Throwable e) {
		log.error("handlerThrowable ex:{}", e);
		RespVo respVo = RespVo.ok(BaseErrorCode.SYSTEM_ERROR, e.getMessage());
		log.info("<<<<< {} << {}ms << {}", request.getServletPath(), time(), JSON.toJSONString(respVo));
		return respVo;
	}

	@ExceptionHandler(BusinessException.class)
	public RespVo handlerServiceException(BusinessException e) {
		log.error("handlerServiceException ex:{}", e);
		RespVo respVo = RespVo.ok(e.getCode(), e.getMessage());
		log.info("<<<<< {} << {}ms << {}", request.getServletPath(), time(), JSON.toJSONString(respVo));
		return respVo;
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public RespVo handlerHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		log.error("handlerHttpRequestMethodNotSupportedException ex:{}", e);
		RespVo respVo = RespVo.ok(BaseErrorCode.PARAM_ERROR, e.getMessage());
		log.info("xxxxx {} << {}ms << {}", request.getServletPath(), time(), JSON.toJSONString(respVo));
		return respVo;
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public RespVo handlerHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		log.error("handlerHttpMessageNotReadableException ex:{}", e);
		RespVo respVo = RespVo.ok(BaseErrorCode.PARAM_ERROR, e.getMessage());
		log.info("xxxxx {} << {}ms << {}", request.getServletPath(), time(), JSON.toJSONString(respVo));
		return respVo;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public RespVo handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		StringBuilder builder = new StringBuilder(32);
		for (FieldError error : e.getBindingResult().getFieldErrors()) {
			builder.append(", [").append(error.getField()).append(":").append(error.getDefaultMessage()).append("]");
		}
		String msg = builder.substring(2);
		log.error("handlerMethodArgumentNotValidException ex:{}", e);
		RespVo respVo = RespVo.ok(BaseErrorCode.PARAM_ERROR, msg);
		log.info("xxxxx {} << {}ms << {}", request.getServletPath(), time(), JSON.toJSONString(respVo));
		return respVo;
	}

	private long time() {
		Object obj = request.getAttribute(TIME);
		return obj != null ? (System.currentTimeMillis() - (Long) obj) : 0;
	}
}
