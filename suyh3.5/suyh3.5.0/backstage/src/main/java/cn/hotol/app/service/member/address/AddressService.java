package cn.hotol.app.service.member.address;

import cn.hotol.app.base.RetInfo;

/**
 * Created by lizhun on 2016/12/9.
 */
public interface AddressService {
    /**
     * @Purpose  分页查找用户地址
     * @version  3.0
     * @author   lizhun
     * @param    memb_id,currentPage,pageSize
     * @return   RetInfo
     */
    public RetInfo memberAddressPage(int memb_id, int currentPage, int pageSize);
}
