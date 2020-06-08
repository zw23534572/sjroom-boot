package github.sjroom.base.context.async;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 异步配置
 *
 * @author L.cm
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties("dop.async")
public class DopAsyncProperties {
	/**
	 * 异步核心线程数，默认：2
	 */
	private int corePoolSize = 8;
	/**
	 * 异步最大线程数，默认：50
	 */
	private int maxPoolSize = 50;
	/**
	 * 队列容量，默认：100
	 */
	private int queueCapacity = 100;
	/**
	 * 线程存活时间，默认：60
	 */
	private int keepAliveSeconds = 60;
}
