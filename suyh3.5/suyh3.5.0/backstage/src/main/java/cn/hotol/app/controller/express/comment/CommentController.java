package cn.hotol.app.controller.express.comment;

import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.comment.CommentService;
import cn.hotol.app.service.funcion.FuncionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class CommentController {

    @Resource
    private FuncionService funcionService;
    @Resource
    private CommentService commentService;

    /**
     * @Purpose  评论列表
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/express/comment/list")
    public ModelAndView list(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                             HttpServletRequest request){
        ModelAndView  mv = new ModelAndView("/express/comment/index");
        mv.addObject("retInfo", commentService.commentPage(currentPage ,10, request));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }


}
