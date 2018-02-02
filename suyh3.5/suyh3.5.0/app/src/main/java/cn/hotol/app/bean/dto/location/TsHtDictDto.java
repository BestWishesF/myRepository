package cn.hotol.app.bean.dto.location;

import java.io.Serializable;

/**
 * Created by LuBin
 * Date 2016-12-02.
 */
public class TsHtDictDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int dict_id;//主标识
    private String code_name;//名称
    private String dict_img;//字典图片
    private String dict_code;//字典编码

    public int getDict_id() {
        return dict_id;
    }

    public void setDict_id(int dict_id) {
        this.dict_id = dict_id;
    }

    public String getCode_name() {
        return code_name;
    }

    public void setCode_name(String code_name) {
        this.code_name = code_name;
    }

    public String getDict_img() {
        return dict_img;
    }

    public void setDict_img(String dict_img) {
        this.dict_img = dict_img;
    }

    public String getDict_code() {
        return dict_code;
    }

    public void setDict_code(String dict_code) {
        this.dict_code = dict_code;
    }
}
