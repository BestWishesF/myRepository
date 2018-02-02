package cn.hotol.app.service.three.address;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.TdHtMembAddress;
import cn.hotol.app.bean.dto.address.SetDefaultAddressDto;
import cn.hotol.app.bean.dto.address.TdHtMembAddressDto;
import cn.hotol.app.bean.dto.dict.TsHtDictIndex;
import cn.hotol.app.bean.dto.location.*;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.dto.page.PageDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.*;
import cn.hotol.app.repository.TdHtMembAddressRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liyafei on 2016/12/2.
 */
@Service
public class MembAddressServiceImpl implements MembAddressService {
    @Resource
    private TdHtMembAddressRepository tdHtMembAddressRepository;

    private static Logger logger = Logger.getLogger(MembAddressServiceImpl.class);

    /**
     * @param memb_id
     * @return List<TdHtMembAddress>
     * @Purpose 根据用户id查询收发件地址信息
     * @version 3.0
     * @author liyafei
     */
    @Override
    public RetInfo findAddress(PageDto pageDto, int memb_id) {
        String logInfo = this.getClass().getName() + ":findAddress:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Page<TdHtMembAddressDto> page = new Page<TdHtMembAddressDto>(pageDto.getPage_size(), pageDto.getPage_no());
            pageDto.setLimit_str(page.getLimitCriterion());
            pageDto.setId(memb_id);
            //获取收发件地址信息
            int count = tdHtMembAddressRepository.findMemberAddressCount(pageDto);
            List<TdHtMembAddressDto> addressList = tdHtMembAddressRepository.findMemberAddress(pageDto);

            page.setTotalCount(count);
            for (int i = 0; i < addressList.size(); i++) {
                TdHtMembAddressDto tdHtMembAddressDto = addressList.get(i);

                //若手机号为空，返回固话
                if (tdHtMembAddressDto.getAdd_mobile_phone() == null || "".equals(tdHtMembAddressDto.getAdd_mobile_phone().trim())) {
                    tdHtMembAddressDto.setAdd_mobile_phone(tdHtMembAddressDto.getAdd_telephone());
                }
                Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
                String province = dicts.get("" + tdHtMembAddressDto.getAdd_province()).getCode_name();
                tdHtMembAddressDto.setProvince(province);
                String city = dicts.get("" + tdHtMembAddressDto.getAdd_city()).getCode_name();
                tdHtMembAddressDto.setCity(city);
                String area = dicts.get("" + tdHtMembAddressDto.getAdd_region()).getCode_name();
                tdHtMembAddressDto.setRegion(area);

                if(tdHtMembAddressDto.getAdd_detail_address().indexOf(Constant.SENDER_ADDRESS_CONNECT) >= 0){
                    tdHtMembAddressDto.setAdd_street(tdHtMembAddressDto.getAdd_detail_address().split(Constant.SENDER_ADDRESS_CONNECT)[0]);
                }else {
                    tdHtMembAddressDto.setAdd_street("");
                }
                tdHtMembAddressDto.setAdd_detail_address(tdHtMembAddressDto.getAdd_detail_address().replace(Constant.SENDER_ADDRESS_CONNECT, ""));
            }

            Map<String, Object> addressMap = new HashMap<String, Object>();
            addressMap.put("total_pages", page.getTotalPages());
            addressMap.put("page_no", page.getPageNo());
            addressMap.put("items", addressList);

            retInfo.setMark("0");
            retInfo.setTip("获取数据成功");
            retInfo.setObj(addressMap);

        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }


