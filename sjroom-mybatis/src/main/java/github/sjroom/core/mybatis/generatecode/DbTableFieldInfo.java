package github.sjroom.core.mybatis.generatecode;


import lombok.Data;

/**
 * <p>
 * 数据库表字段反射信息
 * </p>
 *
 * @author Zhouwei
 */
@Data
public class DbTableFieldInfo {

    /**
     * <p>
     * 判断该字段，是否为关键字。
     * </p>
     * true 为, false 不是
     */
    private boolean related = false;

    /**
     * 字段名
     */
    private String column;
    /**
     * 字段名-类型
     */
    private String columnType;
    /**
     * 字段的长度
     */
    private String columnLength;
    /**
     * 表的描述
     */
    private String comment;
    /**
     * 属性名
     */
    private String property;
    /**
     * 属性名-类型
     */
    private String propertyType;
    /**
     * 是否允许为空
     */
    private Boolean isNull;


    public DbTableFieldInfo() {

    }

    public DbTableFieldInfo(String column, String property) {
        this.column = column;
        this.property = property;
    }
}
