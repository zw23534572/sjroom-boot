package github.sjroom.core.mybatis.injector.methods;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import github.sjroom.core.mybatis.injector.BizSqlMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.Objects;

import static java.util.stream.Collectors.joining;

/**
 * 根据 ID 更新有值字段
 *
 * @author hubin
 * @since 2018-04-06
 */
@Slf4j
public class UpdateByBizId extends AbstractBizIdMethod {

	@Override
	public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
		// 业务主键信息
		TableFieldInfo bizIdField = super.getBIdField(modelClass, tableInfo);
		if (bizIdField == null) {
			log.info("Entity {} 没有 @TableBId 注解.", modelClass);
			return null;
		}
		boolean logicDelete = tableInfo.isLogicDelete();
		BizSqlMethod sqlMethod = BizSqlMethod.UPDATE_BY_BID;
		final String additional = optlockVersion() + tableInfo.getLogicDeleteSql(true, false);

		String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(),
			sqlSet(logicDelete, tableInfo, bizIdField),
			bizIdField.getColumn(), ENTITY_DOT + bizIdField.getProperty(), additional);
		SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
		return addUpdateMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), sqlSource);
	}

	private String sqlSet(boolean logic, TableInfo table, TableFieldInfo bizIdField) {
		String sqlScript = getAllSqlSet(logic, table, bizIdField);
		sqlScript = SqlScriptUtils.convertIf(sqlScript, String.format("%s != null", ENTITY), true);
		return SqlScriptUtils.convertSet(sqlScript);
	}

	/**
	 * 获取所有的 sql set 片段
	 *
	 * @param ignoreLogicDelFiled 是否过滤掉逻辑删除字段
	 * @return sql 脚本片段
	 */
	private static String getAllSqlSet(boolean ignoreLogicDelFiled, TableInfo table, TableFieldInfo bizIdField) {
		// bid 更新的时候要去除 set bid = xxx，因为根据业务id进行更新的时候有值也不能 set
		return table.getFieldList().stream()
			.filter(i -> {
				if (ignoreLogicDelFiled) {
					return !(table.isLogicDelete() && i.isLogicDelete());
				}
				return true;
			})
			.filter(tableFieldInfo -> !tableFieldInfo.getProperty().equals(bizIdField.getProperty()))
			.map(i -> i.getSqlSet(ENTITY_DOT))
			.filter(Objects::nonNull)
			.collect(joining(NEWLINE));
	}
}
