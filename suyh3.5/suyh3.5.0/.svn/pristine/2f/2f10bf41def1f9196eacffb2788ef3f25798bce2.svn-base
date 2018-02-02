package cn.hotol.app.service.banner;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.banner.TsHtBannerDto;
import cn.hotol.app.bean.dto.banner.TsHtBannerIndex;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.repository.TsHtBannerRepository;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhun on 2016/12/1.
 */
public class BannerServiceImpl implements BannerService {
    private static Logger logger = Logger.getLogger(BannerServiceImpl.class);
    private TsHtBannerRepository tsHtBannerRepository;

    @Override
    public List<TsHtBannerIndex> findTsHtBannerIndex() {
        String logInfo = this.getClass().getName() + ":findTsHtBannerIndex:";
        logger.info("======" + logInfo + "begin======");
        List<TsHtBannerIndex> tsHtBannerIndices = null;
        try {
            tsHtBannerIndices = tsHtBannerRepository.findTsHtBannerIndex();

        } catch (Exception e) {

            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return tsHtBannerIndices;
    }

    @Override
    public RetInfo bannerPage(int is_valid, int currentPage, int pageSize) {
        String logInfo = this.getClass().getName() + ":memberAddressPage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {

            int totalRecord = tsHtBannerRepository.findBannerSize(is_valid);//总条数
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord, currentPage, pageSize);
                map.put("is_valid", is_valid);
                List<TsHtBannerDto> tsHtBannerDtos = tsHtBannerRepository.findBannerPage(map);
                map.put("currentPage", currentPage);
                map.put("banners", tsHtBannerDtos);

                retInfo.setMark("0");
                retInfo.setTip("成功");
                retInfo.setObj(map);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("暂无您要查找的结果");
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("totalPage", 0);
                params.put("totalRecord", 0);
                params.put("currentPage", 1);
                retInfo.setObj(params);
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    @Override
    public RetInfo updateBannerState(TsHtBannerDto tsHtBannerDto) {
        String logInfo = this.getClass().getName() + ":memberAddressPage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            tsHtBannerRepository.updateBannerState(tsHtBannerDto);
            retInfo.setMark("0");
            retInfo.setTip("成功");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    @Override
    public RetInfo updateBanner(TsHtBannerDto tsHtBannerDto) {
        String logInfo = this.getClass().getName() + ":updateBanner:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            tsHtBannerRepository.updateBanner(tsHtBannerDto);
            retInfo.setMark("0");
            retInfo.setTip("成功");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    @Override
    public RetInfo insertBanner(TsHtBannerDto tsHtBannerDto) {
        String logInfo = this.getClass().getName() + ":insertBanner:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            tsHtBannerRepository.insertBanner(tsHtBannerDto);
            retInfo.setMark("0");
            retInfo.setTip("成功");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    @Override
    public TsHtBannerDto findBannerById(int banner_id) {
        String logInfo = this.getClass().getName() + ":findBannerById:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        TsHtBannerDto tsHtBannerDto = new TsHtBannerDto();
        try {
            tsHtBannerDto = tsHtBannerRepository.findBannerById(banner_id);

        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return tsHtBannerDto;
    }

    public void setTsHtBannerRepository(TsHtBannerRepository tsHtBannerRepository) {
        this.tsHtBannerRepository = tsHtBannerRepository;
    }
}
