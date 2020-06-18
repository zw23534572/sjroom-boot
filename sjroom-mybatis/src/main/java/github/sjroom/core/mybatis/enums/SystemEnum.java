package github.sjroom.core.mybatis.enums;

import github.sjroom.core.utils.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <B>说明 系统枚举</B><BR>
 *
 * @version 1.0.0.
 */
@AllArgsConstructor
@Getter
public enum SystemEnum {
    BASE(1, "BaseEntity"),
    TENANT(2, "TenantEntity"),
    SYSTEM(3, "SystemEntity"),
    BUSINESS(4, "BusinessEntity")
    ;

    private Integer code;
    private String value;



    public static SystemEnum valueOf(Integer code) {
        for (SystemEnum typeEnum : SystemEnum.values()) {
            if (ObjectUtil.nullSafeEquals(code, typeEnum.code)) {
                return typeEnum;
            }
        }
        return null;
    }

}
