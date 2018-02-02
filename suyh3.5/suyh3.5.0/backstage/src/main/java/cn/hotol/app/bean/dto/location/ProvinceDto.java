package cn.hotol.app.bean.dto.location;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LuBin
 * Date 2016-12-02.
 */
public class ProvinceDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int dict_id;//主标识
    private String province_name;//省名称

    private List<CityDto> city_list;//省下面的城市

    public int getDict_id() {
        return dict_id;
    }

    public void setDict_id(int dict_id) {
        this.dict_id = dict_id;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public List<CityDto> getCity_list() {
        return city_list;
    }

    public void setCity_list(List<CityDto> city_list) {
        this.city_list = city_list;
    }
}
