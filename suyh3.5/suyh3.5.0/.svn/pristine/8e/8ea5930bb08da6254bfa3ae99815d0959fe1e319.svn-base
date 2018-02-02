package cn.hotol.app.bean.dto.banner;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by lizhun on 2016/12/1.
 */
public class TsHtBannerDto {
    private int banner_id;//id
    @NotNull(message = "名称为1-32个字符")
    @Length(min = 1,max = 32, message = "名称为1-32个字符")
    private String img_name;//名称

    private String img_src;//图片地址
    @NotNull(message = "图片链接1-64个字符")
    @Length(min = 1,max = 64, message = "图片链接1-64个字符")
    private String img_link;//图片链接
    private int img_type;//类别1APP
    private int is_valid;//是否可用 0是1否
    private int img_sort;//排序
    @NotNull(message = "图片alt为1-32个字符")
    @Length(min = 1,max = 32, message = "名称为1-32个字符")
    private String img_alt;//图片alt

    public int getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(int banner_id) {
        this.banner_id = banner_id;
    }

    public int getImg_type() {
        return img_type;
    }

    public void setImg_type(int img_type) {
        this.img_type = img_type;
    }

    public int getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(int is_valid) {
        this.is_valid = is_valid;
    }

    public int getImg_sort() {
        return img_sort;
    }

    public void setImg_sort(int img_sort) {
        this.img_sort = img_sort;
    }

    public String getImg_alt() {
        return img_alt;
    }

    public void setImg_alt(String img_alt) {
        this.img_alt = img_alt;
    }

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
