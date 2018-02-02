package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.role.TdHtRoleDto;

import java.util.List;

/**
 * Created by Administrator on 2017-03-04.
 */
public interface TdHtRoleRepository {

    /**
     * @Purpose  查询所有的角色
     * @version  3.0
     * @author   lubin
     * @return   List<TdHtRoleDto>
     */
    public List<TdHtRoleDto> findAllRole(TdHtAdminDto tdHtAdmin);

    /**
     * @Purpose  通过角色id查询角色信息
     * @version  3.0
     * @author   lubin
     * @param    role_id
     * @return   TdHtRoleDto
     */
    public TdHtRoleDto findRoleById(int role_id);

    /**
     * @Purpose  查询角色权限下的角色
     * @version  3.0
     * @author   lubin
     * @param    role_id
     * @return   List<TdHtRole>
     */
    public List<TdHtRoleDto> findRoleByJurisdiction(int role_id);
    /**
     * @Purpose  添加角色
     * @version  4.0
     * @author   lizhun
     * @return   void
     */
    public void insertRole(TdHtRoleDto tdHtRoleDto);
    /**
     * @Purpose  修改角色
     * @version  4.0
     * @author   lizhun
     * @return   void
     */
    public void updateRole(TdHtRoleDto tdHtRoleDto);
    /**
     * @Purpose  修改角色不可用
     * @version  4.0
     * @author   lizhun
     * @return   void
     */
    public void updateRoleValid(TdHtRoleDto tdHtRoleDto);
}
