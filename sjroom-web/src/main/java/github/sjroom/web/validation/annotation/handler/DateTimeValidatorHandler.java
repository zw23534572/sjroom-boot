package github.sjroom.web.validation.annotation.handler;


import github.sjroom.web.validation.annotation.DateTime;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;

public class DateTimeValidatorHandler implements ConstraintValidator<DateTime, String> {
	private DateTime dateTime;

	@Override
	public void initialize(DateTime dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// 如果 value 为空则不进行格式验证，为空验证可以使用 @NotBlank @NotNull @NotEmpty 等注解来进行控制，职责分离
		if (StringUtils.isBlank(value)) {
			return true;
		}

		String format = dateTime.format();
		if (value.length() != format.length()) {
			return false;
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			simpleDateFormat.parse(value);
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
