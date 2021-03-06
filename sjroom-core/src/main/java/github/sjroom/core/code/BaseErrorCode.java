package github.sjroom.core.code;

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
public interface BaseErrorCode extends IErrorCode {
	String SUCCESS = "200"; //成功
	String PARAM_ERROR = "400"; //参数错误：{0}
	String PARAM_ERROR_01 = "40001";  //参数错误：{0}不能为空
	String UNAUTHORIZED_ERROR = "401"; //鉴权错误
	String SYSTEM_ERROR = "500"; //500-系统错误
	String SYSTEM_ERROR_ADD_01 = "501"; //系统错误,添加

}
