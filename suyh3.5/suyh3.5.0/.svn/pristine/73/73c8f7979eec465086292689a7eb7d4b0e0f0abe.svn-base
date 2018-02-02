package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.express.number.TsHtExpressOpenNumberDto;

import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2017-01-06.
 */
public interface TsHtExpressOpenNumberRepository {

    /**
     * @param tsHtExpressOpenNumberDtoList
     * @Purpose 批量插入快递单号
     * @version  3.0
     * @author   lubin
     * @return
     */
    public void batchInsertExpressNumber(List<TsHtExpressOpenNumberDto> tsHtExpressOpenNumberDtoList);

    /**
     * @param tsHtExpressOpenNumberDto
     * @Purpose 查询一个可以使用的快递单号
     * @version  3.0
     * @author   lubin
     * @return TsHtExpressOpenNumberDto
     */
    public TsHtExpressOpenNumberDto findCanUseExpressNumber(TsHtExpressOpenNumberDto tsHtExpressOpenNumberDto);

    /**
     * @param tsHtExpressOpenNumberDto
     * @Purpose 修改快递单号状态
     * @version  3.0
     * @author   lubin
     * @return
     */
    public void updateExpressNumberState(TsHtExpressOpenNumberDto tsHtExpressOpenNumberDto);

    /**
     * @Purpose  根据快递公司查找所有的快递公司单号数量
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   int
     */
    public int findExpOpenNumberByExpIdSize(Map<String, Object> map);

    /**
     * @Purpose  根据快递公司查找所有的快递公司单号分页
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   List<TsHtExpressOpenNumberDto>
     */
    public List<TsHtExpressOpenNumberDto> findExpOpenNumberByExpIdPage(Map<String, Object> map);

}
