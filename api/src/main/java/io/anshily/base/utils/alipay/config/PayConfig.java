package io.anshily.base.utils.alipay.config;

public class PayConfig {

	public static String payExpTime = "60m";

	// 支付宝
	// =====================================================================================================
	public static String appid = "2088121453123651";
	public static String alipayPartner = "2088121453123651";
	public static String alipayMD5Key = "cf2dqiyncc95z6wcou1xafoxsjb9v4u1";
	//支付宝公钥
	public static String alipayRSAPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	//商户私钥（pkcs8编码后的）
	public static String alipayRSAPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAM5bSlLbAavS314qzEReLfKGNS/rGXu56UgJQUtCXD2g0rhAWfxyBptRE6tHmxcVycK/cvMPe2IX6ObvzxrdzTAe+kbcyloSJhLZUW/RRgHWZ6kpRGDMmXB25HpiDJZZ79ptYJmPzUM0ooovDCG4FbLEWxOGVBGiRE/k22IO31YZAgMBAAECgYAtQzrnN6CDNsrF1e9eI6ZCZwutigzZ5ap0oYsP3etrnSX7Qio6xCwMz1jYHem3w5CwxduJ0uCGYKY2WW19AB+sRImV9SEnZnyeSGD0E9iREDmhlF4AYBiz13vFrPjEJKzyzk+Q5plZY+PhzP+wKcSJrqgTG6HL+mzZBf3iEmIHcQJBAO5pHWeOvvrt37fzXdAdHKAzvbxxANspuNMG6AfGdIxRSgKe/U704HOxfcn80FraCGV1GaZmNHBXlYYgvbMK/zUCQQDdlMSvAJQQCtPIWvs2hVwWprfMN225rQ01I07F7/1FCvmSn5L9Zjbi+BstmEgYdhHsv7opEjfxtaxgEP7JWePVAkEA0JaBFojKyQp0ESJJMvs1f2ilmYiZAic3wNIrEhptABNjGHX4FCulcTOaZa3esY5PiEzy2yZSvPO0K7zbnBmD/QJBANxswRhCYAhxbOrULzG2irxxz0eVIE9kak4hOJzJt872Mv38RnJUO2egXxEFezryQbSdqihvWj3Wd4r9fSh6hp0CQC88vquF2nmZedabNzXJiKQKAHOL/YAq92/x/ZDcn+rCHvyzUsZfeoyxZSUX51CHkt14nVosIzdjokDwJCXFYKg=";

	public static String alipaySeller = "woyaomei029@163.com";

	public static String alipayNotifyUrlForKuaiJie = "http://api.woyaomei.com/alipay/alipayNotifyForKuaiJie.action";
	public static String alipayNotifyUrlForWap = "http://api.woyaomei.com/alipay/alipayNotifyForWap.action";
	public static String alipayReturnUrlForWap = "http://api.woyaomei.com/alipay/alipayCallbackForWap.action";

}
