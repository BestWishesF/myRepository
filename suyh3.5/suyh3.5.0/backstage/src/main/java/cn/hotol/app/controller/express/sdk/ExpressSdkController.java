package cn.hotol.app.controller.express.sdk;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.expresssdk.TsHtExpressSdkDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.express.sdk.ExpressSdkService;
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

/**
 * Created by Administrator on 2017-01-31.
 */

@Controller
public class ExpressSdkController {

    @Resource
    private ExpressSdkService expressSdkService;
    @Resource
    private FuncionService funcionService;

    /**
     * @return ModelAndView
     * @Purpose 快递sdk列表
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/admin/express/sdk/list")
    public ModelAndView list(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                             @RequestParam(value = "eoa_id", required = true) Integer eoa_id,
                             HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/express/sdk/index");
        mv.addObject("retInfo", expressSdkService.findExpressSdkPage(eoa_id, currentPage, 10));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

    /**
     * @return ModelAndView
     * @Purpose 添加sdk页面跳转
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/admin/express/sdk/jump/insert")
    public ModelAndView jumpInsert(@RequestParam(value = "eoa_id", required = true) Integer eoa_id,
                                   HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/express/sdk/insert");
        mv.addObject("eoa_id", eoa_id);
        return mv;
    }

    /**
     * @return ModelAndView
     * @Purpose 修改sdk页面跳转
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/admin/express/sdk/jump/update")
    public ModelAndView jumpUpdate(@RequestParam(value = "exp_sdk_id", required = true) Integer exp_sdk_id,
                                   HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/express/sdk/update");
        mv.addObject("retInfo", expressSdkService.findExpressSdkById(exp_sdk_id));
        return mv;
    }

    /**
     * @Purpose  添加sdk
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     */
    @RequestMapping(value = "/admin/express/sdk/insert")
    @ResponseBody
    public RetInfo insertExpressSdk(@ModelAttribute TsHtExpressSdkDto tsHtExpressSdkDto) {
        return expressSdkService.insertExpressSdk(tsHtExpressSdkDto);
    }

    /**
     * @Purpose  修改sdk
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     */
    @RequestMapping(value = "/admin/express/sdk/update")
    @ResponseBody
    public RetInfo updateExpressSdk(@ModelAttribute TsHtExpressSdkDto tsHtExpressSdkDto) {
        return expressSdkService.updateExpressSdk(tsHtExpressSdkDto);
    }

}
