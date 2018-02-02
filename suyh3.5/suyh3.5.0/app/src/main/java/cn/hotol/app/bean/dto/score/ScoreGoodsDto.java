package cn.hotol.app.bean.dto.score;

/**
 * Created by liyafei on 2016/12/13.
 */
public class ScoreGoodsDto {
    private String goods_img;//物品图片
    private String goods_name;//物品名称
    private int goods_score;//所需积分
    private int goods_state;//状态0有效1无效

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getGoods_score() {
        return goods_score;
    }

    public void setGoods_score(int goods_score) {
        this.goods_score = goods_score;
    }

    public int getGoods_state() {
        return goods_state;
    }

    public void setGoods_state(int goods_state) {
        this.goods_state = goods_state;
    }
}
