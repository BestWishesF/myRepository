package cn.hotol.app.common.util;

import com.jianzhou.sdk.BusinessService;
import org.apache.log4j.Logger;

/**
 * Created by lizhun on 2016/11/30.
 */
public class SendMessage {
    private static Logger logger = Logger.getLogger(SendMessage.class);
    /**
     * @Purpose  建州短信发送
     * @version  1.0
     * @author   lizhun
     * @param    phone(手机号码)
     * @param    content(发送内容)
     * @return   boolean
     */
    public static boolean sendMessage(String phone, String content) {
        String logInfo =  ":sendMessage:";
        logger.info("======" + logInfo + "begin======");
        boolean flag = false;
        try {
            BusinessService bs = new BusinessService();
            bs.setWebService("http://www.jianzhou.sh.cn/JianzhouSMSWSServer/services/BusinessService");
            int result = bs.sendBatchMessage("sdk_hzhtwl", "100emall.com" , phone, content +  "【速邮汇】");
            //返回结果大于0发送成功 反之则打印返回值
            if (result > 0) {
                flag = true;
            } else {
                logger.error("======result=:" + result + "======");
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return flag;


    }
}
