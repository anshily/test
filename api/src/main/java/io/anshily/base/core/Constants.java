package io.anshily.base.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目常量
 */
public final class Constants {
    public static final String BASE_PACKAGE = "io.anshily";//项目基础包名称，根据自己公司的项目修改
    public static final String FRONT_DAO_PACKAGE = BASE_PACKAGE + ".front.dao";
    public static final String ADMIN_DAO_PACKAGE = BASE_PACKAGE + ".admin.dao";
    public static final String BIZ_DAO_PACKAGE = BASE_PACKAGE + ".biz.dao";
    public static final String MODEL_PACKAGE = BASE_PACKAGE + ".model";//Model所在包
    public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".dao";//Mapper所在包
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";//Service所在包
    public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";//ServiceImpl所在包
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller";//Controller所在包
    public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".base.core.Mapper";//Mapper插件基础接口的完全限定名
    //其他常量 非常重要
    public static final String PATH_IMAGE_PATH = "../uploads/";
    public static final String PATH_MANAGER_PATH = "../dkwd_web/";
    public static final String PATH_IMAGE_PATH_DB = "uploads/";


    //极光推送
    public static final String JG_MASTER_SECRET = "5b4f86a1049a0495d7b91201";
    public static final String JG_APP_KEY = "cd51a022b164170086722799";


//    gxchain test config

    public static final String EXAMPLE_ACCOUNT = "zinianly-1";
    public static final String EXAMPLE_PRIVATE_KEY = "5KCuWPtZ1jTMNP9G7vETiNJdesA27RzWHmfmXZrkmibfM3WhUJ2";
    public static final String EXAMPLE_PUBLIC_KEY = "GXC5aYScqt7azSG42J9c3GCXcRPrGrHUaQXgaQ1b4S1E1rkALGkfE";


    /* ~~~~~~~~~~~~~~~~~~~~~返回数据状态 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /*
     * 注意: 添加了全局静态常量之后, 必须在messageMap中添加对应的中文描述
     */
    // 查询成功
//    public staticByhang final String CODE_NORMAL = "0";
    public static final Integer SUCCESS = 0;
    public static final Integer FAIL = 400;
    public static final Integer UNAUTHORIZED = 401;
    public static final Integer NOT_FOUND = 404;
    public static final Integer INTERNAL_SERVER_ERROR = 500;

    //0 -1000内的编码不允许动 自定义请使用1000外的编码
    // 查询失败
    public static final Integer CODE_ERR_UNKNOWN = 100;
    public static final Integer CODE_ERR_PARAM = 101;
    public static final Integer CODE_ERR_ACCOUNT_EXIST = 103;
    public static final Integer CODE_ERR_ACCOUNT_NO_EXIST = 104;
    public static final Integer CODE_ERR_PARAM_ID = 102;
    public static final Integer CODE_ERR_IMAGE_NAME = 150;
    public static final Integer CODE_ERR_WAYBILL_UNKNOWN = 151;
    public static final Integer CODE_ERR_SMS_INVALID = 152;
    public static final Integer CODE_ERR_SOURCEING_NOT_EXIST = 153;
    public static final Integer CODE_ERR_DRIVER_CAR_STATUS = 154;
    public static final Integer CODE_ERR_DRIVER_NOT_SHORT_TERM = 155;
    public static final Integer CODE_ERR_OLD_PASSWORD_ERR = 156;
    public static final Integer CODE_ERR_NOT_BANK_CARD = 157;
    public static final Integer CODE_ERR_SOURCEING_STATUS_ERR = 158;
    public static final Integer CODE_ERR_BANK_CARD_EXIST = 159;
    public static final Integer CODE_ERR_USER_ERR_STATUS = 160;
    public static final Integer CODE_ERR_COMPANY_NAME_ERR = 161;
    public static final Integer CODE_ERR_STATEMENT_COMPANY_ERR = 162;
    public static final Integer CODE_ERR_DRIVER_CARDETAIL_STATUS = 163;
    public static final Integer CODE_ERR_PASSWORD_ERR = 164;
    public static final Integer CODE_ERR_ENTITY_IS_NULL = 165;
    public static final Integer CODE_ERR_SOURCING_INFO_ERR = 167;
    public static final Integer CODE_ERR_STATEMENT_COMPANY_NOT_EXIST = 168;
    public static final Integer CODE_ERR_CHAINS_NOT_EXIST = 169;
    public static final Integer CODE_ERR_ADDRESS_NOT_EXIST_STATEMENTCOMPANY = 170;
    public static final Integer CODE_SELECT_DRIVER_FULL = 171;
    public static final Integer CODE_NOT_SELECT_DRIVER = 172;
    public static final Integer CODE_NOT_DRIVER_SELECT = 173;
    public static final Integer CODE_WAYBILL_SUCCESS = 174;
    public static final Integer CODE_NOT_EXIST_ROLES = 175;    //角色不存在

