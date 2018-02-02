package cn.hotol.app.bean.dto.article;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by LuBin
 * Date 2016-12-03.
 */
public class TsHtArticleBeanDto implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer arti_id;//文章id
    @NotNull(message = "文章标题1-32个字符")
    @Length(min = 1,max = 32, message = "文章标题1-32个字符")
    private String arti_name;//名称
    private Integer menu_id;//菜单id
    @NotNull(message = "文章内容1-20000个字符")
    @Length(min = 1,max = 20000, message = "文章内容1-20000个字符")
    private String arti_content;//内容
    private Integer arti_sort;//排序
    private Integer is_valid;//是否显示 0是1否
    private String creater;//创建者
    private Timestamp cret_date;//创建日期
    private String modifier;//最后修改者
    private Timestamp mod_date;//修改日期
    public int getArti_id() {
        return arti_id;
    }

    public void setArti_id(int arti_id) {
        this.arti_id = arti_id;
    }

    public String getArti_name() {
        return arti_name;
    }

    public void setArti_name(String arti_name) {
        this.arti_name = arti_name;
    }

    public String getArti_content() {
        return arti_content;
    }

    public void setArti_content(String arti_content) {
        this.arti_content = arti_content;
    }

    public int getArti_sort() {
        return arti_sort;
    }

    public void setArti_sort(int arti_sort) {
        this.arti_sort = arti_sort;
    }

    public void setArti_id(Integer arti_id) {
        this.arti_id = arti_id;
    }

    public Integer getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(Integer menu_id) {
        this.menu_id = menu_id;
    }

    public void setArti_sort(Integer arti_sort) {
        this.arti_sort = arti_sort;
    }

    public Integer getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(Integer is_valid) {
        this.is_valid = is_valid;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Timestamp getCret_date() {
        return cret_date;
    }

    public void setCret_date(Timestamp cret_date) {
        this.cret_date = cret_date;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Timestamp getMod_date() {
        return mod_date;
    }

    public void setMod_date(Timestamp mod_date) {
        this.mod_date = mod_date;
    }
}
