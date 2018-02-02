package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.dict.DictInitialsSortDto;
import cn.hotol.app.bean.dto.dict.TsHtDictAddressDto;
import cn.hotol.app.bean.dto.dict.TsHtDictIndex;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liyafei on 2016/12/2.
 */
@Repository
public interface TsHtDictRepository {
    /**
     * @return List<TsHtDictIndex>
     * @Purpose 查找字典
     * @version 3.0
     * @author liyafei
     */
    public TsHtDictIndex findTsHtDictIndex(int dict_id);

    /**
     * @Purpose   通过地址名称和父节点id查询地址
     * @version   3.0
     * @author    lubin
     * @time      2017-03-25
     * @param     tsHtDictAddressDto  查询条件
     * @return    List<TsHtDictAddressDto>  查询结果
     */
    public List<TsHtDictAddressDto> findDictByParentName(TsHtDictAddressDto tsHtDictAddressDto);

    /**
     * @Purpose   通过字典名称查询字典
     * @version   3.0
     * @author    lubin
     * @time      2017-04-10
     * @param     tsHtDictAddressDto  查询条件
     * @return    List<DictInitialsSortDto>  查询结果
     */
    public List<DictInitialsSortDto> findDictByName(TsHtDictAddressDto tsHtDictAddressDto);
}
