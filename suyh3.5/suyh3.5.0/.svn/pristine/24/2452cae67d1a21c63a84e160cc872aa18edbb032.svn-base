package cn.hotol.app.service.config;

import cn.hotol.app.bean.dto.config.TsHtDataConfigDto;
import cn.hotol.app.repository.TsHtDataConfigRepository;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-03-16.
 */
public class ConfigServiceImpl implements ConfigService {

    private static Logger logger = Logger.getLogger(ConfigServiceImpl.class);

    private TsHtDataConfigRepository tsHtDataConfigRepository;

    /**
     * @param
     * @return Map<String, Object>
     * @Purpose 查询所有的数据配置
     * @version 3.0
     * @author lubin
     */
    @Override
    public Map<String, Object> findAllDataConfig() {
        String logInfo = this.getClass().getName() + ":saveMembInvoice:";
        logger.info("======" + logInfo + "begin======");
        Map<String, Object> dataConfigMap = new HashMap<String, Object>();
        try {
            List<TsHtDataConfigDto> tsHtDataConfigDtoList = tsHtDataConfigRepository.findAllDataConfig();
            for (int i = 0; i < tsHtDataConfigDtoList.size(); i++) {
                TsHtDataConfigDto tsHtDataConfigDto = tsHtDataConfigDtoList.get(i);
                dataConfigMap.put(tsHtDataConfigDto.getData_id(), tsHtDataConfigDto.getData_value());
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return dataConfigMap;
    }

    public void setTsHtDataConfigRepository(TsHtDataConfigRepository tsHtDataConfigRepository) {
        this.tsHtDataConfigRepository = tsHtDataConfigRepository;
    }
}
