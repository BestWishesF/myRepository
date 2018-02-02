package cn.hotol.app.controller.loginlog;

import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.funcion.FuncionService;
import cn.hotol.app.service.loginlog.LoginLogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class LoginlogController {


    @Resource
    private LoginLogService loginLogService;
    @Resource
    private FuncionService funcionService;

    /**
     * @Purpose  用户登录记录列表
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/loginlog/list")
    public ModelAndView list(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                             @RequestParam(value = "user_type", required = true) Integer user_type,
                             HttpServletRequest request){
        ModelAndView mv ;
        if (user_type == 1){
            mv = new ModelAndView("/member/loginlog");
        } else  if (user_type == 2) {
            mv = new ModelAndView("/agent/loginlog");
        } else {
            mv = new ModelAndView("/admin/loginlog");
        }
        mv.addObject("retInfo", loginLogService.loginLogPage(user_type,currentPage ,10, request));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

}
