package cn.hotol.app.bean.dto.price;

import cn.hotol.app.bean.dto.expressorder.TdHtExpressOrderCollectDto;

import java.util.List;

/**
 * Created by LuBin
 * Date 2017-02-14.
 */
public class MembCalculationPriceDto {

    private int send_province_id;
    private int express_id;
    private double exp_ord_gratuity;
    private List<TdHtExpressOrderCollectDto> collects;

    public int getSend_province_id() {
        return send_province_id;
    }

    public void setSend_province_id(int send_province_id) {
        this.send_province_id = send_province_id;
    }

    public int getExpress_id() {
        return express_id;
    }

    public void setExpress_id(int express_id) {
        this.express_id = express_id;
    }

    public List<TdHtExpressOrderCollectDto> getCollects() {
        return collects;
    }

    public void setCollects(List<TdHtExpressOrderCollectDto> collects) {
        this.collects = collects;
    }

    public double getExp_ord_gratuity() {
        return exp_ord_gratuity;
    }

    public void setExp_ord_gratuity(double exp_ord_gratuity) {
        this.exp_ord_gratuity = exp_ord_gratuity;
    }
}
