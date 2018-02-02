package cn.hotol.app.bean.dto.dict;

/**
 * Created by LuBin
 * Date 2016-12-02.
 */
public class TsHtDictAddressDto {

    private int dict_id;//主标示--单纯主键使用，自增长字段
    private String code_type;//字典类型--比如：province省city市area区express_category快件类别express_ask快件要求
    private String code_name;//名称--如：浙江省
    private int parent_id;//父标示--数据字段中数据存在上下级的关系
    private int dict_sort;//表示在二级标示中的位置，前台输出数据的顺序
    private int status;//0：可用 1：不可用,非零即可表示为不可用
    private String dict_img;//字典图片
    private String dict_code;//字典编码

    public int getDict_id() {
        return dict_id;
    }

    public void setDict_id(int dict_id) {
        this.dict_id = dict_id;
    }

    public String getCode_type() {
        return code_type;
    }

    public void setCode_type(String code_type) {
        this.code_type = code_type;
    }

    public String getCode_name() {
        return code_name;
    }

    public void setCode_name(String code_name) {
        this.code_name = code_name;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getDict_sort() {
        return dict_sort;
    }

    public void setDict_sort(int dict_sort) {
        this.dict_sort = dict_sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
