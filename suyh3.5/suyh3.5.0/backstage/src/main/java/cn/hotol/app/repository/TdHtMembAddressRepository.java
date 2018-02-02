package cn.hotol.app.repository;

import cn.hotol.app.bean.TdHtMembAddress;
import cn.hotol.app.bean.dto.address.TdHtMembAddressDto;

import java.util.List;
import java.util.Map;

/**
 * Created by liyafei on 2016/12/2.
 */
public interface TdHtMembAddressRepository {

    /**
     * @Purpose  查找用户地址数量
     * @version  3.0
     * @author   lizhun
     * @param    memb_id
     * @return   int
     */
    public int findMemberAddressSize(int memb_id);
    /**
     * @Purpose  分页查找用户地址
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   List<TdHtMembAddressDto>
     */
    public List<TdHtMembAddressDto> findMemberAddressPage(Map<String, Object> map);

    /**
     * @Purpose  保存地址
     * @version  3.0
     * @author   lubin
     * @param    tdHtMembAddress
     * @return
     */
    public void insertAddress(TdHtMembAddress tdHtMembAddress);

    /**
     * @Purpose  根据用户id查询用户的收件人地址
     * @version  3.0
     * @author   lubin
     * @param
     * @return List<TdHtMembAddressDto>
     */
    public List<TdHtMembAddressDto> findMemberRevAddress();

    /**
     * @Purpose   查询用户默认发件地址
     * @version   3.0
     * @author    lubin
     * @time      2017-03-29
     * @param     memb_id  用户id
     * @return    TdHtMembAddressDto 用户默认发件地址
     */
    public TdHtMembAddressDto findMembDefSendAddress(int memb_id);
}
