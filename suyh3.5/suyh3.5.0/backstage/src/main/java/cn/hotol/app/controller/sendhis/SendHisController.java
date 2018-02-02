package cn.hotol.app.controller.sendhis;

import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.funcion.FuncionService;
import cn.hotol.app.service.sendhis.SendHisService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class SendHisController {


    @Resource
    private SendHisService sendHisService;
    @Resource
    private FuncionService funcionService;

    /**
     * @Purpose  短信发送记录列表
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/sendhis/list")
    public ModelAndView list(@RequestParam(value = "currentPage", required = true) Integer currentPage, HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/sendhis/index");
        mv.addObject("retInfo", sendHisService.sendHisPage(currentPage ,10));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

}
