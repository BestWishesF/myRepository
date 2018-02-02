package cn.hotol.app.bean;

import cn.hotol.app.common.util.Page;

/**
 * 地址分页
 * Created by liyafei on 2016/12/5.
 */
public class QueryAddressPage {
    private Page page;//分页
    private int memb_id;//
    private int add_type;//
    private String limitCriterion;//限制条件

    public int getMemb_id() {
        return memb_id;
    }

    public void setMemb_id(int memb_id) {
        this.memb_id = memb_id;
    }

    public int getAdd_type() {
        return add_type;
    }

    public void setAdd_type(int add_type) {
        this.add_type = add_type;
    }

    public String getLimitCriterion() {
        return limitCriterion;
    }

    public void setLimitCriterion(String limitCriterion) {
        this.limitCriterion = limitCriterion;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

}
