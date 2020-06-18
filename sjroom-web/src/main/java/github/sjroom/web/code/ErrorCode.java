package github.sjroom.web.code;

/**
 * ErrorCode
 *
 * @author Tom.Zeng
 * @date 2019/3/29 19:41
 */
public interface ErrorCode {
    /**
     * 200-成功
     */
    Integer SUCCESS = 200;
    /**
     * 400-参数错误
     */
    Integer PARAM_ERROR = 400;
    /**
     * 500-系统错误
     */
    Integer SYSTEM_ERROR = 500;
}
