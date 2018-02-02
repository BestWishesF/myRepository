package cn.hotol.app.bean.dto.banner;

import java.io.Serializable;

/**
 * Created by lizhun on 2016/12/1.
 */
public class TsHtBannerIndex implements Serializable {
    private static final long serialVersionUID = 1L;

    private String img_name;//名称
    private String img_src;//图片地址
    private String img_link;//图片链接

    public String getImg_name() {
        return img_name;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }

    public String getImg_src() {
        return img_src;
    }

    public void setImg_src(String img_src) {
        this.img_src = img_src;
    }

    public String getImg_link() {
        return img_link;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }
}
