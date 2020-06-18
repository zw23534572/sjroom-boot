package github.sjroom.web.validation.annotation;


import github.sjroom.web.validation.annotation.handler.TelephoneValidatorHandler;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TelephoneValidatorHandler.class)
public @interface Telephone {
	String message() default "格式错误";

	String regexp() default "^1(3|4|5|7|8)\\d{9}$";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
