package github.sjroom.web.validation.annotation;


import github.sjroom.web.validation.annotation.handler.PatternsValidatorHandler;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PatternsValidatorHandler.class)
public @interface Patterns {

    String message() default "格式错误";

    String regexp() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
