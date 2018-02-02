package cn.hotol.app.controller.member.refund;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.refund.TdHtMembRefundChangeDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.funcion.FuncionService;
import cn.hotol.app.service.member.refund.RefundService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Administrator on 2017-04-27.
 */
@Controller
public class RefundController {

    @Resource
    private RefundService refundService;
    @Resource
    private FuncionService funcionService;

    /**
     * @Purpose  分页查找申请退款记录
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/found/refundList")
    public ModelAndView refundList(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                                   @RequestParam(value = "refund_state", required = true) Integer refund_state,
                                   HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/refund/order/index");
        mv.addObject("retInfo", refundService.findRefundPage(currentPage, 10, refund_state));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

    /**
     * @return RetInfo
     * @Purpose 处理订单退款
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/admin/found/handleRefundExpOrd")
    @ResponseBody
    public RetInfo handleRefundExpOrd(@Valid @ModelAttribute TdHtMembRefundChangeDto tdHtMembRefundChangeDto, BindingResult result) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            retInfo = refundService.handleRefundExpOrd(tdHtMembRefundChangeDto);
        }
        return retInfo;
    }

}
