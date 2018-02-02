package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.green.TdHtGreenMailDto;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-03-07.
 */
public interface TdHtGreenMailRepository {

    /**
     * @Purpose  查询活动记录数量
     * @version  3.0
     * @author   lubin
     * @param    map
     * @return   int
     */
    public int findMembGreenMailSize(Map<String, Object> map);

    /**
     * @Purpose  分页查询活动记录
     * @version  3.0
     * @author   lubin
     * @param    map
     * @return   List<TdHtGreenMailDto>
     */
    public List<TdHtGreenMailDto> findMembGreenMailPage(Map<String, Object> map);

    /**
     * @Purpose  通过id查询用户活动记录信息
     * @version  3.0
     * @author   lubin
     * @param    green_mail_id
     * @return   TdHtGreenMailDto
     */
    public TdHtGreenMailDto findGreenMailById(int green_mail_id);

    /**
     * @Purpose  修改用户活动记录状态
     * @version  3.0
     * @author   lubin
     * @param    tdHtGreenMailDto
     * @return
     */
    public void updateGreenMailState(TdHtGreenMailDto tdHtGreenMailDto);

}