    //运营端角色id
    public static final String CODE_ADMIN_ROLE_ID = "BQO1aXuV2vLPdKMVLCfWE8MIkD9Mhyks";
    public static final String CODE_WEIXIU_ROLE_ID = "oLOAla8QHsgGsfpgwek47Mgyfgmvxpwn";
    public static final String CODE_YOUQI_ROLE_ID = "lXguLQr30LO6pcYcFe9snkjbqN4lA9HT";
    public static final String CODE_QIPEI_ROLE_ID = "SqWRSbOHelXa6DwBi9uEMAPaWKCsAy9S";
    public static final String CODE_COMPONY_ROLE_ID = "TuXdtaapxOxPZ3ZO9U298mA7A9kmrcwH";
    public static final String CODE_STATEMENT_ROLE_ID = "MrJtT4LpiarIckNIlxsCFk7XK4rnIOPs";
    //融云配置
    public static final String RONGYUN_ACCOUNT_SID = "8aaf07085e4aee52015e4bd30cd40028";
    public static final String RONGYUN_ACCOUNT_TOKEN = "d394f006091f43f3892fecfe02d9a4bf";
    public static final String RONGYUN_APP_ID = "8aaf070864b08c210164b7204f8b054e";


    //后台运营端消息提示
    public static final Integer CODE_ERR_AREA_REPEAT = 1000;    //区域已存在
    public static final Integer CODE_ERR_TYPE_NULL = 1001;    //类型不允许为空
    public static final Integer CODE_ERR_USER_NAME = 1002;    //用户名或密码不能为空
    public static final Integer CODE_ERR_NAME_NULL = 1003;    //单位名称不允许为空
    public static final Integer CODE_ERR_UNIT_ADDRESS = 1004;    //单位地址已存在
    public static final Integer CODE_ERR_PHONE_NUMBER = 1005;    //手机号不能为空
    public static final Integer CODE_ERR_PASSWORD = 1006;    //密码不能为空
    public static final Integer CODE_ERR_QUESTION_TITLE_EXIST = 1007;    //问题标题已存在
    public static final Integer CODE_ERR_NOT_PHONE_NUMBER = 1008;    //未绑定手机号
    public static final Integer CODE_ERR_NOT_AUTHENTICATION = 1009;    //到个人中心完成认证方可回答
    public static final Integer CODE_ANSWERED_THE_QUESTION = 1010;    //您已回答过此问题，不能重复回答
    public static final Integer CODE_ERR_NICKNAME_EXIST = 1011;    // 昵称已存在
    public static final Integer CODE_COMMENTS_THE_ANSWER = 1012;    //您已评论过此回答，不能重复评论
    public static final Integer CODE_BLESSING_THE_ANSWER = 1013;    //您已加持过此回答，不能重复加持
    public static final Integer CODE_LIKE_THE_COMMENTS = 1014;    //您已赞过此评论，不能重复点赞
    public static final Integer CODE_ERR_ACCOUNT = 1015;    //该账号已被禁用
    public static final Integer CODE_ERR_BALANCE = 1016;    //余额不足，请先充值
    public static final Integer CODE_FOCUS_THE_MEMBER = 1017;    //已关注，不能重复关注
    public static final Integer CODE_ERROR_FOCUS_THE_MEMBER = 1018;    //您并没有关注此人
    public static final Integer CODE_HIDDEN_THE_DYNAMIC = 1019;    //该动态已隐藏
    public static final Integer CODE_ERROR_HIDDEN_THE_DYNAMIC = 1020;    //该动态未隐藏
    public static final Integer CODE_ERROR_PERSONAL_WEIGHT_NUMBER = 1021;    //个人重量值不能小于90
    public static final Integer CODE_ERROR_ANSWER_WEIGHT_NUMBER = 1022;    //回答重量值不能小于90
    public static final Integer CODE_ERR_REAL_NAME_EXIST = 1023;    //姓名不能相同
    public static final Integer CODE_ERR_NEW_PASSWORD_ERR = 1024;    //新密码和旧密码不能一致
    public static final Integer CODE_ERR_ACCOUNT_OR_PASSWORD_ERR = 1026;    //密码或账号错误


