package github.sjroom.core.code;

import java.io.Serializable;

/**
 * 状态码接口
 *
 * @author L.cm
 */
public interface IErrorCode extends Serializable {
	/**
	 * 返回的code码
	 *
	 * @return code
	 */
	String getCode();

	/**
	 * 返回的消息
	 *
	 * @return 消息
	 */
	String getMsg();
}
