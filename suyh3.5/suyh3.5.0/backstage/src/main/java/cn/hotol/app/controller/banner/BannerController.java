package cn.hotol.app.controller.banner;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.banner.TsHtBannerDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.banner.BannerService;
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
public class BannerController {
    @Resource
    private FuncionService funcionService;

    @Resource
    private BannerService bannerService;

    /**
     * @Purpose  banner列表
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/banner/list")
    public ModelAndView list(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                             @RequestParam(value = "is_valid", required = true) Integer is_valid,
                             HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/banner/index");
        mv.addObject("retInfo", bannerService.bannerPage(is_valid ,currentPage ,10));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }
    /**
     * @Purpose  修改banner状态
     * @version  3.0
     * @author   lizhun
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/banner/updateState")
    @ResponseBody
    public RetInfo updateState(@ModelAttribute TsHtBannerDto tsHtBannerDto, HttpServletRequest request){
        return bannerService.updateBannerState(tsHtBannerDto);

    }
    /**
     * @Purpose  修改banner
     * @version  3.0
     * @author   lizhun
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/banner/update")
    @ResponseBody
    public RetInfo update(@Valid @ModelAttribute TsHtBannerDto tsHtBannerDto, BindingResult result){

        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            retInfo =  bannerService.updateBanner(tsHtBannerDto);
        }
        return retInfo;
    }
    /**
     * @Purpose  添加banner
     * @version  3.0
     * @author   lizhun
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/banner/insert")
    @ResponseBody
    public RetInfo insert(@Valid @ModelAttribute TsHtBannerDto tsHtBannerDto, BindingResult result){

        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            retInfo =  bannerService.insertBanner(tsHtBannerDto);
        }
        return retInfo;

    }
    /**
     * @Purpose  跳转到添加banner页面
     * @version  3.0
     * @author   lizhun
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/banner/jump/insert")
    public ModelAndView jumpInsert(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/banner/insert");
        return mv;
    }
    /**
     * @Purpose  跳转到更新banner页面
     * @version  3.0
     * @author   lizhun
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/banner/jump/update")
    public ModelAndView jumpUpdate(@RequestParam(value = "banner_id", required = true) Integer banner_id,
                                   HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/banner/update");
        mv.addObject("banner", bannerService.findBannerById(banner_id));
        return mv;
    }
}
