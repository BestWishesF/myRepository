package cn.hotol.app.service.three.green;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.green.TdHtGreenMailDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.repository.TdHtGreenMailRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017-03-07.
 */

@Service
public class GreenServiceImpl implements GreenService {

    private static Logger logger = Logger.getLogger(GreenServiceImpl.class);

    @Resource
    private TdHtGreenMailRepository tdHtGreenMailRepository;

    /**
     * @param token
     * @return RetInfo
     * @Purpose 查询用户的参与活动记录
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findGreenMailByMemb(String token) {
        String logInfo = this.getClass().getName() + ":findGreenMainByMemb:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtGreenMailDto tdHtGreenMailDto = tdHtGreenMailRepository.findGreenMailByMemb(member.getMemb_id());
            if (tdHtGreenMailDto != null) {
                if(tdHtGreenMailDto.getState()==0){
                    retInfo.setMark("0");
                    retInfo.setObj(tdHtGreenMailDto);
                }else{
                    retInfo.setMark("2");
                    retInfo.setTip("您已经参与过该活动.");
                    retInfo.setObj(tdHtGreenMailDto);
                }
            } else {
                retInfo.setMark("1");
                retInfo.setTip("您还没有通过app下单.");
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param tdHtGreenMailDto
     * @return RetInfo
     * @Purpose 保存用户接收礼品信息
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo saveGreenAddress(TdHtGreenMailDto tdHtGreenMailDto) {
        String logInfo = this.getClass().getName() + ":saveMembAddres:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            tdHtGreenMailDto.setState(1);
            tdHtGreenMailRepository.updateGreenMail(tdHtGreenMailDto);
            retInfo.setMark("0");
            retInfo.setTip("保存成功.");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }
}
