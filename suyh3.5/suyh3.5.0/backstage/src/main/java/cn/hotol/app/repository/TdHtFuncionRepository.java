package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.funcion.TdHtFuncionDto;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-03-07.
 */
public interface TdHtFuncionRepository {

    /**
     * @Purpose  根据角色查找角色父功能
     * @version  3.4.0
     * @author   lizhun
     * @param    role_id
     * @return   List<TdHtFuncionDto>
     */
    public List<TdHtFuncionDto> findFatherFuncion(int role_id);
    /**
     * @Purpose  根据角色id,及父id查找子角色功能
     * @version  3.4.0
     * @author   lizhun
     * @param    map
     * @return   List<TdHtFuncionDto>
     */
    public List<TdHtFuncionDto> findSonFuncion(Map<String, Object> map);
    /**
     * @Purpose  根据角色id查找角色所有功能
     * @version  3.4.0
     * @author   lizhun
     * @param    role_id
     * @return   List<TdHtFuncionDto>
     */
    public List<TdHtFuncionDto> findFuncionsByRoleId(int role_id);
}
