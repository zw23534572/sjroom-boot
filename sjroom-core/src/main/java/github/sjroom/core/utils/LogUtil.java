package github.sjroom.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogUtil {

	private static Map<String, Logger> loggers = new ConcurrentHashMap<>();

	/**
	 * 根据路径来获取日志记录器
	 */
	public static Logger getLoggerByPath(String rootName, String pathInfo) {
		String pathName = pathToName(pathInfo);
		if (rootName == null || rootName.isEmpty()) return getLogger(pathName);
		return getLogger(rootName + '.' + pathName);
	}

	/**
	 * 路径转换为logger的点分割名称，去掉首尾的点
	 */
	private static String pathToName(String pathInfo) {
		if (pathInfo == null || pathInfo.isEmpty()) return pathInfo;

		StringBuilder buf = new StringBuilder();
		for (int i = 0, len = pathInfo.length(); i < len; i++) {
			char ch = pathInfo.charAt(i);
			if (ch == '/' || ch == '\\') ch = '.';
			buf.append(ch);
		}
		if (buf.charAt(buf.length() - 1) == '.') buf.deleteCharAt(buf.length() - 1);
		if (buf.length() > 0 && buf.charAt(0) == '.') return buf.substring(1);
		return buf.toString();
	}

	public static Logger getLogger(String name) {
		if (name == null || name.isEmpty()) name = "log." + name;
		return loggers.computeIfAbsent(name, LoggerFactory::getLogger);
	}


}
