package cn.hotol.app.service.three.homepage;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.expressorder.SendRecordDto;
import cn.hotol.app.bean.TdHtExpressSearch;
import cn.hotol.app.bean.dto.banner.TsHtBannerIndex;
import cn.hotol.app.bean.dto.expressorder.ExpressOrderDto;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.bean.dto.logistics.LogisticsInfoDto;
import cn.hotol.app.bean.dto.logistics.TdHtExpressSearchDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.common.util.KdniaoTrackQueryAPI;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.repository.TdHtExpressCollectRepository;
import cn.hotol.app.repository.TdHtExpressSearchRepository;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 首页
 * Created by liyafei
 * Date 2016-12-1.
 */

@Service
public class HomePageServiceImpl implements HomePageService {

    @Resource
    private TdHtExpressSearchRepository tdHtExpressSearchRepository;

    @Resource
    private TdHtExpressCollectRepository tdHtExpressCollectRepository;

    private static Logger logger = Logger.getLogger(HomePageServiceImpl.class);

    /**
     * @return TsHtBanner
     * @Purpose 首页
     * @version 3.0
     * @author liyafei
     */
    @Transactional
    @Override
    public RetInfo homePage(TdHtMemberDto tdHtMemberDto) {
        String logInfo = this.getClass().getName() + ":homePage:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Map<Integer, Object> expressCompany = (Map<Integer, Object>) MemcachedUtils.get(MemcachedKey.EXPRESS_COMPANY);
            List<TsHtDictDto> expressCompanyList = (List<TsHtDictDto>) expressCompany.get(0);//获取所有快递公司
            //获取banner
            List<TsHtBannerIndex> tsHtBannerIndexe = (List<TsHtBannerIndex>) MemcachedUtils.get(MemcachedKey.BANNER_INDEX);
            //查询寄件记录
            List<SendRecordDto> sendRecordDtoList = tdHtExpressCollectRepository.findNewTenSendRecord(tdHtMemberDto.getMemb_id());
            List<ExpressOrderDto> sendExpRecordList = new ArrayList<>();

            for (int s = 0; s < sendRecordDtoList.size(); s++) {
                SendRecordDto sendRecordDto = sendRecordDtoList.get(s);
                ExpressOrderDto expressOrderDto = new ExpressOrderDto();
                String expressImg = "";
                String expDetailed = "没有物流信息";
                String shipperCode = "";
                for (int e = 0; e < expressCompanyList.size(); e++) {
                    TsHtDictDto tsHtDictDto = expressCompanyList.get(e);
                    if (tsHtDictDto.getDict_id() == sendRecordDto.getExpress_id()) {
                        expressImg = tsHtDictDto.getDict_img();
                        shipperCode = tsHtDictDto.getDict_code();
                    }
                }
                TdHtExpressSearch tdHtExpressSearch = new TdHtExpressSearch();
                tdHtExpressSearch.setExp_number(sendRecordDto.getExpress_number());
                tdHtExpressSearch.setExp_name(sendRecordDto.getExpress_name());
                tdHtExpressSearch.setMemb_id(tdHtMemberDto.getMemb_id());
                TdHtExpressSearch tdHtExpressSearchDB = tdHtExpressSearchRepository.findExpressSearchByWaybill(tdHtExpressSearch);
                if (tdHtExpressSearchDB != null) {
                    expDetailed = tdHtExpressSearchDB.getExp_detailed();
                }
                expressOrderDto.setExp_detailed(expDetailed);
                expressOrderDto.setShipper_code(shipperCode);
                expressOrderDto.setImg_src(expressImg);
                //获取更新时间
                switch (sendRecordDto.getExp_ord_clt_state()) {
                    case 0:
                        expressOrderDto.setDate(format.format(sendRecordDto.getCollect_time()));
                        break;
                    case 1:
                        expressOrderDto.setDate(format.format(sendRecordDto.getCollect_time()));
                        break;
                    case 2:
                        expressOrderDto.setDate(format.format(new Date()));
                        break;
                    case 3:
                        expressOrderDto.setDate(format.format(sendRecordDto.getDelivery_time()));
                        break;
                    case 4:
                        expressOrderDto.setDate(format.format(sendRecordDto.getSign_time()));
                        break;
                }
                expressOrderDto.setStatus(sendRecordDto.getExp_ord_clt_state());
                expressOrderDto.setExpress_number(sendRecordDto.getExpress_number());
                expressOrderDto.setExpress_name(sendRecordDto.getExpress_name());

                sendExpRecordList.add(expressOrderDto);
            }
            //查询查件记录
            List<TdHtExpressSearchDto> tdHtExpressSearchDtoList = tdHtExpressSearchRepository.findTdHtExpressSearch(tdHtMemberDto.getMemb_id());

            List<ExpressOrderDto> searchDto = new ArrayList<>();
            for (int i = 0; i < tdHtExpressSearchDtoList.size(); i++) {
                TdHtExpressSearchDto tdHtExpressSearchDto = tdHtExpressSearchDtoList.get(i);
                ExpressOrderDto expressOrderDto = new ExpressOrderDto();
                String expressImg = "";
                for (int j = 0; j < expressCompanyList.size(); j++) {
                    TsHtDictDto tsHtDictDto = expressCompanyList.get(j);
                    if (tsHtDictDto.getDict_code().equals(tdHtExpressSearchDto.getExp_code())) {
                        expressImg = tsHtDictDto.getDict_img();
                    }
                }
                expressOrderDto.setShipper_code(tdHtExpressSearchDto.getExp_code());
                expressOrderDto.setExp_detailed(tdHtExpressSearchDto.getExp_detailed());
                expressOrderDto.setImg_src(expressImg);
                expressOrderDto.setStatus(tdHtExpressSearchDto.getExp_state());
                expressOrderDto.setExpress_name(tdHtExpressSearchDto.getExp_name());
                expressOrderDto.setExpress_number(tdHtExpressSearchDto.getExp_number());
                expressOrderDto.setDate(format.format(tdHtExpressSearchDto.getExp_sea_time()));
                searchDto.add(expressOrderDto);
            }
            retInfo.setMark("0");
            retInfo.setTip("获取数据成功");

            Map<String, Object> map = new HashMap<>();
            map.put("banner", tsHtBannerIndexe);
            map.put("send", sendExpRecordList);
            map.put("search", searchDto);

            retInfo.setObj(map);

        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }
}
