package github.sjroom.web.validation.annotation.handler;


import github.sjroom.web.validation.annotation.Telephone;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class TelephoneValidatorHandler implements ConstraintValidator<Telephone, String> {
	private Telephone telephone;

	@Override
	public void initialize(Telephone telephone) {
		this.telephone = telephone;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// 如果 value 为空则不进行格式验证，为空验证可以使用 @NotBlank @NotNull @NotEmpty 等注解来进行控制，职责分离
		if (StringUtils.isBlank(value)) {
			return true;
		}

		if (!Pattern.matches(telephone.regexp(), value)) {
			return false;
		}

		return true;
	}
}
