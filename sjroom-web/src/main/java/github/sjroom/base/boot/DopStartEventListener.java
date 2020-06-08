package github.sjroom.base.boot;

import github.sjroom.core.utils.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;

/**
 * 项目启动事件通知
 *
 * @author L.cm
 */
@Slf4j
@Configuration
public class DopStartEventListener {

    @Async
    @Order
    @EventListener(WebServerInitializedEvent.class)
    public void afterStart(WebServerInitializedEvent event) {
        WebServerApplicationContext applicationContext = event.getApplicationContext();
        Environment environment = applicationContext.getEnvironment();
        String appName = environment.getProperty("spring.application.name");
        int localPort = event.getWebServer().getPort();
        String profile = StringUtils.arrayToCommaDelimitedString(environment.getActiveProfiles());
        // level.warn 保证在log4j和控制台同时输出
        log.warn("\n---[{}]---启动完成，当前使用的端口:[{}]，环境变量:[{}]---", appName, localPort, profile);
        // 如果有 web，打印开发阶段的 web ui 地址
        if (ClassUtil.isPresent("springfox.documentation.spring.web.plugins.Docket", null)) {
			System.out.println(String.format("\nhttp://localhost:%s/doc.html", localPort));
        } else {
			System.out.println(String.format("\nhttp://localhost:%s", localPort));
        }
    }
}
