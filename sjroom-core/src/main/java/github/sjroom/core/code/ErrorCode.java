package github.sjroom.core.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务 code，需要配置 code 前缀，业务只需要关心code中间5位
 *
 * <p>
 * 业务标识：2位，开发人员按照业务模块定义，例如：(01)SKU。 业务错误码：3位，开发人员自己定义。
 * </p>
 *
 * @author manson.zhou
 * @version 1.0.0
 * @since 2019-05-27 08:34
 */
public interface ErrorCode extends IErrorCode {
	/**
	 * SYS00200-成功
	 */
	String SUCCESS = "SYS00200";
	/**
	 * SYS00400-参数错误
	 */
	String PARAM_ERROR = "SYS00400";
	/**
	 * SYS00500-系统错误
	 */
	String SYSTEM_ERROR = "SYS00500";
	/**
	 * SYS00201-平台调用上下文为空
	 */
	String NO_CONTEXT = "SYS00201";

}
