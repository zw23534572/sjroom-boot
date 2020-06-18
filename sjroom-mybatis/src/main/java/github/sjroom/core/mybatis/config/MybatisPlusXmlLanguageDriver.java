package github.sjroom.core.mybatis.config;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;

/**
 * 修改自 mybatis-plus 继承 {@link XMLLanguageDriver} 重装构造函数, 使用自己的 MybatisDefaultParameterHandler
 *
 * @author manson.zhou
 */
public class MybatisPlusXmlLanguageDriver extends XMLLanguageDriver {

	@Override
	public ParameterHandler createParameterHandler(MappedStatement mappedStatement,
												   Object parameterObject, BoundSql boundSql) {
		// TODO 使用 MybatisDefaultParameterHandler 而不是 ParameterHandler
		return new MybatisPlusParameterHandler(mappedStatement, parameterObject, boundSql);
	}
}
