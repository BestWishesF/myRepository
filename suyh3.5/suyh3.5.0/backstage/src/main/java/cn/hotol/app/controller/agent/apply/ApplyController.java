package cn.hotol.app.controller.agent.apply;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.agent.apply.TdHtAgentApplyDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.agent.apply.AgentApplyService;
import cn.hotol.app.service.funcion.FuncionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class ApplyController {

    @Resource
    private FuncionService funcionService;
    @Resource
    private AgentApplyService agentApplyService;

    /**
     * @Purpose  待审核代理人申请列表
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/agent/apply/list")
    public ModelAndView list(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                             @RequestParam(value = "apply_state", required = true) Integer apply_state,
                             HttpServletRequest request){
        ModelAndView mv ;
        if (apply_state == 1){
            mv = new ModelAndView("/agent/apply/index");
        } else  if (apply_state == 2) {
            mv = new ModelAndView("/agent/apply/not");
        } else {
            mv = new ModelAndView("/agent/apply/fail");
        }
        mv.addObject("retInfo", agentApplyService.agentAppllyPage(apply_state,currentPage ,10, request));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }
    /**
     * @Purpose  审核
     * @version  3.0
     * @author   lizhun
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/agent/apply/examine", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo examine(@RequestBody TdHtAgentApplyDto tdHtAgentApplyDto, HttpServletRequest request){
        return agentApplyService.examine(tdHtAgentApplyDto);
    }

    /**
     * @Purpose  审核代理人页面跳转
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     */
    @RequestMapping(value = "/admin/agent/apply/jump")
    public ModelAndView jump(@RequestParam(value = "apply_id", required = true) Integer apply_id,
                             @RequestParam(value = "father_id", required = true) Integer father_id,
                             HttpServletRequest request){
        ModelAndView mv=new ModelAndView("/agent/apply/examine");
        mv.addObject("retInfo", agentApplyService.agentAppllyJump(apply_id));
        mv.addObject("father_id", father_id);
        return mv;
    }

}
