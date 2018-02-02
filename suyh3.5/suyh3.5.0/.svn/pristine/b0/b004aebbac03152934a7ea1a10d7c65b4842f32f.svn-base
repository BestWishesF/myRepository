package cn.hotol.app.controller.member.score;

import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.funcion.FuncionService;
import cn.hotol.app.service.member.score.ScoreService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class ScoreController {

    @Resource
    private ScoreService scoreService;
    @Resource
    private FuncionService funcionService;

    /**
     * @Purpose  分页查找用户积分变化记录
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/score/list")
    public ModelAndView list(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                             @RequestParam(value = "memb_id", required = true) Integer memb_id,
                             HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/score/index");
        mv.addObject("retInfo", scoreService.memberScorePage(memb_id,currentPage, 10));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

}
