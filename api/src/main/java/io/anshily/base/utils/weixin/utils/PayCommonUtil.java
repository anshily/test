package io.anshily.base.utils.weixin.utils;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import io.anshily.base.core.Constants;
import io.anshily.base.core.Result;
import io.anshily.base.core.ResultGenerator;
import io.anshily.base.utils.QRCode.QrCodeCreateUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;


public class PayCommonUtil {
    @SuppressWarnings("unused")
    private static Logger log = LoggerFactory.getLogger(PayCommonUtil.class);

    public static String CreateNoncestr(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < length; i++) {
            Random rd = new Random();
            res += chars.indexOf(rd.nextInt(chars.length() - 1));
        }
        return res;
    }

    /**
     * 是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     *
     * @return boolean
     */
    public static boolean isTenpaySign(String characterEncoding, SortedMap<Object, Object> packageParams, String API_KEY) {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!"sign".equals(k) && null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        }

        sb.append("key=" + API_KEY);

        //算出摘要
        String mysign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toLowerCase();
        String tenpaySign = ((String) packageParams.get("sign")).toLowerCase();

        //System.out.println(tenpaySign + "    " + mysign);
        return tenpaySign.equals(mysign);
    }

    public static String CreateNoncestr() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < 16; i++) {
            Random rd = new Random();
            res += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        return res;
    }

    /**
     * @param characterEncoding 编码格式
     * @param parameters        请求参数
     * @return
     * @author 武亚洲
     * @date 2014-12-5下午2:29:34
     * @Description：sign签名
     */
    @SuppressWarnings("rawtypes")
    public static String createSign(String characterEncoding, SortedMap<Object, Object> parameters, boolean flag) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        if (flag) {
            sb.append("key=" + WXAPPConfigUtil.API_KEY);
        }

        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }

    /**
     * @param characterEncoding 编码格式
     * @param parameters        请求参数
     * @return
     * @author 武亚洲
     * @date 2014-12-5下午2:29:34
     * @Description：sign签名
     */
    @SuppressWarnings("rawtypes")
    public static String createSignForJs(String characterEncoding, SortedMap<Object, Object> parameters, boolean flag) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        System.out.println(sb);
        if (flag) {
            sb.append("key=" + WXConfigUtil.API_KEY);
        }

        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }

    /**
     * @param parameters 请求参数
     * @return
     * @author 武亚洲
     * @date 2014-12-5下午2:32:05
     * @Description：将请求参数转换为xml格式的string
     */
    @SuppressWarnings("rawtypes")
    public static String getRequestXml(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
//            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k) || "appid".equalsIgnoreCase(k) || "mch_id".equalsIgnoreCase(k) || "mch_id".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * @param return_code 返回编码
     * @param return_msg  返回信息
     * @return
     * @author 武亚洲
     * @date 2014-12-3上午10:17:43
     * @Description：返回给微信的参数
     */
    public static String setXML(String return_code, String return_msg) {
        //resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
        //+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
        //resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
        //		+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        /*FileWriter writer = new FileWriter(Constants.PATH_IMAGE_PATH + "wx_notify.txt", false);
		writer.write("");
		writer.close();
		*/
        return "<xml><return_code><![CDATA[" + return_code
                + "]]></return_code><return_msg><![CDATA[" + return_msg
                + "]]></return_msg></xml>";
    }

    /*
     * 微信支付
     * app_id appID
     * mch_id 商户号
     * out_trade_no 订单编号
     * total_fee 订单金额
     * openid 只有在微信公众号才有
     * body 商品描述
     * trade_type APP/JSAPI
     * product_id 产品id
     * attach 商家数据包
     * notify_url 回调接口地址
     * */
    public static SortedMap<Object, Object> wxPay(String app_id, String mch_id, String out_trade_no, Double total_fee, String openid, String body, String trade_type, String product_id, String attach, String notify_url, HttpServletRequest request) throws Exception {
        try {
            SortedMap<Object, Object> data = new TreeMap<Object, Object>();
            data.put("appid", app_id);
            data.put("mch_id", mch_id);
            data.put("body", body);
            data.put("nonce_str", PayCommonUtil.CreateNoncestr());

            data.put("sign_type", "MD5");
            data.put("out_trade_no", out_trade_no);
            data.put("fee_type", "CNY");
            data.put("total_fee", String.valueOf(Math.round(total_fee * 100)));
            if (trade_type.equals("JSAPI")) {
                data.put("openid", openid);
            }
            data.put("spbill_create_ip", HttpCommonUtil.getIpAddr(request));
            data.put("notify_url", notify_url);
            data.put("trade_type", trade_type);
            data.put("product_id", product_id);
            data.put("attach", attach);
            String sign = "";
            if (trade_type.equals("APP")) {
                sign = PayCommonUtil.createSign("UTF-8", data, true);
            }
            if (trade_type.equals("JSAPI")) {
                sign = PayCommonUtil.createSignForJs("UTF-8", data, true);
            }
            data.put("sign", sign);
            String string = PayCommonUtil.getRequestXml(data);
            System.out.println(string);
            String result = HttpCommonUtil.httpsRequest(WXAPPConfigUtil.UNIFIED_ORDER_URL, "POST", string);
            System.out.println(result);
            /*统一下单接口返回正常的prepay_id，再按签名规范重新生成签名后，将数据传输给APP。参与签名的字段名为appId，partnerId，prepayId，nonceStr，timeStamp，package。注意：package的值格式为Sign=WXPay*/
            Map map = XMLUtil.doXMLParse(result);
            SortedMap<Object, Object> resultMap = new TreeMap<Object, Object>();
            if (map != null) {
                String sign_other = "";
//				resultMap.put("trade_type", trade_type);
                //微信内支付
                if (trade_type.equals("JSAPI")) {
                    resultMap.put("signType", "MD5");
                    resultMap.put("appId", map.get("appid"));
                    resultMap.put("nonceStr", PayCommonUtil.CreateNoncestr());
                    //本来生成的时间戳是13位，但是ios必须是10位，所以截取了一下
                    resultMap.put("timeStamp", System.currentTimeMillis() / 1000 + "");
                    resultMap.put("package", "prepay_id=" + map.get("prepay_id"));
                    sign_other = PayCommonUtil.createSignForJs("UTF-8", resultMap, true);
                    resultMap.put("paySign", sign_other);
                }
                //app支付
                if (trade_type.equals("APP")) {
                    resultMap.put("appid", map.get("appid"));
                    resultMap.put("partnerid", map.get("mch_id"));
                    resultMap.put("prepayid", map.get("prepay_id"));
                    resultMap.put("noncestr", PayCommonUtil.CreateNoncestr());
                    //本来生成的时间戳是13位，但是ios必须是10位，所以截取了一下
                    resultMap.put("timestamp", String.valueOf(System.currentTimeMillis()).substring(0, 10));
                    resultMap.put("package", "Sign=WXPay");
                    sign_other = PayCommonUtil.createSign("UTF-8", resultMap, true);
                    resultMap.put("paysign", sign_other);
                }
            }
            resultMap.put("result", map);
            return resultMap;
        } catch (Exception err) {
            err.printStackTrace();
            return null;
        }
    }

    /**
     * 微信二维码支付
     * @return
     */
    public static Result wxQRPay(String balance, HttpServletRequest request) throws Exception{
        SortedMap<Object, Object> param = new TreeMap<Object, Object>();
        //公众号ID
        param.put("appid", WXConfigUtil.APPID);
        //订单主题
        param.put("body", "下单");
        //商户号
        param.put("mch_id", WXConfigUtil.MCH_ID);
        //随机字符串
        param.put("nonce_str", PayCommonUtil.getRandomString(16));
        //回调地址
        param.put("notify_url", WXConfigUtil.WX_PAY_NOTIFY_URL);
        //商户订单号
        param.put("out_trade_no", PayCommonUtil.getRandomString(16));
        //客户端IP
        param.put("spbill_create_ip", HttpCommonUtil.getIpAddr(request));
        //金额
        param.put("total_fee", PayCommonUtil.changeY2F(balance));
        //订单类型
        param.put("trade_type", "NATIVE");
        String sign = createSign(param, WXConfigUtil.API_KEY);
        //签名
        param.put("sign", sign);
        System.out.println("========== sign: " + sign);
        String xml = PayCommonUtil.getRequestXML(param);
        System.out.println("========== xml: " + xml);
        String result = HttpCommonUtil.httpsRequest(WXConfigUtil.QRCODE_PAY_URL, "POST", xml);
        System.out.println("==========content: " + result);
        JSONObject result_xml = JSONObject.parseObject(PayCommonUtil.xml2JSON(result));
        if ("OK".equals(result_xml.get("return_msg"))) {
            String return_code = result_xml.getString("code_url");
            String url = QrCodeCreateUtil.createQrCode(return_code);
            return ResultGenerator.successResult(url);
        } else {
            return ResultGenerator.errResult(Constants.FAIL);
        }
    }

    public static Map getWXNotifyData(HttpServletRequest request, String wx_key) {
        try {
            //读取参数
            InputStream inputStream;
            StringBuffer sb = new StringBuffer();
            inputStream = request.getInputStream();
            String s;
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((s = in.readLine()) != null) {
                sb.append(s);
            }
            in.close();
            inputStream.close();

            //解析xml成map
            Map<String, String> m = new HashMap<String, String>();
            m = XMLUtil.doXMLParse(sb.toString());
            //过滤空 设置 TreeMap
            SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
            Iterator it = m.keySet().iterator();
            while (it.hasNext()) {
                String parameter = (String) it.next();
                String parameterValue = m.get(parameter);

                String v = "";
                if (null != parameterValue) {
                    v = parameterValue.trim();
                }
                packageParams.put(parameter, v);
            }
            //判断签名是否正确
            if (PayCommonUtil.isTenpaySign("UTF-8", packageParams, wx_key)) {
                return packageParams;
            } else {
                return null;
            }
        } catch (Exception err) {
            err.printStackTrace();
            return null;
        }
    }

    // 随机字符串生成
    public static String getRandomString(int length) { // length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    //金额转换
    public static String changeY2F(String amount) {
        String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥
        // 或者$的金额
        int index = currency.indexOf(".");
        int length = currency.length();
        Long amLong = 0l;
        if (index == -1) {
            amLong = Long.valueOf(currency + "00");
        } else if (length - index >= 3) {
            amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
        } else if (length - index == 2) {
            amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
        } else {
            amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
        }
        return amLong.toString();
    }

    // 生成 签名
    public static String createSign(SortedMap<Object, Object> parame, String apiKey) throws Exception {
        StringBuffer buffer = new StringBuffer();
        Set set = parame.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            Object value = (String) entry.getValue();
            if (null != value && !"".equals(value) && !"sign".equals(key) && !"key".equals(key)) {
                buffer.append(key + "=" + value + "&");
            }
        }
        buffer.append("key=" + apiKey);
        System.out.println("============ " + buffer.toString());
        String sign = MD5(buffer.toString()).toUpperCase();
        System.out.println("签名参数：" + sign);
        return sign;
    }

    //  MD5 加密
    public static String MD5(String data) throws Exception {
        java.security.MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    // MAP 转 xml
    public static String getRequestXML(SortedMap<Object, Object> parame) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<xml>");
        Set set = parame.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            //过滤相关字段sign
            if ("sign".equalsIgnoreCase(key)) {
                buffer.append("<" + key + ">" + "<![CDATA[" + value + "]]>" + "</" + key + ">");
            } else {
                buffer.append("<" + key + ">" + value + "</" + key + ">");
            }
        }
        buffer.append("</xml>");
        return buffer.toString();
    }

    //  xml 转 json
    public static String xml2JSON(String xml) {
        JSONObject obj = new JSONObject();
        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(is);
            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            List<Element> elementList = root.elements();
            for (Element e : elementList)
                obj.put(e.getName(), e.getText());
            return obj.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
