package github.sjroom.base.code;

import github.sjroom.base.env.DopProperties;
import github.sjroom.base.env.ServerInfo;
import github.sjroom.core.code.IResultCode;
import github.sjroom.core.utils.ObjectUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * code 翻译器
 *
 * @author manson.zhou
 */
@RequiredArgsConstructor
public abstract class CodeTranslator {
    private static final int CODE_MAX_LENGTH = 11;
    private final ServerInfo serverInfo;
    private final DopProperties properties;

    private static final String SYSTEM_PREFIX = "SYS";
    private static final String SUCCESS_CODE = "1";


    /**
     * 组装完整的 11 位 code
     *
     * @param resultCode 返回的code
     * @return 完整的code
     */
    public String getFullCode(IResultCode resultCode) {
        return getFullCode(resultCode.getCode());
    }

    public String getFullCode(String code) {
        if (ObjectUtil.nullSafeEquals(SUCCESS_CODE, code)
            || code.length() >= 11) {
            return code;
        }
        StringBuilder codeBuilder = new StringBuilder(11);
        // 系统 code 已经添加了前缀
        if (!code.startsWith(SYSTEM_PREFIX)) {
            // 三位服务名缩写（大写）
            String shortName = properties.getServer().getShortName();
            codeBuilder.append(shortName.toUpperCase());
        }
        codeBuilder.append(code);
        codeBuilder.append(serverInfo.getIpSuffix());
        return codeBuilder.toString();
    }



    /**
     * code 翻译
     *
     * @param code
     * @param message
     * @param args
     * @return
     */
    public TranslatedInfo translation(String code, String message, Object[] args) {
        if (code.length() >= CODE_MAX_LENGTH) {
            message = doTranslation(code, message, args);
            return TranslatedInfo.builder().code(code).message(message).build();
        }
        // 系统 code 已经添加了前缀
        if (!code.startsWith(SYSTEM_PREFIX)) {
            // 三位服务名缩写（大写）
            String shortName = properties.getServer().getShortName();
            code = shortName + code;
        }
        message = doTranslation(code, message, args);
        code = code + serverInfo.getIpSuffix();
        return TranslatedInfo.builder().code(code).message(message).build();
    }

    /**
     * 翻译API定义
     *
     * @param code
     * @param message
     * @param args
     * @return
     */
    public abstract String doTranslation(String code, String message, Object[] args);

    @Getter
    @Setter
    @Builder
    public static class TranslatedInfo {
        private String code;
        private String message;
    }


}

