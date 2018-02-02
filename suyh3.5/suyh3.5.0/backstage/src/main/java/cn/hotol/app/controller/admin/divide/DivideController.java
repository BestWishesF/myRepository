package cn.hotol.app.controller.admin.divide;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.admin.divide.TdHtAdminDivideDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.admin.divide.DivideService;
import cn.hotol.app.service.funcion.FuncionService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Administrator on 2017-02-27.
 */
@Controller
public class DivideController {

    @Resource
    private DivideService divideService;
    @Resource
    private FuncionService funcionService;

    /**
     * @Purpose  通过区域和类型查询划分区域列表
     * @version  3.0
     * @author   lizhun
     * @return   Ret Info
     */
    @RequestMapping(value="/admin/divide/findGridByRegion")
    @ResponseBody
    public RetInfo updateState(@ModelAttribute TdHtAdminDivideDto tdHtAdminDivideDto, HttpServletRequest request){
        return divideService.findGridByRegion(tdHtAdminDivideDto);
    }

    /**
     * @Purpose  跳转到划分区域列表页面
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/dict/divide/jump/list")
    public ModelAndView jumpDivideList(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                                       @RequestParam(value = "region_id", required = true) Integer region_id,
                                       HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/dict/divide/index");
        mv.addObject("retInfo", divideService.findDivideByRegionPage(currentPage, 10 , region_id));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

    /**
     * @Purpose  跳转到添加划分区域列表页面
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/dict/divide/jump/insert")
    public ModelAndView jumpDivideInsert(@RequestParam(value = "region_id", required = true) Integer region_id,
                                         @RequestParam(value = "father_id", required = true) Integer father_id,
                                       HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/dict/divide/insert");
        mv.addObject("retInfo", divideService.findDivideByRegion(region_id));
        mv.addObject("father_id", father_id);
        return mv;
    }

    /**
     * @Purpose  添加划分区域列表页面
     * @version  3.0
     * @author   lubin
     * @param    tdHtAdminDivideDto  划分区域信息
     * @return   RetInfo
     */
    @RequestMapping(value = "/admin/dict/divide/insert", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo insertDivide(@Valid @RequestBody TdHtAdminDivideDto tdHtAdminDivideDto, BindingResult result) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            retInfo =  divideService.insertDivide(tdHtAdminDivideDto);
        }
        return retInfo;
    }

    /**
     * @Purpose  跳转到修改划分区域列表页面
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/dict/divide/jump/update")
    public ModelAndView jumpDivideUpdate(@RequestParam(value = "divide_id", required = true) Integer divide_id,
                                         @RequestParam(value = "father_id", required = true) Integer father_id,
                                         HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/dict/divide/update");
        mv.addObject("retInfo", divideService.findDivideById(divide_id));
        mv.addObject("father_id", father_id);
        return mv;
    }

    /**
     * @Purpose  修改划分区域
     * @version  3.0
     * @author   lubin
     * @param    tdHtAdminDivideDto  划分区域信息
     * @return   RetInfo
     */
    @RequestMapping(value = "/admin/dict/divide/update", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo updateDivide(@Valid @RequestBody TdHtAdminDivideDto tdHtAdminDivideDto, BindingResult result) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            retInfo =  divideService.updateDivide(tdHtAdminDivideDto);
        }
        return retInfo;
    }

}
