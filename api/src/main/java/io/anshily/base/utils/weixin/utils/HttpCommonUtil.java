package io.anshily.base.utils.weixin.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;


/**
 * 通用工具类
 * @author 武亚洲
 * @date 2014-11-21下午9:10:30
 */
public class HttpCommonUtil {
	@Autowired
	private HttpServletRequest request;
	private static Logger log = LoggerFactory.getLogger(HttpCommonUtil.class);
	/**
	 * 发送https请求
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return 返回微信服务器响应的信息
	 */
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded"); 
			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			return buffer.toString();
		} catch (ConnectException ce) {
			log.error("连接超时：{}", ce);
		} catch (Exception e) {
			log.error("https请求异常：{}", e);
		}
		return null;
	}

	/**
	 * 获取接口访问凭证
	 * 
	 * @param
	 * @return
	 */
//	public staticByhang Token getToken(String appid, String appsecret) {
//		Token token = null;
//		String requestUrl = WXAPPConfigUtil.TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
//		// 发起GET请求获取凭证
//		JSONObject jsonObject = JSONObject.fromObject(httpsRequest(requestUrl, "GET", null));
//
//		if (null != jsonObject) {
//			try {
//				token = new Token();
//				token.setAccessToken(jsonObject.getString("access_token"));
//				token.setExpiresIn(jsonObject.getInt("expires_in"));
//			} catch (JSONException e) {
//				token = null;
//				// 获取token失败
//				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
//			}
//		}
//		return token;
//	}
	public static String urlEncodeUTF8(String source){
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * @Description: 获取客户端IP地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
		if (request == null)
			return null;
		String s = request.getHeader("X-Forwarded-For");
		if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
			s = request.getHeader("Proxy-Client-IP");
		if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
			s = request.getHeader("WL-Proxy-Client-IP");
		if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
			s = request.getHeader("HTTP_CLIENT_IP");
		if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
			s = request.getHeader("HTTP_X_FORWARDED_FOR");
		if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
			s = request.getRemoteAddr();
		if ("127.0.0.1".equals(s) || "0:0:0:0:0:0:0:1".equals(s))
			try {
				s = InetAddress.getLocalHost().getHostAddress();
			}
			catch (UnknownHostException unknownhostexception) {
			}
		return s;
	}
	//获取请求体中的字符串(POST)
	public static String getBodyData(HttpServletRequest request) {
		StringBuffer data = new StringBuffer();
		String line = null;
		BufferedReader reader = null;
		try {
			reader = request.getReader();
			while (null != (line = reader.readLine()))
				data.append(line);
		} catch (IOException e) {
		} finally {
		}
		return data.toString();
	}

}