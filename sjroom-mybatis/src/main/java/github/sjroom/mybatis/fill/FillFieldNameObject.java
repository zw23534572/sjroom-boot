package github.sjroom.mybatis.fill;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * @author george.ouyang
 */
@Data
public class FillFieldNameObject {

    private Set<FieldInfo> fieldInfoSet = Sets.newHashSetWithExpectedSize(8);

    @Data
    @AllArgsConstructor
    public static class FieldInfo {
        /**
         * 赋值字段
         */
        private Field field;
        /**
         * 调用的第三方class
         */
        private Class invokeClass;
        /**
         * 调用的第三方的方法
         */
        private String invokeMethod;
        /**
         * 调用的第三方的方法
         */
        private Set invokeArgs;
        /**
         * 调用后的值
         */
        private Map mapData;
    }


}