    public static Map<Integer, String> messageMap = new HashMap<Integer, String>() {
        private static final long serialVersionUID = 769847890431L;

        {
            // 查询成功
//            put(CODE_NORMAL, "操作成功");
            put(SUCCESS, "成功");
            put(FAIL, "失败");
            put(UNAUTHORIZED, "未认证（签名错误）");
            put(NOT_FOUND, "接口不存在");
            put(INTERNAL_SERVER_ERROR, "系统繁忙");

            // 查询失败
            put(CODE_ERR_UNKNOWN, "系统繁忙");
            put(CODE_ERR_PARAM, "参数异常");
            put(CODE_ERR_PARAM_ID, "参数异常: 缺少id");
            put(CODE_ERR_IMAGE_NAME, "图片拓展名异常");
            put(CODE_ERR_WAYBILL_UNKNOWN, "未知运单");
            put(CODE_ERR_SMS_INVALID, "验证码不正确");
            put(CODE_ERR_ACCOUNT_EXIST, "账号已存在");
            put(CODE_ERR_ACCOUNT_NO_EXIST, "该用户不存在");
            put(CODE_NOT_EXIST_ROLES, "角色不存在");
            put(CODE_ERR_NICKNAME_EXIST, "昵称已存在");
            put(CODE_COMMENTS_THE_ANSWER, "您已评论过此回答，不能重复评论");
            put(CODE_BLESSING_THE_ANSWER, "您已加持过此回答，不能重复加持");
            put(CODE_LIKE_THE_COMMENTS, "您已赞过此评论，不能重复点赞");
            put(CODE_ERR_ACCOUNT, "该账号已被禁用");
            put(CODE_ERR_BALANCE, "余额不足，请先充值");

            //后台运营端消息提示
            put(CODE_ERR_AREA_REPEAT, "该区域已存在");
            put(CODE_ERR_TYPE_NULL, "类型不允许为空");
            put(CODE_ERR_USER_NAME, "用户名或密码不能为空");
            put(CODE_ERR_PHONE_NUMBER, "手机号不能为空");
            put(CODE_ERR_PASSWORD, "密码不能为空");
            put(CODE_ERR_NAME_NULL, "单位名称不允许为空");
            put(CODE_ERR_UNIT_ADDRESS, "单位地址已存在");
            put(CODE_ERR_SOURCEING_NOT_EXIST, "货源不存在");
            put(CODE_ERR_DRIVER_CAR_STATUS, "您的信息未通过审核");
            put(CODE_ERR_DRIVER_CARDETAIL_STATUS, "车辆信息未完善");
            put(CODE_ERR_DRIVER_NOT_SHORT_TERM, "您在当前区域未短期租赁承运公司");
            put(CODE_ERR_OLD_PASSWORD_ERR, "旧密码不正确");
            put(CODE_ERR_PASSWORD_ERR, "密码不正确");
            put(CODE_ERR_NOT_BANK_CARD, "未绑定银行卡信息");
            put(CODE_ERR_SOURCEING_STATUS_ERR, "货源已被抢完或者货源已取消");
            put(CODE_ERR_BANK_CARD_EXIST, "已绑定银行卡");
            put(CODE_ERR_USER_ERR_STATUS, "用户信息未审核");
            put(CODE_ERR_COMPANY_NAME_ERR, "您所属的运输公司不存在或未审核通过");
            put(CODE_ERR_STATEMENT_COMPANY_ERR, "货源可挂靠的结算公司不存在");
            put(CODE_ERR_QUESTION_TITLE_EXIST, "问题标题已存在");
            put(CODE_ERR_NOT_PHONE_NUMBER, "未绑定手机号");
            put(CODE_ERR_NOT_AUTHENTICATION, "到个人中心完成认证方可回答");
            put(CODE_ANSWERED_THE_QUESTION, "您已回答过此问题，不能重复回答");
            put(CODE_FOCUS_THE_MEMBER, "已关注，不能重复关注");
            put(CODE_ERROR_FOCUS_THE_MEMBER, "您并没有关注此人");
            put(CODE_HIDDEN_THE_DYNAMIC, "该动态已隐藏");
            put(CODE_ERROR_HIDDEN_THE_DYNAMIC, "该动态未隐藏");
            put(CODE_ERROR_PERSONAL_WEIGHT_NUMBER, "个人重量值不能小于90");
            put(CODE_ERROR_ANSWER_WEIGHT_NUMBER, "回答重量值不能小于90");
            put(CODE_ERR_REAL_NAME_EXIST, "姓名不能相同");
            put(CODE_ERR_NEW_PASSWORD_ERR, "新密码和旧密码不能一致");

            put(CODE_ERR_ENTITY_IS_NULL, "查询结果为空");
            put(CODE_ERR_SOURCING_INFO_ERR, "货源信息错误");
            put(CODE_ERR_ACCOUNT_OR_PASSWORD_ERR, "账号或密码错误，请重新登录");

        }
    };


}
