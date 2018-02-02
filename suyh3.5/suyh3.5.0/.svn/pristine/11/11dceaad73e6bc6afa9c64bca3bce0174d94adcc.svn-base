package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.admin.TdHtAdminDto;

import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-03.
 */
public interface TdHtAdminRepository {

    /**
     * @Purpose  根据管理员登录用户名查找管理员
     * @version  3.0
     * @author   lizhun
     * @param    user_name
     * @return   TdNtAdmin
     */
    public TdHtAdminDto findAdminByUserName(String user_name);
    /**
     * @Purpose  更新管理员最后登陆时间IP
     * @version  3.0
     * @author   lizhun
     * @param    tdHtAdminDto
     * @return   void
     */
    public void updateAdminLoginIp(TdHtAdminDto tdHtAdminDto);
    /**
     * @Purpose  更新管理员密码
     * @version  3.0
     * @author   lizhun
     * @param    tdHtAdminDto
     * @return   void
     */
    public void updateAdminPsd(TdHtAdminDto tdHtAdminDto);

    /**
     * @Purpose  查询有效的管理员帐号数量
     * @version  3.0
     * @author   lubin
     * @return   int
     */
    public int findAllAdminSize(TdHtAdminDto tdHtAdminDto);

    /**
     * @Purpose  分页查询有效的管理员帐号
     * @version  3.0
     * @author   lubin
     * @param    map
     * @return   List<TdHtAdminDto>
     */
    public List<TdHtAdminDto> findAllAdminPage(Map<String, Object> map);

    /**
     * @Purpose  创建新的管理员帐号
     * @version  3.0
     * @author   lubin
     * @param    tdHtAdminDto
     * @return   void
     */
    public void insertAdmin(TdHtAdminDto tdHtAdminDto);

    /**
     * @Purpose  修改管理员帐号信息
     * @version  3.0
     * @author   lubin
     * @param    tdHtAdminDto
     * @return   void
     */
    public void updateAdmin(TdHtAdminDto tdHtAdminDto);

    /**
     * @Purpose  通过id查询管理员帐号信息
     * @version  3.0
     * @author   lubin
     * @param    admin_id
     * @return   void
     */
    public TdHtAdminDto findAdminById(int admin_id);

    /**
     * @Purpose  修改管理员帐号的有效性
     * @version  3.0
     * @author   lubin
     * @param    admin_id
     * @return   void
     */
    public void updateAdminValid(int admin_id);
}
