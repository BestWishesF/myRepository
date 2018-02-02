package cn.hotol.app.repository;

import cn.hotol.app.bean.TdHtMembAddress;
import cn.hotol.app.bean.dto.address.TdHtMembAddressDto;
import cn.hotol.app.bean.dto.page.PageDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liyafei on 2016/12/2.
 */
@Repository
public interface TdHtMembAddressRepository {

    /**
     * @param tdHtMembAddress
     * @return List<TdHtMembAddressDto>
     * @Purpose 根据用户id查询地址信息
     * @version 3.0
     * @author liyafei
     */
    public List<TdHtMembAddressDto> findAddressByMember(TdHtMembAddress tdHtMembAddress);

    /**
     * @param memb_id
     * @return TdHtMembAddressDto
     * @Purpose 根据用户id查询地址信息总数
     * @version 3.0
     * @author lubin
     */
    public TdHtMembAddressDto findMemberDefaultAddress(int memb_id);

    /**
     * @param tdHtMembAddress
     * @return void
     * @Purpose 添加收发件地址信息
     * @version 3.0
     * @author liyafei
     */
    public void insertAddress(TdHtMembAddress tdHtMembAddress);

    /**
     * @param tdHtMembAddress
     * @return void
     * @Purpose 修改收发件地址信息
     * @version 3.0
     * @author liyafei
     */
    public void updateAddress(TdHtMembAddress tdHtMembAddress);

    /**
     * @return void
     * @Purpose 修改默认地址为非默认地址
     * @version 3.0
     * @author liyafei
     */
    public void updateDefault(TdHtMembAddress tdHtMembAddress);


    /**
     * @param add_id
     * @return void
     * @Purpose 删除收发件地址信息
     * @version 3.0
     * @author liyafei
     */
    public void deleteAddress(int add_id);

    /**
     * @param add_id
     * @Purpose 修改地址为默认地址
     * @version 3.0
     * @author lubin
     */
    public void updateDefaultAddress(int add_id);

    /**
     * @param pageDto
     * @return int
     * @Purpose 查询用户地址数量
     * @version 3.0
     * @author lubin
     */
    public int findMemberAddressCount(PageDto pageDto);

    /**
     * @param pageDto
     * @return List<TdHtMembAddressDto>
     * @Purpose 分页查询用户地址
     * @version 3.0
     * @author lubin
     */
    public List<TdHtMembAddressDto> findMemberAddress(PageDto pageDto);

    /**
     * @param add_id
     * @return TdHtMembAddressDto
     * @Purpose 通过地址id查询地址
     * @version 3.0
     * @author lubin
     */
    public TdHtMembAddressDto findMemberAddressById(int add_id);

    /**
     * @param add_id
     * @Purpose 更新用户地址发件次数
     * @version 3.0
     * @author lubin
     */
    public void updateAddressSendExpressSize(int add_id);

    /**
     * @Purpose   根据条件查询用户的收件地址数量
     * @version   3.0
     * @author    lubin
     * @time      2017-04-05
     * @param     pageDto  查询条件
     * @return    int 查询结果
     */
    public int findMembAddByBeanSize(PageDto pageDto);

    /**
     * @Purpose   根据条件查询用户的收件地址列表
     * @version   3.0
     * @author    lubin
     * @time      2017-04-05
     * @param     pageDto  查询条件
     * @return    List<TdHtMembAddressDto> 查询结果
     */
    public List<TdHtMembAddressDto> findMembAddByBeanPage(PageDto pageDto);
}
