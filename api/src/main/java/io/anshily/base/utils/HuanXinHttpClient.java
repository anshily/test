package io.anshily.base.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HuanXinHttpClient {

	//apiURL就是访问第三方的URL
	private static String apiURL = "http://a1.easemob.com/1154170608178586/xiangshow/token";

	//apiURL就是访问第三方的URL
	private static String apiUSERURL = "http://a1.easemob.com/1154170608178586/xiangshow/users";
	//创建应用的时候会对应生成
	private static String client_id = "YXA6hr26UGYqEee7K-XiYQpmhQ";

	private static String client_secret = "YXA6NmZO-Kgrgy0oZGvEsKRnjwghzHQ";

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		System.out.println("=========1获取token=========");
		String accessToken = getToken();// 获取token
		if (accessToken != null)
			System.out.println(accessToken);

		String regest = regestUserByToken(
				"YWMtYZLqfnv3Eee3AwlxJ_vTmwAAAAAAAAAAAAAAAAAAAAGGvbpQZioR57sr5eJhCmaFAgMAAAFdwDz7IABPGgBvAV-6ly-ooQlydmUvGJMhCxQvgyu5ZNJeCcFt_2sNOg",
				"wang", "123456");

	}

	public static String getToken() throws Exception {
		String resultStr = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(apiURL);
		//JsonParser jsonparer =JsonParser;// 初始化解析json格式的对象
		// 接收参数json列表
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("grant_type", "client_credentials");
		jsonParam.put("client_id", client_id);
		jsonParam.put("client_secret", client_secret);
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
		@SuppressWarnings("unused")
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

			JSONObject token = JSONObject.fromObject(responseContent);

			resultStr = token.getString("access_token"); //responseContent;
		}
		// 关闭连接 ,释放资源
		httpClient.getConnectionManager().shutdown();
		return resultStr;
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
		@SuppressWarnings("unused")
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
		@SuppressWarnings("unused")
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
