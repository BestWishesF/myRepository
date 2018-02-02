package cn.hotol.app.common.util;

import cn.hotol.app.bean.dto.push.PushParamsDto;
import cn.hotol.app.common.Constant;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.payload.APNPayload;
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
public class GetuiIosAgentUtil {

    private static Logger logger = Logger.getLogger(GetuiIosAgentUtil.class);

    /**
     * @Purpose  IOS端代理人个推推送
     * @version  1.0
     * @author   lubin
     * @param    pushParamsDto  推送参数
     * @return
     */
    public static void pushAgent(PushParamsDto pushParamsDto){
        String logInfo =  ":pushAgent:";
        logger.info("======" + logInfo + "begin======");
        try {
            TransmissionTemplate template = new TransmissionTemplate();
            template.setAppId(Constant.GETUI_AGENT_APPID);
            template.setAppkey(Constant.GETUI_AGENT_APPKEY);
            template.setTransmissionType(1);        //应用启动类型，1：强制应用启动 2：等待应用启动

            Map<String, String> map = new HashMap<String, String>();
            map.put("from", pushParamsDto.getOpen_id());
            String str = JSONObject.fromObject(map).toString();
            template.setTransmissionContent(str);  //透传内容

            //APN高级推送
            APNPayload apnpayload = new APNPayload();
            APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
            alertMsg.setBody(pushParamsDto.getText());
            alertMsg.setActionLocKey("english");
            alertMsg.setLocKey("");
            alertMsg.addLocArg("LocArg");
            alertMsg.setLaunchImage("LaunchImage");

            //IOS8.2支持字段
            alertMsg.setTitle(pushParamsDto.getTitle());
            alertMsg.setTitleLocKey("");
            alertMsg.addTitleLocArg("TitleLocArg");
            apnpayload.setBadge(0);
            apnpayload.setAlertMsg(alertMsg);
            apnpayload.setContentAvailable(1);
            //apnpayload.Category = "";
            apnpayload.setSound("default");
            apnpayload.addCustomMsg("payload", "payload");
            template.setAPNInfo(apnpayload);

            IGtPush push = new IGtPush("", Constant.GETUI_AGENT_APPKEY, Constant.GETUI_AGENT_MASTERSECRET);


            SingleMessage message = new SingleMessage();
            message.setData(template);
            IPushResult pushResult = push.pushAPNMessageToSingle(Constant.GETUI_AGENT_APPID, pushParamsDto.getCid(), message);
            logger.error("======result:" + pushResult.getResponse() + "======");
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
    public static void pushAgentList(List<PushParamsDto> pushParamsDtoList){
        String logInfo =  ":pushAgentList:";
        logger.info("======" + logInfo + "begin======");
        try {
            for (int i = 0 ; i < pushParamsDtoList.size() ; i++){
                PushParamsDto pushParamsDto = pushParamsDtoList.get(i);
                pushAgent(pushParamsDto);
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
    }

}
