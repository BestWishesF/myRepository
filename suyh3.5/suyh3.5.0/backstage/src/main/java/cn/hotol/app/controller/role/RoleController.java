package cn.hotol.app.controller.role;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.role.TdHtRoleDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.dictionary.DictionaryService;
import cn.hotol.app.service.funcion.FuncionService;
import cn.hotol.app.service.role.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class RoleController {

    @Resource
    private FuncionService funcionService;
    @Resource
    private RoleService roleService;
    @Resource
    private DictionaryService dictionaryService;


    /**
     * @Purpose  查找角色
     * @version  3.4.0
     * @author   lizhun
     * @param    request
     * @return   ModelAndView
     */
    @RequestMapping(value = "/admin/role/list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/role/index");
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        mv.addObject("roles", roleService.findRoles(tdHtAdmin));
        return mv;
    }
    /**
     * @Purpose  跳转到添加角色页面
     * @version  3.4.0
     * @author   lizhun
     * @param    request
     * @return   ModelAndView
     */
    @RequestMapping(value = "/admin/role/insert/jump")
    public ModelAndView insertj(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/role/insert");
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("functions", funcionService.findFuncionByRole(tdHtAdmin, null));
        mv.addObject("retInfo", dictionaryService.findByAdmin(tdHtAdmin));
        return mv;
    }
    /**
     * @Purpose  跳转到更新角色页面
     * @version  3.4.0
     * @author   lizhun
     * @param    request
     * @return   ModelAndView
     */
    @RequestMapping(value = "/admin/role/update/jump")
    public ModelAndView updatej(HttpServletRequest request, @RequestParam(value = "role_id" , required = true)int role_id ) {
        ModelAndView mv = new ModelAndView("/role/update");
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        TdHtRoleDto tdHtRoleDto = roleService.findRoleById(role_id);
        mv.addObject("role", tdHtRoleDto);
        mv.addObject("retInfo", dictionaryService.findByAdmin(tdHtAdmin));
        mv.addObject("functions", funcionService.findFuncionByRole(tdHtAdmin, tdHtRoleDto));
        return mv;
    }
    /**
     * @Purpose  添加角色
     * @version  3.4.0
     * @author   lizhun
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/role/insert", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo insert(@RequestBody TdHtRoleDto tdHtRoleDto, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        return roleService.insertRole(tdHtAdmin, tdHtRoleDto);

    }
    /**
     * @Purpose  更新角色
     * @version  3.4.0
     * @author   lizhun
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/role/update", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo update(@RequestBody TdHtRoleDto tdHtRoleDto, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        return roleService.updateRole(tdHtAdmin, tdHtRoleDto);

    }
}
