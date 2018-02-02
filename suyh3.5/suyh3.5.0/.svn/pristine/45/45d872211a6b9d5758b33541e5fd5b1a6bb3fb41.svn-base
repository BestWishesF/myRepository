package cn.hotol.app.service.thirdparty;

import cn.hotol.app.bean.dto.thirdparty.TsHtThirdDto;
import cn.hotol.app.repository.TsHtThirdRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuBin
 * Date 2017-01-02.
 */
@Service
public class ThirdPartyServiceImpl implements ThirdPartyService {

    private static Logger logger = Logger.getLogger(ThirdPartyServiceImpl.class);

    @Resource
    private TsHtThirdRepository tsHtThirdRepository;

    @Override
    public List<TsHtThirdDto> findTsHtThird() {
        String logInfo = this.getClass().getName() + ":findTsHtThird:";
        logger.info("======" + logInfo + "begin======");
        List<TsHtThirdDto> tsHtThirdDtoList = new ArrayList<TsHtThirdDto>();
        try {
            tsHtThirdDtoList=tsHtThirdRepository.findTsHtThird();
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return tsHtThirdDtoList;
    }

    public void setTsHtThirdRepository(TsHtThirdRepository tsHtThirdRepository) {
        this.tsHtThirdRepository = tsHtThirdRepository;
    }
}
