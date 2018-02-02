package cn.hotol.app.controller.feedback;

import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.feedback.FeedbackService;
import cn.hotol.app.service.funcion.FuncionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class FeedbackController {

    @Resource
    private FeedbackService feedbackService;
    @Resource
    private FuncionService funcionService;

    /**
     * @Purpose  反馈意见列表
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/feedback/list")
    public ModelAndView list(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                             @RequestParam(value = "user_cate", required = true) Integer user_cate,
                             HttpServletRequest request){
        ModelAndView mv ;
        if (user_cate == 1){
            mv = new ModelAndView("/member/feedback/index");
        }  else {
            mv = new ModelAndView("/agent/feedback/index");
        }
        mv.addObject("retInfo", feedbackService.feedbackPage(user_cate,currentPage ,10, request));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }


}
