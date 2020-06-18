package github.sjroom.core.exception;

import github.sjroom.core.code.IResultCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Optional;

/**
 * 业务异常
 *
 * @author L.cm
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -7374847755494906434L;

	@Getter
	@Setter
	private Map<String, Object> values;

	@Getter
	@Setter
	private Integer httpStatus;

	@Getter
	private String code;

	@Getter
	@Setter
	private Object[] i18Args;

	public BusinessException() {
	}

	public BusinessException(IResultCode resultCode) {
		this(resultCode.getCode(), resultCode.getMsg());
		Optional.ofNullable(resultCode.getHttpStatus())
			.map(v -> v.getStatusCode())
			.ifPresent(v -> this.setHttpStatus(v));
	}

	public BusinessException(IResultCode resultCode, Object[] i18Args) {
		this(resultCode);
		setI18Args(i18Args);
	}

	public BusinessException(String code, String msg) {
		this(code, msg, null);
	}


	public BusinessException(Exception cause) {
		this(null, cause.getMessage(), cause);
	}


	public BusinessException(String code, String msg, Exception cause) {
		super(msg, cause);
		this.code = code;
	}

	public final static String StateCode = "stateCode";
	public final static String StateMsg = "stateMsg";
	public final static String DetailMsg = "detailMsg";
	public final static String InnerError = "SYS00101";

}
