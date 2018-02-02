package cn.hotol.app.bean.dto.admin.divide;

import cn.hotol.app.bean.dto.admin.divide.grid.TdHtAdminDivideGridDto;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Administrator on 2017-03-03.
 */
public class TdHtAdminDivideDto {

    private int divide_id;//划分区域id
    private int region_id;//所属区域id
    @NotNull(message = "划分区域名称不能为空.")
    @Length(min = 1, max = 64, message = "划分区域名称长度在1~64之间.")
    private String divide_name;//划分区域名称
    private int divide_state;//状态：（0：无效；1有效；）
    private int divide_type;//划分区域类型（1-管理员；2-代理人；）

    private List<TdHtAdminDivideGridDto> divideGridDtoList;

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

    public int getDivide_state() {
        return divide_state;
    }

    public void setDivide_state(int divide_state) {
        this.divide_state = divide_state;
    }

    public List<TdHtAdminDivideGridDto> getDivideGridDtoList() {
        return divideGridDtoList;
    }

    public void setDivideGridDtoList(List<TdHtAdminDivideGridDto> divideGridDtoList) {
        this.divideGridDtoList = divideGridDtoList;
    }

    public int getDivide_type() {
        return divide_type;
    }

    public void setDivide_type(int divide_type) {
        this.divide_type = divide_type;
    }
}
