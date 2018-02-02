package cn.hotol.app.controller.article;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.article.TsHtArticleBeanDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.article.ArticleService;
import cn.hotol.app.service.funcion.FuncionService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


@Controller
public class ArticleController {

    @Resource
    private FuncionService funcionService;
    @Resource
    private ArticleService articleService;

    /**
     * @Purpose  文章列表
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/article/list")
    public ModelAndView list(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                             @RequestParam(value = "is_valid", required = true) Integer is_valid,
                             HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/article/index");
        mv.addObject("retInfo", articleService.findTsNtArticleInfoByValid(is_valid ,currentPage ,10));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }
    /**
     * @Purpose  修改文章状态
     * @version  3.0
     * @author   lizhun
     * @return   Ret Info
     */
    @RequestMapping(value="/admin/article/updateState")
    @ResponseBody
    public RetInfo updateState(@ModelAttribute TsHtArticleBeanDto tsHtArticleBeanDto, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        tsHtArticleBeanDto.setModifier(tdHtAdmin.getUser_name());
        return articleService.updateTsNtArticleInfoValid(tsHtArticleBeanDto);
    }
    /**
     * @Purpose  修改文章
     * @version  3.0
     * @author   lizhun
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/article/update")
    @ResponseBody
    public RetInfo update(@Valid @ModelAttribute TsHtArticleBeanDto tsHtArticleBeanDto, BindingResult result,HttpServletRequest request){

        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            HttpSession session = request.getSession(false);
            TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
            tsHtArticleBeanDto.setModifier(tdHtAdmin.getUser_name());
            retInfo =  articleService.updateTsNtArticleInfo(tsHtArticleBeanDto);
        }
        return retInfo;
    }
    /**
     * @Purpose  添加文章
     * @version  3.0
     * @author   lizhun
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/article/insert")
    @ResponseBody
    public RetInfo insert(@Valid @ModelAttribute TsHtArticleBeanDto tsHtArticleBeanDto, BindingResult result,HttpServletRequest request){

        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            HttpSession session = request.getSession(false);
            TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
            tsHtArticleBeanDto.setCreater(tdHtAdmin.getUser_name());
            tsHtArticleBeanDto.setModifier(tdHtAdmin.getUser_name());
            retInfo =  articleService.insertArticle(tsHtArticleBeanDto);
        }
        return retInfo;

    }
    /**
     * @Purpose  跳转到添加文章页面
     * @version  3.0
     * @author   lizhun
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/article/jump/insert")
    public ModelAndView jumpInsert(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/article/insert");
        return mv;
    }
    /**
     * @Purpose  跳转到更新文章页面
     * @version  3.0
     * @author   lizhun
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/article/jump/update")
    public ModelAndView jumpUpdate(@RequestParam(value = "arti_id", required = true) Integer arti_id,
                                   HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/article/update");
        mv.addObject("article", articleService.findTsNtArticleInfoById(arti_id));
        return mv;
    }
}
