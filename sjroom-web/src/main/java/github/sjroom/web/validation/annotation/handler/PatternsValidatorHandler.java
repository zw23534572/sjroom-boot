package github.sjroom.web.validation.annotation.handler;

import github.sjroom.web.validation.annotation.Patterns;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PatternsValidatorHandler implements ConstraintValidator<Patterns, String> {
    private Patterns patterns;

    @Override
    public void initialize(Patterns patterns) {
        this.patterns = patterns;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 如果 value 为空则不进行格式验证，为空验证可以使用 @NotBlank @NotNull @NotEmpty 等注解来进行控制，职责分离
        if (StringUtils.isBlank(value)) {
            return true;
        }

        if (!Pattern.matches(patterns.regexp(), value)) {
            return false;
        }

        return true;
    }
}
