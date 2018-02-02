package cn.hotol.app.service.price;

import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.bean.dto.price.TsHtRegionPriceDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.repository.TsHtDictRepository;
import cn.hotol.app.repository.TsHtRegionPriceRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-08.
 */

@Service
public class ExpressPriceServiceImpl implements ExpressPriceService {

    private static Logger logger = Logger.getLogger(ExpressPriceServiceImpl.class);

    @Resource
    private TsHtRegionPriceRepository tsHtRegionPriceRepository;
    @Resource
    private TsHtDictRepository tsHtDictRepository;

    /**
     * @return Map<Integer, List<TsHtRegionPriceDto>>
     * @Purpose 查找快递定价信息
     * @version 3.0
     * @author lubin
     */
    @Override
    public Map<String, List<TsHtRegionPriceDto>> findExpRegionPrice() {
        String logInfo = this.getClass().getName() + ":findExpRegionPrice:";
        logger.info("======" + logInfo + "begin======");
        Map<String, List<TsHtRegionPriceDto>> expRegionPrice = new HashMap<String, List<TsHtRegionPriceDto>>();
        try {
            List<TsHtDictDto> expressCompany = tsHtDictRepository.findDictByType(Constant.EXPRESS_COMPANY);

            for (int i = 0; i < expressCompany.size(); i++) {
                TsHtDictDto tsHtDictDto = expressCompany.get(i);
                List<TsHtRegionPriceDto> ExpRegionPriceList = tsHtRegionPriceRepository.findExpRegionPrice(tsHtDictDto.getDict_id());
                expRegionPrice.put(String.valueOf(tsHtDictDto.getDict_id()), ExpRegionPriceList);
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return expRegionPrice;
    }

    public void setTsHtRegionPriceRepository(TsHtRegionPriceRepository tsHtRegionPriceRepository) {
        this.tsHtRegionPriceRepository = tsHtRegionPriceRepository;
    }

    public void setTsHtDictRepository(TsHtDictRepository tsHtDictRepository) {
        this.tsHtDictRepository = tsHtDictRepository;
    }
}
