package github.sjroom.core.mybatis.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.LocalTimeTypeHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 自定义扩展 LocalDate 的处理器
 *
 * <p>
 * 目标： 不处理时区
 *  java    LocalTime
 *  mysql   time
 * </p>
 *
 */
public class DopLocalTimeTypeHandler extends LocalTimeTypeHandler {

	/**
	 * mysql最大精度
	 */
	public static final DateTimeFormatter Time_Formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSS");

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, LocalTime parameter, JdbcType jdbcType)
		throws SQLException {
		ps.setObject(i, Time_Formatter.format(parameter));
	}

}
