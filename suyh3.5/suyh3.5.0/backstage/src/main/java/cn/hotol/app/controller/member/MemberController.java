package cn.hotol.app.controller.member;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.funcion.FuncionService;
import cn.hotol.app.service.member.MemberService;
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
public class MemberController {

    @Resource
    private FuncionService funcionService;
    @Resource
    private MemberService memberService;

    /**
     * @Purpose  分页查找用户
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/member/index")
    public ModelAndView index(@RequestParam(value = "currentPage", required = true, defaultValue = "1") Integer currentPage
            , HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/member/index");
        mv.addObject("retInfo", memberService.memberPage(currentPage, 10, request));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

    /**
     * @Purpose  锁定或解锁用户
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/member/lock")
    @ResponseBody
    public RetInfo lock(@ModelAttribute TdHtMemberDto tdHtMemberDto, HttpServletRequest request){
        return memberService.lock(tdHtMemberDto);

    }

    /**
     * @Purpose  搜索用户
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/member/search")
    public ModelAndView search(@ModelAttribute TdHtMemberDto tdHtMemberDto,
                             @RequestParam(value = "currentPage", required = true) Integer currentPage,
                               HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/member/search/index");
        mv.addObject("retInfo", memberService.memberSearchPage(tdHtMemberDto ,currentPage ,10, request));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

    /**
     * @Purpose  修改用户角色
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/member/updateRole")
    @ResponseBody
    public RetInfo updateRole(@ModelAttribute TdHtMemberDto tdHtMemberDto, HttpServletRequest request){
        return memberService.updateRole(tdHtMemberDto);

    }

    /**
     * @Purpose  赠送用户优惠券页面跳转
     * @version  3.0
     * @author   lubin
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/member/activity/findGiveCouActMemb")
    public ModelAndView findGiveCouActMemb(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/member/activity/index");
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

    /**
     * @Purpose  赠送用户优惠券
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/member/activity/giveMemberCoupon")
    @ResponseBody
    public RetInfo giveMemberCoupon(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdminDto = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        RetInfo retInfo = new RetInfo();
        if(tdHtAdminDto == null){
            retInfo.setMark("1");
            retInfo.setTip("请登录.");
        }else {
            return memberService.giveMemberCoupon();
        }
        return retInfo;
    }
}
