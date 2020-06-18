package github.sjroom.web.validation;


import github.sjroom.core.code.CommonsCode;
import github.sjroom.core.ResultAssert;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * hibernate-validator校验工具类
 */
public class ValidatorUtils {
	private static Validator validator;

	static {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	/**
	 * 校验对象
	 *
	 * @param object 待校验对象
	 * @param groups 待校验的组
	 */
	public static void validateEntity(Object object, Class<?>... groups) {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
		if (!constraintViolations.isEmpty()) {
			StringBuilder msg = new StringBuilder();
			for (ConstraintViolation<Object> constraint : constraintViolations) {
				msg.append(constraint.getMessage()).append("<br>");
			}
			ResultAssert.throwFail(CommonsCode.DATA_VALIDATION_FAILED, msg.toString());
		}
	}
}
