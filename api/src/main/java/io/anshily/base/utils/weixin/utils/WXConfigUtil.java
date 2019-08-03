package io.anshily.base.utils.weixin.utils;

/**
 * 微信公众号配置工具类
 *
 * @author 武亚洲
 * @date 2014-11-21下午9:10:30
 */
public class WXConfigUtil {
    /**
     * 服务号相关信息
     */
    public final static String APPID = "wx01b237bebb45e136";//微信公众号id
    public final static String APP_SECRECT = "bb2dcba032500d88dcb4c43a3b1fc848";//服务号的应用密码
    public final static String MCH_ID = "1487688962";//商户号
    public final static String API_KEY = "qaz12wsx34edc12rfv45gf64hf7rj58f";//微信支付商家API密钥
    public final static String BASE_URL = "http://www.huozhuhui.com/";
//	public final staticByhang String BASE_URL = "http://q.hnqingyun.com/";

    //微信支付成功回调
    public final static String WX_PAY_NOTIFY_URL = BASE_URL + "/wx/pay/payMarginNotify";


    //oauth2授权时回调action
    public final static String REDIRECT_URI = BASE_URL + "wyhy/api/user/wx_login";
    //重定向的微信端首页地址
    public final static String REDIRECT_HOME_URI = BASE_URL + "wyhy_wx/www";
    /**
     * 微信基础接口地址
     */
    //获取token接口(GET)
    public final static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret=" + APP_SECRECT;
    //JSAPI_TICKET_URL
    public final static String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=ACCESS_TOKEN";

    //oauth2授权接口(GET)
    public final static String OAUTH2_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + APPID + "&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    //获取access_token接口
    public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID + "&secret=" + APP_SECRECT + "&code=CODE" + "&grant_type=authorization_code";
    //刷新access_token接口（GET）
    public final static String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + APPID + "&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
    //拉取用户信息接口
    public final static String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    // 菜单创建接口（POST）
    public final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    // 菜单查询（GET）
    public final static String MENU_GET_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
    // 菜单删除（GET）
    public final static String MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
    //    二维码支付
    public final static String QRCODE_PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
}
