package cn.hotol.app.service.three.timer;

import cn.hotol.app.bean.dto.thirdparty.TsHtThirdDto;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.common.util.WeChatHttpsUtil;
import cn.hotol.app.repository.TsHtThirdRepository;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by LuBin
 * Date 2017-02-16.
 */
public class WeChatTokenTimer {

    private static Logger logger = Logger.getLogger(WeChatTokenTimer.class);

    @Resource
    private TsHtThirdRepository tsHtThirdRepository;

    /**
     * @return RetInfo
     * @Purpose 生成微信配置
     * @version 3.0
     * @author lubin
     */
    public void generateWeChatToken() {
        String logInfo = this.getClass().getName() + ":generateWeChatToken:";
        logger.info("======" + logInfo + "begin======");
        try {
            List<TsHtThirdDto> tsHtThirdDtoList = tsHtThirdRepository.findAllThird();
            for (int i = 0; i < tsHtThirdDtoList.size(); i++) {
                TsHtThirdDto tsHtThirdDto = tsHtThirdDtoList.get(i);
                String access_token = WeChatHttpsUtil.obtainAccessToken(tsHtThirdDto.getAppid(), tsHtThirdDto.getAppsecret());
                if (access_token != null) {
                    String jssdk_ticket = WeChatHttpsUtil.obtainJssdkTicket(access_token);
                    tsHtThirdDto.setAccesstokentime(new Date());
                    tsHtThirdDto.setAccesstoken(access_token);
                    tsHtThirdDto.setJstickettime(new Date());
                    tsHtThirdDto.setJsticket(jssdk_ticket);
                    tsHtThirdRepository.updateThirdById(tsHtThirdDto);

                    MemcachedUtils.delete(MemcachedKey.WE_CHAT_ACCESS_TOKEN + tsHtThirdDto.getAppid());
                    MemcachedUtils.delete(MemcachedKey.WE_CHAT_JSSDK_TOKEN + tsHtThirdDto.getAppid());

                    MemcachedUtils.add(MemcachedKey.WE_CHAT_ACCESS_TOKEN + tsHtThirdDto.getAppid(), access_token, new Date(0));
                    MemcachedUtils.add(MemcachedKey.WE_CHAT_JSSDK_TOKEN + tsHtThirdDto.getAppid(), jssdk_ticket, new Date(0));
                }
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
    }

}
