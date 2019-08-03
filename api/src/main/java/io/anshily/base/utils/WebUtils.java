package io.anshily.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebUtils {

	private static final Logger logger = LoggerFactory.getLogger(WebUtils.class);
	private static final int MAX_AGE = 60 * 60 * 24 * 365;

	/**
	 * 判断本地应用服务器是否正在运行
	 * @param port 端口
	 * @return 返回true则代表正在运行
	 */
	public static boolean isServerRunning(int port) {
		try {
			Socket socket = new Socket("127.0.0.1", port);
			socket.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * 添加Cookie
	 * @param name 名字
	 * @param value 值
	 * @param response HttpServletResponse对象
	 */
	public static void addCookie(final String name, final String value, final HttpServletResponse response) {
		try {
			Cookie cookie = new Cookie(name, value);
			cookie.setMaxAge(MAX_AGE);
			response.addCookie(cookie);
		} catch (RuntimeException e) {
			logger.error("添加Cookie时出错！", e);
		}
	}
	/**
	 * 获得Cookie
	 * @param name 名字
	 * @param value 值
	 * @param response HttpServletResponse对象
	 */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        Cookie returnCookie = null;

        if (cookies == null) {
            return returnCookie;
        }

        for (final Cookie thisCookie : cookies) {
            if (thisCookie.getName().equals(name) && !"".equals(thisCookie.getValue())) {
                returnCookie = thisCookie;
                break;
            }
        }

        return returnCookie;
    }
	/**
	 * 转义Html内容中的特殊符号
	 * @param html 未转义的html内容
	 * @return 转义后的html内容
	 */
	public static String escapeXml(final String html) {
		if (Utils.isEmpty(html)) {
			return html;
		}

		StringBuilder builder = new StringBuilder();

		int length = html.length();
		char c;

		for (int i = 0; i < length; i++) {

			c = html.charAt(i);
			switch (c) {
			case '&':
				builder.append("&amp;");
				break;
			case '<':
				builder.append("&lt;");
				break;
			case '>':
				builder.append("&gt;");
				break;
			case '"':
				builder.append("&quot;");
				break;
			case '\'':
				builder.append("&#39;");
				break;
			default:
				builder.append(c);
				break;
			}
		}

		return builder.toString();
	}

	/**
	 * 转义html中的脚本标记
	 * @param html 未转义的html内容
	 * @return 去掉脚本标记的html内容
	 */
	public static String escapeJavaScript(final String html) {
		if (html == null) {
			return html;
		}
		Pattern pattern = Pattern.compile(
				"(<script[^>]*>[\\s\\S]*?</script[^>]*>)|(<[^<]*(javascript|on[\\s\\S]*?=|frame)[^>]*>)",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(html);
		StringBuffer stringBuffer = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(stringBuffer, escapeXml(matcher.group()));
		}
		matcher.appendTail(stringBuffer);
		return stringBuffer.toString();
	}

	//效验
	public static boolean sqlValidate(String str) {
		if(Utils.isEmpty(str))return false;
        str = str.toLowerCase();//统一转为小写
        String badStr = "exec|execute|insert|delete|update|drop|truncate|" +
                "char|declare|sitename|net user|xp_cmdshell|;|+|create|" +
                "table|from|grant|group_concat|column_name|" +
                "information_schema.columns|table_schema|union|where|select|order by|count(|" +
                "master";//过滤掉的sql关键字，可以手动添加
        String[] badStrs = badStr.split("\\|");
        for (int i = 0; i < badStrs.length; i++) {
            if (str.indexOf(badStrs[i]) >= 0) {
                return true;
            }
        }
        return false;
    }
}
