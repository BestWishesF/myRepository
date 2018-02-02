package cn.hotol.app.controller.express.open.number;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.express.number.BatchExpressNumberDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.express.open.number.ExpressOpenNumberService;
import cn.hotol.app.service.express.sdk.jingdong.ExpressSdkJingDongService;
import cn.hotol.app.service.express.sdk.shentong.ExpressSdkShenTongService;
import cn.hotol.app.service.express.sdk.tiantian.ExpressSdkTianTianService;
import cn.hotol.app.service.funcion.FuncionService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by LuBin
 * Date 2017-01-12.
 */

@Controller
public class ExpressOpenNumberController {

    @Resource
    private ExpressOpenNumberService expressOpenNumberService;
    @Resource
    private ExpressSdkTianTianService expressSdkTianTianService;
    @Resource
    private ExpressSdkJingDongService expressSdkJingDongService;
    @Resource
    private ExpressSdkShenTongService expressSdkShenTongService;
    @Resource
    private FuncionService funcionService;

    /**
     * @return ModelAndView
     * @Purpose 快递单号列表
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/admin/express/open/number/list")
    public ModelAndView list(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                             @RequestParam(value = "express_id", required = true) Integer express_id,
                             @RequestParam(value = "region_id", required = true) Integer region_id,
                             @RequestParam(value = "exp_open_state", required = true) Integer exp_open_state,
                             HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/express/number/index");
        mv.addObject("retInfo", expressOpenNumberService.expressOpenNumberPage(express_id, region_id, exp_open_state, currentPage, 10));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

    /**
     * @Purpose  添加数据
     * @version  3.0
     * @author   lizhun
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/express/open/number/insert")
    @ResponseBody
    public RetInfo insert(@Valid @ModelAttribute BatchExpressNumberDto batchExpressNumberDto, BindingResult result){
        RetInfo retInfo = new RetInfo();
        if(batchExpressNumberDto.getStar_number() == null || "".equals(batchExpressNumberDto.getStar_number())){
            if(batchExpressNumberDto.getExpress_id()== Constant.HHTT){
                retInfo=expressSdkTianTianService.extractExpressNo(batchExpressNumberDto);
            }else if(batchExpressNumberDto.getExpress_id()==Constant.JD){
                if(batchExpressNumberDto.getNumber_size()>100){
                    retInfo.setMark("1");
                    retInfo.setTip("每次最多获取100个单号");
                }else{
                    retInfo=expressSdkJingDongService.extractExpressNo(batchExpressNumberDto);
                }
            }else if(batchExpressNumberDto.getExpress_id()==Constant.STO){
                retInfo=expressSdkShenTongService.extractExpressNo(batchExpressNumberDto);
            }else {
                retInfo.setMark("1");
                retInfo.setTip("该快递公司暂无单号获取接口.");
            }
        }else{
            retInfo=expressOpenNumberService.insertExpressNumber(batchExpressNumberDto);
        }
        return retInfo;
    }

}
