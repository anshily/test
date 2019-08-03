package io.anshily.base.utils;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import io.anshily.base.core.Constants;

public class JGPush {
    //极光推送给所有人发送消息
    public static boolean sendToAll(String content){
        JPushClient jpushClient = new JPushClient(Constants.JG_MASTER_SECRET, Constants.JG_APP_KEY, null, ClientConfig.getInstance());

        // For push, all you need do is to build PushPayload object.
        PushPayload payload = PushPayload.alertAll(content);

        try {
            PushResult result = jpushClient.sendPush(payload);
            if (result.statusCode == 0){
                return true;
            }else {
                return false;
            }
        } catch (APIConnectionException | APIRequestException e) {
            // Connection error, should retry later
            System.out.println(e);
            return false;
        }
    }
    //极光推送给单个用户发送小 需传用户推送注册id 和消息内容
    public static boolean sendToOne(String registrationID,String content){
        JPushClient jpushClient = new JPushClient(Constants.JG_MASTER_SECRET, Constants.JG_APP_KEY, null, ClientConfig.getInstance());

        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.registrationId(registrationID)).setNotification(Notification.alert(content)).build();

        try {
            PushResult result = jpushClient.sendPush(payload);
            if (result.statusCode == 0){
                return true;

            }else {
                return false;

            }
        } catch (APIConnectionException | APIRequestException e) {
            System.out.println(e);
            return false;
        }
    }
    //极光推送给单个用户发送小 需传用户推送注册id 和消息内容
    public static boolean sendToOneWithTitle(String registrationID,String title,String content){
        JPushClient jpushClient = new JPushClient(Constants.JG_MASTER_SECRET, Constants.JG_APP_KEY, null, ClientConfig.getInstance());

        try {
            PushPayload payload_ios = PushPayload.newBuilder()
                    .setPlatform(Platform.ios())
                    .setAudience(Audience.registrationId(registrationID))
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(IosNotification.newBuilder()
                                    .setAlert(IosAlert.newBuilder().setTitleAndBody(title,"",content).build())
                                    .setMutableContent(true)
                                    .setBadge(0)
                                    .setSound("happy")
                                    .addExtra("form", "JPush")
                                    .build())
                            .build())
                    .build();
            PushResult result_ios = jpushClient.sendPush(payload_ios);
            if ((result_ios != null && result_ios.statusCode == 0)){
                return true;
            }else {
                return false;
            }
        } catch (APIConnectionException | APIRequestException e) {
            System.out.println(e);
            PushPayload payload_android = PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.registrationId(registrationID)).setNotification(Notification.android(content, title, null)).build();
            try {
                PushResult result_android = jpushClient.sendPush(payload_android);
                if ((result_android != null && result_android.statusCode == 0)){
                    return true;
                }else {
                    return false;
                }
            }catch (APIConnectionException | APIRequestException err){
                return false;
            }
        }
    }
}
