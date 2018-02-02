package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.courier.CourierDto;
import cn.hotol.app.bean.dto.express.region.TsHtExpressOpenRegionDto;

import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-03.
 */
public interface TsHtExpressOpenRegionRepository {

    /**
     * @Purpose
     * @version  3.0
     * @author   lizhun
     * @return   List<CourierDto>
     */
    public List<CourierDto> findExpressOpenRegion();
    /**
     * @Purpose  根据快递公司查找所有的快递公司开通区域数量
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   int
     */
    public int findExpOpenRegionByExpIdSize(Map<String, Object> map);
    /**
     * @Purpose  根据快递公司查找所有的快递公司开通区域分页
     * @version  3.0
     * @author   lizhun
     * @param    map
     * @return   List<TsHtExpressOpenRegionDto>
     */
    public List<TsHtExpressOpenRegionDto> findExpOpenRegionByExpIdPage(Map<String, Object> map);
    /**
     * @Purpose  添加快递公司开通区域
     * @version  3.0
     * @author   lizhun
     * @param    tsHtExpressOpenRegionDto
     * @return   void
     */
    public void insertExpOpenRegion(TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto);
    /**
     * @Purpose  修改快递公司开通区域
     * @version  3.0
     * @author   lizhun
     * @param    tsHtExpressOpenRegionDto
     * @return   void
     */
    public void updateExpOpenRegion(TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto);
    /**
     * @Purpose  根据相关信息查找快递开通区域
     * @version  3.0
     * @author   lizhun
     * @param    tsHtExpressOpenRegionDto
     * @return   TsHtExpressOpenRegionDto
     */
    public TsHtExpressOpenRegionDto findExpOpenRegionByBean(TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto);

    /**
     * @Purpose 查找所有的开通区域
     * @version  3.0
     * @author   lubin
     * @return   List<CourierDto>
     */
    public List<CourierDto> findAllExpressOpenRegion();

    /**
     * @param eoa_id
     * @Purpose 根据id查询开通区域
     * @version  3.0
     * @author   lubin
     * @return   TsHtExpressOpenRegion
     */
    public TsHtExpressOpenRegionDto findExpOpenRegByEoaId(int eoa_id);

    /**
     * @Purpose  查询该城市开通寄件服务区
     * @version  3.0
     * @author   lubin
     * @param    tsHtExpressOpenRegionDto
     * @return   List<TsHtExpressOpenRegionDto>
     */
    public List<TsHtExpressOpenRegionDto> findRegOpenNumByCity(TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto);

    /**
     * @Purpose  修改开通区域信息
     * @version  3.0
     * @author   lubin
     * @param    tsHtExpressOpenRegionDto
     * @return
     */
    public void updateRegOpenInfoById(TsHtExpressOpenRegionDto tsHtExpressOpenRegionDto);
}
