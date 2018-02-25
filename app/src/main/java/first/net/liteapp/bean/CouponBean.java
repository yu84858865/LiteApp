package first.net.liteapp.bean;

import java.io.Serializable;

/**
 * Created by 10960 on 2018/2/25.
 */

public class CouponBean implements Serializable{

    private int couponPrice;
    private int id;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(int couponPrice) {
        this.couponPrice = couponPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
