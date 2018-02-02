package cn.hotol.app.common.util;

import cn.hotol.app.common.Constant;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lizhun on 16/1/12.
 */
public class AndroidGetuiUtil {
    static String appId = Constant.GETUI_AGENT_APPID;
    static String appkey = Constant.GETUI_AGENT_APPKEY;
    static String master = Constant.GETUI_AGENT_MASTERSECRET;
    static String CID = "7ffac663adcd2dfccd964447c540a835";
//    static String CID = "8ea5c04b46d6fe3b6acf0960f2fc42fc";
    static String host = "http://sdk.open.api.igexin.com/apiex.htm";
    public static void main(String[] args) throws Exception {
        IGtPush push = new IGtPush(host, appkey, master);
//        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
//        template.setAppId(appId);
//        template.setAppkey(appkey);

        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
//        template.setTransmissionType(1);
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("from", "3");
//        String str = JSONObject.fromObject(map).toString();
//        template.setTransmissionContent(str);

//        Style1 style1 = new Style1();
        // 设置通知栏标题与内容
//        style1.setTitle("标题");
//        style1.setText("内容");
//        template.setTitle("标题");
//        template.setText("内容");
        // 配置通知栏图标
//        style1.setLogo("ic_logon.png");
//        template.setLogo("ic_logon.png");
        // 配置通知栏网络图标
//        style1.setLogoUrl("");
//        template.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
//        style1.setRing(true);
//        style1.setVibrate(true);
//        style1.setClearable(true);
//        template.setStyle(style1);
//        template.setIsRing(true);
//        template.setIsVibrate(true);
//        template.setIsClearable(true);

        // 设置定时展示时间`
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        //离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
//        message.setData(notificationTemplateDemo(appId, appkey));
        message.setData(transmissionTemplateDemo());
        message.setPushNetWorkType(0); //可选。判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
        Target target = new Target();

        target.setAppId(appId);
        target.setClientId(CID);
        //用户别名推送，cid和用户别名只能2者选其一
        //String alias = "个";
        //target.setAlias(alias);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
        } else {
            System.out.println("服务器响应异常");
        }
    }


    public static TransmissionTemplate transmissionTemplateDemo() {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appkey);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        Map<String, String> map = new HashMap<String, String>();
        map.put("from", "1");
        map.put("sound_name", "esp_sustom_order_come");
        map.put("logo_url", "http://file.hotol.cn/app/data/config/agent_logo.png");
//        map.put("logo_url", "http://file.hotol.cn/app/data/config/logo.png");
        map.put("title", "速邮汇-代理人");
        map.put("body", "您有新的快件可以受理");
        String str = JSONObject.fromObject(map).toString();
        template.setTransmissionContent(str);
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");

//        template.setAPNInfo(getAPNPayload());

        return template;
    }

    public static NotificationTemplate notificationTemplateDemo(String appId, String appkey) {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appkey);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(1);
        Map<String, String> map = new HashMap<String, String>();
        map.put("from", "4");
        String str = JSONObject.fromObject(map).toString();
        template.setTransmissionContent(str);
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");

        /*Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle("请输入通知栏标题");
        style.setText("请输入通知栏内容");
        // 配置通知栏图标
        style.setLogo("ic_logon.png");
        // 配置通知栏网络图标
        style.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);*/

        template.setAPNInfo(getAPNPayload());

        return template;
    }

    public static APNPayload getAPNPayload(){
        APNPayload apnPayload = new APNPayload();
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setBody("内容");
        alertMsg.setActionLocKey("english");
        alertMsg.setLocKey("");
        alertMsg.addLocArg("LocArg");
        alertMsg.setLaunchImage("LaunchImage");
        //IOS8.2支持字段
        alertMsg.setTitle("标题");
        alertMsg.setTitleLocKey("");
        alertMsg.addTitleLocArg("TitleLocArg");
        apnPayload.setBadge(0);
        apnPayload.setAlertMsg(alertMsg);
        apnPayload.setContentAvailable(1);
        //apnpayload.Category = "";
        apnPayload.setSound("default");
        apnPayload.addCustomMsg("payload", "payload");
        return apnPayload;
    }



}
