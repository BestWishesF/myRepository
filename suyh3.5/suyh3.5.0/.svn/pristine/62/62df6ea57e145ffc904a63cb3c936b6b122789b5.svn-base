package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.banner.TsHtBannerDto;
import cn.hotol.app.bean.dto.banner.TsHtBannerIndex;

import java.util.List;
import java.util.Map;

/**
 * Created by lizhun on 2016/12/1.
 */
public interface TsHtBannerRepository {
    /**
     * @Purpose  查找显示的banner
     * @version  3.0
     * @author   lizhun
     * @return   List<TsHtBannerIndex>
     */
    public List<TsHtBannerIndex> findTsHtBannerIndex();
    /**
     * @Purpose  根据状态查找banner
     * @version  3.0
     * @author   lizhun
     * @param    user_type
     * @return   int
     */
    public int findBannerSize(int user_type);
    /**
     * @Purpose  分页根据状态查找banner
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   List<TsHtBannerDto>
     */
    public List<TsHtBannerDto> findBannerPage(Map<String, Object> map);
    /**
     * @Purpose  修改banner状态
     * @version  3.0
     * @author   lizhun
     * @param    tsHtBannerDto
     * @return   void
     */
    public void updateBannerState(TsHtBannerDto tsHtBannerDto);
    /**
     * @Purpose  修改banner
     * @version  3.0
     * @author   lizhun
     * @param    tsHtBannerDto
     * @return   void
     */
    public void updateBanner(TsHtBannerDto tsHtBannerDto);
    /**
     * @Purpose  添加banner
     * @version  3.0
     * @author   lizhun
     * @param    tsHtBannerDto
     * @return   void
     */
    public void insertBanner(TsHtBannerDto tsHtBannerDto);
    /**
     * @Purpose  根据id查找banner
     * @version  3.0
     * @author   lizhun
     * @param    banner_id
     * @return   TsHtBannerDto
     */
    public TsHtBannerDto findBannerById(int banner_id);
}
