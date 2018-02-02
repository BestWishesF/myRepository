package cn.hotol.app.bean.dto.config;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-03-16.
 */
public class TsHtDataConfigDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String data_id;
    private String data_value;

    public String getData_id() {
        return data_id;
    }

    public void setData_id(String data_id) {
        this.data_id = data_id;
    }

    public String getData_value() {
        return data_value;
    }

    public void setData_value(String data_value) {
        this.data_value = data_value;
    }
}
