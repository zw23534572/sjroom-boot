package github.sjroom.core.mybatis.injector.methods;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import github.sjroom.core.mybatis.injector.BizSqlMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;

/**
 * 根据ID 查询一条数据
 *
 * @author hubin
 * @since 2018-04-06
 */
@Slf4j
public class SelectByBizId extends AbstractBizIdMethod {

	@Override
	public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
		// 业务主键信息
		TableFieldInfo bizIdField = super.getBIdField(modelClass, tableInfo);
		if (bizIdField == null) {
			log.info("Entity {} 没有 @TableBId 注解.", modelClass);
			return null;
		}
		BizSqlMethod sqlMethod = BizSqlMethod.LOGIC_SELECT_BY_BID;
		SqlSource sqlSource = new RawSqlSource(configuration, String.format(sqlMethod.getSql(),
			sqlSelectColumns(tableInfo, false),
			tableInfo.getTableName(), bizIdField.getColumn(), bizIdField.getProperty(),
			tableInfo.getLogicDeleteSql(true, false)), Object.class);
		return this.addSelectMappedStatementForTable(mapperClass, sqlMethod.getMethod(), sqlSource, tableInfo);
	}
}
