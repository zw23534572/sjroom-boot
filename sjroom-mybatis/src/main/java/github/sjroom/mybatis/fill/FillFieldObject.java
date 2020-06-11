package github.sjroom.mybatis.fill;

import lombok.Data;

import java.lang.reflect.Field;

/**
 * @author manson.zhou
 */
@Data
public class FillFieldObject {
	/**
	 * 赋值字段
	 */
	private Field field;

	/**
	 * 调用的第三方class
	 */
	private Class invokeClass;
	/**
	 * 调用的第三方的方法
	 */
	private String invokeMethod;
}
