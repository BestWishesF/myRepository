package cn.hotol.app.common.util;

/*
 *      User: lubin
 *      Date: 2015/11/18
 *      Time: 17:50
 */

import cn.hotol.app.common.Constant;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JPushAndroidUtil {

    protected static final Logger LOG = LoggerFactory.getLogger(JPushAndroidUtil.class);

    public void memberPush(PushPayload pushPayload){
        JPushClient jpushClient = new JPushClient("ceb5480b0751d32b9a48c11d", "a007ef86473b89bf2b67dedf", 3);

        try {
            PushResult result = jpushClient.sendPush(pushPayload);
            LOG.info("==========推送用户结果==========" + result);

        } catch (APIConnectionException e) {
            // Connection error, should retry later
            LOG.error("Connection error, should retry later", e);

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            LOG.error("Should review the error, and fix the request", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    public void agencyPush(PushPayload pushPayload){
        JPushClient jpushClient = new JPushClient("2bbcd30f0a4ce136509235fa", "685b4f663d02118b84ed582c", 3);

        try {
            PushResult result = jpushClient.sendPush(pushPayload);
            LOG.info("==========推送代理人结果==========" + result);

        } catch (APIConnectionException e) {
            // Connection error, should retry later
            LOG.error("Connection error, should retry later", e);

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            LOG.error("Should review the error, and fix the request", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }


    /**
     * android端根据推送标识registrationId推送通知和消息
     * @param registrationId 推送标识
     * @param alert 通知内容
     * @param extra 附加内容
     * @param content 消息内容
     * @return
     */
    public PushPayload pushNoticeAndNews(List<String> registrationId, String alert, String extra, String content){
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(registrationId))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(alert)
                                .addExtra("from", extra)
                                .build())
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(content)
                        .addExtra("from",extra)
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(Constant.PUSH_ENVIRONMENT)
                        .build())
                .build();
    }

    /**
     * android端根据推送标识registrationId推送通知
     * @param registrationId 推送标识
     * @param alert 通知内容
     * @param extra 附加内容
     * @return
     */
    public PushPayload pushNotice(List<String> registrationId, String alert, String extra){
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(registrationId))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(alert)
                                .addExtra("from", extra)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(Constant.PUSH_ENVIRONMENT)
                        .build())
                .build();
    }

    /**
     * android端根据推送标识registrationId推送消息
     * @param registrationId 推送标识
     * @param extra 附加内容
     * @param content 消息内容
     * @return
     */
    public PushPayload pushNews(List<String> registrationId, String extra, String content){
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(registrationId))
                .setMessage(Message.newBuilder()
                        .setMsgContent(content)
                        .addExtra("from",extra)
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(Constant.PUSH_ENVIRONMENT)
                        .build())
                .build();
    }

    public static void main(String[] strings){
        JPushAndroidUtil jPushAndroidUtil=new JPushAndroidUtil();
        List<String> list=new ArrayList<String>();
        list.add("0a0cf17bb9d");
        jPushAndroidUtil.memberPush(jPushAndroidUtil.pushNoticeAndNews(list,"通知","1","消息"));
    }

}
