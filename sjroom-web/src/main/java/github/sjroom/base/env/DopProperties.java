package github.sjroom.base.env;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 平台配置
 *
 * @author Katrel.Zhou
 * @version 1.0.0
 * @date 2019/5/17 17:44
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties("dop")
public class DopProperties {
    private Server server;

    @Getter
    @Setter
    public static class Server {
        /**
         * 三位的服务名缩写（小写）
         */
        private String shortName;
    }
}
