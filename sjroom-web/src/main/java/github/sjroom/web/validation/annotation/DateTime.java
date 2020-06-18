package github.sjroom.web.validation.annotation;


import github.sjroom.web.validation.annotation.handler.DateTimeValidatorHandler;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateTimeValidatorHandler.class)
public @interface DateTime {
	String message() default "日志格式错误";

	String format() default "yyyy-MM-dd";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
