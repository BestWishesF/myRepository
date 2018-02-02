package cn.hotol.app.service.banner;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.banner.TsHtBannerDto;
import cn.hotol.app.bean.dto.banner.TsHtBannerIndex;

import java.util.List;

/**
 * Created by lizhun on 2016/12/1.
 */
public interface BannerService {
    /**
     * @Purpose  查找显示的banner
     * @version  3.0
     * @author   lizhun
     * @return   List<TsHtBannerIndex>
     */
    public List<TsHtBannerIndex> findTsHtBannerIndex();
    /**
     * @Purpose  分页查找banner
     * @version  3.0
     * @author   lizhun
     * @param    is_valid,currentPage,pageSize
     * @return   RetInfo
     */
    public RetInfo bannerPage(int is_valid, int currentPage, int pageSize);
    /**
     * @Purpose  更新banner状态
     * @version  3.0
     * @author   lizhun
     * @param    tsHtBannerDto
     * @return   RetInfo
     */
    public RetInfo updateBannerState(TsHtBannerDto tsHtBannerDto);
    /**
     * @Purpose  更新banner
     * @version  3.0
     * @author   lizhun
     * @param    tsHtBannerDto
     * @return   RetInfo
     */
    public RetInfo updateBanner(TsHtBannerDto tsHtBannerDto);
    /**
     * @Purpose  添加banner
     * @version  3.0
     * @author   lizhun
     * @param    tsHtBannerDto
     * @return   RetInfo
     */
    public RetInfo insertBanner(TsHtBannerDto tsHtBannerDto);
    /**
     * @Purpose  根据id查找banner
     * @version  3.0
     * @author   lizhun
     * @param    banner_id
     * @return   RetInfo
     */
    public TsHtBannerDto findBannerById(int banner_id);
}
