package github.sjroom.web.context.servlet;

import github.sjroom.web.utils.Util2Web;
import github.sjroom.web.utils.WebUtil;
import github.sjroom.core.context.ContextConstants;
import github.sjroom.core.utils.Base64Util;
import github.sjroom.core.utils.JsonUtil;
import github.sjroom.core.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Spring boot 控制器 请求日志，方便代码调试
 * <p>
 * 根据自定义logger进行输出，level必须是debug以下才进行输出
 * * 使用方式:
 * * 例如 logging.level.mvc.api=debug 或者 logging.level.mvc.api.xxx.xxx.xxx=debug ,才将制定的请求进行log输出
 * 1  优化性能
 * 2，日志中可能包含敏感信息，在高级别环境中，默认是关闭的，在调试或者特殊需求，可以指定打开
 *
 * @author L.cm
 */
@Slf4j
@Aspect
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ControllerReqLogAspect {

    /**
     * AOP 环切 控制器 R 返回值
     * <p>
     * * @see GetMapping
     * * @see PostMapping
     * * @see PutMapping
     * * @see DeleteMapping
     * * @see PatchMapping
     *
     * @param point JoinPoint
     * @return Object
     * @throws Throwable 异常
     */
    @Around(
        "execution(public !static * *(..)) && "
            + "(@within(org.springframework.stereotype.Controller)"
            + " || @within(org.springframework.web.bind.annotation.RestController) )"
            + " && (@annotation(org.springframework.web.bind.annotation.RequestMapping)"
            + " || @annotation(org.springframework.web.bind.annotation.GetMapping) "
            + " || @annotation(org.springframework.web.bind.annotation.PostMapping) "
            + " || @annotation(org.springframework.web.bind.annotation.PutMapping) "
            + " || @annotation(org.springframework.web.bind.annotation.DeleteMapping) "
            + " || @annotation(org.springframework.web.bind.annotation.PatchMapping))"
    )
    public Object aroundApi(ProceedingJoinPoint point) throws Throwable {

        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = ms.getMethod();
        HttpServletRequest request = WebUtil.getRequest();
        Logger logger = request != null ? Util2Web.getLogger(request) : null;
        String requestURI = request != null ? request.getRequestURI() : null;
        String requestMethod = request != null ? request.getMethod() : null;
        long startNs = System.nanoTime();

        // 打印执行时间
        printRequestLog(request, logger, requestURI, requestMethod);


        Class<?> returnType = method.getReturnType();

        Object result = null;
        if (void.class == returnType) {
            point.proceed();
        } else {
            result = point.proceed();
        }

        printResponseLog(logger, requestURI, requestMethod, startNs, returnType, result);

        return result;

    }


    private void printRequestLog(HttpServletRequest request, Logger logger, String requestURI, String requestMethod) {
        try {

            if (logger == null //兼容单元测试
                || !logger.isDebugEnabled()) {
                return;
            }
//            Object[] args = point.getArgs();
            // 构建成一条长 日志，避免并发下日志错乱
            StringBuilder beforeReqLog = new StringBuilder(300);
            // 日志参数
            List<Object> beforeReqArgs = new ArrayList<>();
            beforeReqLog.append("\n\n================  Request Start  ================\n");
            // 打印路由
            beforeReqLog.append("===> {}: {}\n");
            beforeReqArgs.add(requestMethod);
            beforeReqArgs.add(requestURI);

            // 只打印透传的 header，打印时 Base64 解密
            Stream.of(ContextConstants.PLAT_CONTEXT_ID, ContextConstants.BUSINESS_CONTEXT_ID)
                .forEach(headerName -> {
                    String headerValue = request.getHeader(headerName);
                    if (StringUtil.isNotBlank(headerValue)) {
                        beforeReqLog.append("===Headers===  {}: {}\n");
                        beforeReqArgs.add(headerName);
                        beforeReqArgs.add(Base64Util.decode(headerValue));
                    }
                });
            beforeReqLog.append("================   Request End   ================\n");
            logger.debug(beforeReqLog.toString(), beforeReqArgs.toArray());
        } catch (Exception e) {
            logger.warn("printRequestLog failed;ex:{},msg:{}", e.getClass(), e.getMessage());
        }

    }

    private void printResponseLog(Logger logger, String requestURI, String requestMethod, long startNs, Class<?> returnType, Object result) {
        try {
            if (logger == null || !logger.isDebugEnabled()) {
                return;
            }
            // aop 执行后的日志
            StringBuilder afterReqLog = new StringBuilder(200);
            afterReqLog.append("\n\n================  Response Start  ================\n");
            // 日志参数
            List<Object> afterReqArgs = new ArrayList<>();
            // 非 null 打印返回结构体
            if (result != null && JsonUtil.mapper.canSerialize(returnType)) {
                afterReqLog.append("===Result===  {}\n");
                afterReqArgs.add(JsonUtil.toString(result));
            }
            long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
            afterReqLog.append("<=== {}: {} ({} ms)\n");
            afterReqArgs.add(requestMethod);
            afterReqArgs.add(requestURI);
            afterReqArgs.add(tookMs);
            afterReqLog.append("================   Response End   ================\n");

            logger.debug(afterReqLog.toString(), afterReqArgs.toArray());
        } catch (Exception e) {
            logger.warn("printResponseLog failed;ex:{},msg:{}", e.getClass(), e.getMessage());
        }

    }

}
