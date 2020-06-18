package github.sjroom.core.mybatis.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import github.sjroom.core.mybatis.annotation.TransactionEnhance;
import github.sjroom.core.mybatis.handler.DopLocalDateTimeTypeHandler;
import github.sjroom.core.mybatis.handler.DopLocalDateTypeHandler;
import github.sjroom.core.mybatis.handler.DopLocalTimeTypeHandler;
import github.sjroom.core.mybatis.injector.TableBIdSqlInjector;
import github.sjroom.core.mybatis.plugins.PerformanceInterceptor;
import org.apache.ibatis.type.EnumTypeHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * MybatisPlus配置
 *
 * @author L.cm
 */
@Configuration
@PropertySource({
	"classpath:/dop-mybatis/mybatis.properties"
})
public class MybatisPlusConfig {

	@Bean
	public TransactionEnhance genSpringAopWrapper() {
		return new TransactionEnhance();
	}

	/**
	 * mybatis-plus 乐观锁拦截器
	 */
	@Bean
	public OptimisticLockerInterceptor optimisticLockerInterceptor() {
		return new OptimisticLockerInterceptor();
	}

	/**
	 * mybatis-plus分页插件
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}

	/**
	 * sql 注入
	 */
	@Bean
	public ISqlInjector sqlInjector() {
		return new TableBIdSqlInjector();
	}

	/**
	 * SQL执行效率插件
	 *
	 * @return PerformanceInterceptor
	 */
	@Configuration
	@Profile({"dev1", "dev2"})
	@ConditionalOnProperty(value = "mybatis-plus.sql-log.enable", matchIfMissing = true)
	public static class MybatisPlusSqlLog {

		@Bean
		public PerformanceInterceptor performanceInterceptor(Environment environment) {
			return new PerformanceInterceptor();
		}
	}

	/**
	 * IEnum 枚举配置
	 */
	@Bean
	public ConfigurationCustomizer configurationCustomizer() {
		return new MybatisPlusCustomizers();
	}

	/**
	 * MetaObjectHandler
	 */
	@Bean
	public MetaObjectHandler metaObjectHandler(ObjectProvider<IMetaObjectDataSources> objectProvider) {
		IMetaObjectDataSources metaObjectDataSources = objectProvider.getIfAvailable(() -> new IMetaObjectDataSources() {
		});
		return new MybatisPlusMetaObjectHandler(metaObjectDataSources);
	}

	/**
	 * 自定义配置
	 */
	public static class MybatisPlusCustomizers implements ConfigurationCustomizer {

		@Override
		public void customize(MybatisConfiguration configuration) {
			configuration.getLanguageRegistry().setDefaultDriverClass(MybatisPlusXmlLanguageDriver.class);
			configuration.setDefaultEnumTypeHandler(EnumTypeHandler.class);
			configuration.getTypeHandlerRegistry().register(LocalDate.class, DopLocalDateTypeHandler.class);
			configuration.getTypeHandlerRegistry().register(LocalDateTime.class, DopLocalDateTimeTypeHandler.class);
			configuration.getTypeHandlerRegistry().register(LocalTime.class, DopLocalTimeTypeHandler.class);

		}
	}

}
