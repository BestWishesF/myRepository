package cn.hotol.app.bean.dto.admin.divide;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017-03-08.
 */
public class DivideDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int divide_id;//划分区域id
    private int region_id;//所属区域id
    private String divide_name;//划分区域名称
    private int divide_type;//划分区域类型（）

    private List<Double> lngs;//经度集合
    private List<Double> lats;//纬度集合

    public int getDivide_id() {
        return divide_id;
    }

    public void setDivide_id(int divide_id) {
        this.divide_id = divide_id;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public String getDivide_name() {
        return divide_name;
    }

    public void setDivide_name(String divide_name) {
        this.divide_name = divide_name;
    }

    public int getDivide_type() {
        return divide_type;
    }

    public void setDivide_type(int divide_type) {
        this.divide_type = divide_type;
    }

    public List<Double> getLngs() {
        return lngs;
    }

    public void setLngs(List<Double> lngs) {
        this.lngs = lngs;
    }

    public List<Double> getLats() {
        return lats;
    }

    public void setLats(List<Double> lats) {
        this.lats = lats;
    }
}
