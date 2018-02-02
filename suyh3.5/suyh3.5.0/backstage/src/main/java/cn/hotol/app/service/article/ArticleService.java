package cn.hotol.app.service.article;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.article.TsHtArticleBeanDto;

import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-03.
 */
public interface ArticleService {

    /**
     * @Purpose  查找显示的单篇的文章
     * @version  3.0
     * @author   lubin
     * @return   Map<Integer,Object>
     */
    public Map<Integer,Object> findIndividualArticle();

    /**
     * @Purpose  根据id查找文章
     * @version  3.0
     * @author   lizhun
     * @return   TdNtBannerInfo
     */
    public TsHtArticleBeanDto findTsNtArticleInfoById(int arti_id);
    /**
     * @Purpose  分页查找文章
     * @version  3.0
     * @author   lizhun
     * @return   RetInfo
     */
    public RetInfo findTsNtArticleInfoByValid(int is_valid , int currentPage, int pageSize);
    /**
     * @Purpose  添加文章
     * @version  3.0
     * @author   lizhun
     * @param    tsHtArticleBeanDto
     * @return   RetInfo
     */
    public RetInfo insertArticle(TsHtArticleBeanDto tsHtArticleBeanDto);
    /**
     * @Purpose  更新文章
     * @version  3.0
     * @author   lizhun
     * @return   RetInfo
     */
    public RetInfo updateTsNtArticleInfo(TsHtArticleBeanDto tsHtArticleBeanDto);
    /**
     * @Purpose  更新文章是否可用
     * @version  3.0
     * @author   lizhun
     * @return   RetInfo
     */
    public RetInfo updateTsNtArticleInfoValid(TsHtArticleBeanDto tsHtArticleBeanDto);
}
