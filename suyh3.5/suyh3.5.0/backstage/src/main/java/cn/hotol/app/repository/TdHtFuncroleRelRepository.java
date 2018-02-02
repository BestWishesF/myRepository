package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.funcion.TdHtFuncroleRelDto;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-03-07.
 */
public interface TdHtFuncroleRelRepository {

    /**
     * @Purpose  根据id查找角色id和功能id
     * @version  4.0
     * @author   lizhun
     * @param    map
     * @return   TdHtFuncroleRelDto
     */
    public TdHtFuncroleRelDto findFuncroleRelByRoleIdAndFuncId(Map<String,Integer> map);

    /**
     * @Purpose  根据权限id查找角色权限关联信息
     * @version  4.0
     * @author   lizhun
     * @param    role_id
     * @return   List<TdHtFuncroleRelDto>
     */
    public List<TdHtFuncroleRelDto> findFuncroleRelsByRoleId(int role_id);
    /**
     * @Purpose  添加角色权限关联信息
     * @version  4.0
     * @author   lizhun
     * @return   void
     */
    public void insertFuncroleRel(TdHtFuncroleRelDto TdHtFuncroleRelDto);
    /**
     * @Purpose  修改角色限关联信息
     * @version  4.0
     * @author   lizhun
     * @return   void
     */
    public void updateFuncroleRel(TdHtFuncroleRelDto TdHtFuncroleRelDto);
    /**
     * @Purpose  修改角色所有功能不可用
     * @version  4.0
     * @author   lizhun
     * @return   void
     */
    public void updateFuncroleRelByRoleId(int role_id);
}
