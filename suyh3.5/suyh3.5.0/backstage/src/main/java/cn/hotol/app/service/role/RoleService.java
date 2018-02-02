package cn.hotol.app.service.role;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.role.TdHtRoleDto;

import java.util.List;

/**
 * Created by lizhun on 2017/4/22.
 */
public interface RoleService {
    /**
     * @Purpose  添加角色
     * @version  3.4.0
     * @author   lizhun
     * @param    tdHtAdmin
     * @return   RetInfo
     */
    public RetInfo insertRole(TdHtAdminDto tdHtAdmin, TdHtRoleDto tdHtRoleDto);
    /**
     * @Purpose  修改角色
     * @version  3.4.0
     * @author   lizhun
     * @param    tdHtAdmin
     * @return   RetInfo
     */
    public RetInfo updateRole(TdHtAdminDto tdHtAdmin, TdHtRoleDto tdHtRoleDto);
    /**
     * @Purpose  查找角色
     * @version  3.4.0
     * @author   lizhun
     * @param    tdHtAdmin
     * @return   List<TdHtRoleDto>
     */
    public List<TdHtRoleDto> findRoles(TdHtAdminDto tdHtAdmin);
    /**
     * @Purpose  根据id查找角色
     * @version  3.4.0
     * @author   lizhun
     * @param    role_id
     * @return   TdHtRoleDto
     */
    public TdHtRoleDto findRoleById(int role_id);
}
