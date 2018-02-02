package cn.hotol.app.controller.agent;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.agent.apply.TdHtAgentApplyDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.agent.AgentService;
import cn.hotol.app.service.funcion.FuncionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class AgentController {
    @Resource
    private FuncionService funcionService;

    @Resource
    private AgentService agentService;

    /**
     * @Purpose  代理人列表
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/agent/list")
    public ModelAndView list(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                             @RequestParam(value = "agent_state", required = true) Integer agent_state,
                             HttpServletRequest request){
        ModelAndView mv ;
        if (agent_state == 1){
            mv = new ModelAndView("/agent/apply");
        } else  if (agent_state == 2) {
            mv = new ModelAndView("/agent/index");
        } else {
            mv = new ModelAndView("/agent/lock");
        }
        mv.addObject("retInfo", agentService.agentPage(agent_state,currentPage ,10, request));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

    /**
     * @Purpose  锁定或解锁代理人
     * @version  3.0
     * @author   lizhun
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/agent/lock")
    @ResponseBody
    public RetInfo lock(@ModelAttribute TdHtAgentDto tdHtAgentDto, HttpServletRequest request){
        return agentService.lock(tdHtAgentDto);

    }

    /**
     * @Purpose 查询可指派的代理人列表
     * @version  3.0
     * @author   lubin
     * @param agent_phone
     * @return RetInfo
     */
    @RequestMapping(value = "/admin/agent/findAssignAgentList")
    @ResponseBody
    public RetInfo findAssignAgentList(@RequestParam(value = "agent_phone", required = true) String agent_phone,
                                       @RequestParam(value = "add_city", required = true) Integer add_city) {
        RetInfo retInfo = agentService.findAssignAgentList(agent_phone, add_city);
        return retInfo;
    }

    /**
     * @Purpose  按条件查询代理人分页
     * @version  3.0
     * @author
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/agent/searchList")
    public ModelAndView searchList(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                                   @RequestParam(value = "agent_id", required = true) Integer agent_id,
                                   @RequestParam(value = "agent_mobile", required = true) String agent_mobile,
                                   @RequestParam(value = "agent_name", required = true) String agent_name,
                                   HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/agent/search");
        mv.addObject("retInfo", agentService.searchList(currentPage ,10,agent_id,agent_mobile,agent_name, request));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

    /**
     * @Purpose  代理人修改页面跳转
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     */
    @RequestMapping(value = "/admin/agent/update/jump")
    public ModelAndView jump(@RequestParam(value = "agent_id", required = true) Integer agent_id, HttpServletRequest request,
                             @RequestParam(value = "father_id", required = true) Integer father_id){
        ModelAndView mv=new ModelAndView("/agent/update");
        mv.addObject("retInfo", agentService.agentUpdateJump(agent_id));
        mv.addObject("father_id", father_id);
        return mv;
    }

    /**
     * @Purpose  修改代理人信息
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/agent/updateAgent", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo updateAgent(@RequestBody TdHtAgentApplyDto tdHtAgentApplyDto){
        return agentService.updateAgent(tdHtAgentApplyDto);
    }
}
