package cn.hotol.app.common.util;

import cn.hotol.app.common.Constant;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;

/**
 * Created by lizhun on 16/1/12.
 */
public class IosGetuiUtil {
    static String appId = Constant.GETUI_MEMBER_APPID;
    static String appKey = Constant.GETUI_MEMBER_APPKEY;
    static String masterSecret = Constant.GETUI_MEMBER_MASTERSECRET;
//    static String devicetoken = "a85235f5babd1f8f8b6c10e909b9ca8eaca71315c90f65512c75daa97399162c";
    static String url ="http://sdk.open.api.igexin.com/serviceex";
    //使用https的域名
    //static String url = "https://api.getui.com/apiex.htm";
    public static void apnpush(String title,String body,String devicetoken,String stt_id)  {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionType(1);        //应用启动类型，1：强制应用启动 2：等待应用启动
        template.setTransmissionContent(stt_id);  //透传内容


        //APN高级推送
        APNPayload apnpayload = new APNPayload();
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setBody(body);
        alertMsg.setActionLocKey("english");
        alertMsg.setLocKey("");
        alertMsg.addLocArg("LocArg");
        alertMsg.setLaunchImage("LaunchImage");
        //IOS8.2支持字段
        alertMsg.setTitle(title);
        alertMsg.setTitleLocKey("");
        alertMsg.addTitleLocArg("TitleLocArg");
        apnpayload.setBadge(0);
        apnpayload.setAlertMsg(alertMsg);
        apnpayload.setContentAvailable(1);
        //apnpayload.Category = "";
        apnpayload.setSound("esp_custom1.caf");
        apnpayload.addCustomMsg("payload", "payload");
        apnpayload.addCustomMsg("from","1");
        template.setAPNInfo(apnpayload);

        IGtPush push = new IGtPush(url, appKey, masterSecret);


        SingleMessage message = new SingleMessage();
        message.setData(template);
        IPushResult pushResult = push.pushAPNMessageToSingle(appId, devicetoken, message);
        System.out.println(pushResult.getResponse());
        //设置客户端展示时间
        //String begin = "2015-03-06 14:28:10";
        //String end = "2015-03-06 14:38:20";
        //template.setDuration(begin, end);

        //return template;
//        IGtPush push = new IGtPush(url, appKey, masterSecret);
//        APNTemplate t = new APNTemplate();
//        APNPayload apnpayload = new APNPayload();
//        apnpayload.setSound("default");
//        apnpayload.setContentAvailable(stt_id);
//        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
//        alertMsg.setTitle(title);
//        alertMsg.setBody(body);
//        alertMsg.setTitleLocKey("ccccc");
//        alertMsg.setActionLocKey("ddddd");
//        apnpayload.setAlertMsg(alertMsg);
//
//        t.setAPNInfo(apnpayload);
//        SingleMessage sm = new SingleMessage();
//        sm.setData(t);
//        IPushResult ret0 = push.pushAPNMessageToSingle(appId,devicetoken, sm);
//        System.out.println(ret0.getResponse());

    }

    public static void main(String[] args) throws Exception {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("from", "2");
//        String str = JSONObject.fromObject(map).toString();
//        apnpush("标题","内容","76020167585c0b45d2e04f81f590e197e99367bad14ad8a35659c0235e952a58",str);

        /*PushParamsDto pushParamsDto = new PushParamsDto();
        pushParamsDto.setSound("default");
        pushParamsDto.setOpen_id("5");
        pushParamsDto.setText("您的快件已被支付.");
        pushParamsDto.setTitle("速邮汇");
        pushParamsDto.setCid("2226ba73d71a413f88808eee0b868f650f760722c4ab26d6d89eeb66a272d3b2");
        GetuiIosMemberUtil.pushMember(pushParamsDto);*/

        System.out.println(MemcachedUtils.get(MemcachedKey.DIVIDE_DATA_MAP));

    }
}
