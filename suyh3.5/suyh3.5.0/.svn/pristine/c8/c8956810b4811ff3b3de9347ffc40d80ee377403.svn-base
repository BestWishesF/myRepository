package cn.hotol.app.controller.member.invoice;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.member.invoice.InvoiceExamineDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.express.sdk.debang.ExpressSdkDeBangService;
import cn.hotol.app.service.express.sdk.jingdong.ExpressSdkJingDongService;
import cn.hotol.app.service.express.sdk.shentong.ExpressSdkShenTongService;
import cn.hotol.app.service.express.sdk.tiantian.ExpressSdkTianTianService;
import cn.hotol.app.service.express.sdk.zhongtong.ExpressSdkZhongTongService;
import cn.hotol.app.service.funcion.FuncionService;
import cn.hotol.app.service.member.invoice.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017-03-28.
 */

@Controller
public class InvoiceController {

    @Resource
    private InvoiceService invoiceService;
    @Resource
    private ExpressSdkTianTianService expressSdkTianTianService;
    @Resource
    private ExpressSdkDeBangService expressSdkDeBangService;
    @Resource
    private ExpressSdkJingDongService expressSdkJingDongService;
    @Resource
    private ExpressSdkShenTongService expressSdkShenTongService;
    @Resource
    private ExpressSdkZhongTongService expressSdkZhongTongService;
    @Resource
    private FuncionService funcionService;

    /**
     * @Purpose  分页查询用户开票记录
     * @version  3.0
     * @author   lubin
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/member/invoice/list")
    public ModelAndView list(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                             @RequestParam(value = "memb_id", required = true) Integer memb_id,
                             HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/member/invoice/index");
        mv.addObject("retInfo", invoiceService.findInvoiceList(currentPage ,10, memb_id));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

    /**
     * @Purpose  按条件分页查询用户开票记录
     * @version  3.0
     * @author   lubin
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/member/invoice/searchList")
    public ModelAndView searchList(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                                   @RequestParam(value = "memb_id", required = true) Integer memb_id,
                                   @RequestParam(value = "mobile", required = true) String mobile,
                                   @RequestParam(value = "state", required = true) String state,
                                   HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/member/invoice/search");
        mv.addObject("retInfo", invoiceService.searchInvoiceList(currentPage ,10, memb_id, mobile, state));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

    /**
     * @Purpose  审核用户开票记录
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     */
    @RequestMapping(value = "/admin/member/invoice/examine")
    @ResponseBody
    public RetInfo invoiceExamine(@ModelAttribute InvoiceExamineDto invoiceExamineDto) {
        RetInfo retInfo = invoiceService.invoiceExamine(invoiceExamineDto);
        if(invoiceExamineDto.getType() == 1){
            if("0".equals(retInfo.getMark())){
                TdHtExpressOrderDto tdHtExpressOrderDto = (TdHtExpressOrderDto) retInfo.getObj();
                if (invoiceExamineDto.getExpress_id() == Constant.HHTT) {
                    retInfo = expressSdkTianTianService.submitOrder(tdHtExpressOrderDto);
                } else if (invoiceExamineDto.getExpress_id() == Constant.DBL) {
                    retInfo = expressSdkDeBangService.submitOrder(tdHtExpressOrderDto);
                } else if (invoiceExamineDto.getExpress_id() == Constant.JD) {
                    retInfo = expressSdkJingDongService.submitOrder(tdHtExpressOrderDto);
                } else if (invoiceExamineDto.getExpress_id() == Constant.STO) {
                    retInfo = expressSdkShenTongService.submitOrder(tdHtExpressOrderDto);
                } else if (invoiceExamineDto.getExpress_id() == Constant.ZTO) {
                    retInfo = expressSdkZhongTongService.submitOrder(tdHtExpressOrderDto);
                } else {
                    retInfo.setMark("1");
                    retInfo.setTip("该快递公司没有开通.");
                }
                retInfo.setObj(tdHtExpressOrderDto);
            }
        }
        return retInfo;
    }


}
