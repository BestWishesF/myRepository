package cn.hotol.app.repository;

import cn.hotol.app.bean.TdHtExpressSearch;
import cn.hotol.app.bean.dto.logistics.TdHtExpressSearchDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by LuBin
 * Date 2016-12-01.
 */

@Repository
public interface TdHtExpressSearchRepository {

    /**
     * @param tdHtExpressSearch 传入运单号、快递公司、用户id
     * @return TdHtExpressSearchDto
     * @Purpose 通过运单号、快递公司、用户id查询查件记录
     * @version 3.0
     * @author lubin
     */
    public TdHtExpressSearch findExpressSearchByWaybill(TdHtExpressSearch tdHtExpressSearch);

    /**
     * @param tdHtExpressSearch 传入快件查询记录
     * @return
     * @Purpose 更新快件查询记录
     * @version 3.0
     * @author lubin
     */
    public void updateExpressSearch(TdHtExpressSearch tdHtExpressSearch);

    /**
     * @param tdHtExpressSearch 传入快件查询记录
     * @return TdHtExpressSearchDto
     * @Purpose 添加快件查询记录
     * @version 3.0
     * @author lubin
     */
    public void insertExpressSearch(TdHtExpressSearch tdHtExpressSearch);

    /**
     * @param memb_id
     * @return List<TdHtExpressSearch>
     * @Purpose 查询查件记录
     * @version 3.0
     * @author liyafei
     */
    public List<TdHtExpressSearchDto> findTdHtExpressSearch(int memb_id);

}
