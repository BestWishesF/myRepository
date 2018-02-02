package cn.hotol.app.service.three.appupdate;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.appupdate.TdHtAppUpdateDto;
import cn.hotol.app.repository.TdHtAppUpdateRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by lizhun on 2017/3/9.
 */
@Service
public class AppUpdateServiceImpl implements AppUpdateService{

    private static Logger logger = Logger.getLogger(AppUpdateServiceImpl.class);
    @Resource
    private TdHtAppUpdateRepository tdHtAppUpdateRepository;
    @Override
    public RetInfo findAppUpdateByType(Map<String, Object> map) {
        String logInfo = this.getClass().getName() + ":findAppUpdateByType:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAppUpdateDto tdHtAppUpdateDto = tdHtAppUpdateRepository.findAppUpdateByType(map);

            retInfo.setMark("0");
            retInfo.setTip("获取最新版本信息成功.");
            retInfo.setObj(tdHtAppUpdateDto);

        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }
}
