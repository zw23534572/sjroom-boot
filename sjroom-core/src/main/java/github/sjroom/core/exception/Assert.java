package github.sjroom.core.exception;

import github.sjroom.core.utils.CollectionUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: manson.zhou
 * @Date: 2020/7/03 11:07
 * @Desc: (业务代码)逻辑验证/断言工具类
 */
public abstract class Assert {

	/**
	 * 异常抛出
	 *
	 * @param code 错误码
	 */
	public static void throwFail(String code) throws BusinessException {
		throwFail0(code, null);
	}

	/**
	 * 异常抛出
	 *
	 * @param code     错误码
	 * @param i18nArgs 参数
	 */
	public static void throwFail(String code, Object... i18nArgs) throws BusinessException {
		throwFail0(code, null, i18nArgs);
	}


	/**
	 * 异常抛出
	 *
	 * @param code      错误码
	 * @param dataPairs 消息
	 * @param i18Args   参数
	 */
	private static void throwFail0(String code, DataPair[] dataPairs, Object... i18Args) throws BusinessException {
		BusinessException exception = new BusinessException(code, i18Args);
		exception.setI18Args(i18Args);
		if (!CollectionUtil.isEmpty(dataPairs)) {
			Map<String, Object> values = Stream.of(dataPairs)
				.filter(v -> v != null && ObjectUtils.allNotNull(v.field, v.data))
				.collect(Collectors.toMap(v -> v.field, v -> v.data));
			exception.setValues(values);
		}

		throw exception;
	}


	/**
	 * 断言api异常,全量参数
	 *
	 * @param expression
	 * @param resultCode
	 * @param dataPairs
	 * @param i18Args
	 * @throws BusinessException
	 */
	private static void throwOnFalse0(boolean expression, String resultCode, DataPair[] dataPairs, Object... i18Args) throws BusinessException {
		if (!expression) {
			throwFail0(resultCode, dataPairs, i18Args);
		}
	}

	/**
	 * 断言api异常,可输入国际化参数
	 *
	 * @param expression
	 * @param resultCode
	 * @param i18Args
	 */
	public static void throwOnFalse(boolean expression, String resultCode, Object... i18Args) throws BusinessException {
		throwOnFalse0(expression, resultCode, null, i18Args);
	}

	public static void assertTrue(boolean expression, String message) {
		if (!expression) {
			throw new FrameworkException(message);
		}
	}

	@Getter
	@Setter
	@Builder
	public static class DataPair {
		private String field; // 输出的key
		private Object data; //输出的value
	}
}
