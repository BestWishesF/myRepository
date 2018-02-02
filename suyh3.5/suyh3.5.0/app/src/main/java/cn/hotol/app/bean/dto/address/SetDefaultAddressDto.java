package cn.hotol.app.bean.dto.address;

/**
 * Created by LuBin
 * Date 2016-12-23.
 */
public class SetDefaultAddressDto {

    private int add_id;//id
    private int add_type;//类型1发件人2收件人

    public int getAdd_id() {
        return add_id;
    }

    public void setAdd_id(int add_id) {
        this.add_id = add_id;
    }

    public int getAdd_type() {
        return add_type;
    }

    public void setAdd_type(int add_type) {
        this.add_type = add_type;
    }
}
