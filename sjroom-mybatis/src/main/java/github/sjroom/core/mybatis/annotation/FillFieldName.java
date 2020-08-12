package github.sjroom.core.mybatis.annotation;

import java.lang.annotation.*;
import java.util.Set;

/**
 * @author george.ouyang
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FillFieldName {

	/**
	 * 调用的类名
	 */
	Class invoke() default Object.class;

	/**
	 * 调用的方法名
	 */
	String invokeMethod() default "fillFieldName";

	/**
	 * 参数
	 */
	String invokeArg() default "";

}
