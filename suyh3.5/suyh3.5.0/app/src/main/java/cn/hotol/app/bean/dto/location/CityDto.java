package cn.hotol.app.bean.dto.location;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LuBin
 * Date 2016-12-02.
 */
public class CityDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int dict_id;//主标识
    private int province_id;//城市所属省id
    private String city_name;//城市名称

    private List<AreaDto> area_list;//城市下的区

    public int getDict_id() {
        return dict_id;
    }

    public void setDict_id(int dict_id) {
        this.dict_id = dict_id;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public List<AreaDto> getArea_list() {
        return area_list;
    }

    public void setArea_list(List<AreaDto> area_list) {
        this.area_list = area_list;
    }
}
