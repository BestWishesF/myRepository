package cn.hotol.app.controller.api.admin;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.page.PageDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.service.admin.AdminService;
import cn.hotol.app.service.funcion.FuncionService;
import cn.hotol.app.service.role.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017-05-05.
 */

@Controller
public class AdminApiController {

    @Resource
    private AdminService adminService;
    @Resource
    private FuncionService funcionService;
    @Resource
    private RoleService roleService;

    /**
     * @return ModelAndView
     * @Purpose 分页查询管理员帐号
     * @version 3.0
     * @author
     */
    @RequestMapping(value = "/app/3/token/admin/accounts/list", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo list(@RequestBody PageDto pageDto, HttpServletRequest request) {
        RetInfo retInfo = adminService.findAdminPage(pageDto.getPage_no(), pageDto.getPage_size(), request);
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        RetInfo funcions = funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id")));
        retInfo.setFuncion(funcions.getObj());
        return retInfo;
    }

}
