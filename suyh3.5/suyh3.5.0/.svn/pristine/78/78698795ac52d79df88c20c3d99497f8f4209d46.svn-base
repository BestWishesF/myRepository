package cn.hotol.app.controller.dict;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.location.TsHtDictList;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.admin.divide.DivideService;
import cn.hotol.app.service.funcion.FuncionService;
import cn.hotol.app.service.location.LocationService;
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


@Controller
public class DictController {

    @Resource
    private FuncionService funcionService;
    @Resource
    private LocationService locationService;
    @Resource
    private DivideService divideService;

    /**
     * @Purpose  根据类别查找列表
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/dict/type/list")
    public ModelAndView type(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                             @RequestParam(value = "code_type", required = true) String code_type,
                             HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv = new ModelAndView("/dict/index");
        mv.addObject("retInfo", locationService.dictByTypePage(code_type ,currentPage ,10));
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        if(tdHtAdmin.getProvince_id() > 0 && tdHtAdmin.getCity_id() == 0 && tdHtAdmin.getRegion_id() == 0 && "province".equals(code_type)){
            mv = new ModelAndView("/dict/son");
            mv.addObject("retInfo", locationService.dictByParentIdPage(tdHtAdmin.getProvince_id(),"city" ,currentPage ,10));
            mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, 96));
        }
        if(tdHtAdmin.getCity_id() > 0 && tdHtAdmin.getRegion_id() == 0 && "province".equals(code_type)){
            mv = new ModelAndView("/dict/son");
            mv.addObject("retInfo", locationService.dictByParentIdPage(tdHtAdmin.getCity_id(),"area" ,currentPage ,10));
            mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, 99));
        }
        if(tdHtAdmin.getRegion_id() > 0 && "province".equals(code_type)){
            mv = new ModelAndView("/dict/divide/index");
            mv.addObject("retInfo", divideService.findDivideByRegionPage(currentPage, 10 , tdHtAdmin.getRegion_id()));
            mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, 102));
        }
        return mv;
    }
    /**
     * @Purpose  根据父类别查找列表
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/dict/parent/list")
    public ModelAndView parent(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                               @RequestParam(value = "parent_id", required = true) Integer parent_id,
                               @RequestParam(value = "code_type", required = true) String code_type,
                               HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/dict/son");
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("retInfo", locationService.dictByParentIdPage(parent_id,code_type ,currentPage ,10));
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

    /**
     * @Purpose  修改数据
     * @version  3.0
     * @author   lizhun
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/dict/update")
    @ResponseBody
    public RetInfo update(@Valid @ModelAttribute TsHtDictList tsHtDictList, BindingResult result){

        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            retInfo =  locationService.updateDict(tsHtDictList);
        }
        return retInfo;
    }
    /**
     * @Purpose  添加数据
     * @version  3.0
     * @author   lizhun
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/dict/insert")
    @ResponseBody
    public RetInfo insert(@Valid @ModelAttribute TsHtDictList tsHtDictList, BindingResult result){

        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            retInfo =  locationService.insertDict(tsHtDictList);
        }
        return retInfo;

    }
    /**
     * @Purpose  跳转到添加数据页面
     * @version  3.0
     * @author   lizhun
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/dict/jump/insert")
    public ModelAndView jumpInsert(@RequestParam(value = "code_type", required = true) String code_type,
                                   @RequestParam(value = "parent_id", required = true) Integer parent_id,
                                   @RequestParam(value = "father_id", required = true) Integer father_id){
        ModelAndView mv = new ModelAndView("/dict/insert");
        mv.addObject("code_type", code_type);
        mv.addObject("parent_id", parent_id);
        mv.addObject("father_id", father_id);
        return mv;
    }
    /**
     * @Purpose  跳转到更新数据页面
     * @version  3.0
     * @author   lizhun
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/dict/jump/update")
    public ModelAndView jumpUpdate(@RequestParam(value = "dict_id", required = true) Integer dict_id,
                                   @RequestParam(value = "father_id", required = true) Integer father_id){
        ModelAndView mv = new ModelAndView("/dict/update");
        mv.addObject("dict", locationService.findDictById(dict_id));
        mv.addObject("father_id", father_id);
        return mv;
    }
    /**
     * @Purpose  根据省id查找市列表
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/dict/city")
    public ModelAndView city(@RequestParam(value = "parent_id", required = true) Integer parent_id,
                             HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/dict/city");
        mv.addObject("retInfo", locationService.findCityByProvinceId(parent_id));
        return mv;
    }
    /**
     * @Purpose  根据市id查找区列表
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/dict/area")
    public ModelAndView area(@RequestParam(value = "parent_id", required = true) Integer parent_id,
                             HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/dict/area");
        mv.addObject("retInfo", locationService.findAreaByCityId(parent_id));
        return mv;
    }

    /**
     * @Purpose  根据区id查找划分区列表
     * @version  3.0
     * @author   lubin
     * @return   ModelAndView
     */
    @RequestMapping(value="/admin/dict/divide")
    public ModelAndView divide(@RequestParam(value = "region_id", required = true) Integer region_id){
        ModelAndView mv = new ModelAndView("/dict/divide");
        mv.addObject("retInfo", divideService.findDivideByRegionId(region_id));
        return mv;
    }

}
