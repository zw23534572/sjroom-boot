package github.sjroom.core.mybatis.fill;


import com.google.common.collect.Lists;
import github.sjroom.core.exception.BusinessException;
import github.sjroom.core.extension.SpringExtensionLoader;
import github.sjroom.core.mybatis.annotation.FillFieldName;
import github.sjroom.core.mybatis.page.PageResult;
import github.sjroom.core.utils.AddPropertiesUtil;
import github.sjroom.core.utils.CollectionUtil;
import github.sjroom.core.utils.ObjectUtil;
import github.sjroom.core.utils.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 填充字典值和用户名称
 *
 * @author manson.zhou
 */
@Aspect
@Configuration
@Slf4j
@SuppressWarnings("unchecked")
public class FillFieldNameAspect {

	/**
	 * 1.拦截FillFieldNameText.class
	 * 2.从返回值对象上获取字段注解信息
	 * 3.循环返回值设置字典值
	 * <p>
	 * This is the method which I would like to execute after a selected method execution.
	 */
	@Around(value = "@annotation(github.sjroom.core.mybatis.annotation.FillField))")
	public Object aroundApi(ProceedingJoinPoint point) throws Throwable {
		MethodSignature ms = (MethodSignature) point.getSignature();
		Method method = ms.getMethod();
		Class<?> returnType = method.getReturnType();
		Object retVal = null;

		if (void.class == returnType) {
			point.proceed();
			return retVal;
		}
		retVal = point.proceed();
		if (ObjectUtil.isNull(retVal)) {
			log.warn("aroundApi retVal is emtpy");
			return retVal;
		}
		Object retValObject;
		Collection retCollection;
		if (retVal instanceof Collection) {
			retCollection = (Collection) retVal;
			if (CollectionUtil.isEmpty(retCollection)) {
				return null;
			}
			retValObject = retCollection.iterator().next();
		} else if (retVal instanceof PageResult) {
			retCollection = ((PageResult) retVal).getRecords();
			if (CollectionUtil.isEmpty(retCollection)) {
				return null;
			}
			retValObject = retCollection.iterator().next();
		} else {
			retCollection = Lists.newArrayList(retVal);
			retValObject = retVal;
		}

		List<FillFieldObject> fillFieldObjects = new ArrayList<>();
		Class<?> retValObjectClass = retValObject.getClass();
		Field[] fields = retValObjectClass.getDeclaredFields();

		for (Field field : fields) {
			FillFieldName fillFieldName = field.getAnnotation(FillFieldName.class);
			if (Objects.isNull(fillFieldName)) {
				continue;
			}

			FillFieldObject fillFieldObject = new FillFieldObject();
			fillFieldObject.setField(field);
			fillFieldObject.setInvokeClass(fillFieldName.invoke());
			fillFieldObject.setInvokeMethod(fillFieldName.invokeMethod());
			fillFieldObjects.add(fillFieldObject);
		}

		return fillVal(retVal, retCollection, fillFieldObjects);
	}


	/**
	 * 开始反射赋值
	 *
	 * @param fillFieldObjects
	 */
	private Object fillVal(Object retVal, Collection retCollection, List<FillFieldObject> fillFieldObjects) {
		if (CollectionUtil.isEmpty(fillFieldObjects)) {
			return retVal;
		}

		// 获取所有值,将其变成集合
		List<InvokeObject> invokeObjects = new ArrayList<>();
		retCollection.forEach(obj -> fillFieldObjects.forEach(fieldInfo -> {
			Field field = fieldInfo.getField();
			field.setAccessible(true);
			try {
				if (ObjectUtil.isNotNull(field.get(obj))) {
					InvokeObject invokeObject = new InvokeObject(fieldInfo.getInvokeClass(), fieldInfo.getInvokeMethod());
					boolean isOrg = false;
					for (InvokeObject orgInvokeObject : invokeObjects) {
						if (orgInvokeObject.equals(invokeObject)) {
							orgInvokeObject.getInvokeArgs().add(field.get(obj));
							isOrg = true;
						}
					}
					if (!isOrg) {
						invokeObjects.add(invokeObject);
					}
				}
			} catch (IllegalAccessException e) {
				throw new BusinessException(e);
			}
		}));

		// 获取所有值,调用第三方法返回的值
		invokeObjects.forEach(invokeObject -> {
			try {
				Object invokeClass = SpringExtensionLoader.getSpringBean(invokeObject.getInvokeClass());
				Method thirdPartyMethod = invokeClass.getClass().getMethod(invokeObject.getInvokeMethod(), Set.class);
				invokeObject.setMapData((Map) thirdPartyMethod.invoke(invokeClass, invokeObject.getInvokeArgs()));
			} catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
				throw new BusinessException(e);
			}
		});


		//进行赋值操作.
		List<Object> objects = new ArrayList<>();
		retCollection.forEach(obj -> {
			Map<String, Object> propertyMap = new HashMap<>();
			fillFieldObjects.forEach(fieldInfo -> {
				try {
					Field field = fieldInfo.getField();
					String fieldText = field.getName().endsWith("Id") ? field.getName().substring(0, field.getName().lastIndexOf("Id")) : field.getName();
					fieldText = fieldText + "Name";
//					log.debug("进行赋值操作 fieldName:{} fieldText:{} fieldGet:{}", field.getName(), fieldText, field.get(obj));
					InvokeObject invokeObject = new InvokeObject(fieldInfo.getInvokeClass(), fieldInfo.getInvokeMethod());
					for (InvokeObject orgInvokeObject : invokeObjects) {
						if (orgInvokeObject.equals(invokeObject)) {
							Object fieldValue = orgInvokeObject.getMapData().get(field.get(obj));
							if (fieldValue instanceof String) {
								propertyMap.put(fieldText, fieldValue);
							} else if (fieldValue == null) {
								propertyMap.put(fieldText, StringPool.EMPTY);
							} else {
								Field valueField = this.getFields(fieldValue.getClass());
								if (ObjectUtil.isNotNull(valueField)) {
									propertyMap.put(fieldText, valueField.get(fieldValue));
								}
							}
						}
					}
				} catch (IllegalAccessException e) {
					throw new BusinessException(e);
				}
			});
			objects.add(AddPropertiesUtil.getTarget(obj, propertyMap));
		});

		if (retVal instanceof Collection) {
			return objects;
		} else if (retVal instanceof PageResult) {
			((PageResult) retVal).setRecords(objects);
			return retVal;
		} else {
			return objects.get(0);
		}
	}

	/**
	 * 获取该类的所有属性列表
	 *
	 * @param entityClass 反射类
	 * @return
	 */
	private Field getFields(Class<?> entityClass) {
		Field[] fields = entityClass.getDeclaredFields();
		for (Field field : fields) {

			/* 过滤静态属性 */
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}

			/* 过滤 transient关键字修饰的属性 */
			if (Modifier.isTransient(field.getModifiers())) {
				continue;
			}

			/* 过滤注解非表字段属性 */
			FillFieldName tableField = field.getAnnotation(FillFieldName.class);
			if (tableField != null) {
				field.setAccessible(true);//对私有字段的访问取消权限检查。暴力访问
				return field;
			}
		}
		log.warn("entityClass:{} have no @FillFieldName annotation ", entityClass.getName());
		return null;
	}
}
