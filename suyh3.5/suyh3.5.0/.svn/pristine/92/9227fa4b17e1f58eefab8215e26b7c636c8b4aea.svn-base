package cn.hotol.app.service.three.dictionary;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.courier.CourierDto;

/**
 * Created by LuBin
 * Date 2016-12-02.
 */
public interface DictionaryService {

    /**
     * @return RetInfo
     * @Purpose 根据字典类别查询字典数据
     * @version 3.0
     * @author lubin
     * @Param code_type
     */
    public RetInfo findDictByType(String codeType);

    /**
     * @return RetInfo
     * @Purpose 获取开通快递公司数据
     * @version 3.0
     * @author lubin
     * @Param courierDto
     */
    public RetInfo findExpressCompany(CourierDto courierDto);

    /**
     * @return RetInfo
     * @Purpose 获取上门时间数据
     * @version 3.0
     * @author lubin
     * @Param code_type
     */
    public RetInfo findCollectTime(String code_type, int version);

}
