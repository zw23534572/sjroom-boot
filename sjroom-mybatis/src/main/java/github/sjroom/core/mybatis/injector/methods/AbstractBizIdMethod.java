package github.sjroom.core.mybatis.injector.methods;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import github.sjroom.core.mybatis.util.IdUtil;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * 业务id处理
 *
 * @author manson.zhou
 */
public abstract class AbstractBizIdMethod extends AbstractMethod {
    /**
     * 储存反射类表信息
     */
    private static final ConcurrentMap<Class<?>, TableFieldInfo> TABLE_BID_INFO_CACHE = new ConcurrentHashMap<>();

    /**
     * 获取 bid 属性信息
     *
     * @param modelClass 模型
     * @param tableInfo  表信息
     * @return TableFieldInfo
     */
    protected TableFieldInfo getBIdField(Class<?> modelClass, TableInfo tableInfo) {
        return TABLE_BID_INFO_CACHE.computeIfAbsent(modelClass, (key) -> getTableFieldInfo(key, tableInfo));
    }

    private TableFieldInfo getTableFieldInfo(Class<?> modelClass, TableInfo tableInfo) {
        String fieldName = IdUtil.getBIdFieldName(modelClass);
        if (fieldName == null) {
            return null;
        }
        List<TableFieldInfo> fieldInfoList = tableInfo.getFieldList();
        for (TableFieldInfo tableFieldInfo : fieldInfoList) {
            String property = tableFieldInfo.getProperty();
            if (fieldName.equals(property)) {
                return tableFieldInfo;
            }
        }
        return null;
    }
}
