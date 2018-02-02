package cn.hotol.app.service.three.gridchange;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.gridchange.TdHtGridChangeDto;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.repository.TdHtAgentRepository;
import cn.hotol.app.repository.TdHtGridChangeRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by LuBin
 * Date 2017-01-05.
 */

@Service
public class TdHtGridChangeServiceImpl implements TdHtGridChangeService {

    private static Logger logger = Logger.getLogger(TdHtGridChangeServiceImpl.class);

    @Resource
    private TdHtGridChangeRepository tdHtGridChangeRepository;
    @Resource
    private TdHtAgentRepository tdHtAgentRepository;

    /**
     * @param tdHtGridChangeDto
     * @return RetInfo
     * @Purpose 代理人上传经纬度
     * @version 3.0
     * @author lubin
     */
    @Transactional
    @Override
    public RetInfo uploadCoordinates(TdHtGridChangeDto tdHtGridChangeDto, String token) {
        String logInfo = this.getClass().getName() + ":uploadCoordinates:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentDto agent = (TdHtAgentDto) MemcachedUtils.get(token);

            TdHtAgentDto tdHtAgentDto = tdHtAgentRepository.findAgentById(agent.getAgent_id());

            tdHtGridChangeDto.setAgent_id(tdHtAgentDto.getAgent_id());
            tdHtGridChangeDto.setAgent_month(CommonUtil.getMonth());
            tdHtGridChangeDto.setGrid_chg_time(new Timestamp(System.currentTimeMillis()));
            tdHtGridChangeRepository.insertAgentCoordinates(tdHtGridChangeDto);

            tdHtAgentDto.setAgent_longitude(tdHtGridChangeDto.getAgent_longitude());
            tdHtAgentDto.setAgent_latitude(tdHtGridChangeDto.getAgent_latitude());
            tdHtAgentRepository.updateAgentCoordinates(tdHtAgentDto);

            MemcachedUtils.replace(token, tdHtAgentDto, new Date(20 * 24 * 60 * 60 * 1000));
            retInfo.setMark("0");
            retInfo.setTip("坐标更新成功.");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }
}
