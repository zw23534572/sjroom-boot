package github.sjroom.mybatis.generatecode;

import github.sjroom.core.utils.StringPool;
import github.sjroom.mybatis.util.UtilId;
import lombok.Data;

import java.util.List;

@Data
public class EnumGenerator {
    /**
     * sql模板
     */
    public static final String SQL_INSERT_TMPLATE = "INSERT INTO `web`.`base_error_code`(`error_code_id`, `project_code`, `error_code`, `error_text`, `language`, `status`, `created_by`, `created_at`, `updated_by`, `updated_at`) \n" +
        "VALUES ({0}, '{1}', '{1}{2}', '{3}', 'zh', 1, 1, '2019-07-22 07:38:27', 1, '2019-07-22 07:38:27');";
    /**
     * sql模板
     */
    public static final String SQL_DELETE_TMPLATE = "DELETE FROM `web`.`base_error_code` WHERE `project_code` = '{0}';";

    /**
     * 项目的短名称
     */
    private String projectShortName;
    /**
     * 枚举类列表的所有值
     */
    private List<OptionalEnum> optionalEnumList;

    public EnumGenerator() {

    }

    public EnumGenerator(String projectShortName, List<OptionalEnum> optionalEnumList) {
        this.projectShortName = projectShortName;
        this.optionalEnumList = optionalEnumList;
    }


    public static class OptionalEnum {
        private String code;
        private String msg;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    /**
     * 将ApiCode 转换成 sql
     */
    public void convertApiCodeToSql() {

        StringBuilder sbSql = new StringBuilder();

        String tempDeleteSql = SQL_DELETE_TMPLATE.replace("{0}", projectShortName);
        sbSql.append(tempDeleteSql).append(StringPool.NEWLINE);

        for (OptionalEnum optionalEnum : optionalEnumList) {
            String tempInsertSql = SQL_INSERT_TMPLATE.replace("{0}", String.valueOf(UtilId.getBId()))
                .replace("{1}", projectShortName)
                .replace("{2}", optionalEnum.getCode())
                .replace("{3}", optionalEnum.getMsg());

            sbSql.append(tempInsertSql).append(StringPool.NEWLINE);
        }

        System.out.println(sbSql.toString());
    }

}
