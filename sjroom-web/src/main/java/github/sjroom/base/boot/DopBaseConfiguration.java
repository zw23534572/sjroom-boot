package github.sjroom.base.boot;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @Author: manson.zhou
 * @Date: 2019/7/3 9:15
 * @Desc: ****
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@PropertySource({
    "classpath:/dop-base/dop-base.properties"
})
public class DopBaseConfiguration {
}
