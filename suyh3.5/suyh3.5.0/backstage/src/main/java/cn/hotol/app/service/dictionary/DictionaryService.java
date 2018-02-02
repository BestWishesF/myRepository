package cn.hotol.app.service.dictionary;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.courier.CourierDto;
import cn.hotol.app.bean.dto.dict.DictInitialsSortDto;
import cn.hotol.app.bean.dto.dict.TsHtDictIndex;
import cn.hotol.app.bean.dto.location.TsHtDictDto;

import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-02.
 */
public interface DictionaryService {

    /**
     * @Purpose  根据字典类别查询字典数据
     * @version  3.0
     * @author   lubin
     * @return   Map<String,Object>
     */
    public Map<String,Object> findDictByType();

    /**
     * @Purpose  查询字典中的快递公司配置
     * @version  3.0
     * @author   lubin
     * @return   Map<Integer,Object>
     */
    public Map<Integer,Object> findOpenExpressCompany();

    /**
     * @Purpose  查询字典中的上门时间配置
     * @version  3.0
     * @author   lubin
     * @return   List<TsHtDictDto>
     */
    public List<TsHtDictDto> findCollectTime();
    /**
     * @Purpose  查询字典数据
     * @version  3.0
     * @author   lubin
     * @return   List<TsHtDictDto>
     */
    public List<TsHtDictDto> findDicts();

    /**
     * @Purpose  查询快递公司开通区域数据
     * @version  3.0
     * @author   lubin
     * @return   List<CourierDto>
     */
    public List<CourierDto> findCourierDto();

    /**
     * @Purpose  查询已经开通的快递公司列表
     * @version  3.0
     * @author   lubin
     * @return   List<CourierDto>
     */
    public List<TsHtDictDto> findOpenRegionExpCompany();

    /**
     * @Purpose   查询所有的字典数据的id、名称和父id
     * @version   3.0
     * @author    lubin
     * @time      2017-04-01
     * @return    List<TsHtDictIndex>  查询结果
     */
    public Map<String, TsHtDictIndex> findAllDictIndex();

    /**
     * @Purpose   查询字典按首字母排序
     * @version   3.0
     * @author    lubin
     * @time      2017-04-08
     * @return    Map<String, List<DictInitialsSortDto>>
     */
    public Map<String, List<DictInitialsSortDto>> findAllDictByInitials();

    /**
     * @Purpose   查找相关区域信息
     * @version   3.4.0
     * @author    lizhun
     * @time      2017-04-24
     * @return    RetInfo
     */
    public RetInfo findByAdmin(TdHtAdminDto tdHtAdminDto);
}
