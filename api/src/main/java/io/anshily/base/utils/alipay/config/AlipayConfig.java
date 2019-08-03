package io.anshily.base.utils.alipay.config;

/**
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。

 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {

	public static String gateway = "https://mapi.alipay.com/gateway.do?";

	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = PayConfig.alipayPartner;

	public static String md5_key = PayConfig.alipayMD5Key;

	// 商户的私钥
	public static String rsa_private_key = PayConfig.alipayRSAPrivateKey;

	// 支付宝的公钥，无需修改该值
	public static String rsa_public_key = PayConfig.alipayRSAPublicKey;

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";

	// 签名方式 ,可以是RSA,也可以是MD5,试过了,都可以
	public static String sign_type = "RSA";

	public static String seller_email = PayConfig.alipaySeller;

	// 商户appid
	public static String APPID = "";

	// 私钥 pkcs8格式的
	public static String RSA_PRIVATE_KEY = "";

	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://商户网关地址/alipay.trade.wap.pay-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	public static String return_url = "http://商户网关地址/alipay.trade.wap.pay-JAVA-UTF-8/return_url.jsp";

	// 请求网关地址
	public static String URL = "https://openapi.alipay.com/gateway.do";

	// 编码
	public static String CHARSET = "UTF-8";

	// 返回格式
	public static String FORMAT = "json";

	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjrEVFMOSiNJXaRNKicQuQdsREraftDA9Tua3WNZwcpeXeh8Wrt+V9JilLqSa7N7sVqwpvv8zWChgXhX/A96hEg97Oxe6GKUmzaZRNh0cZZ88vpkn5tlgL4mH/dhSr3Ip00kvM4rHq9PwuT4k7z1DpZAf1eghK8Q5BgxL88d0X07m9X96Ijd0yMkXArzD7jg+noqfbztEKoH3kPMRJC2w4ByVdweWUT2PwrlATpZZtYLmtDvUKG/sOkNAIKEMg3Rut1oKWpjyYanzDgS7Cg3awr1KPTl9rHCazk15aNYowmYtVabKwbGVToCAGK+qQ1gT3ELhkGnf3+h53fukNqRH+wIDAQAB";

	// 日志记录目录
	public static String log_path = "/log";

	// RSA2
	public static String SIGNTYPE = "RSA2";

}
