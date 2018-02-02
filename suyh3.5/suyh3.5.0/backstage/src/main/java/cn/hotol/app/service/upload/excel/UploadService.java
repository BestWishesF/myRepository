package cn.hotol.app.service.upload.excel;

import cn.hotol.app.base.RetInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2017-01-24.
 */
public interface UploadService {

    /**
     * @param mapList
     * @return RetInfo
     * @Purpose 导入表格数据
     * @version 3.0
     * @author lubin
     */
    public RetInfo uploadExpOrdData(List<Map<String, Object>> mapList, String exp_ord_time, String agent_phone,String member_phone);

}
