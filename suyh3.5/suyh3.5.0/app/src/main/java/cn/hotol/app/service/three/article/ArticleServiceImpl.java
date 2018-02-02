package cn.hotol.app.service.three.article;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.article.TsHtArticleDto;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-03.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private static Logger logger = Logger.getLogger(ArticleServiceImpl.class);

    /**
     * @return RetInfo
     * @Param arti_id 文章id
     * @Purpose 查询显示的单篇文章
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findIndividualArticle(Integer arti_id) {
        String logInfo = this.getClass().getName() + ":findIndividualArticle:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<Integer, TsHtArticleDto> provinceDtoMap = (Map<Integer, TsHtArticleDto>) MemcachedUtils.get(MemcachedKey.INDIVIDUAL_ARTICLE);

            retInfo.setMark("0");
            retInfo.setTip("文章数据获取成功.");
            retInfo.setObj(provinceDtoMap.get(arti_id));

        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }
}
