package cn.hotol.app.service.funcion;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.role.TdHtRoleDto;

public interface FuncionService {

    /**
     * @Purpose  根据角色查找角色父功能
     * @version  3.4.0
     * @author   lizhun
     * @param    tdHtAdmin
     * @return   RetInfo
     */
    public RetInfo findFatherFuncion(TdHtAdminDto tdHtAdmin);
    /**
     * @Purpose  根据角色id,及父id查找子角色功能
     * @version  3.4.0
     * @author   lizhun
     * @param    tdHtAdmin
     * @return   RetInfo
     */
    public RetInfo findSonFuncion(TdHtAdminDto tdHtAdmin, int father_id);
    /**
     * @Purpose  根据角色id该角色所有功能
     * @version  3.4.0
     * @author   lizhun
     * @param    tdHtAdmin
     * @return   RetInfo
     */
    public RetInfo findFuncionByRole(TdHtAdminDto tdHtAdmin, TdHtRoleDto tdHtRoleDto);


}
