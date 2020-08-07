package github.sjroom.secrity.code;

import github.sjroom.core.code.BaseErrorCode;

/**
 * @author manson.zhou
 * @version 1.0.0
 * @since 2020-07-03 08:34
 */
public interface ISecrityErrorCode extends BaseErrorCode {
	String TOKEN_NOT_NULL = "501";  //token 不能为null
	String TOKEN_NOT_NULL_NAME = "502";  //用户名称不能为null
	String TOKEN_EXPIRED = "503";  //token 过期
	String TOKEN_USER_NAME_DB_NULL = "504";  //未查询到该用户
}
