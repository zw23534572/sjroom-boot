package github.sjroom.core.mybatis.injector.methods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.methods.Insert;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import github.sjroom.core.mybatis.util.IdUtil;
import github.sjroom.core.utils.ObjectUtil;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * 插入一条数据（选择字段插入）
 *
 * @author manson.zhou
 * @since 2019-06-03
 */
public class InsertBizId extends Insert {

	@Override
	public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
		// 业务id属性
		String bIdFieldName = IdUtil.getBIdFieldName(modelClass);
		if (bIdFieldName == null) {
			return super.injectMappedStatement(mapperClass, modelClass, tableInfo);
		}
		// key 生成策略
		KeyGenerator keyGenerator = new NoKeyGenerator();
		SqlMethod sqlMethod = SqlMethod.INSERT_ONE;
		// 修改了 getAllInsertSqlColumnMaybeIf 方法，将 bizId 字段不使用 if 包裹
		String columnScript = SqlScriptUtils.convertTrim(getAllInsertSqlColumnMaybeIf(tableInfo, bIdFieldName),
			LEFT_BRACKET, RIGHT_BRACKET, null, COMMA);
		String valuesScript = SqlScriptUtils.convertTrim(getAllInsertSqlPropertyMaybeIf(tableInfo, bIdFieldName, null),
			LEFT_BRACKET, RIGHT_BRACKET, null, COMMA);
		String keyProperty = null;
		String keyColumn = null;
		// 表包含主键处理逻辑,如果不包含主键当普通字段处理
		if (StringUtils.isNotEmpty(tableInfo.getKeyProperty())) {
			if (tableInfo.getIdType() == IdType.AUTO) {
				// 自增主键
				keyGenerator = new Jdbc3KeyGenerator();
				keyProperty = tableInfo.getKeyProperty();
				keyColumn = tableInfo.getKeyColumn();
			} else {
				if (null != tableInfo.getKeySequence()) {
					keyGenerator = TableInfoHelper.genKeyGenerator(tableInfo, builderAssistant, sqlMethod.getMethod(), languageDriver);
					keyProperty = tableInfo.getKeyProperty();
					keyColumn = tableInfo.getKeyColumn();
				}
			}
		}
		String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), columnScript, valuesScript);
		SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
		return this.addInsertMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), sqlSource, keyGenerator, keyProperty, keyColumn);
	}

	/**
	 * 获取 insert 时候字段 sql 脚本片段
	 * <p>insert into table (字段) values (值)</p>
	 * <p>位于 "字段" 部位</p>
	 *
	 * <li> 自动选部位,根据规则会生成 if 标签 </li>
	 *
	 * @return sql 脚本片段
	 */
	private static String getAllInsertSqlColumnMaybeIf(TableInfo tableInfo, String bIdFieldName) {
		List<TableFieldInfo> fieldList = tableInfo.getFieldList();
		return tableInfo.getKeyInsertSqlColumn(true) + fieldList.stream().map(tableFieldInfo -> getInsertSqlColumnMaybeIf(tableFieldInfo, bIdFieldName))
			.collect(joining(NEWLINE));
	}

	/**
	 * 获取 insert 时候字段 sql 脚本片段
	 * <p>insert into table (字段) values (值)</p>
	 * <p>位于 "字段" 部位</p>
	 *
	 * <li> 根据规则会生成 if 标签 </li>
	 *
	 * @return sql 脚本片段
	 */
	private static String getInsertSqlColumnMaybeIf(TableFieldInfo tableFieldInfo, String bIdFieldName) {
		// 判断是否 bizId 字段
		String property = tableFieldInfo.getProperty();
		if (ObjectUtil.nullSafeEquals(property, bIdFieldName)) {
			return tableFieldInfo.getInsertSqlColumn();
		}
		return tableFieldInfo.getInsertSqlColumnMaybeIf();
	}


	/**
	 * 获取所有 insert 时候插入值 sql 脚本片段
	 * <p>insert into table (字段) values (值)</p>
	 * <p>位于 "值" 部位</p>
	 *
	 * <li> 自动选部位,根据规则会生成 if 标签 </li>
	 *
	 * @return sql 脚本片段
	 */
	private static String getAllInsertSqlPropertyMaybeIf(final TableInfo tableInfo, final String bIdFieldName, final String prefix) {
		final String newPrefix = prefix == null ? EMPTY : prefix;
		List<TableFieldInfo> fieldList = tableInfo.getFieldList();
		return tableInfo.getKeyInsertSqlProperty(newPrefix, true) + fieldList.stream()
			.map(i -> getInsertSqlPropertyMaybeIf(i, bIdFieldName, newPrefix)).collect(joining(NEWLINE));
	}

	/**
	 * 获取 insert 时候插入值 sql 脚本片段
	 * <p>insert into table (字段) values (值)</p>
	 * <p>位于 "值" 部位</p>
	 *
	 * <li> 根据规则会生成 if 标签 </li>
	 *
	 * @return sql 脚本片段
	 */
	private static String getInsertSqlPropertyMaybeIf(TableFieldInfo tableFieldInfo, String bIdFieldName, final String prefix) {
		// 判断是否 bizId 字段
		String property = tableFieldInfo.getProperty();
		if (ObjectUtil.nullSafeEquals(property, bIdFieldName)) {
			return tableFieldInfo.getInsertSqlProperty(prefix);
		}
		return tableFieldInfo.getInsertSqlPropertyMaybeIf(prefix);
	}
}
