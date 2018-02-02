package cn.hotol.app.controller.three.logistics;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.logistics.LogisticCodeDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.service.three.logistics.LogisticsService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LuBin
 * Date 2016-12-01.
 */

@Controller
public class LogisticsController {

    @Resource
    private LogisticsService logisticsService;

    /**
     * @param logisticCodeDto
     * @return RetInfo
     * @Purpose 根据快递单号查询快递公司
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/findExpressCode", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findExpressCodeByWaybill(@RequestBody LogisticCodeDto logisticCodeDto, HttpServletRequest request) {
        long start = System.currentTimeMillis();
        RetInfo retInfo = logisticsService.findExpressCodeByWaybill(logisticCodeDto);
        long end = System.currentTimeMillis();
        return retInfo;
    }

    /**
     * @param logisticCodeDto
     * @return RetInfo
     * @Purpose 通过快递公司和单号查询物流信息
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/findExpressLogistic", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findExpressLogisticByWaybill(@RequestBody LogisticCodeDto logisticCodeDto, HttpServletRequest request,
                                                @RequestHeader(value = "token", required = true) String token) {
        TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);
        long start = System.currentTimeMillis();
        RetInfo retInfo =  logisticsService.findExpressLogisticByWaybill(logisticCodeDto, tdHtMemberDto);
        long end = System.currentTimeMillis();
        return retInfo;
    }

    /**
     * @return RetInfo
     * @Purpose 查询前十条的查件记录
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/findFirstTenExpSearch", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findFirstTenExpSearch(@RequestHeader(value = "token", required = true) String token) {
        return logisticsService.findFirstTenExpSearch(token);
    }

    /**
     * @return RetInfo
     * @Purpose 查询前十条的查件记录
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/search/logistics", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/x-www-form-urlencoded;charset=utf-8")
    @ResponseBody
    public void logistics(HttpServletRequest request, HttpServletResponse response) throws IOException {


        String RequestData = request.getParameter("RequestData");
        System.out.println(RequestData);

        PrintWriter out = response.getWriter();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject obj = new JSONObject();
        obj.put("EBusinessID", "1257267");
        obj.put("UpdateTime", sdf.format(new Date()));
        obj.put("Success", true);
        obj.put("Reason", "");
        out.write(obj.toString());
        out.flush();
        out.close();
    }

}
