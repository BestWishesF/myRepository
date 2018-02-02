package cn.hotol.app.controller.express.open.region;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.express.region.TsHtExpressOpenRegionDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.express.open.region.ExpressOpenRegionService;
import cn.hotol.app.service.funcion.FuncionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class ExpressOpenRegionController {

     @Resource
     private ExpressOpenRegionService expressOpenRegionService;
     @Resource
     private FuncionService funcionService;

    /**
     * @Purpose  快递开通区域列表
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/express/open/region/list")
    public ModelAndView list(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                             @RequestParam(value = "express_id", required = true) Integer express_id,
                             HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/express/region/index");
        mv.addObject("retInfo", expressOpenRegionService.expressOpenRegionPage(express_id,currentPage,10));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }
    /**
     * @Purpose  添加快递开通区域
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/express/open/region/insert")
    @ResponseBody
    public RetInfo insert(@ModelAttribute TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto, HttpServletRequest request){
        return expressOpenRegionService.insertExpressOpenRegion(tsHtExpressOpenRegionDto);

    }
    /**
     * @Purpose  更新快递开通区域
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/express/open/region/update")
    @ResponseBody
    public RetInfo update(@ModelAttribute TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto, HttpServletRequest request){
        return expressOpenRegionService.updateExpressOpenRegion(tsHtExpressOpenRegionDto);

    }

    /**
     * @Purpose  通过id查询开放区域信息
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/express/open/region/findRegionById")
    @ResponseBody
    public RetInfo findRegionById(@ModelAttribute TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto, HttpServletRequest request){
        return expressOpenRegionService.findRegionById(tsHtExpressOpenRegionDto);

    }
}
