package cn.hotol.app.service.location;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.location.ProvinceDto;
import cn.hotol.app.bean.dto.location.TsHtDictList;

import java.util.List;

/**
 * Created by LuBin
 * Date 2016-12-02.
 */
public interface LocationService {

    /**
     * @Purpose  查找省市区信息
     * @version  3.0
     * @author   lubin
     * @return   List<ProvinceDto>
     */
    public List<ProvinceDto> findAllProvincialCity();
    /**
     * @Purpose  根据类别查找数据
     * @version  3.0
     * @author   lizhun
     * @param    code_type,currentPage,pageSize
     * @return   RetInfo
     */
    public RetInfo dictByTypePage(String code_type, int currentPage, int pageSize);
    /**
     * @Purpose  根据父id查找数据
     * @version  3.0
     * @author   lizhun
     * @param    parent_id,currentPage,pageSize
     * @return   RetInfo
     */
    public RetInfo dictByParentIdPage(int parent_id,String code_type, int currentPage, int pageSize);
    /**
     * @Purpose  添加数据
     * @version  3.0
     * @author   lizhun
     * @param    tsHtDictList
     * @return   RetInfo
     */
    public RetInfo insertDict(TsHtDictList tsHtDictList);
    /**
     * @Purpose  修改数据
     * @version  3.0
     * @author   lizhun
     * @param    tsHtDictList
     * @return   RetInfo
     */
    public RetInfo updateDict(TsHtDictList tsHtDictList);
    /**
     * @Purpose  根据id查找数据
     * @version  3.0
     * @author   lizhun
     * @param    dict_id
     * @return   TsHtDictList
     */
    public TsHtDictList findDictById(int dict_id);
    /**
     * @Purpose  根据省id查找市信息
     * @version  3.0
     * @author   lizhun
     * @param    parent_id
     * @return   RetInfo
     */
    public RetInfo findCityByProvinceId(int parent_id);
    /**
     * @Purpose  根据市id查找区信息
     * @version  3.0
     * @author   lizhun
     * @param    parent_id
     * @return   RetInfo
     */
    public RetInfo findAreaByCityId(int parent_id);
}
