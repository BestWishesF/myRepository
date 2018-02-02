package cn.hotol.app.controller.three.invoice;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.invoice.TdHtMembInvoiceDto;
import cn.hotol.app.service.three.invoice.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Administrator on 2017-03-18.
 */

@Controller
public class InvoiceController {

    @Resource
    private InvoiceService invoiceService;

    /**
     * @param tdHtMembInvoiceDto
     * @Purpose 用户申请开票
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/6/token/saveMembInvoice", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo saveMembInvoice(@Valid @RequestBody TdHtMembInvoiceDto tdHtMembInvoiceDto, BindingResult result, HttpServletRequest request,
                                   @RequestHeader(value = "token", required = true) String token) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return invoiceService.saveMembInvoice(tdHtMembInvoiceDto, token, request);
        }
        return retInfo;
    }

    /**
     * @param tdHtMembInvoiceDto
     * @Purpose 通过id查询用户开票详情
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/6/token/findMembIvcById", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findMembIvcById(@RequestBody TdHtMembInvoiceDto tdHtMembInvoiceDto) {
        return invoiceService.findMembIvcById(tdHtMembInvoiceDto.getMemb_ivc_id());
    }

    /**
     * @param token
     * @Purpose 通过用户最近一次开票详情
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/6/token/findLatelyIvcInfo", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findLatelyIvcInfo(@RequestHeader(value = "token", required = true) String token) {
        return invoiceService.findLatelyIvcInfo(token);
    }

}
