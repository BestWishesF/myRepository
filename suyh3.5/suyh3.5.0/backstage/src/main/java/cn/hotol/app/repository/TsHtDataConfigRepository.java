package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.config.TsHtDataConfigDto;

import java.util.List;

/**
 * Created by Administrator on 2017-03-16.
 */
public interface TsHtDataConfigRepository {

    /**
     * @param
     * @return List<TsHtDataConfigDto>
     * @Purpose 查询所有的数据配置
     * @version 3.0
     * @author lubin
     */
    public List<TsHtDataConfigDto> findAllDataConfig();

}
