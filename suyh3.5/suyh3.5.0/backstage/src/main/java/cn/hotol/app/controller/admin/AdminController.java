package cn.hotol.app.controller.admin;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.Ip;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.service.admin.AdminService;
import cn.hotol.app.service.funcion.FuncionService;
import cn.hotol.app.service.role.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class AdminController {


    @Resource
    private AdminService adminService;
    @Resource
    private FuncionService funcionService;
    @Resource
    private RoleService roleService;


    /**
     * @Purpose  跳转到登陆页面
     * @version  3.0
     * @author   lizhun
     * @return   ModelAndView
     */
    @RequestMapping(value="/login")
    public ModelAndView login(){
        ModelAndView mv = new ModelAndView("/login/login");
        return mv;
    }
    /**
     * @Purpose  登录
     * @version  3.0
     * @author   lizhun
     * @param    tdHtAdminDto
     * @return   RetInfo
     */
    @RequestMapping(value="/logins")
    @ResponseBody
    public RetInfo logins(@ModelAttribute TdHtAdminDto tdHtAdminDto, HttpServletRequest request){
        tdHtAdminDto.setLogin_ip(Ip.getIpAddr(request));
        return adminService.login(tdHtAdminDto,request);
    }
    /**
     * @Purpose  跳转到登陆页
     * @version  3.0
     * @author   lizhun
     * @param    request
     * @return   RetInfo
     */
    @RequestMapping(value="/skip")
    public ModelAndView skip(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/login/skip");
        return mv;
    }
    /**
     * @Purpose  修改密码
     * @version  3.0
     * @author   lizhun
     * @return   RetInfo
     */
    @RequestMapping(value="/admin/updatePsd")
    @ResponseBody
    public RetInfo updatePsd(@ModelAttribute TdHtAdminDto tdHtAdminDto, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        tdHtAdminDto.setUser_name(tdHtAdmin.getUser_name());
        return adminService.updatePsd(tdHtAdminDto, request);
    }

    /**
     * @Purpose  app端登录
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     */
    @RequestMapping(value="/app/3/admin/login", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo apiLogin(@RequestBody TdHtAdminDto tdHtAdminDto, HttpServletRequest request,
                            @RequestHeader(value = "client_type", required = true) Integer client_type){
        tdHtAdminDto.setLogin_ip(Ip.getIpAddr(request));
        tdHtAdminDto.setLogin_client(client_type);
        return adminService.appLogin(tdHtAdminDto,request);
    }

    /**
     * @Purpose  app端修改密码
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     */
    @RequestMapping(value="/app/3/token/admin/updatePsd", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo apiUpdatePsd(@RequestBody TdHtAdminDto tdHtAdminDto, @RequestHeader(value = "token", required = true) String token){
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto)MemcachedUtils.get(token);
        tdHtAdminDto.setUser_name(tdHtAdmin.getUser_name());
        return adminService.appUpdatePsd(tdHtAdminDto);
    }

    /**
     * @Purpose  app端退出登录
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     */
    @RequestMapping(value="/app/3/token/admin/exitLogin", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo exitLogin(@RequestHeader(value = "token", required = true) String token){
        MemcachedUtils.delete(token);
        RetInfo retInfo=new RetInfo();
        retInfo.setMark("0");
        retInfo.setTip("退出系统.");
        return retInfo;
    }

    /**
     * @return ModelAndView
     * @Purpose 分页查询管理员帐号
     * @version 3.0
     * @author
     */
    @RequestMapping(value = "/admin/accounts/list")
    public ModelAndView list(@RequestParam(value = "currentPage", required = true) Integer currentPage, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/admin/index");
        mv.addObject("retInfo", adminService.findAdminPage(currentPage, 10, request));
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

    /**
     * @Purpose 创建新的管理员帐号
     * @version  3.0
     * @author   lubin
     * @param tdHtAdminDto
     * @return RetInfo
     */
    @RequestMapping(value = "/admin/accounts/insert", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo insertAdmin(@RequestBody TdHtAdminDto tdHtAdminDto) {
        RetInfo retInfo = adminService.insertAdmin(tdHtAdminDto);
        return retInfo;
    }

    /**
     * @Purpose 修改管理员帐号
     * @version  3.0
     * @author   lubin
     * @param tdHtAdminDto
     * @return RetInfo
     */
    @RequestMapping(value = "/admin/accounts/update", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo updateAdmin(@RequestBody TdHtAdminDto tdHtAdminDto) {
        RetInfo retInfo = adminService.updateAdmin(tdHtAdminDto);
        return retInfo;
    }

    /**
     * @return ModelAndView
     * @Purpose 跳转到添加管理员帐号页面
     * @version 3.0
     * @author
     */
    @RequestMapping(value = "/admin/accounts/insert/jump")
    public ModelAndView insert(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/admin/insert");
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("roles", roleService.findRoles(tdHtAdmin));
        return mv;
    }

    /**
     * @return ModelAndView
     * @Purpose 跳转到更新管理员帐号页面
     * @version 3.0
     * @author
     */
    @RequestMapping(value = "/admin/accounts/update/jump")
    public ModelAndView update(@RequestParam(value = "admin_id", required = true) Integer admin_id, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/admin/update");
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("roles", roleService.findRoles(tdHtAdmin));
        mv.addObject("admin", adminService.findAdminById(admin_id,request));
        return mv;
    }

    /**
     * @Purpose 重置管理员帐号密码
     * @version  3.0
     * @author   lubin
     * @param tdHtAdminDto
     * @return RetInfo
     */
    @RequestMapping(value = "/admin/accounts/resetPass")
    @ResponseBody
    public RetInfo resetPass(@ModelAttribute TdHtAdminDto tdHtAdminDto) {
        RetInfo retInfo = adminService.resetPass(tdHtAdminDto);
        return retInfo;
    }

    /**
     * @Purpose 注销帐号
     * @version  3.0
     * @author   lubin
     * @param admin_id
     * @return RetInfo
     */
    @RequestMapping(value = "/admin/accounts/cancelAccounts")
    @ResponseBody
    public RetInfo cancelAccounts(@RequestParam(value = "admin_id", required = true) Integer admin_id) {
        RetInfo retInfo = adminService.cancelAccounts(admin_id);
        return retInfo;
    }
}
