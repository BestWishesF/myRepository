package cn.hotol.app.controller.agent.member;

import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.agent.member.AgentMemberService;
import cn.hotol.app.service.funcion.FuncionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017-03-11.
 */

@Controller
public class AgentMemberController {

    @Resource
    private AgentMemberService agentMemberService;
    @Resource
    private FuncionService funcionService;

    /**
     * @Purpose  代理人列表
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/agent/member/list")
    public ModelAndView list(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                             @RequestParam(value = "agent_id", required = true) Integer agent_id,
                             HttpServletRequest request){
        ModelAndView mv ;
        mv = new ModelAndView("/agent/member/index");
        mv.addObject("retInfo", agentMemberService.findAgentMemberPage(currentPage,10, agent_id));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

}
