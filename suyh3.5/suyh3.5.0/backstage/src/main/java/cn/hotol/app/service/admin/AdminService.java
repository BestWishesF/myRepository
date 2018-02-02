package cn.hotol.app.service.admin;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lizhun on 2016/12/9.
 */
public interface AdminService {
    /**
     * @Purpose  登陆
     * @version  3.0
     * @author   lizhun
     * @param    tdHtAdminDto
     * @return   RetInfo
     */
    public RetInfo login(TdHtAdminDto tdHtAdminDto, HttpServletRequest request);
    /**
     * @Purpose  修改密码
     * @version  3.0
     * @author   lizhun
     * @param    tdHtAdminDto
     * @return   RetInfo
     */
    public RetInfo updatePsd(TdHtAdminDto tdHtAdminDto, HttpServletRequest request);

    /**
     * @Purpose  app端登陆
     * @version  3.0
     * @author   lubin
     * @param    tdHtAdminDto
     * @return   RetInfo
     */
    public RetInfo appLogin(TdHtAdminDto tdHtAdminDto, HttpServletRequest request);
    /**
     * @Purpose  app端修改密码
     * @version  3.0
     * @author   lubin
     * @param    tdHtAdminDto
     * @return   RetInfo
     */
    public RetInfo appUpdatePsd(TdHtAdminDto tdHtAdminDto);

    /**
     * @Purpose  分页查询管理员帐号
     * @version  3.0
     * @author   lubin
     * @param    currentPage
     * @return   RetInfo
     */
    public RetInfo findAdminPage(int currentPage, int pageSize, HttpServletRequest request);

    /**
     * @Purpose  创建新的管理员帐号
     * @version  3.0
     * @author   lubin
     * @param    tdHtAdminDto
     * @return   RetInfo
     */
    public RetInfo insertAdmin(TdHtAdminDto tdHtAdminDto);

    /**
     * @Purpose  修改管理员帐号
     * @version  3.0
     * @author   lubin
     * @param    tdHtAdminDto
     * @return   RetInfo
     */
    public RetInfo updateAdmin(TdHtAdminDto tdHtAdminDto);

    /**
     * @Purpose  根据id查找管理员
     * @version  4.0
     * @author   lizhun
     * @param    admin_id
     * @return   TdHtAdminDto
     */
    public TdHtAdminDto findAdminById(int admin_id, HttpServletRequest request);

    /**
     * @Purpose  重置管理员帐号密码
     * @version  3.0
     * @author   lubin
     * @param    tdHtAdminDto
     * @return   RetInfo
     */
    public RetInfo resetPass(TdHtAdminDto tdHtAdminDto);

    /**
     * @Purpose  注销帐号
     * @version  3.0
     * @author   lubin
     * @param    admin_id
     * @return   RetInfo
     */
    public RetInfo cancelAccounts(int admin_id);

    public TdHtAdminDto findAdminByName(String user_name);
}
