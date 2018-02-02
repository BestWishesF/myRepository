package cn.hotol.app.service.member.address;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.address.TdHtMembAddressDto;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.repository.TdHtMembAddressRepository;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhun on 2016/12/17.
 */
public class AddressServiceImpl implements AddressService {
    private static Logger logger = Logger.getLogger(AddressServiceImpl.class);
    private TdHtMembAddressRepository tdHtMembAddressRepository;
    @Override
    public RetInfo memberAddressPage(int memb_id, int currentPage, int pageSize) {
        String logInfo = this.getClass().getName() + ":memberAddressPage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {

            int totalRecord = tdHtMembAddressRepository.findMemberAddressSize(memb_id);//总条数
            if (totalRecord > 0) {
                Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
                Map<String, Object> map = CommonUtil.page(totalRecord ,currentPage ,pageSize);
                map.put("memb_id", memb_id);
                List<TdHtMembAddressDto> tdHtMembAddressDtos = tdHtMembAddressRepository.findMemberAddressPage(map);
                for (int i= 0 ; i < tdHtMembAddressDtos.size() ; i++){
                    String province = dicts.get("" + tdHtMembAddressDtos.get(i).getAdd_province()).getCode_name();
                    String city = dicts.get("" + tdHtMembAddressDtos.get(i).getAdd_city()).getCode_name();
                    String area = dicts.get("" + tdHtMembAddressDtos.get(i).getAdd_region()).getCode_name();
                    String address = province + city + area + tdHtMembAddressDtos.get(i).getAdd_detail_address().replace(Constant.SENDER_ADDRESS_CONNECT, "");
                    tdHtMembAddressDtos.get(i).setAdd_detail_address(address);
                }
                map.put("currentPage", currentPage);
                map.put("adddress", tdHtMembAddressDtos);

                retInfo.setMark("0");
                retInfo.setTip("成功");
                retInfo.setObj(map);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("暂无您要查找的结果");
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("totalPage", 0);
                params.put("totalRecord", 0);
                params.put("currentPage", 1);
                retInfo.setObj(params);
            }

        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    public void setTdHtMembAddressRepository(TdHtMembAddressRepository tdHtMembAddressRepository) {
        this.tdHtMembAddressRepository = tdHtMembAddressRepository;
    }
}
