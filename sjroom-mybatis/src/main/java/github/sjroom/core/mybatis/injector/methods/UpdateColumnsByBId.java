package github.sjroom.core.mybatis.injector.methods;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import github.sjroom.core.mybatis.injector.BizSqlMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 根据 id 更新指定的字段，不进行 null 判断
 *
 * @author manson.zhou
 */
@Slf4j
public class UpdateColumnsByBId extends AbstractBizIdMethod {
	public static final String SQL_METHOD = "updateColumnsByBId";

	@Override
	public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
		// 业务主键信息
		TableFieldInfo bizIdField = super.getBIdField(modelClass, tableInfo);
		if (bizIdField == null) {
			log.info("Entity {} 没有 @TableBId 注解.", modelClass);
			return null;
		}
		// <script>UPDATE table SET xx = xx WHERE %s=#{%s} %s</script>
		// <script>UPDATE table ${sqlSet} WHERE %s=#{%s}</script>
//		<foreach collection="coll" item="item" separator=",">
//			${item.column} = #{item.field}
//		</foreach>
		String sql = String.format(BizSqlMethod.UPDATE_BY_BID.getSql(), tableInfo.getTableName(),
			" SET " + SqlScriptUtils.convertForeach("${item.column} = #{item.field}", COLLECTION, null, "item", COMMA),
			bizIdField.getColumn(), ENTITY_DOT + bizIdField.getProperty(), EMPTY);
		SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
		return addUpdateMappedStatement(mapperClass, modelClass, SQL_METHOD, sqlSource);
	}

}
