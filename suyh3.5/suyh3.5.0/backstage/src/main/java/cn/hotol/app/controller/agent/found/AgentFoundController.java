package cn.hotol.app.controller.agent.found;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.agent.found.TdHtAgentFoundChangeDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.agent.found.AgentFoundService;
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
public class AgentFoundController {

    @Resource
    private AgentFoundService agentFoundService;
    @Resource
    private FuncionService funcionService;

    /**
     * @Purpose  代理人资金变化列表
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/agent/found/list")
    public ModelAndView list(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                             @RequestParam(value = "agent_id", required = true) Integer agent_id,
                             HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/agent/found/index");
        mv.addObject("retInfo", agentFoundService.agentFoundPage(agent_id,currentPage ,10));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

    /**
     * @Purpose  修改代理人资金变化记录状态
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/agent/found/update")
    @ResponseBody
    public RetInfo updateAgentFound(@ModelAttribute TdHtAgentFoundChangeDto tdHtAgentFoundChangeDto, HttpServletRequest request){
        return agentFoundService.updateAgentFound(tdHtAgentFoundChangeDto);
    }

    /**
     * @Purpose  分页查询代理人提现待审核列表
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/agent/found/applyList")
    public ModelAndView findWithdrawalsPage(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                             @RequestParam(value = "afchg_state", required = true) Integer afchg_state,
                             HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/agent/found/apply");
        mv.addObject("retInfo", agentFoundService.findWithdrawalsPage(currentPage ,10, afchg_state));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }
}
