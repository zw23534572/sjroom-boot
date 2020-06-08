package github.sjroom.base.utils;//package github.sjroom.core.utils;

import github.sjroom.core.utils.Charsets;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

/**
 * url处理工具类
 *
 * @author L.cm
 */
@UtilityClass
public class UrlUtil extends org.springframework.web.util.UriUtils {

    /**
     * url编码，默认为 utf-8
     *
     * @param source 字符串
     * @return 编码之后的url
     */
    public static String encode(String source) {
        return UrlUtil.encode(source, Charsets.UTF_8);
    }

    /**
     * url解码，默认为 utf-8
     *
     * @param source 字符串
     * @return 解码之后的url
     */
    public static String decode(String source) {
        return StringUtils.uriDecode(source, Charsets.UTF_8);
    }
}
