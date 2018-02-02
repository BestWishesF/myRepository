package cn.hotol.app.bean.dto.coupon;

import cn.hotol.app.common.util.Page;

/**
 * Created by LuBin
 * Date 2016-12-13.
 */
public class CouponPageDto {

    private Page page;
    private int state;//状态
    private int memb_id;//用户id
    private String limit_str;//分页查询参数

    public String getLimit_str() {
        return limit_str;
    }

    public void setLimit_str(String limit_str) {
        this.limit_str = limit_str;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getMemb_id() {
        return memb_id;
    }

    public void setMemb_id(int memb_id) {
        this.memb_id = memb_id;
    }
}
