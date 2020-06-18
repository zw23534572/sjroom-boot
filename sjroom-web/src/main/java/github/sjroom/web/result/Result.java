package github.sjroom.web.result;

import github.sjroom.core.code.IResultCode;
import github.sjroom.core.exception.BusinessException;
import github.sjroom.core.utils.ObjectUtil;
import github.sjroom.core.utils.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Optional;

/**
 * 响应信息主体
 *
 * @param <T> 泛型标记
 * @author L.cm
 */
@Getter
@Setter
@ToString
@ApiModel(description = "返回信息")
public class Result<T> implements Serializable {
	private static final long serialVersionUID = -1160662278280275915L;
	/**
	 * 定义成功的code
	 */
	private static final String SUCCESS_CODE = "1";
	private static final String SUCCESS_MSG = "成功";

	@ApiModelProperty(value = "code值", required = true)
	private String stateCode;
	@ApiModelProperty(value = "消息", required = true)
	private String stateMsg;
	@ApiModelProperty("返回对象")
	private T data;

	public Result(T t) {
		this(SUCCESS_CODE, SUCCESS_MSG, t);
	}

	private Result(IResultCode resultCode) {
		this(resultCode.getCode(), resultCode.getMsg(), null);
	}

	private Result(IResultCode resultCode, String msg) {
		this(resultCode.getCode(), msg, null);
	}

	private Result(IResultCode resultCode, T data) {
		this(resultCode.getCode(), resultCode.getMsg(), data);
	}

	private Result(String stateCode, String stateMsg, T data) {
		this.stateCode = stateCode;
		this.stateMsg = stateMsg;
		this.data = data;
	}

	/**
	 * 判断返回是否为成功
	 *
	 * @param result Result
	 * @return 是否成功
	 */
	public static boolean isSuccess(@Nullable Result<?> result) {
		return Optional.ofNullable(result)
			.map(Result::getStateCode)
			.map(x -> ObjectUtil.nullSafeEquals(SUCCESS_CODE, x))
			.orElse(Boolean.FALSE);
	}

	/**
	 * 判断返回是否为成功
	 *
	 * @param result Result
	 * @return 是否成功
	 */
	public static boolean isNotSuccess(@Nullable Result<?> result) {
		return !Result.isSuccess(result);
	}

	/**
	 * 获取data
	 *
	 * @param result Result
	 * @param <T>    泛型标记
	 * @return 泛型对象
	 */
	@Nullable
	public static <T> T getData(@Nullable Result<T> result) {
		return Optional.ofNullable(result)
			.filter(Result::isSuccess)
			.map(x -> x.data)
			.orElse(null);
	}

	/**
	 * 返回成功
	 *
	 * @param <T> 泛型标记
	 * @return Result
	 */
	public static <T> Result<T> success() {
		return new Result<>(SUCCESS_CODE, SUCCESS_MSG, null);
	}

	/**
	 * 成功-携带数据
	 *
	 * @param data 数据
	 * @param <T>  泛型标记
	 * @return Result
	 */
	public static <T> Result<T> success(@Nullable T data) {
		return new Result<>(SUCCESS_CODE, SUCCESS_MSG, data);
	}

	/**
	 * 成功-携带数据
	 *
	 * @param key  如果存在key,用key值包装一次
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T> Result<T> success(String key, @Nullable T data) {
		HashMap<String, Object> hashMap = new HashMap<>();
		if (StringUtil.isNotBlank(key)) {
			hashMap.put(key, data);
		}
		return new Result(SUCCESS_CODE, SUCCESS_MSG, hashMap);
	}

	/**
	 * 当 result 不成功时：直接抛出失败异常，返回传入的 rCode
	 *
	 * @param result R
	 * @param rCode  异常code码
	 */
	public static void throwOnFail(Result<?> result, IResultCode rCode) {
		if (Result.isNotSuccess(result)) {
			throw new BusinessException(rCode);
		}
	}

	/**
	 * 当 result 不成功时：直接抛出失败异常，返回传入的 rCode、args
	 *
	 * @param result R
	 */
	public static void throwOnFail(Result<?> result, IResultCode rCode, Object... args) {
		if (Result.isNotSuccess(result)) {
			throw new BusinessException(rCode, args);
		}
	}

	/**
	 * 当 status 不为 true 时：直接抛出失败异常，返回传入的 rCode
	 *
	 * @param status status
	 * @param rCode  异常code码
	 */
	public static void throwOnFalse(Boolean status, IResultCode rCode) {
		if (ObjectUtil.isFalse(status)) {
			throw new BusinessException(rCode);
		}
	}

	/**
	 * 当 status 不为 true 时：直接抛出失败异常，返回传入的 rCode、args
	 *
	 * @param status status
	 * @param rCode  异常code码
	 * @param args   异常返回的字段
	 */
	public static void throwOnFalse(Boolean status, IResultCode rCode, Object... args) {
		if (ObjectUtil.isFalse(status)) {
			throw new BusinessException(rCode, args);
		}
	}

	/**
	 * 直接抛出失败异常，抛出 code 码
	 *
	 * @param rCode IResultCode
	 */
	public static void throwFail(IResultCode rCode) {
		throw new BusinessException(rCode);
	}


	/**
	 * 直接抛出失败异常，抛出 code 码：返回传入的 rCode、args
	 *
	 * @param rCode IResultCode
	 * @param args  异常返回的字段
	 */
	public static void throwFail(IResultCode rCode, Object... args) {
		throw new BusinessException(rCode, args);
	}

}
