package cn.hotol.app.controller.express;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.express.ExpOrdBillDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderCollectDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.location.TsHtDictList;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.service.express.ExpressService;
import cn.hotol.app.service.express.sdk.debang.ExpressSdkDeBangService;
import cn.hotol.app.service.express.sdk.jingdong.ExpressSdkJingDongService;
import cn.hotol.app.service.express.sdk.shentong.ExpressSdkShenTongService;
import cn.hotol.app.service.express.sdk.tiantian.ExpressSdkTianTianService;
import cn.hotol.app.service.express.sdk.zhongtong.ExpressSdkZhongTongService;
import cn.hotol.app.service.funcion.FuncionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ExpressController {

    @Resource
    private ExpressService expressService;
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
     * @return ModelAndView
     * @Purpose 快递订单列表
     * @version 3.0
     * @author lizhun
     */
    @RequestMapping(value = "/admin/express/order/list")
    public ModelAndView list(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                             @RequestParam(value = "exp_ord_state", required = true) Integer exp_ord_state,
                             @RequestParam(value = "memb_type", required = true) Integer memb_type,
                             HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        if (memb_type == 4) {
            mv.setViewName("/express/bigcustomer/index");
        } else {
            mv.setViewName("/express/order/index");
        }
        mv.addObject("retInfo", expressService.expressordPage(exp_ord_state, currentPage, 10, memb_type, request));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

    /**
     * @return ModelAndView
     * @Purpose 快递收件人列表
     * @version 3.0
     * @author lizhun
     */
    @RequestMapping(value = "/admin/express/order/collect")
    public ModelAndView collectList(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                                    @RequestParam(value = "exp_ord_state", required = true) Integer exp_ord_state,
                                    @RequestParam(value = "exp_ord_id", required = true) Integer exp_ord_id,
                                    @RequestParam(value = "memb_type", required = true) Integer memb_type,
                                    HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        if (memb_type == 4) {
            mv.setViewName("/express/bigcustomer/collect");
        } else {
            mv.setViewName("/express/order/collect");
        }
        mv.addObject("retInfo", expressService.expressCollectPage(exp_ord_state, exp_ord_id, currentPage, 10));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

    /**
     * @return ModelAndView
     * @Purpose 订单入库
     * @version 3.0
     * @author lizhun
     */
    @RequestMapping(value = "/admin/express/order/storage")
    @ResponseBody
    public RetInfo storage(@ModelAttribute TdHtExpressOrderDto tdHtExpressOrderDto, HttpServletRequest request) {
        RetInfo retInfo = expressService.validateExpressNumber(tdHtExpressOrderDto.getExp_ord_id());
        if ("0".equals(retInfo.getMark())) {
            if (tdHtExpressOrderDto.getExpress_id() == Constant.HHTT) {
                retInfo = expressSdkTianTianService.submitOrder(tdHtExpressOrderDto);
            } else if (tdHtExpressOrderDto.getExpress_id() == Constant.DBL) {
                retInfo = expressSdkDeBangService.submitOrder(tdHtExpressOrderDto);
            } else if (tdHtExpressOrderDto.getExpress_id() == Constant.JD) {
                retInfo = expressSdkJingDongService.submitOrder(tdHtExpressOrderDto);
            } else if (tdHtExpressOrderDto.getExpress_id() == Constant.STO) {
                retInfo = expressSdkShenTongService.submitOrder(tdHtExpressOrderDto);
            } else if (tdHtExpressOrderDto.getExpress_id() == Constant.ZTO) {
                retInfo = expressSdkZhongTongService.submitOrder(tdHtExpressOrderDto);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("该快递公司没有开通.");
            }
        }
        return retInfo;
    }

    /**
     * @return ModelAndView
     * @Purpose 打印电子面单页面跳转
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/admin/express/order/collect/print")
    public ModelAndView print(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/express/print/expressPrint");
        return mv;
    }

    /**
     * @return ModelAndView
     * @Purpose 获取面单打印数据
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/admin/express/order/collect/printInfo")
    public ModelAndView print(@RequestParam(value = "ht_number", required = true) String ht_number,
                              HttpServletRequest request) {
        RetInfo retInfo = expressService.findPrintInfo(ht_number);
        if ("0".equals(retInfo.getMark())) {
            Map<String, Object> map = (Map<String, Object>) retInfo.getObj();
            int express_id = Integer.valueOf(map.get("express_id").toString());
            if (express_id == Constant.HHTT) {
                retInfo = expressSdkTianTianService.obtainPaintMarker(map);
            } else if (express_id == Constant.JD) {
                retInfo = expressSdkJingDongService.screenExpress(map);
            } else if (express_id == Constant.STO) {
                retInfo = expressSdkShenTongService.obtainPaintMarker(map);
            } else if (express_id == Constant.ZTO) {
                retInfo = expressSdkZhongTongService.obtainPaintMarker(map);
            }
        }
        ModelAndView mv = new ModelAndView("/express/print/expressPrint");
        mv.addObject("retInfo", retInfo);
        return mv;
    }

    /**
     * @return ModelAndView
     * @Purpose 通过条件查询快递订单列表
     * @version 3.0
     * @author lizhun
     */
    @RequestMapping(value = "/admin/express/order/searchList")
    public ModelAndView searchList(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                                   @RequestParam(value = "exp_ord_state", required = true) Integer exp_ord_state,
                                   @RequestParam(value = "add_mobile_phone", required = true) String add_mobile_phone,
                                   @RequestParam(value = "ht_number", required = true) String ht_number,
                                   @RequestParam(value = "express_number", required = true) String express_number,
                                   @RequestParam(value = "memb_type", required = true) Integer memb_type,
                                   HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        if (memb_type == 4) {
            mv.setViewName("/express/bigcustomer/search");
        } else {
            mv.setViewName("/express/order/search");
        }
        mv.addObject("retInfo", expressService.searchList(exp_ord_state, currentPage, 10, add_mobile_phone, ht_number, express_number, memb_type, request));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

    /**
     * @param tdHtExpressOrderDto
     * @return RetInfo
     * @Purpose 通过订单id查询订单信息
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/admin/express/order/findExpOrd")
    @ResponseBody
    public RetInfo findExpOrd(@ModelAttribute TdHtExpressOrderDto tdHtExpressOrderDto) {
        RetInfo retInfo = expressService.findExpOrd(tdHtExpressOrderDto.getExp_ord_id());
        return retInfo;
    }

    /**
     * @param tdHtExpressOrderDto
     * @return RetInfo
     * @Purpose 通过订单id更新订单信息
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/admin/express/order/updateExpOrd")
    @ResponseBody
    public RetInfo updateExpOrd(@ModelAttribute TdHtExpressOrderDto tdHtExpressOrderDto) {
        RetInfo retInfo = expressService.updateExpOrd(tdHtExpressOrderDto);
        return retInfo;
    }

    /**
     * @param tdHtExpressOrderDto
     * @return RetInfo
     * @Purpose 通过订单id更新订单状态
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/admin/express/order/delExpOrd")
    @ResponseBody
    public RetInfo delExpOrd(@ModelAttribute TdHtExpressOrderDto tdHtExpressOrderDto) {
        RetInfo retInfo = expressService.delExpOrd(tdHtExpressOrderDto.getExp_ord_id());
        return retInfo;
    }

    /**
     * @param tdHtExpressOrderCollectDto
     * @return RetInfo
     * @Purpose 通过快件id查询快件信息
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/admin/express/order/collect/findExpOrdClt")
    @ResponseBody
    public RetInfo findExpOrdClt(@ModelAttribute TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto) {
        RetInfo retInfo = expressService.findExpOrdClt(tdHtExpressOrderCollectDto.getExp_ord_clt_id());
        return retInfo;
    }

    /**
     * @param tdHtExpressOrderCollectDto
     * @return RetInfo
     * @Purpose 通过快件id更新快件信息
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/admin/express/order/collect/updateExpOrdClt")
    @ResponseBody
    public RetInfo updateExpOrdClt(@ModelAttribute TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto) {
        RetInfo retInfo = expressService.updateExpOrdClt(tdHtExpressOrderCollectDto);
        return retInfo;
    }

    /**
     * @param tdHtExpressOrderDto
     * @return RetInfo
     * @Purpose 指派代理人
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/admin/express/order/assignAgent")
    @ResponseBody
    public RetInfo assignAgent(@ModelAttribute TdHtExpressOrderDto tdHtExpressOrderDto) {
        RetInfo retInfo = expressService.assignAgent(tdHtExpressOrderDto);
        return retInfo;
    }

    /**
     * @return ModelAndView
     * @Purpose 通过条件查询快递列表
     * @version 3.0
     * @author lizhun
     */
    @RequestMapping(value = "/admin/express/order/collect/searchOrdBillList")
    public ModelAndView searchOrdBillList(@ModelAttribute ExpOrdBillDto expOrdBillDto, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/express/order/statistics");
        mv.addObject("retInfo", expressService.searchOrdBillList(expOrdBillDto, 10));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

    /**
     * @param
     * @return RetInfo
     * @Purpose 快件数据导出Excel
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/admin/express/order/collect/createExcel")
    public RetInfo createExcel(@ModelAttribute ExpOrdBillDto expOrdBillDto, HttpServletResponse response) {
        RetInfo retInfo = expressService.createExcel(expOrdBillDto, response);
        return retInfo;
    }

    /**
     * @param tdHtExpressOrderDto
     * @return RetInfo
     * @Purpose 通过id查询订单信息
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/admin/express/order/findOrdById")
    @ResponseBody
    public RetInfo findOrdById(@ModelAttribute TdHtExpressOrderDto tdHtExpressOrderDto) {
        RetInfo retInfo = expressService.findOrdById(tdHtExpressOrderDto.getExp_ord_id());
        return retInfo;
    }

    /**
     * @return RetInfo
     * @Purpose app端不同用户权限统计数据
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/admin/statisticsDataByRole", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo statisticsDataByRole(HttpServletRequest request, @RequestHeader(value = "token", required = true) String token) {
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) MemcachedUtils.get(token);
        return expressService.statisticsDataByRole(tdHtAdmin);
    }

    /**
     * @return RetInfo
     * @Purpose app端省市区统计下单量等数据
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/admin/statisticsDataByDict", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo statisticsDataByDict(@RequestBody TsHtDictList tsHtDictList) {
        return expressService.statisticsDataByDict(tsHtDictList);
    }

    /**
     * @return RetInfo
     * @Purpose app端按天统计下单量等数据
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/admin/statisticsDayData", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo statisticsDayData(@RequestBody TsHtDictList tsHtDictList, @RequestHeader(value = "token", required = true) String token) {
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) MemcachedUtils.get(token);
        return expressService.statisticsDayData(tsHtDictList, tdHtAdmin);
    }

    /**
     * @return RetInfo
     * @Purpose app端按需求统计下单量等数据
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/admin/statisticsDataByDemand", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo statisticsDataByDemand(@RequestBody Map params, @RequestHeader(value = "token", required = true) String token) {
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) MemcachedUtils.get(token);
        return expressService.statisticsDataByDemand(params, tdHtAdmin);
    }

    /**
     * @return RetInfo
     * @Purpose app端按时间查询订单列表
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/admin/findExpOrdByTime", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findExpOrdByTime(@RequestBody Map params, @RequestHeader(value = "token", required = true) String token) {
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) MemcachedUtils.get(token);
        return expressService.findExpOrdByTime(params, tdHtAdmin);
    }

    /**
     * @return RetInfo
     * @Purpose app端按订单id查询订单详情
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/admin/findExpOrdDetails", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findExpOrdDetails(@RequestBody TdHtExpressOrderDto expressOrderDto) {
        return expressService.findExpOrdDetails(expressOrderDto.getExp_ord_id());
    }

    /**
     * @return RetInfo
     * @Purpose 大客户快件入库
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/admin/express/bigcustomer/storage")
    @ResponseBody
    public RetInfo bigCustomerStorage(@ModelAttribute TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto) {
        RetInfo retInfo = expressService.bigCustomerStorage(tdHtExpressOrderCollectDto);
        if ("0".equals(retInfo.getMark())) {
            if (retInfo.getObj() != null && !"".equals(retInfo.getObj())) {
                TdHtExpressOrderDto tdHtExpressOrderDto = (TdHtExpressOrderDto) retInfo.getObj();
                if (tdHtExpressOrderDto.getExpress_id() == Constant.HHTT) {
                    retInfo = expressSdkTianTianService.submitOrder(tdHtExpressOrderDto);
                } else if (tdHtExpressOrderDto.getExpress_id() == Constant.DBL) {
                    retInfo = expressSdkDeBangService.submitOrder(tdHtExpressOrderDto);
                } else if (tdHtExpressOrderDto.getExpress_id() == Constant.JD) {
                    retInfo = expressSdkJingDongService.submitOrder(tdHtExpressOrderDto);
                } else if (tdHtExpressOrderDto.getExpress_id() == Constant.STO) {
                    retInfo = expressSdkShenTongService.submitOrder(tdHtExpressOrderDto);
                } else if (tdHtExpressOrderDto.getExpress_id() == Constant.ZTO) {
                    retInfo = expressSdkZhongTongService.submitOrder(tdHtExpressOrderDto);
                } else {
                    retInfo.setMark("1");
                    retInfo.setTip("该快递公司没有开通.");
                }
            }
        }
        return retInfo;
    }

    /**
     * @return ModelAndView
     * @Purpose 获取面单打印数据
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/admin/express/order/batchPrint")
    public ModelAndView batchPrint(@RequestParam(value = "exp_ord_id", required = true) Integer exp_ord_id, HttpServletRequest request) {
        RetInfo retInfo = expressService.batchPrint(exp_ord_id);
        if ("0".equals(retInfo.getMark())) {
            Map<String, Object> retInfoObj = (Map<String, Object>) retInfo.getObj();
            Integer exp_ord_type = (Integer) retInfoObj.get("exp_ord_type");
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Object> dataMap = new HashMap<String, Object>();
            if(exp_ord_type == 1){
                List<Map<String, Object>> mapList = (List<Map<String, Object>>) retInfoObj.get("exp_ord_info");
                for (int i = 0 ; i < mapList.size() ; i ++){
                    Map<String, Object> map = mapList.get(i);
                    int express_id = Integer.valueOf(map.get("express_id").toString());
                    dataMap.put("express_id", express_id);
                    if (express_id == Constant.HHTT) {
                        retInfo = expressSdkTianTianService.obtainPaintMarker(map);
                        Map<String, Object> printData = (Map<String, Object>) retInfo.getObj();
                        printData.put("exp_ord_clt_id", map.get("exp_ord_clt_id"));
                        list.add(printData);
                    } else if (express_id == Constant.JD) {
                        retInfo = expressSdkJingDongService.screenExpress(map);
                        Map<String, Object> printData = (Map<String, Object>) retInfo.getObj();
                        printData.put("exp_ord_clt_id", map.get("exp_ord_clt_id"));
                        list.add(printData);
                    } else if (express_id == Constant.STO) {
                        retInfo = expressSdkShenTongService.obtainPaintMarker(map);
                        Map<String, Object> printData = (Map<String, Object>) retInfo.getObj();
                        printData.put("exp_ord_clt_id", map.get("exp_ord_clt_id"));
                        list.add(printData);
                    } else if (express_id == Constant.ZTO) {
                        retInfo = expressSdkZhongTongService.obtainPaintMarker(map);
                        Map<String, Object> printData = (Map<String, Object>) retInfo.getObj();
                        printData.put("exp_ord_clt_id", map.get("exp_ord_clt_id"));
                        list.add(printData);
                    } else {
                        list.add(map);
                    }
                }
            }
            dataMap.put("list", list);
            dataMap.put("exp_ord_id", exp_ord_id);
            dataMap.put("exp_ord_type", exp_ord_type);
            retInfo.setObj(dataMap);
        }
        ModelAndView mv = new ModelAndView("/express/print/batchPrint");
        mv.addObject("retInfo", retInfo);
        return mv;
    }

    /**
     * @return RetInfo
     * @Purpose 订单申请退款
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/admin/express/order/refundExpOrd")
    @ResponseBody
    public RetInfo refundExpOrd(@ModelAttribute TdHtExpressOrderDto tdHtExpressOrderDto) {
        RetInfo retInfo = expressService.refundExpOrd(tdHtExpressOrderDto.getExp_ord_id());
        return retInfo;
    }

}
