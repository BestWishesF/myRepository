package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.dict.DictInitialsSortDto;
import cn.hotol.app.bean.dto.dict.TsHtDictIndex;
import cn.hotol.app.bean.dto.location.*;

import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-02.
 */

public interface TsHtDictRepository {

    /**
     * @Purpose  查找所有的省份
     * @version  3.0
     * @author   lubin
     * @return   List<ProvinceDto>
     */
    public List<ProvinceDto> findAllProvince();

    /**
     * @Purpose  根据省份查询省份下的城市
     * @version  3.0
     * @author   lubin
     * @param dict_id
     * @return   List<CityDto>
     */
    public List<CityDto> findCityByProvince(int dict_id);

    /**
     * @Purpose  根据城市查询城市下的区
     * @version  3.0
     * @author   lubin
     * @param dict_id
     * @return   List<AreaDto>
     */
    public List<AreaDto> findAreaByCity(int dict_id);

    /**
     * @Purpose  根据字典类别查询字典数据
     * @version  3.0
     * @author   lubin
     * @param code_type
     * @return   List<AreaDto>
     */
    public List<TsHtDictDto> findDictByType(String code_type);

    /**
     * @Purpose  根据区域id查询快递公司列表
     * @version  3.0
     * @author   lubin
     * @return   List<TsHtDictDto>
     */
    public List<TsHtDictDto> findExpressByRegion(int region_id);
    /**
     * @Purpose  查询所有现实的静态数据
     * @version  3.0
     * @author   lubin
     * @return   List<TsHtDictDto>
     */
    public List<TsHtDictDto> findDicts();

    /**
     * @Purpose  根据类别查找数据数量
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   int
     */
    public int findDictByTypeSize(Map<String, Object> map);
    /**
     * @Purpose  根据类别分页查找数据
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   List<TsHtDictList>
     */
    public List<TsHtDictList> findDictByTypePage(Map<String, Object> map);
    /**
     * @Purpose  根据父id查找数据数量
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   int
     */
    public int findDictByParentIdize(Map<String, Object> map);
    /**
     * @Purpose  根据父id分页查找数据
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   List<TsHtDictList>
     */
    public List<TsHtDictList> findDictByParentIdPage(Map<String, Object> map);
    /**
     * @Purpose  添加数据
     * @version  3.0
     * @author   lizhun
     * @param    tsHtDictList
     * @return   void
     */
    public void insertDict(TsHtDictList tsHtDictList);
    /**
     * @Purpose  修改数据
     * @version  3.0
     * @author   lizhun
     * @param    tsHtDictList
     * @return   void
     */
    public void updateDict(TsHtDictList tsHtDictList);
    /**
     * @Purpose  根据id查找数据
     * @version  3.0
     * @author   lizhun
     * @param    dict_id
     * @return   TsHtDictList
     */
    public TsHtDictList findDictById(int dict_id);

    /**
     * @Purpose  根据名称查询数据
     * @version  3.0
     * @author   lubin
     * @param    tsHtDictList
     * @return   TsHtDictList
     */
    public List<TsHtDictList> findDictByName(TsHtDictList tsHtDictList);

    /**
     * @Purpose  根据字典父节点查询数据
     * @version  3.0
     * @author   lubin
     * @param    parent_id
     * @return   List<TsHtDictDto>
     */
    public List<TsHtDictDto> findDictByParent(int parent_id);

    /**
     * @Purpose   查询所有的字典数据的id、名称和父id
     * @version   3.0
     * @author    lubin
     * @time      2017-04-01
     * @return    List<TsHtDictIndex>  查询结果
     */
    public List<TsHtDictIndex> findAllDictIndex();

    /**
     * @Purpose   查询字典按首字母排序
     * @version   3.0
     * @author    lubin
     * @time      2017-04-08
     * @return    List<DictInitialsSortDto>  查询结果
     */
    public List<DictInitialsSortDto> findAllDictByInitials(Map<String, Object> params);
}
