package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.number.TsHtOpenNumberDto;
import org.springframework.stereotype.Repository;

/**
 * Created by LuBin
 * Date 2017-01-14.
 */

@Repository
public interface TsHtOpenNumberRepository {

    /**
     * @param ht_number
     * @return TsHtOpenNumberDto
     * @Purpose 根据厚通单号查询数据库
     * @version 3.0
     * @author lubin
     */
    public TsHtOpenNumberDto findOpenNumberByHtNumber(String ht_number);

    /**
     * @param tsHtOpenNumberDto
     * @Purpose 更新厚通单号状态
     * @version 3.0
     * @author lubin
     */
    public void updateOpenNumberStateByOpenId(TsHtOpenNumberDto tsHtOpenNumberDto);

}
