package github.sjroom.core.mybatis.injector.methods;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import github.sjroom.core.mybatis.injector.BizSqlMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 根据ID集合，批量查询数据
 *
 * @author hubin
 * @since 2018-04-06
 */
@Slf4j
public class SelectBatchByBizIds extends AbstractBizIdMethod {

	@Override
	public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
		// 业务主键信息
		TableFieldInfo bizIdField = super.getBIdField(modelClass, tableInfo);
		if (bizIdField == null) {
			log.info("Entity {} 没有 @TableBId 注解.", modelClass);
			return null;
		}
		BizSqlMethod sqlMethod = BizSqlMethod.LOGIC_SELECT_BATCH_BY_BIDS;
		// 组装
		SqlSource sqlSource = languageDriver.createSqlSource(configuration, String.format(sqlMethod.getSql(),
			sqlSelectColumns(tableInfo, false), tableInfo.getTableName(),
			bizIdField.getColumn(),
			SqlScriptUtils.convertForeach("#{item}", COLLECTION, null, "item", COMMA),
			tableInfo.getLogicDeleteSql(true, false)), Object.class);
		return addSelectMappedStatementForTable(mapperClass, sqlMethod.getMethod(), sqlSource, tableInfo);
	}
}
