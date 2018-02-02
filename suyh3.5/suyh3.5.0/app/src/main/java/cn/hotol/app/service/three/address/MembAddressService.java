package cn.hotol.app.service.three.address;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.TdHtMembAddress;
import cn.hotol.app.bean.dto.address.SetDefaultAddressDto;
import cn.hotol.app.bean.dto.address.TdHtMembAddressDto;
import cn.hotol.app.bean.dto.page.PageDto;

/**
 * Created by liyafei on 2016/12/2.
 * 收发件地址管理
 */
public interface MembAddressService {
    /**
     * @param pageDto
     * @return RetInfo
     * @Purpose 根据用户id查询收发件地址信息
     * @version 3.0
     * @author liyafei
     */
    public RetInfo findAddress(PageDto pageDto, int memb_id);


    /**
     * @param tdHtMembAddress
     * @return RetInfo
     * @Purpose 根据用户id修改收发件地址信息
     * @version 3.0
     * @author liyafei
     */
    public RetInfo updateAddress(TdHtMembAddress tdHtMembAddress);

    /**
     * @param tdHtMembAddress
     * @return RetInfo
     * @Purpose 根据用户id删除收发件地址信息
     * @version 3.0
     * @author liyafei
     */
    public RetInfo deleteAddress(TdHtMembAddress tdHtMembAddress);

    /**
     * @param setDefaultAddressDto
     * @return RetInfo
     * @Purpose 设置用户的默认地址
     * @version 3.0
     * @author lubin
     */
    public RetInfo setDefault(SetDefaultAddressDto setDefaultAddressDto, String token);

    /**
     * @param tdHtMembAddress
     * @return RetInfo
     * @Purpose 保存用户地址信息
     * @version 3.0
     * @author lubin
     */
    public RetInfo saveAddress(TdHtMembAddress tdHtMembAddress, String token);

    /**
     * @param tdHtMembAddressDto
     * @return RetInfo
     * @Purpose 通过id查询用户地址信息
     * @version 3.0
     * @author lubin
     */
    public RetInfo findOneAddress(TdHtMembAddressDto tdHtMembAddressDto);

    /**
     * @param token
     * @return RetInfo
     * @Purpose 查询用户的所有发件地址
     * @version 3.0
     * @author lubin
     */
    public RetInfo findAllSendAddress(String token);

    /**
     * @Purpose   根据条件查询用户的收件地址
     * @version   3.0
     * @author    lubin
     * @time      2017-04-05
     * @param     pageDto  查询条件
     * @return    RetInfo
     */
    public RetInfo findMembAddByBean(PageDto pageDto, String token);

}
