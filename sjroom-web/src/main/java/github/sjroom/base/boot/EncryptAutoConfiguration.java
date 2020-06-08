package github.sjroom.base.boot;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 数据库等密码加密配置
 *
 * @author manson.zhou
 */
@Configuration
@EnableEncryptableProperties
public class EncryptAutoConfiguration {
}
