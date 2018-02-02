package cn.hotol.app.bean;

import java.sql.Timestamp;

/**
 * Created by LuBin
 * Date 2016-12-03.
 */
public class TsHtArticle {

    private int arti_id;//主标识
    private String arti_name;//文字名称
    private int menu_id;//文章归属
    private String arti_content;//文章内容
    private int arti_sort;//文章排序
    private int is_valid;//0：可用1：不可用,非零即可表示为不可用
    private String creater;//创建人
    private Timestamp cret_time;//创建时间
    private String modifierI;//修改人
    private Timestamp mod_time;//修改时间

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

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
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

    public int getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(int is_valid) {
        this.is_valid = is_valid;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Timestamp getCret_time() {
        return cret_time;
    }

    public void setCret_time(Timestamp cret_time) {
        this.cret_time = cret_time;
    }

    public String getModifierI() {
        return modifierI;
    }

    public void setModifierI(String modifierI) {
        this.modifierI = modifierI;
    }

    public Timestamp getMod_time() {
        return mod_time;
    }

    public void setMod_time(Timestamp mod_time) {
        this.mod_time = mod_time;
    }
}
