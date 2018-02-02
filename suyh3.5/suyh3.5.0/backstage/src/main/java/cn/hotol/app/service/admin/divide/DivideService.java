package cn.hotol.app.service.admin.divide;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.divide.TdHtAdminDivideDto;

import java.util.Map;

/**
 * Created by Administrator on 2017-03-04.
 */
public interface DivideService {

    /**
     * @Purpose  查询行政区内的划分区域
     * @version  3.0
     * @author   lubin
     * @param    tdHtAdminDivideDto
     * @return   RetInfo
     */
    public RetInfo findGridByRegion(TdHtAdminDivideDto tdHtAdminDivideDto);

    /**
     * @Purpose  查询所有的划分区域
     * @version  3.0
     * @author   lubin
     * @param
     * @return   Map<String, Object>
     */
    public Map<String, Object> findAllDivide();

    /**
     * @Purpose  分页查询区域下划分区域
     * @version  3.0
     * @author   lubin
     * @time     2017-04-14
     * @param    currentPage  请求页码
     * @param    pageSize 每页显示数量
     * @param    region_id  区域id
     * @return   RetInfo
     */
    public RetInfo findDivideByRegionPage(int currentPage, int pageSize, int region_id);

    /**
     * @Purpose  查询区域下所有的划分区域
     * @version  3.0
     * @author   lubin
     * @time     2017-04-14
     * @param    region_id  区域id
     * @return   RetInfo
     */
    public RetInfo findDivideByRegion(int region_id);

    /**
     * @Purpose  新增划分区域
     * @version  3.0
     * @author   lubin
     * @time     2017-04-14
     * @param    tdHtAdminDivideDto  划分区域信息
     * @return   RetInfo
     */
    public RetInfo insertDivide(TdHtAdminDivideDto tdHtAdminDivideDto);

    /**
     * @Purpose  通过id查询划分区域
     * @version  3.0
     * @author   lubin
     * @time     2017-04-14
     * @param    divide_id  划分区域id
     * @return   RetInfo
     */
    public RetInfo findDivideById(int divide_id);

    /**
     * @Purpose  修改划分区域
     * @version  3.0
     * @author   lubin
     * @time     2017-04-14
     * @param    tdHtAdminDivideDto  划分区域信息
     * @return   RetInfo
     */
    public RetInfo updateDivide(TdHtAdminDivideDto tdHtAdminDivideDto);

    /**
     * @Purpose  根据区id查找划分区列表
     * @version  3.0
     * @author   lubin
     * @time     2017-04-15
     * @param    region_id  行政区id
     * @return   RetInfo
     */
    public RetInfo findDivideByRegionId(int region_id);

}
