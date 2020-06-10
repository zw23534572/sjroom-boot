package github.sjroom.mybatis.annotation;

import java.lang.annotation.*;

/**
 * @author george.ouyang
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FillField {

	String value() default "";
}
