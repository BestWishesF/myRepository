package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.sendhis.TwSmsSendHisDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-06.
 */
@Repository
public interface TwSmsSendHisRepository {
    /**
     * @Purpose  查找短信发送数量
     * @version  3.0
     * @author   lizhun
     * @param
     * @return   int
     */
    public int findSendHisSize();
    /**
     * @Purpose  分页查找短信发送记录
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   List<TwSmsSendHisDto>
     */
    public List<TwSmsSendHisDto> findSendHisPage(Map<String, Object> map);

    /**
     * @Purpose  保存短信发送记录
     * @version  3.0
     * @author   lubin
     * @param    twSmsSendHisDto
     * @return   List<TwSmsSendHisDto>
     */
    public void insertSMSRecord(TwSmsSendHisDto twSmsSendHisDto);

}
