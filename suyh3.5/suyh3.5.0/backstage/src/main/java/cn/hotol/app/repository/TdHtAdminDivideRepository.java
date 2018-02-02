package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.admin.divide.TdHtAdminDivideDto;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-03-03.
 */
public interface TdHtAdminDivideRepository {

    /**
     * @Purpose  查询区下的所有有效的划分区域
     * @version  3.0
     * @author   lubin
     * @param    tdHtAdminDivideDto
     * @return   List<TdHtAdminDivideDto>
     */
    public List<TdHtAdminDivideDto> findDivideByRegion(TdHtAdminDivideDto tdHtAdminDivideDto);

    /**
     * @Purpose  创建新的划分区域
     * @version  3.0
     * @author   lubin
     * @param    tdHtAdminDivideDto
     * @return
     */
    public void insertDivide(TdHtAdminDivideDto tdHtAdminDivideDto);

    /**
     * @Purpose  通过划分区域id修改区域状态
     * @version  3.0
     * @author   lubin
     * @param    divide_id
     * @return
     */
    public void updateDivideState(int divide_id);

    /**
     * @Purpose  通过划分区域id查询该区域信息
     * @version  3.0
     * @author   lubin
     * @param    divide_id
     * @return TdHtAdminDivideDto
     */
    public TdHtAdminDivideDto findDivideById(int divide_id);

    /**
     * @Purpose  查询区域下划分区域数量
     * @version  3.0
     * @author   lubin
     * @time     2017-04-14
     * @param    region_id  区域id
     * @return   int
     */
    public int findDivideByRegionSize(int region_id);

    /**
     * @Purpose  分页查询区域下划分区域
     * @version  3.0
     * @author   lubin
     * @time     2017-04-14
     * @param    map  请求页码
     * @return   List<TdHtAdminDivideDto>
     */
    public List<TdHtAdminDivideDto> findDivideByRegionPage(Map<String, Object> map);

    /**
     * @Purpose  查询区下的所有划分区域
     * @version  3.0
     * @author   lubin
     * @time     2017-04-14
     * @param    region_id  区域id
     * @return   List<TdHtAdminDivideDto>
     */
    public List<TdHtAdminDivideDto> findAllDivideByRegion(int region_id);

    /**
     * @Purpose  修改划分区域
     * @version  3.0
     * @author   lubin
     * @time     2017-04-14
     * @param    tdHtAdminDivideDto  区域信息
     * @return
     */
    public void updateDivide(TdHtAdminDivideDto tdHtAdminDivideDto);

}
