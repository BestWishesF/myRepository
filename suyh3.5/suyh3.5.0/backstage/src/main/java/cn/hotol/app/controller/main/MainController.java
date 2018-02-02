package cn.hotol.app.controller.main;

import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.funcion.FuncionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller

public class MainController {

    @Resource
    private FuncionService funcionService;


    /**
     * @Purpose  left
     * @version  3.4.1
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/left")
    public ModelAndView left(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/main/left");
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("retInfo", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

    /**
     * @Purpose  main
     * @version  3.4.1
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/index")
    public ModelAndView index(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/main/index");
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("admin", tdHtAdmin);
        mv.addObject("retInfo", funcionService.findFatherFuncion(tdHtAdmin));
        return mv;
    }
}
