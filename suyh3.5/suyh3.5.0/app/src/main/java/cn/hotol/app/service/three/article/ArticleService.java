package cn.hotol.app.service.three.article;

import cn.hotol.app.base.RetInfo;

/**
 * Created by LuBin
 * Date 2016-12-03.
 */
public interface ArticleService {

    /**
     * @return RetInfo
     * @Param arti_id 文章id
     * @Purpose 查询显示的单篇文章
     * @version 3.0
     * @author lubin
     */
    public RetInfo findIndividualArticle(Integer arti_id);

}
