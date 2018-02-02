package cn.hotol.app.common.init;

import cn.hotol.app.common.SpringInfo;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.service.article.ArticleService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-03.
 */
public class ArticleInit {

    private static ArticleService articleService = (ArticleService) SpringInfo.getBean("articleService");
    private static Log logger = LogFactory.getLog(ArticleInit.class);

    public static void getArticle() {
        Map<Integer, Object> articleMap = articleService.findIndividualArticle();
        MemcachedUtils.add(MemcachedKey.INDIVIDUAL_ARTICLE, articleMap, new Date(0));
    }

    /**
     * 清缓存，重新加载缓存
     */
    public synchronized static void flush() {
        logger.info("********************start flush**************");
        MemcachedUtils.delete(MemcachedKey.INDIVIDUAL_ARTICLE);
        getArticle();
        logger.info("********************end flush**************");
    }

}
