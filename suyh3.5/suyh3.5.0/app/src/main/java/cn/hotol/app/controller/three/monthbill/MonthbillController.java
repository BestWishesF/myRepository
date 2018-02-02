package cn.hotol.app.controller.three.monthbill;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.expressorder.ExpressOrderPayDto;
import cn.hotol.app.bean.dto.monthbill.ConsumptionRecordDto;
import cn.hotol.app.bean.dto.monthbill.TdHtMembMonthBillDto;
import cn.hotol.app.bean.dto.page.PageDto;
import cn.hotol.app.service.three.monthbill.MonthbillService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2017-02-15.
 */

@Controller
public class MonthbillController {

    @Resource
    private MonthbillService monthbillService;

    /**
     * @param pageDto
     * @return
     * @Purpose 分页查询用户待支付月结账单订单数据
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/findMemberMonthBill", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findMemberMonthBill(@RequestBody PageDto pageDto, @RequestHeader(value = "token", required = true) String token) {
        return monthbillService.findMemberMonthBill(pageDto, token);
    }

    /**
     * @param expressOrderPayDto
     * @return
     * @Purpose 月结账单支付
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/memberPayMonthBill", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo memberPayMonthBill(@RequestBody ExpressOrderPayDto expressOrderPayDto, HttpServletRequest request,
                                      @RequestHeader(value = "token", required = true) String token){
        return monthbillService.memberPayMonthBill(expressOrderPayDto, token, request);
    }

    /**
     * @param pageDto
     * @return
     * @Purpose 分页查询用户月结账单消费记录
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/findMonthBillPage", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findMonthBillPage(@RequestBody PageDto pageDto, @RequestHeader(value = "token", required = true) String token) {
        return monthbillService.findMonthBillPage(pageDto, token);
    }

    /**
     * @param pageDto
     * @return
     * @Purpose 查询月结账单消费详情
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/findMonthBillDetails", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findMonthBillDetails(@RequestBody PageDto pageDto, @RequestHeader(value = "token", required = true) String token) {
        return monthbillService.findMonthBillDetails(pageDto, token);
    }

}
