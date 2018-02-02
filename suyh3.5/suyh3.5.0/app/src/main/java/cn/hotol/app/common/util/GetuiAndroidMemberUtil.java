package cn.hotol.app.common.util;

import cn.hotol.app.bean.dto.push.PushParamsDto;
import cn.hotol.app.common.Constant;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-04-14.
 */
public class GetuiAndroidMemberUtil {

    private static Logger logger = Logger.getLogger(GetuiAndroidMemberUtil.class);

    /**
     * @Purpose  安卓端用户个推推送
     * @version  1.0
     * @author   lubin
     * @param    pushParamsDto  推送参数
     * @return
     */
    public static void pushMember(PushParamsDto pushParamsDto){
        String logInfo =  ":pushMember:";
        logger.info("======" + logInfo + "begin======");
        IPushResult ret = null;
        try {
            IGtPush push = new IGtPush("http://sdk.open.api.igexin.com/apiex.htm", Constant.GETUI_MEMBER_APPKEY, Constant.GETUI_MEMBER_MASTERSECRET);
            TransmissionTemplate template = new TransmissionTemplate();

            // 设置APPID与APPKEY
            template.setAppId(Constant.GETUI_MEMBER_APPID);
            template.setAppkey(Constant.GETUI_MEMBER_APPKEY);
            Map<String, String> map = new HashMap<String, String>();

            // 设置通知栏标题与内容
            map.put("title", pushParamsDto.getTitle());
            map.put("body", pushParamsDto.getText());

            // 配置通知栏图标
            map.put("logo_url", "logo.png");

            // 设置通知是否响铃，震动，或者可清除
            map.put("from", pushParamsDto.getOpen_id());
            map.put("sound_name", pushParamsDto.getSound());

            // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
            template.setTransmissionType(2);
            String str = JSONObject.fromObject(map).toString();
            template.setTransmissionContent(str);
            SingleMessage message = new SingleMessage();
            message.setOffline(true);

            //离线有效时间，单位为毫秒，可选
            message.setOfflineExpireTime(24 * 3600 * 1000);
            message.setData(template);
            message.setPushNetWorkType(0); //可选。判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
            Target target = new Target();

            target.setAppId(Constant.GETUI_MEMBER_APPID);
            target.setClientId(pushParamsDto.getCid());

            try {
                ret = push.pushMessageToSingle(message, target);
            } catch (RequestException e) {
                e.printStackTrace();
                ret = push.pushMessageToSingle(message, target, e.getRequestId());
            }
            if (ret != null) {
                logger.error("======success:" + ret.getResponse().toString() + "======");
            } else {
                logger.info("======error:服务器响应异常======");
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
    }

    /**
     * @Purpose  安卓端代理人个推推送(多个)
     * @version  1.0
     * @author   lubin
     * @param    pushParamsDtoList  推送参数
     * @return
     */
    public static void pushMemberList(List<PushParamsDto> pushParamsDtoList){
        String logInfo =  ":pushMemberList:";
        logger.info("======" + logInfo + "begin======");
        try {
            for (int i = 0 ; i < pushParamsDtoList.size() ; i++){
                PushParamsDto pushParamsDto = pushParamsDtoList.get(i);
                pushMember(pushParamsDto);
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
    }

}
