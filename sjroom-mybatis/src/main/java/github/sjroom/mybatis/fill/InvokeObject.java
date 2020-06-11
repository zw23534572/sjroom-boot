package github.sjroom.mybatis.fill;

import lombok.Data;

import java.util.*;

/**
 * @author manson.zhou
 */
@Data
public class InvokeObject {
	/**
	 * 调用的第三方class
	 */
	private Class invokeClass;
	/**
	 * 调用的第三方的方法
	 */
	private String invokeMethod;
	/**
	 * 调用的第三方参数
	 */
	private Set invokeArgs = new HashSet<>();
	/**
	 * 调用后的值
	 */
	private Map mapData = new HashMap();

	public InvokeObject(Class invokeClass, String invokeMethod) {
		this.invokeClass = invokeClass;
		this.invokeMethod = invokeMethod;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		InvokeObject that = (InvokeObject) o;
		return Objects.equals(invokeClass, that.invokeClass) &&
			Objects.equals(invokeMethod, that.invokeMethod);
	}

	@Override
	public int hashCode() {
		return Objects.hash(invokeClass, invokeMethod);
	}
}
