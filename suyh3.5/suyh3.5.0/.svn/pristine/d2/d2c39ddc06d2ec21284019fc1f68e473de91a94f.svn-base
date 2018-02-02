package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.admin.divide.grid.TdHtAdminDivideGridDto;

import java.util.List;

/**
 * Created by Administrator on 2017-03-03.
 */
public interface TdHtAdminDivideGridRepository {

    /**
     * @Purpose  查询划分区域的各点坐标
     * @version  3.0
     * @author   lubin
     * @param    divide_id
     * @return   List<TdHtAdminDivideGridDto>
     */
    public List<TdHtAdminDivideGridDto> findGridByDivide(int divide_id);

    /**
     * @Purpose  添加划分区域的各点坐标
     * @version  3.0
     * @author   lubin
     * @param    tdHtAdminDivideGridDtoList
     * @return
     */
    public void insertDivideGrids(List<TdHtAdminDivideGridDto> tdHtAdminDivideGridDtoList);

    /**
     * @Purpose  根据划分区域id修改区域坐标
     * @version  3.0
     * @author   lubin
     * @param    divide_id
     * @return
     */
    public void updateGridStateByDivide(int divide_id);

}
