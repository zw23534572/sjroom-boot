package github.sjroom.web.validation.annotation;


import github.sjroom.web.validation.annotation.handler.RangeValidatorHandler;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RangeValidatorHandler.class)
public @interface Range {

    String message() default "范围值错误";

    String format() default "[\"email\",\"mobile\"]";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
