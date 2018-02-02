package cn.hotol.app.controller.three.article;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.TsHtArticle;
import cn.hotol.app.service.three.article.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by LuBin
 * Date 2016-12-03.
 */

@Controller
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     * @return RetInfo
     * @Purpose 文章详情
     * @version 1.0
     * @time 2015-12-28
     * @author lizhun
     */
    @RequestMapping(value = "/article/detail", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView detail(HttpServletRequest request) {
        String arti_id = request.getParameter("arti_id");
        ModelAndView mv = new ModelAndView("/article/detail");
        mv.addObject("article", articleService.findIndividualArticle(Integer.valueOf(arti_id)));
        return mv;
    }
}
