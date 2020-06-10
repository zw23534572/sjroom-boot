package github.sjroom.mybatis.util;

import com.google.common.collect.Maps;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;

import java.beans.PropertyDescriptor;
import java.util.Map;

public class AddPropertiesUtil {

    private static Logger logger = LoggerFactory.getLogger(AddPropertiesUtil.class);

    public static Object getTarget(Object dest, Map<String, Object> addProperties) {
        // get property map
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(dest);
        Map<String, Class> propertyMap = Maps.newHashMap();
        for (PropertyDescriptor d : descriptors) {
            if (!"class".equalsIgnoreCase(d.getName())) {
                propertyMap.put(d.getName(), d.getPropertyType());
            }
        }
        // add extra properties
        addProperties.forEach((k, v) -> propertyMap.put(k, v.getClass()));
        // new dynamic bean
        DynamicBean dynamicBean = new DynamicBean(dest.getClass(), propertyMap);
        // add old value
        propertyMap.forEach((k, v) -> {
            try {
                // filter extra properties
                if (!addProperties.containsKey(k)) {
                    dynamicBean.setValue(k, propertyUtilsBean.getNestedProperty(dest, k));
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
        // add extra value
        addProperties.forEach((k, v) -> {
            try {
                dynamicBean.setValue(k, v);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
        Object target = dynamicBean.getTarget();
        return target;
    }

    public static class DynamicBean {
        /**
         * 目标对象
         */
        private Object target;

        /**
         * 属性集合
         */
        private BeanMap beanMap;

        public DynamicBean(Class superclass, Map<String, Class> propertyMap) {
            this.target = generateBean(superclass, propertyMap);
            this.beanMap = BeanMap.create(this.target);
        }


        /**
         * bean 添加属性和值
         *
         * @param property
         * @param value
         */
        public void setValue(String property, Object value) {
            beanMap.put(property, value);
        }

        /**
         * 获取属性值
         *
         * @param property
         * @return
         */
        public Object getValue(String property) {
            return beanMap.get(property);
        }

        /**
         * 获取对象
         *
         * @return
         */
        public Object getTarget() {
            return this.target;
        }


        /**
         * 根据属性生成对象
         *
         * @param superclass
         * @param propertyMap
         * @return
         */
        private Object generateBean(Class superclass, Map<String, Class> propertyMap) {
            BeanGenerator generator = new BeanGenerator();
            if (null != superclass) {
                generator.setSuperclass(superclass);
            }
            BeanGenerator.addProperties(generator, propertyMap);
            return generator.create();
        }
    }
}
