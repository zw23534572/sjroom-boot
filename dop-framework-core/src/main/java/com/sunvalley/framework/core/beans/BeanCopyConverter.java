package com.sunvalley.framework.core.beans;

import com.sunvalley.framework.core.utils.ClassUtil;
import com.sunvalley.framework.core.utils.ConvertUtil;
import com.sunvalley.framework.core.utils.ReflectUtil;
import com.sunvalley.framework.core.utils.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Converter;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.lang.Nullable;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 组合 spring cglib Converter 和 spring ConversionService
 *
 * @author L.cm
 */
@Slf4j
@AllArgsConstructor
public class BeanCopyConverter implements Converter {
    private static final ConcurrentMap<String, TypeDescriptor> TYPE_CACHE = new ConcurrentHashMap<>(64);
    private final Class<?> sourceClazz;
    private final Class<?> targetClazz;

    /**
     * cglib convert
     *
     * @param value     源对象属性
     * @param target    目标对象属性类
     * @param fieldName 目标的field名，原为 set 方法名，AbstractBeanCopier 里做了更改
     * @return {Object}
     */
    @Override
    @Nullable
    public Object convert(Object value, Class target, final Object fieldName) {
        if (value == null) {
            return null;
        }
        // 类型一样，不需要转换
        if (ClassUtil.isAssignableValue(target, value)) {
            return value;
        }
        try {
            TypeDescriptor sourceDescriptor = BeanCopyConverter.getTypeDescriptor(sourceClazz, (String) fieldName);
            TypeDescriptor targetDescriptor = BeanCopyConverter.getTypeDescriptor(targetClazz, (String) fieldName);
            return ConvertUtil.convert(value, sourceDescriptor, targetDescriptor);
        } catch (Throwable e) {
            log.warn("BeanCopyConverter error", e);
            return null;
        }
    }

    private static TypeDescriptor getTypeDescriptor(final Class<?> clazz, final String fieldName) {
        String srcCacheKey = clazz.getName() + fieldName;
        // 忽略抛出异常的函数，定义完整泛型，避免编译问题
        Try.UncheckedFunction<String, TypeDescriptor> uncheckedFunction = (key) -> {
            // 这里 property 理论上不会为 null
            Field field = ReflectUtil.getField(clazz, fieldName);
            if (field == null) {
                throw new NoSuchFieldException(fieldName);
            }
            return new TypeDescriptor(field);
        };
        return TYPE_CACHE.computeIfAbsent(srcCacheKey, Try.of(uncheckedFunction));
    }
}
