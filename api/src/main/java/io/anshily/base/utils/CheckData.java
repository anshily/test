package io.anshily.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckData {

	/**
	 * 验证邮箱地址是否正确
	 * 
	 * @param email
	 * @return 错误返回 true
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			if (!checkNull(email)) {
				return true;
			}
			String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			// LOG.error("验证邮箱地址错误", e);
			flag = false;
		}

		return flag;
	}

	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 * @return 错误返回 true
	 */
	public static boolean isMobileNO(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(14[0-9]))\\d{8}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			// LOG.error("验证手机号码错误", e);
			flag = false;
		}
		return flag;
	}

	/**
	 * 
	 * 检查字符长度 长度必须是6到13位
	 * 
	 */
	public static boolean checkNull(String obj) {
		boolean flag = false;
		if (obj != null && obj.trim().length() > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * 检查字符长度 长度必须是6到13位
	 * 
	 */
	public static boolean checkLength(String obj) {
		boolean flag = false;
		int num = obj.length();
		if (num >= 6 && num <= 13) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 可以为空 对于卡号只进行长度验证 8-16位
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean checkCardNumber(String obj) {

		// 可以为空
		if (!checkNull(obj)) {
			return true;
		}

		boolean flag = false;
		int num = obj.length();
		if (num >= 8 && num <= 16) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 功能：身份证的有效验证
	 * 
	 * @param IDStr
	 *            身份证号
	 * @return 有效：true 无效：false
	 */
	public static boolean IDCardValidate(String IDStr) throws Exception {
		// 可以为空
		if (!checkNull(IDStr)) {
			return true;
		}

		boolean flag = false;

		// String errorInfo = "";// 记录错误信息
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
		String Ai = "";
		// ================ 号码的长度 15位或18位 ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			// errorInfo = "身份证号码长度应该为15位或18位。";
			return flag;
		}
		// =======================(end)========================

		// ================ 数字 除最后以为都为数字 ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {
			// errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
			return flag;
		}

		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
			// errorInfo = "身份证生日无效。";
			return flag;
		}

		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
				|| (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
			// errorInfo = "身份证生日不在有效范围。";
			return flag;
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			// errorInfo = "身份证月份无效";
			return flag;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			// errorInfo = "身份证日期无效";
			return flag;
		}

		// ================ 地区码时候有效 ================
		//		Object h = null;
		//		if (h.get(Ai.substring(0, 2)) == null) {
		//			// errorInfo = "身份证地区编码错误。";
		//			return flag;
		//		}
		// ==============================================

		// ================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;

		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr) == false) {
				// errorInfo = "身份证无效，不是合法的身份证号码";
				return flag;
			}
		} else {
			return true;
		}

		// =====================(end)=====================
		return true;
	}

	/**
	 * 功能：设置地区编码
	 * 
	 * @return Hashtable 对象
	 */
	@SuppressWarnings("unused")
	private static Object GetAreaCode() {
		//Hashtable hashtable = new Hashtable();
		//hashtable.put("11", "北京");
		//hashtable.put("12", "天津");
		//hashtable.put("13", "河北");
		//hashtable.put("14", "山西");
		//hashtable.put("15", "内蒙古");
		//hashtable.put("21", "辽宁");
		//hashtable.put("22", "吉林");
		//hashtable.put("23", "黑龙江");
		//hashtable.put("31", "上海");
		//hashtable.put("32", "江苏");
		//hashtable.put("33", "浙江");
		//hashtable.put("34", "安徽");
		//hashtable.put("35", "福建");
		//hashtable.put("36", "江西");
		//hashtable.put("37", "山东");
		//hashtable.put("41", "河南");
		//hashtable.put("42", "湖北");
		//hashtable.put("43", "湖南");
		//hashtable.put("44", "广东");
		//hashtable.put("45", "广西");
		//hashtable.put("46", "海南");
		//hashtable.put("50", "重庆");
		//hashtable.put("51", "四川");
		//hashtable.put("52", "贵州");
		//hashtable.put("53", "云南");
		//hashtable.put("54", "西藏");
		//hashtable.put("61", "陕西");
		//hashtable.put("62", "甘肃");
		//hashtable.put("63", "青海");
		//hashtable.put("64", "宁夏");
		//hashtable.put("65", "新疆");
		//hashtable.put("71", "台湾");
		//hashtable.put("81", "香港");
		//hashtable.put("82", "澳门");
		//hashtable.put("91", "国外");
		return null;
	}

	/**
	 * 验证日期字符串是否是YYYY-MM-DD格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDataFormat(String str) {
		boolean flag = false;
		// String
		// regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
		String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		Pattern pattern1 = Pattern.compile(regxStr);
		Matcher isNo = pattern1.matcher(str);
		if (isNo.matches()) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 功能：判断字符串是否为数字
	 * 
	 * @param str
	 * @return 是数字返回true
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean checkNumber(String str) {
		try {
			Double.valueOf(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 去掉数据中的p标签
	 * 
	 * @return
	 */
	public static String takeOutTag(String value) {
		if (!checkNull(value)) {
			return null;
		}

		Pattern pattern = Pattern.compile("</?p[^>]*>");
		Matcher matcher = pattern.matcher(value);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);

		return sb.toString().replaceAll("\r\n\r\n\r\n", "\r\n").replaceAll("\r\n\r\n", "\r\n").replaceAll("&nbsp;", "");

		// return value.replaceAll("<p>", "").replaceAll("</p>",
		// "").replaceAll("\r\n\r\n\r\n", "\r\n").replaceAll("\r\n\r\n",
		// "\r\n").replaceAll("&nbsp;", "");
	}

	/**
	 * 
	 * @Title: isNotEmpty
	 * @Description: [验证字符串是否不为空]
	 * @Author: 王春林（chunlin.wang@51durian.com）
	 * @Date: 2014年8月20日
	 * @param obj
	 *            String
	 * @return boolean
	 */
	public static boolean isNotEmpty(String obj) {
		return obj != null && !obj.trim().equals("") && !obj.equals("null");
	}

	/**
	 * 
	 * @Title: isEmpty
	 * @Description: [方法描述]
	 * @Author: 王春林（chunlin.wang@51durian.com）
	 * @Date: 2014年8月20日
	 * @param obj
	 *            String
	 * @return boolean
	 */
	public static boolean isEmpty(String obj) {
		return obj == null || obj.equals("") || obj.equals("null");
	}

	/**
	 * @Title: validList
	 * @Description: [验证List是否为空]
	 * @Author: 王春林（chunlin.wang@51durian.com）
	 * @Date: 2014年8月20日
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean validList(List obj) {
		if (obj != null && obj.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * @Title: getFmtDate
	 * @Description: [日期转字符串]
	 * @Author: 王春林（chunlin.wang@51durian.com）
	 * @Date: 2014年8月20日
	 * @param date
	 * @param pattern
	 * @return
	 */

	public static String getFmtDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 
	 * @Title: getFmtDate
	 * @Description: [字符串转日期]
	 * @Author: 王春林（chunlin.wang@51durian.com）
	 * @Date: 2014年8月20日
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date getFmtDate(String date, String pattern) {
		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

}
