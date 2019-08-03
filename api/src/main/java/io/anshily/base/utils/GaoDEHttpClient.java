package io.anshily.base.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class GaoDEHttpClient {

	//apiURL就是访问第三方的URL
	private static String apikey = "56984e7302d18a1b26c7151dfcd833e2";

	//apiURL就是访问第三方的URL
	private static String apiUSERURL = "http://restapi.amap.com/v3/geocode/regeo?key=56984e7302d18a1b26c7151dfcd833e2&location=116.481488,39.990464&poitype=小区&radius=1000&extensions=all&batch=false&roadlevel=0";
	//创建应用的时候会对应生成
	private static String client_id = "YXA6hr26UGYqEee7K-XiYQpmhQ";

	private static String client_secret = "YXA6NmZO-Kgrgy0oZGvEsKRnjwghzHQ";

	public static void main(String[] args) throws Exception {
		System.out.println("=========1获取token=========");
		String accessToken = getduanxin("56984e7302d18a1b26c7151dfcd833e2", "116.481488,39.990464");// 获取token
		if (accessToken != null)
			System.out.println(accessToken);

		//s String regest = regestUserByToken("YWMtYZLqfnv3Eee3AwlxJ_vTmwAAAAAAAAAAAAAAAAAAAAGGvbpQZioR57sr5eJhCmaFAgMAAAFdwDz7IABPGgBvAV-6ly-ooQlydmUvGJMhCxQvgyu5ZNJeCcFt_2sNOg","wang","123456");

	}

	public static String httpGet(String url, Map<String, Object> params) {

		String apiUrl = url;
		StringBuffer param = new StringBuffer();
		int i = 0;
		for (String key : params.keySet()) {
			if (i == 0)
				param.append("?");
			else
				param.append("&");
			param.append(key).append("=").append(params.get(key));
			i++;
		}

		String result = "";
		try {
			HttpClient client = new DefaultHttpClient();
			apiUrl += param;
			HttpGet httpget = new HttpGet(apiUrl);
			HttpResponse response = client.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instreams = entity.getContent();
				result = convertStreamToString(instreams);
				System.out.println(result);
			}
		} catch (Exception e) {
		}
		return result;
	}

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/** 
	 * 发送 GET 请求（HTTP），K-V形式 
	 * @param url 
	 * @param params 
	 * @return 
	 */
	public static String doGet(String url, Map<String, Object> params) {
		String apiUrl = url;
		StringBuffer param = new StringBuffer();
		int i = 0;
		for (String key : params.keySet()) {
			if (i == 0)
				param.append("?");
			else
				param.append("&");
			param.append(key).append("=").append(params.get(key));
			i++;
		}
		apiUrl += param;
		String result = null;
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpGet httpPost = new HttpGet(apiUrl);
			HttpResponse response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();

			System.out.println("执行状态码 : " + statusCode);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				result = IOUtils.toString(instream, "UTF-8");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getCityCode(String key, String location) {
		//?key=56984e7302d18a1b26c7151dfcd833e2&location=116.481488,39.990464&poitype=小区&radius=1000&extensions=all&batch=false&roadlevel=0
		String url = "http://restapi.amap.com/v3/geocode/regeo";
		HashMap<String, Object> pamarMap = new HashMap<String, Object>();
		pamarMap.put("key", key);
		pamarMap.put("location", location);
		pamarMap.put("poitype", "小区");
		pamarMap.put("radius", 1000);
		pamarMap.put("extensions", "all");
		pamarMap.put("batch", false);
		pamarMap.put("roadlevel", 0);
		String result = doGet(url, pamarMap);
		return result;
	}

	public static String getduanxin(String key, String location) throws UnsupportedEncodingException {
		//?key=56984e7302d18a1b26c7151dfcd833e2&location=116.481488,39.990464&poitype=小区&radius=1000&extensions=all&batch=false&roadlevel=0
		String url = "http://120.55.197.77:1210/Services/MsgSend.asmx/SendMsg";
		HashMap<String, Object> pamarMap = new HashMap<String, Object>();
		pamarMap.put("userCode", "MWDCF");
		pamarMap.put("userPass", "#7n@44@_Ny");
		pamarMap.put("DesNo", "18991286697");
		pamarMap.put("Msg", "您的验证码是654321【v蛋蛋】");
		pamarMap.put("Channel", 0);
		String result = httpGet(url, pamarMap);
		return result;
	}

	/**获得环信的所有用户
	*/
	public static String get(String method, String token) throws Exception {
		String resultStr = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet post = new HttpGet("");

		//请求时一定要把token放到header带过去
		post.setHeader("Authorization", "Bearer " + token);
		// 请求结束，返回结果
		HttpResponse res = httpClient.execute(post);
		// 如果服务器成功地返回响应
		String responseContent = null; // 响应内容
		HttpEntity httpEntity = res.getEntity();
		responseContent = EntityUtils.toString(httpEntity, "UTF-8");
		//           JSONObject json = JSONObject.parseObject(responseContent);
		//           System.out.println( json);
		// 关闭连接 ,释放资源
		httpClient.getConnectionManager().shutdown();
		return resultStr;
	}

	public static String regestUserByToken(String token, String username, String password) throws Exception {
		String resultStr = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(apiUSERURL);
		post.setHeader("Authorization", "Bearer " + token);
		//JsonParser jsonparer =JsonParser;// 初始化解析json格式的对象
		// 接收参数json列表
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("username", username);
		jsonParam.put("password", password);

		StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");

		post.setEntity(entity);
		// 请求结束，返回结果
		HttpResponse res = httpClient.execute(post);
		// 如果服务器成功地返回响应
		String responseContent = null; // 响应内容
		HttpEntity httpEntity = res.getEntity();
		responseContent = EntityUtils.toString(httpEntity, "UTF-8");
		Gson gosn = new GsonBuilder().create();
		//System.out.println( responseContent);
		//JsonObject json = JsonParser.parse(responseContent);
		// JSONObject json = JSONObject.parseObject(responseContent);
		// .getAsJsonObject();

		if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			//if (json.get("errcode") != null){
			//    //resultStr = json.get("errcode").getAsString();
			//} else {// 正常情况下
			//    resultStr = json.get("access_token").toString();
			//}
			resultStr = responseContent;
		}
		// 关闭连接 ,释放资源
		httpClient.getConnectionManager().shutdown();
		return resultStr;
	}

}
