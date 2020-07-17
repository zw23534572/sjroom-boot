package github.sjroom.core.exception;

import github.sjroom.core.code.I18nUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

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

	public BusinessException(String code) {
		this(code, null, null);
	}

	public BusinessException(String code, Object... args) {
		this(code, I18nUtil.getMessage(code, args), null);
	}

	public BusinessException(Exception cause) {
		this(null, cause.getMessage(), cause);
	}


	public BusinessException(String code, String msg, Exception cause) {
		super(msg, cause);
		this.code = code;
	}
}
