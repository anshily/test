package io.anshily.base.utils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {
	public static String[] TABLESINDEX = { "01", "02", "03", "04", "05", "06",
			"07", "08", "09", "10", "11", "12" };

	public static String SPLITFLG = "@";

	public static String SUPERSPLITFLG = "#";

	public static int EARTH_RADIUS = 6378137;

	public static boolean isNotEmpty(String obj) {
		return obj != null && !obj.trim().equals("") && !obj.equals("null");
	}

	public static boolean isEmpty(String obj) {
		return obj == null || obj.equals("") || obj.equals("null");
	}

	public static String convertNullToEmpty(String str) {
		return isEmpty(str) ? "" : str;
	}

	public static String getFmtDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	@SuppressWarnings("deprecation")
	public static String getFmtDate(Date date, String pattern, int val) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date d = new Date(date.getYear(), date.getMonth(), date.getDate() + val);
		return sdf.format(d);
	}

	public static Date getDate(String date, String hour, String minute) {
		if (isEmpty(date) || isEmpty(hour))
			return null;
		if (isEmpty(minute))
			minute = "00";
		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			d = sdf.parse(date + " " + hour + ":" + minute);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	public static String getFmtStrDate_(String date) {
		if (date.length() < 10)
			return "";
		return date.substring(0, 4) + date.substring(5, 7)
				+ date.substring(8, 10);
	}

	public static int getFmtIntDay(String date) {
		int day = 0;
		if (date.length() < 10)
			return day;
		return Integer.parseInt(date.substring(8, 10));
	}

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

	/**
	 * 
	 * @方法名：getWeekFirstDay
	 * @功能描述：[取得指定日期所在周的第一天 ]
	 * @作者：王春林 (wangchunlin@durian.com)
	 * @日期：Mar 20, 2014
	 * @param date
	 * @return String
	 */
	public static String getWeekFirstDay(Date date, String pattern) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return getFmtDate(c.getTime(), pattern);
	}

	/**
	 * 
	 * @方法名：getWeekLastDay
	 * @功能描述：[取得指定日期所在周的最后一天 ]
	 * @作者：王春林 (wangchunlin@durian.com)
	 * @日期：Mar 20, 2014
	 * @param date
	 * @param pattern
	 * @return String
	 */
	public static String getWeekLastDay(Date date, String pattern) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return getFmtDate(c.getTime(), pattern);
	}

	public static String generateKey() {
		try {
			Thread.sleep(100);
		} catch (Exception ex) {

		}
		String key = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssms");
		key = sdf.format(new Date())
				+ String.valueOf((int) (Math.random() * 10000));
		for (; key.length() < 20;) {
			key = key + "0";
		}
		return key;
	}

	/**
	 * 替换字符串msgtext当中的由{}指示的部分内容，返回完整的字符串
	 * 
	 * @param msgtext
	 * @param params
	 * @return
	 */
	public static String substituteParams(String msgtext, Object params[]) {
		Locale locale = Locale.CHINESE;
		String localizedStr = null;
		if (params == null || msgtext == null) {
			return msgtext;
		}
		StringBuffer b = new StringBuffer(100);
		MessageFormat mf = new MessageFormat(msgtext);
		if (locale != null) {
			mf.setLocale(locale);
			b.append(mf.format(params));
			localizedStr = b.toString();
		}
		return localizedStr;
	}

	public static Double changeLongToDouble(long longView) {
		String doubleStr = Long.toString(longView);
		return new Double(doubleStr);
	}

	public static boolean contrastTime(Date contrastTime, Date nowTime,
			int dayCount) {
		boolean result = false;
		long dayCountTime = dayCount * 24 * 60 * 60 * 1000;
		if (nowTime.getTime() - contrastTime.getTime() > dayCountTime) {
			result = true;
		}
		return result;
	}

	public static String toGBK(String uniStr) {
		String gbStr = "";
		if (uniStr == null) {
			uniStr = "";
		}
		try {
			byte[] tempByte = uniStr.getBytes("ISO8859_1");
			gbStr = new String(tempByte, "GB2312");
		} catch (Exception ex) {
		}
		return gbStr;
	}

	public static String toUni(String gbStr) {
		String uniStr = "";
		if (gbStr == null) {
			gbStr = "";
		}
		try {
			byte[] tempByte = gbStr.getBytes("GB2312");
			uniStr = new String(tempByte, "ISO8859_1");
		} catch (Exception ex) {
		}
		return uniStr;
	}

	public static String LongTo2str(Long value) {
		BigInteger src = new BigInteger(value.toString());// 转换为BigInteger类型
		return src.toString(2);
	}

	public static Long str2ToLong(String value) {
		BigInteger src = new BigInteger(value, 2);// 转换为BigInteger类型
		return new Long(src.toString());
	}

	public static String stringTo8str2(String value) {
		BigInteger src = new BigInteger(value);
		String str2 = src.toString(2);
		while (str2.length() < 8) {
			str2 = "0" + str2;
		}
		return str2;
	}

	// 将毫秒数换算成x天x时x分x秒x毫秒;
	public static String format(long ms) {
		int ss = 1000;
		int mi = 60;
		int hh = mi * 60;
		int dd = hh * 24;
		long day = Math.abs(Math.abs(ms) / dd);
		long hour = Math.abs((Math.abs(ms) - day * dd) / hh);
		long minute = Math.abs((Math.abs(ms) - day * dd - hour * hh) / mi);
		long second = Math.abs((Math.abs(ms) - day * dd - hour * hh - minute
				* mi)
				/ ss);
		long milliSecond = Math.abs(Math.abs(ms) - day * dd - hour * hh
				- minute * mi - second * ss);
		String strDay = day < 10 ? "0" + day : "" + day;
		String strHour = hour < 10 ? "0" + hour : "" + hour;
		String strMinute = minute < 10 ? "0" + minute : "" + minute;
		@SuppressWarnings("unused")
		String strSecond = second < 10 ? "0" + second : "" + second;
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : ""
				+ milliSecond;
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : ""
				+ strMilliSecond;
		return strDay + " 天" + strHour + " 小时" + strMinute + " 分钟";
	}

	/**
	 * 
	 * @Description: [把单引号转义为双单引号]
	 * @Date: Jul 1, 2011
	 * @@param inStr
	 * @@return
	 */
	public static String escapeSingleQuotes(String inStr) {
		if (Utils.isNotEmpty(inStr)) {
			inStr = inStr.replace("'", "''");
			inStr = inStr.replace("\\", "\\\\");
			inStr = inStr.trim();
		}else{
			inStr = "";
		}
		return inStr;
	}

	/**
	 * 
	 * @Description: [弧度换算]
	 * @Date: Feb 9, 2012
	 * @@param gpsData
	 * @@return
	 */
	public static Double rad(Double gpsData) {
		return gpsData * Math.PI / 180.0;
	}

	/**
	 * 
	 * @Description: [计算两个gis经纬度之间的距离]
	 * @Date: Feb 9, 2012
	 * @@param lat1纬度 如34.321300
	 * @@param lng1
	 * @@param lat2
	 * @@param lng2
	 * @@return
	 */
	public static Long GetDistance(Double lat1, Double lng1, Double lat2,
			Double lng2) {
		Double radLat1 = rad(lat1);
		Double radLat2 = rad(lat2);
		Double a = radLat1 - radLat2;
		Double b = rad(lng1) - rad(lng2);
		Double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		Long s1 = Math.round(s);
		return s1;
	}

	/**
	 * 
	 * @Description: [将地图坐标转化为平面坐标]
	 * @Date: Feb 9, 2012
	 * @@param gpsX
	 * @@param gpsY
	 * @@param width 平面宽度
	 * @@param height 平面高度
	 * @@return
	 */
	public static String getPlaneCoordinate(Double gpsX, Double gpsY,
			Double maxLon, Double minLon, Double maxLat, Double minLat,
			int maxX, int minX, int maxY, int minY) {
		String planeCoor = "";
		int width = maxX - minX;
		int height = maxY - minY;
		// X轴上每像素代表的经度秒数
		Double scaleX = ((maxLon - minLon) * 3600) / width;
		// Y轴上每像素代表的纬度秒数
		Double scaleY = ((maxLat - minLat) * 3600) / height;

		Double X = (gpsX - minLon) * 3600 / scaleX;
		Double Y = (gpsY - minLat) * 3600 / scaleY;

		X = X + minX;
		Y = minY + Y;

		planeCoor = Math.round(X) + "," + Math.round(Y);
		/**
		 * 屏幕坐标转经纬度坐标公式如下： 公式：gpsX = X * scaleX/3600 + minLon； 公式：gpsY = maxLat
		 * - y* scaleY/3600；
		 */
		return planeCoor;
	}

	/**
	 * 中文符号转换为英文
	 */
	public static String conversionCHToEN(String s) {
		s = s.replace("／", "/");
		s = s.replace("—", "-");
		s = s.replace("。", ".");
		s = s.replace("（", "(");
		s = s.replace("）", ")");
		s = s.replace("？", "?");
		s = s.replace("；", ";");
		s = s.replace("：", ":");
		s = s.replace("，", ",");
		return s;
	}

	/**
	 * 截取字符串，显示.. flag 0-中间变.. 1-后边变.. number 前边显示几位
	 */
	public static String subString(String s, int flag, int number) {
		if (flag == 0) {
			if (s.length() > number) {
				return s.substring(0, number) + ".."
						+ s.substring(s.length() - 1, s.length());
			} else {
				return s;
			}
		} else {
			if (s.length() > number) {
				return s.substring(0, number) + "..";
			} else {
				return s;
			}
		}
	}

	/**
	 * 
	 * @方法名：getRandomCode
	 * @功能描述：[生成六位随机数]
	 * @作者：王春林 (wangchunlin@durian.com)
	 * @日期：2014年10月9日
	 * @return
	 */
	public static String getRandomCode() {
		// String[] beforeShuffle = new String[] { "2", "3", "4", "5", "6", "7",
		// "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
		// "k", "l", "m", "n", "p", "q", "r", "s", "t", "u", "v",
		// "w", "x", "y", "z" };
		String[] beforeShuffle = new String[] { "0", "1", "2", "3", "4", "5",
				"6", "7", "8", "9" };
		List<?> list = Arrays.asList(beforeShuffle);
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		String afterShuffle = sb.toString();
		String result = afterShuffle.substring(3, 9);
		return result;
	}

	/**
	 * 获取IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("PRoxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if ("0:0:0:0:0:0:0:1".equals(ip)) {
			ip = "127.0.0.1";
		}
		return ip;
	}

	public static String getSqlIds(String str) {
		String ids = str.substring(0, str.length() - 1);
		ids = "'" + ids + "'";
		ids = ids.replace(",", "','");
		return ids;
	}

	public static Calendar getDateOfLastMonth(Calendar date) {
		Calendar lastDate = (Calendar) date.clone();
		lastDate.add(Calendar.MONTH, -1);
		return lastDate;
	}

	public static String getDateOfLastMonth(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		try {
			Date date = sdf.parse(dateStr);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			return sdf.format(getDateOfLastMonth(c).getTime());
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date format(yyyy-MM): "
					+ dateStr);
		}
	}

	/**
	 * 
	 * @方法名：getMinimumIntegerDigits
	 * @功能描述：[给数字补位 在组合某一个编号时需要以0来补位，比如1的时候需要显示 0001]
	 * @作者：王春林 (wangchunlin@durian.com)
	 * @日期：Dec 4, 2013
	 * @param number
	 * @param size
	 * @return String
	 */
	public static String getMinimumIntegerDigits(long number, int size) {
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setMinimumIntegerDigits(size);
		formatter.setGroupingUsed(false);
		String s = formatter.format(number);
		return s;
	}

	public static boolean isNum(String str){
		 return str.matches("^[0-9]+$");
	}
	
	public static boolean isNumeric(String str){
		 return str.matches("^[0-9]+(\\.[0-9]{1,2})?$");
	} 
	public static boolean isPhone(String str){
		String regex = "^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$|^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\\d{8}$";
		return str.matches(regex);
	}
	public static boolean isLetterDigit(String str) {
		  String regex = "^[a-z0-9A-Z]+$";
		  return str.matches(regex);
		 }
	
	public static boolean isLeDigit(String str) {
		  String regex = "^[a-zA-Z]+$";
		  return str.matches(regex);
		 }
	
	//孕周校验
	public static boolean isGestationalWeeks(String str){
		String regex = "^([1-3]{1}[0-9]{1})[w]?[+]?[1-6]?|([4]{1}[0-1]{1})[w]?[+]?[1-6]?|([4]{1}[2]{1})[w]?$";
		return str.matches(regex);
	}
	
	//生成7位随机数
