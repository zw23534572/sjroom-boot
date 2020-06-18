package github.sjroom.web.validation.annotation.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import github.sjroom.web.validation.annotation.Range;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RangeValidatorHandler implements ConstraintValidator<Range, String> {
	private Range range;

	@Override
	public void initialize(Range range) {
		this.range = range;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// 如果 value 为空则不进行格式验证，为空验证可以使用 @NotBlank @NotNull @NotEmpty 等注解来进行控制，职责分离
		if (StringUtils.isBlank(value)) {
			return true;
		}

		boolean flag = false;
		JSONArray formats = JSON.parseArray(range.format());
		for (int i = 0; i <= formats.size() - 1; i++) {
			if (value.equals(formats.get(i))) {
				flag = true;
				break;
			}
		}
		return flag;
	}
}
