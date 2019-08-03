package io.anshily.base.utils.alipay.wapAlipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class AliPayForApp {
    public static String aliPay(String body,String title, String outTradeNo, String totalAmount,String passbackParams, String notifyUrl){
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", AliPayConfigForApp.APP_ID, AliPayConfigForApp.APP_PRIVATE_KEY, "json", "utf-8", AliPayConfigForApp.ALIPAY_PUBLIC_KEY, "RSA2");
//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(body);
        model.setSubject(title);
        model.setOutTradeNo(outTradeNo);
        //商家公共数据包
        model.setPassbackParams(passbackParams);
        model.setTimeoutExpress("30m");
        model.setTotalAmount(totalAmount);
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Map<String,String> getAliNotifyData(HttpServletRequest request) throws IOException {
//        FileWriter writer1 = new FileWriter(Constants.PATH_IMAGE_PATH + "aliPayNotify1.txt", false);
//        writer1.write("1111111");
//        writer1.close();
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
//        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
//        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
//        try {
//            boolean flag = AlipaySignature.rsaCheckV1(params, AliPayConfigForApp.ALIPAY_PUBLIC_KEY, "UTF-8","RSA2");
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//        FileWriter writer = new FileWriter(Constants.PATH_IMAGE_PATH + "aliPayNotify.txt", false);
//        writer.write(params.toString());
//        writer.close();
        return params;
    }
}
