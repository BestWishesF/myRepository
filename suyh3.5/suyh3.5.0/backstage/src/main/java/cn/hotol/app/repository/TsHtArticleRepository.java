package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.article.TsHtArticleBeanDto;
import cn.hotol.app.bean.dto.article.TsHtArticleDto;

import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-03.
 */
public interface TsHtArticleRepository {

    /**
     * @Purpose  查找显示的单篇的文章
     * @version  3.0
     * @author   lubin
     * @return   List<TsHtArticleDto>
     */
    public List<TsHtArticleDto> findIndividualArticle();
    /**
     * @Purpose  查找所有文章 数量
     * @version  3.0
     * @author   lizhun
     * @return   List<TdNtBannerInfoIndex>
     */
    public Integer findArticleSize(int is_valid);
    /**
     * @Purpose  查找所有文章 分页
     * @version  3.0
     * @author   lizhun
     * @return   List<TdNtBannerInfo>
     */
    public List<TsHtArticleBeanDto> findArticlePage(Map<String, Object> map);
    /**
     * @Purpose  更新文章信息
     * @version  3.0
     * @author   lizhun
     * @return   void
     */
    public void updateArticle(TsHtArticleBeanDto tsNtArticleInfo);
    /**
     * @Purpose  添加文章
     * @version  1.0
     * @author   lizhun
     * @return   void
     */
    public void insertArticle(TsHtArticleBeanDto tsNtArticleInfo);
    /**
     * @Purpose  根据id查找文章
     * @version  3.0
     * @author   lizhun
     * @return   TdNtBannerInfo
     */
    public TsHtArticleBeanDto findArticleById(int arti_id);
    /**
     * @Purpose  更新文章信息
     * @version  3.0
     * @author   lizhun
     * @return   void
     */
    public void updateArticleValid(TsHtArticleBeanDto tsNtArticleInfo);
}
