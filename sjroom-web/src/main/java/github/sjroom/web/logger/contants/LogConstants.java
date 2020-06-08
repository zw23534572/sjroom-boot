package github.sjroom.web.logger.contants;

/**
 * 日志 常量值
 *
 * @author manson.zhou
 */
public interface LogConstants {
	/**
	 * MDC request id key
	 */
	String MDC_REQUEST_ID_KEY = "xReqId";
	/**
	 * 日志目录变量名
	 */
	String LOGGING_PATH = "LOGGING_PATH";
	/**
	 * Root 日志级别
	 */
	String ROOT_LOG_LEVEL = "LOG_ROOT_LEVEL";
	/**
	 * 日志路径
	 */
	String LOGGING_PATH_FILE = "logger/log4j2.xml";
	/**
	 * 错误信息
	 */
	String ERROR_MESSAGE = "errorMsg";
	/**
	 * 错误详细信息
	 */
	String ERROR_DETAIL_MESSAGE = "errorDetailMsg";
	/**
	 * 错误发生的时间
	 */
	String ERROR_DATE_TIME = "timestamp";
	/**
	 * 错误的code码
	 */
	String ERROR_CODE = "errorCode";
}
