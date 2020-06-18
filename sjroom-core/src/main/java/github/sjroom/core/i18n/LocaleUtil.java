package github.sjroom.core.i18n;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * 国际化操作类
 *
 * @author berger
 * @date 2019-03-30
 */
@Slf4j
@Component
public class LocaleUtil {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private MessageSource messageSource;

	private final char SIGN = '&';

	private final String SPLIT_SIGN = "lang=";

	private final char SPLIT_SIGN_EQUAL = '=';

	private final char SPLIT_UNDER_LINE = '_';

	/**
	 * 获取当前语言
	 *
	 * @return
	 */
	private Locale getLocale() {
		Locale locale = null;
		String langParam = "";
		String queryString = request.getQueryString();
		if (StringUtils.isEmpty(queryString)) {
			return LocaleContextHolder.getLocale();
		}

		int index = queryString.indexOf(SIGN);
		if (index < 0 && queryString.startsWith(SPLIT_SIGN)) {
			langParam = queryString;
		} else {
			String[] params = StringUtils.split(queryString, SIGN);
			for (String param : params) {
				if (param.indexOf(SPLIT_SIGN) == 0) {
					langParam = param;
					break;
				}
			}
		}
		if (!StringUtils.isEmpty(langParam) && langParam.length() > SPLIT_SIGN.length()) {
			String[] langKeyValue = StringUtils.split(langParam, SPLIT_SIGN_EQUAL);
			String lang = StringUtils.isEmpty(langKeyValue[1]) ? "" : langKeyValue[1].trim();
			if (!StringUtils.isEmpty(lang)) {
				if (lang.indexOf(SPLIT_UNDER_LINE) < 0) {
					locale = new Locale(lang, "");
				} else {
					String[] langArr = StringUtils.split(lang, SPLIT_UNDER_LINE);
					locale = new Locale(langArr[0], langArr[1]);
				}
			}
		}

		if (null == locale) {
			locale = LocaleContextHolder.getLocale();
		}

		return locale;
	}

	/**
	 * 获取当前语言对应的信息
	 *
	 * @param code
	 * @param args
	 * @return
	 */
	public String getMessage(String code, String[] args) {
		return messageSource.getMessage(code, args, this.getLocale());
	}

	/**
	 * 获取当前语言对应的信息
	 *
	 * @param code
	 * @return
	 */
	public String getMessage(String code) {
		return this.getMessage(code, null);
	}

	/**
	 * 获取当前语言对应的信息
	 *
	 * @param code
	 * @param args
	 * @return
	 */
	public String getMessage(int code, String[] args) {
		return this.getMessage(String.valueOf(code), args);
	}

	/**
	 * 获取当前语言对应的信息
	 *
	 * @param code
	 * @return
	 */
	public String getMessage(int code) {
		return this.getMessage(code, null);
	}
}
