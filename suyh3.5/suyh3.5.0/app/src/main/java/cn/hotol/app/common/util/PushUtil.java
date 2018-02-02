package cn.hotol.app.common.util;

import cn.hotol.app.bean.dto.push.PushParamsDto;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-04-18.
 */
public class PushUtil {

    private static Logger logger = Logger.getLogger(PushUtil.class);

    /**
     * @Purpose  Android端代理人推送
     * @version  1.0
     * @author   lubin
     * @param    push_token 推送参数
     * @return
     */
    public static void pushAndroidAgent(String push_token, String alert, String extra, String sound, int version){
        String logInfo =  ":pushAndroidAgent:";
        logger.info("======" + logInfo + "begin======");
        try {
            if(version > 6){
                PushParamsDto pushParamsDto = new PushParamsDto();
                pushParamsDto.setCid(push_token);
                pushParamsDto.setTitle("速邮汇-代理人");
                pushParamsDto.setText(alert);
                pushParamsDto.setOpen_id(extra);
                pushParamsDto.setSound(sound);
                GetuiAndroidAgentUtil.pushAgent(pushParamsDto);
            }else {
                JPushAndroidUtil jPushAndroidUtil = new JPushAndroidUtil();
                List<String> registrationIds = new ArrayList<String>();
                registrationIds.add(push_token);
                jPushAndroidUtil.agencyPush(jPushAndroidUtil.pushNotice(registrationIds, alert, extra));
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
    }

    /**
     * @Purpose  Android端用户推送
     * @version  1.0
     * @author   lubin
     * @param    push_token 推送参数
     * @return
     */
    public static void pushAndroidMember(String push_token, String alert, String extra, String sound, int version){
        String logInfo =  ":pushAndroidMember:";
        logger.info("======" + logInfo + "begin======");
        try {
            if(version > 6){
                PushParamsDto pushParamsDto = new PushParamsDto();
                pushParamsDto.setCid(push_token);
                pushParamsDto.setTitle("速邮汇");
                pushParamsDto.setText(alert);
                pushParamsDto.setOpen_id(extra);
                pushParamsDto.setSound(sound);
                GetuiAndroidMemberUtil.pushMember(pushParamsDto);
            }else {
                JPushAndroidUtil jPushAndroidUtil = new JPushAndroidUtil();
                List<String> registrationIds = new ArrayList<String>();
                registrationIds.add(push_token);
                jPushAndroidUtil.memberPush(jPushAndroidUtil.pushNotice(registrationIds, alert, extra));
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
    }

    /**
     * @Purpose  IOS端代理人推送
     * @version  1.0
     * @author   lubin
     * @param    push_token 推送参数
     * @return
     */
    public static void pushIosAgent(String push_token, String alert, String extra, String sound, int version){
        String logInfo =  ":pushIosAgent:";
        logger.info("======" + logInfo + "begin======");
        try {
            if(version > 6){
                PushParamsDto pushParamsDto = new PushParamsDto();
                pushParamsDto.setCid(push_token);
                pushParamsDto.setTitle("速邮汇-代理人");
                pushParamsDto.setText(alert);
                pushParamsDto.setOpen_id(extra);
                pushParamsDto.setSound(sound);
                GetuiIosAgentUtil.pushAgent(pushParamsDto);
            }else {
                JPushIOSUtil jPushIOSUtil = new JPushIOSUtil();
                List<String> registrationIds = new ArrayList<String>();
                registrationIds.add(push_token);
                jPushIOSUtil.agencyPush(jPushIOSUtil.pushNotice(registrationIds, alert, sound, extra));
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
    }

    /**
     * @Purpose  IOS端用户推送
     * @version  1.0
     * @author   lubin
     * @param    push_token 推送参数
     * @return
     */
    public static void pushIosMember(String push_token, String alert, String extra, String sound, int version){
        String logInfo =  ":pushIosMember:";
        logger.info("======" + logInfo + "begin======");
        try {
            if(version > 6){
                PushParamsDto pushParamsDto = new PushParamsDto();
                pushParamsDto.setCid(push_token);
                pushParamsDto.setTitle("速邮汇");
                pushParamsDto.setText(alert);
                pushParamsDto.setOpen_id(extra);
                pushParamsDto.setSound(sound);
                GetuiIosMemberUtil.pushMember(pushParamsDto);
            }else {
                JPushIOSUtil jPushIOSUtil = new JPushIOSUtil();
                List<String> registrationIds = new ArrayList<String>();
                registrationIds.add(push_token);
                jPushIOSUtil.memberPush(jPushIOSUtil.pushNotice(registrationIds, alert, sound, extra));
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
    }

}
