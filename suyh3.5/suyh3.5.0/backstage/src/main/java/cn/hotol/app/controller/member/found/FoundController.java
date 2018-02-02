package cn.hotol.app.controller.member.found;

import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.funcion.FuncionService;
import cn.hotol.app.service.member.found.FoundService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class FoundController {

    @Resource
    private FoundService foundService;
    @Resource
    private FuncionService funcionService;

    /**
     * @Purpose  分页查找用户资金变化记录
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/found/list")
    public ModelAndView list(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                             @RequestParam(value = "memb_id", required = true) Integer memb_id,
                             HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/found/index");
        mv.addObject("retInfo", foundService.memberFoundPage(memb_id,currentPage, 10));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

}
