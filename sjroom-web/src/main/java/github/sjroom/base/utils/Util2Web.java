/*
 * Copyright 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package github.sjroom.base.utils;

import github.sjroom.core.utils.IoUtil;
import github.sjroom.core.utils.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * 关于jee web容器的工具类
 *
 * @author smj
 * @since 0.1.0
 */
@Slf4j
public class Util2Web {

	/**
	 * 为显示而转换[]为List或String
	 */
	public static Map<String, Object> getParameterMap(HttpServletRequest request) {
		Map<String, Object> params = new TreeMap<>();

		for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			String[] values = entry.getValue();
			if (values.length == 0) {
				params.put(entry.getKey(), null);
			} else if (values.length == 1) {
				params.put(entry.getKey(), values[0]);
			} else {
				params.put(entry.getKey(), Arrays.asList(entry.getValue()));
			}
		}
		return params;
	}

	/**
	 * 为显示而转换[]为List或String
	 */
	public static Map<String, Object> getHeaders(HttpServletRequest request) {
		Map<String, Object> headers = new TreeMap<>();

		for (Enumeration<String> iter = request.getHeaderNames(); iter.hasMoreElements(); ) {
			String name = iter.nextElement();
			List<String> values = new ArrayList<>();
			for (Enumeration<String> iter2 = request.getHeaders(name); iter2.hasMoreElements(); ) {
				values.add(iter2.nextElement());
			}

			if (values.isEmpty()) {
				headers.put(name, null);
			} else if (values.size() == 1) {
				headers.put(name, values.get(0));
			} else {
				headers.put(name, values);
			}
		}
		return headers;
	}

	public static Map<String, String> getHeaderValues(HttpServletRequest request) {
		Map<String, String> headers = new TreeMap<>();
		for (Enumeration<String> iter = request.getHeaderNames(); iter.hasMoreElements(); ) {
			String name = iter.nextElement();
			headers.put(name, request.getHeader(name));
		}
		return headers;
	}


	/**
	 * 获取数据从web环境中, 数据来源顺序:
	 * <ol>
	 * <li>{@linkplain HttpServletRequest#getAttribute(String)}</li>
	 * <li>{@linkplain HttpServletRequest#getParameter(String)}</li>
	 * <li>{@linkplain HttpSession#getAttribute(String)}</li>
	 * <li>{@linkplain ServletContext#getAttribute(String)}</li>
	 * </ol>
	 */
	private static Object getValue(HttpServletRequest req, String name) {
		Object value = req.getAttribute(name);
		if (!Objects.nonNull(value)) {
			return value;
		}

		value = req.getParameter(name);
		if (!Objects.nonNull(value)) {
			return value;
		}

		value = req.getSession().getAttribute(name);
		if (!Objects.nonNull(value)) {
			return value;
		}

		return req.getServletContext().getAttribute(name);
	}


	public static String getDomain(HttpServletRequest request) {
		return getDomain(request.getServerName());
	}

	/**
	 * @param domain request.getServerName()
	 * @return 根域或ip
	 */
	public static String getDomain(String domain) {
		boolean ip = true;
		int pointCount = 0;
		for (int i = domain.length() - 1; i >= 0; i--) {
			char ch = domain.charAt(i);
			if (ch == '.') {
				pointCount++;
			} else if (ch < '0' || ch > '9') {
				ip = false;
				break;
			}
		}
		if (ip && pointCount == 3) {
			return null;
		}

		int pos2 = domain.lastIndexOf('.');
		if (pos2 == -1 || pos2 == 0) {
			return null;
		}

		int pos1 = domain.lastIndexOf('.', pos2 - 1);
		if (pos1 != -1) {
			return domain.substring(pos1);
		}
		return '.' + domain;
	}

	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}

		String sessionId = null;
		for (Cookie cookie : cookies) {
			if (cookieName.equals(cookie.getName())) {
				sessionId = cookie.getValue();
			}
		}
		return sessionId;
	}


	public static Map<String, Object> getRequestInfo(HttpServletRequest request) {
		return getRequestInfo(request, true, true, false);
	}

	public static Map<String, Object> getRequestInfo(HttpServletRequest request, boolean remote, boolean headers, boolean attributes) {
		Map<String, Object> result = new LinkedHashMap<>();
		if (remote) {
			result.put("remoteAddr", request.getRemoteAddr());
			result.put("remoteHost", request.getRemoteHost());
			result.put("remotePort", request.getRemotePort());
			result.put("remoteUser", request.getRemoteUser());
		}
		if (headers) {
			Map<String, List<String>> map = new LinkedHashMap<>();
			for (Enumeration<String> iter = request.getHeaderNames(); iter.hasMoreElements(); ) {
				String name = iter.nextElement();
				List<String> values = new ArrayList<>();
				for (Enumeration<String> iter2 = request.getHeaders(name); iter2.hasMoreElements(); ) {
					values.add(iter2.nextElement());
				}
				map.put(name, values);
			}
			result.put("headers", map);
		}

		result.put("authType", request.getAuthType());

		result.put("scheme", request.getScheme());
		result.put("serverName", request.getServerName());
		result.put("serverPort", request.getServerPort());
		result.put("contextPath", request.getContextPath());

		result.put("pathInfo", request.getPathInfo());

		result.put("requestURI", request.getRequestURI());
		result.put("requestURL", request.getRequestURL());
		result.put("queryString", request.getQueryString());
		result.put("parameterMap", request.getParameterMap());

		if (attributes) {
			Map<String, Object> tmp = new LinkedHashMap<>();
			for (Enumeration<String> iter = request.getAttributeNames(); iter.hasMoreElements(); ) {
				String name = iter.nextElement();
				Object value = request.getAttribute(name);
				if (value != null) {
					String className = value.getClass().getName();
					if (className.indexOf("spring") != -1) {
						String str = value.toString();
						value = str.indexOf(className) == -1 ? className + '@' + str : str;
					}
				}
				tmp.put(name, value);
			}
			result.put("attributes", tmp);
		}
		return result;
	}


	public static Map<String, Object> getInfo(MultipartFile mf) {
		Map<String, Object> result = new LinkedHashMap<>();
		String name0 = mf.getOriginalFilename();
		result.put("type", mf.getContentType());
		result.put("name0", name0);
		result.put("sizeH", IoUtil.prettyByte(mf.getSize()));
		result.put("size", mf.getSize());
		String name = mf.getName();
		if (Objects.equals(name, name0)) {
			result.put("name", name);
		}
		result.put("class", mf.getClass().getName());
		return result;
	}

	public static Map<String, Object> getInfo(Part part) {
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("type", part.getContentType());
		String name0 = part.getSubmittedFileName();
		result.put("name0", name0);
		result.put("sizeH", IoUtil.prettyByte(part.getSize()));
		result.put("size", part.getSize());
		String name = part.getName();
		if (Objects.equals(name, name0)) {
			result.put("name", name);
		}
		result.put("class", part.getClass().getName());
		return result;
	}

	public static String getRootPath(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
	}

	public static String getBasePath(HttpServletRequest request) {
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		if (basePath.endsWith("/")) {
			return basePath;
		}
		return basePath + "/";
	}

	/**
	 * logger.name=mvc.……
	 */
	public static Logger getLogger(HttpServletRequest request) {
		return getLogger(request, "mvc");
	}

	/**
	 * logger.name=${rootName}.……
	 */
	public static Logger getLogger(HttpServletRequest request, String rootName) {
		String requestMapping = getBestMatchingPatternAttribute(request);
		return LogUtil.getLoggerByPath(rootName, requestMapping == null || requestMapping.isEmpty() ? "/" : requestMapping);
	}

	/**
	 * 获取{@linkplain org.springframework.web.bind.annotation.RequestMapping}对应的path
	 */
	public static String getBestMatchingPatternAttribute(HttpServletRequest request) {
		return (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
	}

	/**
	 * header值 -&gt QueryString -&gt cookie (如果cookie不存在且是url传递，cookie将被自动设置)
	 */
	public static String getRequestValue(HttpServletRequest request, HttpServletResponse response, String name) {
		String result = request.getHeader(name);
		if (StringUtils.isNotBlank(result)) return result;

		result = request.getParameter(name);
		if (StringUtils.isNotBlank(result)) {
			if (StringUtils.isEmpty(getCookieValue(request, name))) {
				response.addCookie(new Cookie(name, result));
			}
			return result;
		}

		return Util2Web.getCookieValue(request, name);
	}

	/**
	 * 获取客户端ip地址，存在安全问题，大家应当直接调用{@linkplain HttpServletRequest#getRemoteAddr()}
	 * app-center，可以直接调用CallContext.getIp()即可
	 */
	@Deprecated
	public static String getRemoteAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (invalidIp(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (invalidIp(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (invalidIp(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (invalidIp(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (invalidIp(ip)) {
			ip = request.getRemoteAddr();
		}

		//如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串ip值，
		//如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 192.168.1.100,
		//用户真实IP为X-Forwarded-For中第一个非unknown的有效IP字符串： 192.168.1.110
		if (ip != null && ip.indexOf(",") != -1) {
			String[] ips = ip.split("\\,");
			int x = 0;
			while (x < ips.length) {
				ip = ips[x++].trim();
				if (!isPrivateIp(ip)) {
					break;
				}
			}
		}
		return ip;
	}

	/**
	 * 私有IP地址范围：
	 * A类：10.0.0.0-10.255.255.255
	 * B类：172.16.0.0-172.31.255.255
	 * C类：192.168.0.0-192.168.255.255
	 *
	 * @param ip
	 * @return
	 */
	public static boolean isPrivateIp(String ip) {
		boolean flag = false;
		if (ip != null && ip.indexOf(".") != -1) {
			String[] tokens = ip.split("\\.");
			if (tokens.length < 4) {
				return false;
			}
			if ("10".equals(tokens[0]) || ("192".equals(tokens[0]) && "168".equals(tokens[1]))) {
				flag = true;
			} else if ("172".equals(tokens[0])) {
				int code = Integer.parseInt(tokens[1]);
				flag = code > 15 && code < 32;
			}
		}

		return flag;
	}

	/**
	 * 无效ip
	 */
	private static boolean invalidIp(String ip) {
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println(isPrivateIp(null));
		System.out.println(isPrivateIp(""));
		System.out.println(isPrivateIp("a.b"));
		System.out.println(isPrivateIp("10.2.1.177"));
		System.out.println(isPrivateIp("198.169.0.41"));
	}
}
