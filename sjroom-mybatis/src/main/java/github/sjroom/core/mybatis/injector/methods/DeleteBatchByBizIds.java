package github.sjroom.core.mybatis.injector.methods;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import github.sjroom.core.mybatis.injector.BizSqlMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 根据 ID 集合删除
 *
 * @author hubin
 * @since 2018-04-06
 */
@Slf4j
public class DeleteBatchByBizIds extends AbstractBizIdMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        // 业务主键信息
        TableFieldInfo bizIdField = super.getBIdField(modelClass, tableInfo);
        if (bizIdField == null) {
            log.info("Entity {} 没有 @TableBId 注解.", modelClass);
            return null;
        }
        String sql;
        BizSqlMethod sqlMethod = BizSqlMethod.LOGIC_DELETE_BATCH_BY_BIDS;
        if (tableInfo.isLogicDelete()) {
            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlLogicSet(tableInfo),
                bizIdField.getColumn(),
                SqlScriptUtils.convertForeach("#{item}", COLLECTION, null, "item", COMMA),
                tableInfo.getLogicDeleteSql(true, false));
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
            return addUpdateMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), sqlSource);
        } else {
            sqlMethod = BizSqlMethod.DELETE_BATCH_BY_BIDS;
            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(),
                bizIdField.getColumn(),
                SqlScriptUtils.convertForeach("#{item}", COLLECTION, null, "item", COMMA));
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
            return this.addDeleteMappedStatement(mapperClass, sqlMethod.getMethod(), sqlSource);
        }
    }
}
