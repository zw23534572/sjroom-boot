package ${currentPackage};

/**
 * <B>说明：枚举类</B><BR>
 *
 * @author ${config.author}
 * @version 1.0.0.
 * @date ${date}
 */
public enum ${className} {
<% for(var item in statusList) { %>
    ${item.fieldName}(${item.code},"${item.msg}")${itemLP.index!=itemLP.size?",":";"}
<% } %>

    private Integer code;
    private String msg;

    ${className}Enum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static String getMsg(Integer code) {
        if (code == null) {
            return null;
        }
        for (${className} en : ${className}.values()) {
            if (code.equals(en.getCode())) {
                return en.getMsg();
            }
        }
        return null;
    }

}
