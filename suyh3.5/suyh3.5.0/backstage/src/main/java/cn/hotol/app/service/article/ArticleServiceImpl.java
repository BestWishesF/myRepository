package cn.hotol.app.service.article;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.article.TsHtArticleBeanDto;
import cn.hotol.app.bean.dto.article.TsHtArticleDto;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.repository.TsHtArticleRepository;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-03.
 */
public class ArticleServiceImpl implements ArticleService {

    private static Logger logger = Logger.getLogger(ArticleServiceImpl.class);

    @Resource
    private TsHtArticleRepository tsHtArticleRepository;

    /**
     * @return Map<Integer,Object>
     * @Purpose 查找显示的单篇的文章
     * @version 3.0
     * @author lubin
     */
    @Override
    public Map<Integer, Object> findIndividualArticle() {
        String logInfo = this.getClass().getName() + ":findIndividualArticle:";
        logger.info("======" + logInfo + "begin======");
        Map<Integer, Object> articleMap = new HashMap<Integer, Object>();
        try {
            List<TsHtArticleDto> tsHtArticleDtoList = tsHtArticleRepository.findIndividualArticle();

            for (int i = 0; i < tsHtArticleDtoList.size(); i++) {
                TsHtArticleDto tsHtArticleDto = tsHtArticleDtoList.get(i);
                articleMap.put(tsHtArticleDto.getArti_id(), tsHtArticleDto);
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return articleMap;
    }

    @Override
    public TsHtArticleBeanDto findTsNtArticleInfoById(int arti_id) {
        String logInfo = this.getClass().getName() + ":findTsNtArticleInfoById:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        TsHtArticleBeanDto tsHtArticleBeanDto = new TsHtArticleBeanDto();
        try {
            tsHtArticleBeanDto = tsHtArticleRepository.findArticleById(arti_id);

        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return tsHtArticleBeanDto;
    }

    @Override
    public RetInfo findTsNtArticleInfoByValid(int is_valid, int currentPage, int pageSize) {
        String logInfo = this.getClass().getName() + ":findTsNtArticleInfoByValid:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {

            int totalRecord = tsHtArticleRepository.findArticleSize(is_valid);//总条数
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord, currentPage, pageSize);
                map.put("is_valid", is_valid);
                List<TsHtArticleBeanDto> tsHtArticleBeanDtos = tsHtArticleRepository.findArticlePage(map);
                map.put("currentPage", currentPage);
                map.put("articles", tsHtArticleBeanDtos);

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
    public RetInfo insertArticle(TsHtArticleBeanDto tsHtArticleBeanDto) {
        String logInfo = this.getClass().getName() + ":insertArticle:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            tsHtArticleRepository.insertArticle(tsHtArticleBeanDto);
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
    public RetInfo updateTsNtArticleInfo(TsHtArticleBeanDto tsHtArticleBeanDto) {
        String logInfo = this.getClass().getName() + ":updateTsNtArticleInfo:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            tsHtArticleRepository.updateArticle(tsHtArticleBeanDto);
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
    public RetInfo updateTsNtArticleInfoValid(TsHtArticleBeanDto tsHtArticleBeanDto) {
        String logInfo = this.getClass().getName() + ":updateTsNtArticleInfoValid:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            tsHtArticleRepository.updateArticleValid(tsHtArticleBeanDto);
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

    public void setTsHtArticleRepository(TsHtArticleRepository tsHtArticleRepository) {
        this.tsHtArticleRepository = tsHtArticleRepository;
    }
}
