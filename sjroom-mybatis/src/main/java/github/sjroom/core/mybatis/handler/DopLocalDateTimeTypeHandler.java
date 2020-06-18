package github.sjroom.core.mybatis.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 自定义扩展 LocalDate 的处理器
 *
 * <p>
 * 目标： 不处理时区
 * java    LocalDateTime
 * mysql  datetime
 * </p>
 *
 */
public class DopLocalDateTimeTypeHandler extends LocalDateTimeTypeHandler {

	/**
	 * mysql最大精度
	 */
	public static final DateTimeFormatter DateTime_Formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType)
		throws SQLException {
		ps.setObject(i, DateTime_Formatter.format(parameter));
	}


}
