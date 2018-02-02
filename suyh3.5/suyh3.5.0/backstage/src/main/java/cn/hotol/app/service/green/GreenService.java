package cn.hotol.app.service.green;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.green.TdHtGreenMailDto;

import java.util.Date;

/**
 * Created by Administrator on 2017-03-07.
 */
public interface GreenService {

    /**
     * @Purpose  根据状态分页查找用户参与活动记录
     * @version  3.0
     * @author   lubin
     * @param    state,currentPage,pageSize
     * @return   RetInfo
     */
    public RetInfo findGreenMailPage(int state, int currentPage, int pageSize, String starTime, String endTime, String phone, String name);

    /**
     * @Purpose  修改用户活动记录状态
     * @version  3.0
     * @author   lubin
     * @param    tdHtGreenMailDto
     * @return   RetInfo
     */
    public RetInfo updateGreenMailState(TdHtGreenMailDto tdHtGreenMailDto);

}
