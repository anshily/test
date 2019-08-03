package io.anshily.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * HttpServletResponse帮助类
 * 
 * @author arron.wu
 * 
 */
public final class ResponseUtils {
	public static final Logger log = LoggerFactory.getLogger(ResponseUtils.class);

	/**
	 * 发送文本。使用UTF-8编码。
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderText(HttpServletResponse response, String text) {
		render(response, "text/plain;charset=UTF-8", text);
	}

	/**
	 * 发送json。使用UTF-8编码。
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderJson(HttpServletResponse response, String text) {
		// String start = System.nanoTime();
		// System.out.print(text);
		// render(response, "text/xml;charset=UTF-8", text);
		render(response, "text/plain;charset=UTF-8", text);
		// render(response, "application/json;charset=UTF-8", text);

		// System.out.println("=============================================================="
		// + (System.nanoTime() - start));
	}

	/**
	 * 发送xml。使用UTF-8编码。
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderXml(HttpServletResponse response, String text) {
		render(response, "text/xml;charset=UTF-8", text);
	}

	/**
	 * 发送内容。使用UTF-8编码。
	 * 
	 * @param response
	 * @param contentType
	 * @param text
	 */
	public static void render(HttpServletResponse response, String contentType, String text) {
		// response.setBufferSize(arg0);
		response.setContentType(contentType);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			response.getWriter().write(text);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	public static void renderImage(HttpServletResponse response, InputStream inputStream) throws IOException {
		response.setContentType("image/jpeg");

		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		OutputStream outputStream = response.getOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, len);
		}
	}
	public static void renderFile(HttpServletResponse response, InputStream inputStream, String fileName) throws IOException {
		response.reset();
		response.setContentType("application/x_msdownload");
		response.setHeader("Content-Type", "application/octet-stream");
		response.addHeader("Content-Disposition", "attachment;filename="
			     + new String(fileName.getBytes(), "ISO8859-1"));
		OutputStream outputStream = response.getOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, len);
		}
	}
}
