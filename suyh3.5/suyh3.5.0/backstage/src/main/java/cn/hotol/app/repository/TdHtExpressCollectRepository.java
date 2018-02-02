package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.express.ExpOrdCollectDetailDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderCollectDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by liyafei on 2016/12/1.
 */
@Repository
public interface TdHtExpressCollectRepository {
    /**
     * @Purpose  根据订单id查找收货人数量
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   int
     */
    public int findExpressCollectByExpOrdIdSize(Map<String, Object> map);
    /**
     * @Purpose  根据订单id查找收货人分页
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   List<TdHtExpressOrderCollectDto>
     */
    public List<TdHtExpressOrderCollectDto> findExpressCollectByExpOrdIdPage(Map<String, Object> map);

    /**
     * @Purpose  通过订单id查询快件信息
     * @version  3.0
     * @author   lubin
     * @param    exp_ord_id
     * @return   List<TdHtExpressOrderCollectDto>
     */
    public List<TdHtExpressOrderCollectDto> findExpressCollectByExpOrdId(int exp_ord_id);

    /**
     * @Purpose  根据厚通单号查询快件信息
     * @version  3.0
     * @author   lubin
     * @param    ht_number
     * @return
     */
    public TdHtExpressOrderCollectDto findExpressCollectByHtNumber(String ht_number);

    /**
     * @Purpose  保存快件
     * @version  3.0
     * @author   lubin
     * @param    tdHtExpressOrderCollectDto
     * @return
     */
    public void insertExpressOrderCollect(TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto);

    /**
     * @Purpose  修改订单快件状态
     * @version  3.0
     * @author   lubin
     * @param    tdHtExpressOrderCollectDto
     * @return
     */
    public void updateExpOrdCltState(TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto);

    /**
     * @Purpose  通过快件id查询快件信息
     * @version  3.0
     * @author   lubin
     * @param    exp_ord_clt_id
     * @return TdHtExpressOrderCollectDto
     */
    public TdHtExpressOrderCollectDto findExpOrdCltById(Integer exp_ord_clt_id);

    /**
     * @Purpose  修改订单快件信息
     * @version  3.0
     * @author   lubin
     * @param    tdHtExpressOrderCollectDto
     * @return
     */
    public void updateExpOrdCltInfoById(TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto);

    /**
     * @Purpose  按条件查询快件数量
     * @version  3.0
     * @author   lubin
     * @param    map
     * @return Integer
     */
    public Integer searchOrdBillSize(Map<String, Object> map);

    /**
     * @Purpose  按条件查询快件列表
     * @version  3.0
     * @author   lubin
     * @param    map
     * @return List<Map<String, Object>>
     */
    public List<Map<String, Object>> searchOrdBillList(Map<String, Object> map);

    /**
     * @Purpose  通过订单id查询快件信息
     * @version  3.0
     * @author   lubin
     * @param    exp_ord_id
     * @return List<ExpOrdCollectDetailDto>
     */
    public List<ExpOrdCollectDetailDto> findExpOrdCollectDetailDtoByExpOrdId(int exp_ord_id);

    /**
     * @Purpose  查询大客户快件数量
     * @version  3.0
     * @author   lubin
     * @param    exp_ord_id
     * @return int
     */
    public int findCltByStateAndId(int exp_ord_id);

    /**
     * @Purpose  修改大客户快件状态
     * @version  3.0
     * @author   lubin
     * @param    tdHtExpressOrderCollectDto
     * @return
     */
    public void updateCltById(TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto);

}
