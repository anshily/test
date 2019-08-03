package io.anshily.base.utils.weixin.utils;

import io.anshily.base.core.Constants;
import io.anshily.base.utils.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WeChatSDK {
    public static Map<String, String> getSignPackage(String url) throws IOException {
        return sign(getJsApiTicket(), url);
    }

    public static String  getJsApiTicket() throws IOException {
        String access_token = getAccessToken();
        File file = new File(Constants.PATH_IMAGE_PATH + "jsapi_ticket.txt");
        System.out.println(file.getCanonicalPath());
        if (file.exists()) {
            FileReader fr = new FileReader(Constants.PATH_IMAGE_PATH + "jsapi_ticket.txt");
            int ch = 0;
            String str = "";
            while((ch = fr.read()) != -1){
                str += (char)ch;
            }
            fr.close();
            JSONObject jsapi_ticket_data = JSONObject.fromObject(str);
            if (Long.valueOf(jsapi_ticket_data.get("expire_time").toString()) < Long.valueOf(DateUtil.getNowTimestamp())){
                String data = httpsRequest(WXConfigUtil.JSAPI_TICKET_URL.replace("ACCESS_TOKEN",access_token),"GET",null);
                JSONObject jsapi_ticket_obj = JSONObject.fromObject(data);
                if (data != null){
                    jsapi_ticket_obj.put("expire_time",Long.valueOf(DateUtil.getNowTimestamp()) + 5000000);
                    FileWriter writer = new FileWriter(Constants.PATH_IMAGE_PATH + "jsapi_ticket.txt", false);
                    writer.write(jsapi_ticket_obj.toString());
                    writer.close();
                    return jsapi_ticket_obj.get("ticket").toString();
                }else {
                    throw new RuntimeException(data);
                }
            }else {
                return jsapi_ticket_data.get("ticket").toString();
            }
        }else {
            String data = httpsRequest(WXConfigUtil.JSAPI_TICKET_URL.replace("ACCESS_TOKEN",access_token),"GET",null);
            FileWriter writer = new FileWriter(Constants.PATH_IMAGE_PATH + "jsapi_ticket.txt", false);
            if (data != null){
                JSONObject jsapi_ticket_obj = JSONObject.fromObject(data);
                jsapi_ticket_obj.put("expire_time",Long.valueOf(DateUtil.getNowTimestamp()) + 5000000);
                writer.write(jsapi_ticket_obj.toString());
                writer.close();
                return jsapi_ticket_obj.get("ticket").toString();
            }else {
                throw new RuntimeException(data);
            }
        }
    }
    public static String  getAccessToken() throws IOException {
        File file = new File(Constants.PATH_IMAGE_PATH + "access_token.txt");
        System.out.println(file.getCanonicalPath());
        if (file.exists()) {
            FileReader fr = new FileReader(Constants.PATH_IMAGE_PATH + "access_token.txt");
            int ch = 0;
            String str = "";
            while((ch = fr.read()) != -1){
                str += (char)ch;
            }
            fr.close();
            JSONObject access_token_data = JSONObject.fromObject(str);
            if (Long.valueOf(access_token_data.get("expire_time").toString()) < Long.valueOf(DateUtil.getNowTimestamp())){
                String data = httpsRequest(WXConfigUtil.TOKEN_URL,"GET",null);
                JSONObject access_token_obj = JSONObject.fromObject(data);
                if (data != null && data.contains("access_token")){
                    access_token_obj.put("expire_time",Long.valueOf(DateUtil.getNowTimestamp()) + 5000000);
                    FileWriter writer = new FileWriter(Constants.PATH_IMAGE_PATH + "access_token.txt", false);
                    writer.write(access_token_obj.toString());
                    writer.close();
                    return access_token_obj.get("access_token").toString();
                }else {
                    throw new RuntimeException(data);
                }
            }else {
                return access_token_data.get("access_token").toString();
            }
        }else {
            String data = httpsRequest(WXConfigUtil.TOKEN_URL,"GET",null);
            FileWriter writer = new FileWriter(Constants.PATH_IMAGE_PATH + "access_token.txt", false);
            if (data != null){
                JSONObject access_token_obj = JSONObject.fromObject(data);
                access_token_obj.put("expire_time",Long.valueOf(DateUtil.getNowTimestamp()) + 5000000);
                writer.write(access_token_obj.toString());
                writer.close();
                return access_token_obj.get("access_token").toString();
            }else {
                throw new RuntimeException(data);
            }
        }
    }
    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
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
    protected static String getDate(){
        Date currentTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(currentTime);
    }
    public static String downloadMedia(String mediaIdData) throws IOException {
        String access_token = getAccessToken();
        String media_url = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=";
        if (mediaIdData.contains("[")){
            JSONArray mediaArr = JSONArray.fromObject(mediaIdData);
            JSONArray imgArr = new JSONArray();
            for (int i = 0; i < mediaArr.size();i++){
                String url_str = media_url.concat(access_token).concat("&media_id=").concat(mediaArr.get(i).toString());
                // 生成文件名
                String randomName = UUID.randomUUID().toString() + ".png";
                String imageName =  getDate() + "/" + randomName;
                //服务器真正存放的路径
                String fileName = Constants.PATH_IMAGE_PATH + imageName;
                if (StringUtils.isEmpty(access_token) || StringUtils.isEmpty(mediaArr.get(i).toString()))return null;
                URL url = new URL(url_str);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                InputStream in = conn.getInputStream();
                File file = new File(fileName);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
//                String ContentDisposition = conn.getHeaderField("Content-disposition");
//                String weixinServerFileName = ContentDisposition.substring(ContentDisposition.indexOf("filename")+10, ContentDisposition.length() -1);
//                fileName += weixinServerFileName;
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName));
                byte[] data = new byte[1024];
                int len = -1;
                while ((len = in.read(data)) != -1) {
                    bos.write(data,0,len);
                }
                bos.close();
                in.close();
                conn.disconnect();
//                FileWriter writer3 = new FileWriter(Constants.PATH_IMAGE_PATH + "filetest.txt", false);
//                writer3.write(fileName);
//                writer3.close();
                imgArr.add(Constants.PATH_IMAGE_PATH_DB + imageName);
            }
            return imgArr.toString();
        }else {
            JSONArray imgArr = new JSONArray();
            String url_str = media_url.concat(access_token).concat("&media_id=").concat(mediaIdData);
            // 生成文件名
            String randomName = UUID.randomUUID().toString() + ".png";
            String imageName =  getDate() + "/" + randomName;
            //服务器真正存放的路径
            String fileName = Constants.PATH_IMAGE_PATH + imageName;
            if (StringUtils.isEmpty(access_token) || StringUtils.isEmpty(mediaIdData)) return null;
            URL url = new URL(url_str);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            InputStream in = conn.getInputStream();
            File file = new File(fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
//            String ContentDisposition = conn.getHeaderField("Content-disposition");
//            String weixinServerFileName = ContentDisposition.substring(ContentDisposition.indexOf("filename")+10, ContentDisposition.length() -1);
//            fileName += weixinServerFileName;
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName));
            byte[] data = new byte[1024];
            int len = -1;
            while ((len = in.read(data)) != -1) {
                bos.write(data,0,len);
            }

            bos.close();
            in.close();
            conn.disconnect();
//            FileWriter writer3 = new FileWriter(Constants.PATH_IMAGE_PATH + "filetest.txt", false);
//            writer3.write(fileName);
//            writer3.close();
            imgArr.add(Constants.PATH_IMAGE_PATH_DB + imageName);
            return imgArr.getString(0);
        }
    }
}
