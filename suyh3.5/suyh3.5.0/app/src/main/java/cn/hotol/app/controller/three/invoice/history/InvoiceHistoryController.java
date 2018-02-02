package cn.hotol.app.controller.three.invoice.history;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.invoice.TdHtMembInvoiceDto;
import cn.hotol.app.bean.dto.invoice.history.TdHtMembInvoiceHistoryDto;
import cn.hotol.app.bean.dto.page.PageDto;
import cn.hotol.app.service.three.invoice.history.InvoiceHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017-03-18.
 */

@Controller
public class InvoiceHistoryController {

    @Resource
    private InvoiceHistoryService invoiceHistoryService;

    /**
     * @param pageDto
     * @Purpose 分页查询用户开票历史记录
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/6/token/findIvcHisPage", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findIvcHisPage(@RequestBody PageDto pageDto, @RequestHeader(value = "token", required = true) String token) {
        return invoiceHistoryService.findIvcHisPage(pageDto, token);
    }

    /**
     * @param tdHtMembInvoiceDto
     * @Purpose 用户申请开票
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/6/token/rePayInvoice", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo rePayInvoice(@RequestBody TdHtMembInvoiceDto tdHtMembInvoiceDto, HttpServletRequest request,
                                @RequestHeader(value = "token", required = true) String token) {
        return invoiceHistoryService.rePayInvoice(tdHtMembInvoiceDto, token, request);
    }

    /**
     * @param tdHtMembInvoiceHistoryDto
     * @Purpose 用户取消开票申请
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/6/token/cancelPayInvoice", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo cancelPayInvoice(@RequestBody TdHtMembInvoiceHistoryDto tdHtMembInvoiceHistoryDto) {
        return invoiceHistoryService.cancelPayInvoice(tdHtMembInvoiceHistoryDto);
    }

}