//	public staticByhang String getRandomNo(){
//		Random random = new Random();
//        String r = "";
//        for (int i = 0; i < 7; i++) {
//			r+=random.nextInt(10);
//		}
//		return r;
//	}
	
	public static String getStrings(String strNo){
		// str = "00001102";// 测试用字符串
		String str = strNo.substring(3, strNo.length());
		int len = str.length();//取得字符串的长度
		int index = 0;//预定义第一个非零字符串的位置

		char strs[] = str.toCharArray();// 将字符串转化成字符数组
		for(int i=0; i<len; i++){
		if('0'!=strs[i]){
		index=i;// 找到非零字符串并跳出
		break;
		}
		}
		String strLast = str.substring(index, len);// 截取字符串
		return getNo(Integer.parseInt(strLast));
		
	}
	
	public static String getNo(int str){
		str++;
		 String result ="";
	    //大于1000时直接转换成字符串返回
	   //根据位数的不同前边补不同的0
	      int  length = (str +"").length();
	      if(length == 1){
	        result = "000000"+str;
	      }else if(length ==2){
	        result = "00000"+str;
	      }else if(length==3){
	        result = "0000"+str;
	      }else if(length==4){
	    	result = "000"+str;
	      }else if(length==5){
	    	result = "00"+str;
	      }else if(length==6){
	    	result = "0"+str;
	     }else if(length==7){
	    	 result = String.valueOf(str);
	     }
	    
	    return result;
	  }
	
	//从一个字符串中提取出所有的数字
	public static long getDigitFromString(String input)
	{
		if ((input == null) || (input.length() == 0))
		{
			return -1L;
		}
		return Long.parseLong(input.replaceAll("[^0-9]+", ""));
	}
	
	//BGIseq5001500181514
	public synchronized static String getPoolingNoNumber(String poolingNoMax){
		// str = "00001102";// 测试用字符串
		String str = poolingNoMax.substring(11, poolingNoMax.length());
		int len = str.length();//取得字符串的长度
		int index = 0;//预定义第一个非零字符串的位置

		char strs[] = str.toCharArray();// 将字符串转化成字符数组
		for(int i=0; i<len; i++){
		if('0'!=strs[i]){
		index=i;// 找到非零字符串并跳出
		break;
		}
		}
		String strLast = str.substring(index, len);// 截取字符串
		
		return getPoolingNo(Integer.parseInt(strLast));
	}
	public static String getPoolingNo(int str){
		str++;
		 String result ="";
	    //大于1000时直接转换成字符串返回
	   //根据位数的不同前边补不同的0
	      int  length = (str +"").length();
	      if(length == 1){
	        result = "0000000"+str;
	      }else if(length ==2){
	        result = "000000"+str;
	      }else if(length==3){
	        result = "00000"+str;
	      }else if(length==4){
	    	result = "0000"+str;
	      }else if(length==5){
	    	result = "000"+str;
	      }else if(length==6){
	    	result = "00"+str;
	     }else if(length==7){
	        result = "0"+str;
	     }else if(length==8){
	    	 result=String.valueOf(str);
	     }
	     return result;
	  }
	public static void main(String[] args) {
		System.out.println(isNum("12"));
	
	}
}
