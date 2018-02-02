package cn.hotol.app.controller.three.found;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.expressorder.ExpressOrderPayDto;
import cn.hotol.app.bean.dto.found.TdHtMembFoundChangeDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.dto.page.PageDto;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.service.three.found.FoundService;
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

@Controller
public class FoundController {

    @Resource
    private FoundService foundService;

    /**
     * @param tdHtMembFoundChangeDto
     * @return RetInfo
     * @Purpose 充值
     * @version 3.0
     * @author lizhun
     */
    @RequestMapping(value = "/app/3/token/recharge", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo recharge(@RequestBody TdHtMembFoundChangeDto tdHtMembFoundChangeDto, HttpServletRequest request,
                            @RequestHeader(value = "token", required = true) String token) {
        TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);
        tdHtMembFoundChangeDto.setMemb_id(tdHtMemberDto.getMemb_id());
        return foundService.recharge(tdHtMembFoundChangeDto, request);
    }

    /**
     * @param params
     * @return RetInfo
     * @Purpose 充值结果查询
     * @version 3.0
     * @author lizhun
     */
    @RequestMapping(value = "/app/3/token/recharge/result", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo result(@RequestBody Map<String, String> params) {
        return foundService.rechargeResult(params.get("mfchg_number"));
    }

    /**
     * @param params
     * @return RetInfo
     * @Purpose 代付结果查询
     * @version 3.0
     * @author lizhun
     */
    @RequestMapping(value = "/app/3/agentToken/recharge/result", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo agentesult(@RequestBody Map<String, String> params) {
        return foundService.rechargeResult(params.get("mfchg_number"));
    }

    /**
     * @return RetInfo
     * @Purpose 充值回调
     * @version 3.0
     * @time 2016-12-21
     * @author lizhun
     */
    @RequestMapping(value = "/pay/notify", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    public void notify(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        request.setCharacterEncoding("UTF8");
        //获取头部所有信息
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
        }
        Map<String, Object> data = (Map<String, Object>) params.get("data");
        Map<String, Object> charse = (Map<String, Object>) data.get("object");
//        // 获得 http body 内容
//        BufferedReader reader = request.getReader();
//        StringBuffer buffer = new StringBuffer();
//        String string;
//        while ((string = reader.readLine()) != null) {
//            buffer.append(string);
//        }
//        reader.close();
//        // 解析异步通知数据
//        Event event = Webhooks.eventParse(buffer.toString());

        if ("charge.succeeded".equals(params.get("type"))) {
            //充值
            RetInfo retInfo = foundService.notify(charse);
            if (retInfo.getMark().equals("0")) {
                response.setStatus(200);
            } else {
                response.setStatus(500);
            }

        } else if ("refund.succeeded".equals(params.get("type"))) {
            //退款
            response.setStatus(200);
        } else {
            response.setStatus(500);
        }
    }
//    /**
//     * @Purpose  充值回调
//     * @version  3.0
//     * @time     2016-12-21
//     * @author   lizhun
//     * @return   RetInfo
//     */
//    @RequestMapping(value="/pay/test" ,method = {RequestMethod.GET,RequestMethod.POST} ,consumes = "application/json")
//    public RetInfo test(@RequestBody Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
//
//
//        RetInfo retInfo = foundService.notify(params);
//        return retInfo;
//    }

    /**
     * @param expressOrderPayDto
     * @return RetInfo
     * @Purpose 用户订单支付
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/paymentOrder", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo paymentOrder(@RequestBody ExpressOrderPayDto expressOrderPayDto, HttpServletRequest request,
                                @RequestHeader(value = "token", required = true) String token) {
        return foundService.paymentOrder(expressOrderPayDto, token, request);
    }

    /**
     * @param pageDto
     * @return
     * @Purpose 查询用户收支
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/findMemberFound", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findMemberFound(@RequestBody PageDto pageDto, @RequestHeader(value = "token", required = true) String token) {
        return foundService.findMemberFound(pageDto, token);
    }

    /**
     * @param expressOrderPayDto
     * @return
     * @Purpose 代理人代付
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/replacePayOrder", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo replacePayOrder(@RequestBody ExpressOrderPayDto expressOrderPayDto, HttpServletRequest request,
                                   @RequestHeader(value = "token", required = true) String token) {
        return foundService.replacePayOrder(expressOrderPayDto, token, request);
    }
}