    /**
     * @param tdHtMembAddress
     * @return RetInfo
     * @Purpose 修改收发件地址信息
     * @version 3.0
     * @author liyafei
     */
    @Transactional
    @Override
    public RetInfo updateAddress(TdHtMembAddress tdHtMembAddress) {
        String logInfo = this.getClass().getName() + ":updateAddress:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            boolean verification = true;
            String errMessage = "";
            boolean isAddressMobile = MobileOrPhoneUtil.getIsMobile(tdHtMembAddress.getAdd_mobile_phone());
            boolean isAddressPhone = MobileOrPhoneUtil.getIsPhone(tdHtMembAddress.getAdd_mobile_phone());
            if (tdHtMembAddress.getAdd_mobile_phone() == null || (!isAddressMobile && !isAddressPhone)) {
                errMessage = "请输入正确的联系方式.";
                verification = false;
            }

            if(tdHtMembAddress.getAdd_street() == null){
                tdHtMembAddress.setAdd_street("");
            }

            //从字典中获取省市区数据
            Map<String, TsHtDictIndex> dicts = (Map<String, TsHtDictIndex>) MemcachedUtils.get(MemcachedKey.DICT_INDEX_MAP);
            TsHtDictIndex province = dicts.get("" + tdHtMembAddress.getAdd_province());
            TsHtDictIndex city = dicts.get("" + tdHtMembAddress.getAdd_city());
            TsHtDictIndex region = dicts.get("" + tdHtMembAddress.getAdd_region());

            //判断省市区数据的正确性
            if(province == null){
                errMessage = "请选择正确的省市区.";
                verification = false;
            }else {
                if(city == null){
                    errMessage = "请选择正确的省市区.";
                    verification = false;
                }else {
                    if(region == null){
                        errMessage = "请选择正确的省市区.";
                        verification = false;
                    }
                    if(city.getParent_id() != province.getDict_id()){
                        errMessage = "请选择正确的省市区.";
                        verification = false;
                    }
                    if(region != null && region.getParent_id() != city.getDict_id()){
                        errMessage = "请选择正确的省市区.";
                        verification = false;
                    }
                }
            }

            if (verification) {
                if (tdHtMembAddress.getAdd_id_number() == null) {
                    tdHtMembAddress.setAdd_id_number("");
                }
                if (isAddressPhone) {
                    tdHtMembAddress.setAdd_telephone(tdHtMembAddress.getAdd_mobile_phone().replace("-", ""));
                    tdHtMembAddress.setAdd_mobile_phone("");
                } else {
                    tdHtMembAddress.setAdd_mobile_phone(tdHtMembAddress.getAdd_mobile_phone().replace("-", ""));
                    tdHtMembAddress.setAdd_telephone("");
                }
                if (tdHtMembAddress.getAdd_longitude().intValue() == 0 && tdHtMembAddress.getAdd_latitude().intValue() == 0) {
                    BaiduMapUtil baiduMapUtil = new BaiduMapUtil();
                    if(tdHtMembAddress.getAdd_street() == null){
                        tdHtMembAddress.setAdd_street("");
                    }
                    LocationDto location = baiduMapUtil.changeAddress(province.getCode_name() + city.getCode_name() + region.getCode_name() + tdHtMembAddress.getAdd_street() + tdHtMembAddress.getAdd_detail_address());
                    if (location != null) {
                        tdHtMembAddress.setAdd_longitude(location.getLongitude());
                        tdHtMembAddress.setAdd_latitude(location.getLatitude());
                    }
                }

                //判断地址是收件地址还是发件地址
                if(tdHtMembAddress.getAdd_type() == 1){
                    tdHtMembAddress.setAdd_detail_address(tdHtMembAddress.getAdd_street() + Constant.SENDER_ADDRESS_CONNECT + tdHtMembAddress.getAdd_detail_address());
                }
                tdHtMembAddress.setAdd_state(0);

                tdHtMembAddressRepository.updateAddress(tdHtMembAddress);

                tdHtMembAddress.setAdd_detail_address(tdHtMembAddress.getAdd_detail_address().replace(Constant.SENDER_ADDRESS_CONNECT, ""));
                retInfo.setMark("0");
                retInfo.setTip("修改地址成功");
                retInfo.setObj(tdHtMembAddress);
            } else {
                retInfo.setMark("1");
                retInfo.setTip(errMessage);
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param tdHtMembAddress
     * @return RetInfo
     * @Purpose 根据用户id删除收发件地址信息
     * @version 3.0
     * @author liyafei
     */
    @Override
    public RetInfo deleteAddress(TdHtMembAddress tdHtMembAddress) {
        String logInfo = this.getClass().getName() + ":deleteAddress:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtMembAddressDto tdHtMembAddressDto = tdHtMembAddressRepository.findMemberAddressById(tdHtMembAddress.getAdd_id());
            if (tdHtMembAddressDto != null) {
                tdHtMembAddressRepository.deleteAddress(tdHtMembAddressDto.getAdd_id());
                retInfo.setMark("0");
                retInfo.setTip("删除成功");
            } else {
                retInfo.setMark("-1");
                retInfo.setTip("请选择要删除的地址");
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param setDefaultAddressDto
     * @return RetInfo
     * @Purpose 设置用户的默认地址
     * @version 3.0
     * @author lubin
     */
    @Transactional
    @Override
    public RetInfo setDefault(SetDefaultAddressDto setDefaultAddressDto, String token) {
        String logInfo = this.getClass().getName() + ":setDefault:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtMembAddress tdHtMembAddress = new TdHtMembAddress();
            tdHtMembAddress.setMemb_id(tdHtMemberDto.getMemb_id());
            tdHtMembAddress.setAdd_id(setDefaultAddressDto.getAdd_id());
            tdHtMembAddress.setAdd_type(setDefaultAddressDto.getAdd_type());
            tdHtMembAddressRepository.updateDefault(tdHtMembAddress);
            tdHtMembAddressRepository.updateDefaultAddress(setDefaultAddressDto.getAdd_id());

            retInfo.setMark("0");
            retInfo.setTip("设置默认地址成功.");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param tdHtMembAddress
     * @return RetInfo
     * @Purpose 保存用户地址信息
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo saveAddress(TdHtMembAddress tdHtMembAddress, String token) {
        String logInfo = this.getClass().getName() + ":saveAddress:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);
            //用户id
            tdHtMembAddress.setMemb_id(tdHtMemberDto.getMemb_id());
            boolean verification = true;
            String errMessage = "";
            boolean isAddressMobile = MobileOrPhoneUtil.getIsMobile(tdHtMembAddress.getAdd_mobile_phone());
            boolean isAddressPhone = MobileOrPhoneUtil.getIsPhone(tdHtMembAddress.getAdd_mobile_phone());
            if (tdHtMembAddress.getAdd_mobile_phone() == null || (!isAddressMobile && !isAddressPhone)) {
                errMessage = "请输入正确的联系方式.";
                verification = false;
            }

            if(tdHtMembAddress.getAdd_street() == null){
                tdHtMembAddress.setAdd_street("");
            }

            //从字典中获取省市区数据
            Map<String, TsHtDictIndex> dicts = (Map<String, TsHtDictIndex>) MemcachedUtils.get(MemcachedKey.DICT_INDEX_MAP);
            TsHtDictIndex province = dicts.get("" + tdHtMembAddress.getAdd_province());
            TsHtDictIndex city = dicts.get("" + tdHtMembAddress.getAdd_city());
            TsHtDictIndex region = dicts.get("" + tdHtMembAddress.getAdd_region());

            //判断省市区数据的正确性
            if(province == null){
                errMessage = "请选择正确的省市区.";
                verification = false;
            }else {
                if(city == null){
                    errMessage = "请选择正确的省市区.";
                    verification = false;
                }else {
                    if(region == null){
                        errMessage = "请选择正确的省市区.";
                        verification = false;
                    }
                    if(city.getParent_id() != province.getDict_id()){
                        errMessage = "请选择正确的省市区.";
                        verification = false;
                    }
                    if(region != null && region.getParent_id() != city.getDict_id()){
                        errMessage = "请选择正确的省市区.";
                        verification = false;
                    }
                }
            }

            if (verification) {
                if (tdHtMembAddress.getAdd_id_number() == null) {
                    tdHtMembAddress.setAdd_id_number("");
                }
                if (isAddressPhone) {
                    tdHtMembAddress.setAdd_telephone(tdHtMembAddress.getAdd_mobile_phone().replace("-", ""));
                    tdHtMembAddress.setAdd_mobile_phone("");
                } else {
                    tdHtMembAddress.setAdd_mobile_phone(tdHtMembAddress.getAdd_mobile_phone().replace("-", ""));
                    tdHtMembAddress.setAdd_telephone("");
                }
                if (tdHtMembAddress.getAdd_longitude().intValue() == 0 && tdHtMembAddress.getAdd_latitude().intValue() == 0) {
                    BaiduMapUtil baiduMapUtil = new BaiduMapUtil();
                    if(tdHtMembAddress.getAdd_street() == null){
                        tdHtMembAddress.setAdd_street("");
                    }
                    LocationDto location = baiduMapUtil.changeAddress(province.getCode_name() + city.getCode_name() + region.getCode_name() + tdHtMembAddress.getAdd_street() + tdHtMembAddress.getAdd_detail_address());
                    if (location != null) {
                        tdHtMembAddress.setAdd_longitude(location.getLongitude());
                        tdHtMembAddress.setAdd_latitude(location.getLatitude());
                    }
                }
                tdHtMembAddress.setAdd_state(0);
                tdHtMembAddress.setAdd_express_size(0);
                if (tdHtMembAddress.getAdd_is_default() == 0) {
                    tdHtMembAddressRepository.updateDefault(tdHtMembAddress);
                } else {
                    tdHtMembAddress.setAdd_is_default(1);
                }

                //判断地址是收件地址还是发件地址
                if(tdHtMembAddress.getAdd_type() == 1){
                    tdHtMembAddress.setAdd_detail_address(tdHtMembAddress.getAdd_street() + Constant.SENDER_ADDRESS_CONNECT + tdHtMembAddress.getAdd_detail_address());
                }

                tdHtMembAddressRepository.insertAddress(tdHtMembAddress);
                retInfo.setMark("0");
                retInfo.setTip("保存地址成功");
                retInfo.setObj(tdHtMembAddress.getAdd_id());
            } else {
                retInfo.setMark("1");
                retInfo.setTip(errMessage);
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param tdHtMembAddressDto
     * @return RetInfo
     * @Purpose 通过id查询用户地址信息
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findOneAddress(TdHtMembAddressDto tdHtMembAddressDto) {
        String logInfo = this.getClass().getName() + ":saveAddress:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtMembAddressDto membAddressDto = tdHtMembAddressRepository.findMemberAddressById(tdHtMembAddressDto.getAdd_id());

            //从字典中获取省市区
            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            String province = dicts.get("" + membAddressDto.getAdd_province()).getCode_name();
            membAddressDto.setProvince(province);
            String city = dicts.get("" + membAddressDto.getAdd_city()).getCode_name();
            membAddressDto.setCity(city);
            String area = dicts.get("" + membAddressDto.getAdd_region()).getCode_name();
            membAddressDto.setRegion(area);

            membAddressDto.setAdd_detail_address(membAddressDto.getAdd_detail_address().replace(Constant.SENDER_ADDRESS_CONNECT, ""));

            retInfo.setMark("0");
            retInfo.setTip("地址查询成功.");
            retInfo.setObj(membAddressDto);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param token
     * @return RetInfo
     * @Purpose 查询用户的所有发件地址
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findAllSendAddress(String token) {
        String logInfo = this.getClass().getName() + ":findAllSendAddress:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtMembAddress tdHtMembAddress = new TdHtMembAddress();
            tdHtMembAddress.setMemb_id(tdHtMemberDto.getMemb_id());
            tdHtMembAddress.setAdd_type(1);
            List<TdHtMembAddressDto> membAddressDtoList = tdHtMembAddressRepository.findAddressByMember(tdHtMembAddress);
            //从字典中获取省市区
            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            for (int i = 0; i < membAddressDtoList.size(); i++) {
                TdHtMembAddressDto tdHtMembAddressDto = membAddressDtoList.get(i);

                String province = dicts.get("" + tdHtMembAddressDto.getAdd_province()).getCode_name();
                tdHtMembAddressDto.setProvince(province);
                String city = dicts.get("" + tdHtMembAddressDto.getAdd_city()).getCode_name();
                tdHtMembAddressDto.setCity(city);
                String area = dicts.get("" + tdHtMembAddressDto.getAdd_region()).getCode_name();
                tdHtMembAddressDto.setRegion(area);

                tdHtMembAddressDto.setAdd_detail_address(tdHtMembAddressDto.getAdd_detail_address().replace(Constant.SENDER_ADDRESS_CONNECT, ""));
            }
            retInfo.setMark("0");
            retInfo.setObj(membAddressDtoList);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose   根据条件查询用户的收件地址
     * @version   3.0
     * @author    lubin
     * @time      2017-04-05
     * @param     pageDto  查询条件
     * @return    RetInfo
     */
    @Override
    public RetInfo findMembAddByBean(PageDto pageDto, String token) {
        String logInfo = this.getClass().getName() + ":findMembAddByBean:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //缓存中取出登录用户
            TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);

            //查询条件

            Page<TdHtMembAddressDto> page = new Page<TdHtMembAddressDto>(pageDto.getPage_size(), pageDto.getPage_no());
            pageDto.setLimit_str(page.getLimitCriterion());
            pageDto.setId(tdHtMemberDto.getMemb_id());

            //查询用户收件地址列表
            int count = tdHtMembAddressRepository.findMembAddByBeanSize(pageDto);
            List<TdHtMembAddressDto> tdHtMembAddressDtoList = tdHtMembAddressRepository.findMembAddByBeanPage(pageDto);
            page.setTotalCount(count);

            //从字典中获取省市区
            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            for (int i = 0; i < tdHtMembAddressDtoList.size(); i++) {
                TdHtMembAddressDto tdHtMembAddressDto = tdHtMembAddressDtoList.get(i);

                String province = dicts.get("" + tdHtMembAddressDto.getAdd_province()).getCode_name();
                tdHtMembAddressDto.setProvince(province);
                String city = dicts.get("" + tdHtMembAddressDto.getAdd_city()).getCode_name();
                tdHtMembAddressDto.setCity(city);
                String area = dicts.get("" + tdHtMembAddressDto.getAdd_region()).getCode_name();
                tdHtMembAddressDto.setRegion(area);

                if("".equals(tdHtMembAddressDto.getAdd_mobile_phone().trim()) || tdHtMembAddressDto.getAdd_mobile_phone() == null){
                    tdHtMembAddressDto.setAdd_mobile_phone(tdHtMembAddressDto.getAdd_telephone());
                }
            }

            Map<String, Object> addressMap = new HashMap<String, Object>();
            addressMap.put("total_pages", page.getTotalPages());
            addressMap.put("page_no", page.getPageNo());
            addressMap.put("items", tdHtMembAddressDtoList);

            retInfo.setMark("0");
            retInfo.setTip("查询成功.");
            retInfo.setObj(addressMap);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }
}
