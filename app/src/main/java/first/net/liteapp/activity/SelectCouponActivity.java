package first.net.liteapp.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import first.net.liteapp.R;
import first.net.liteapp.adapter.SelectCouponAdapter;
import first.net.liteapp.bean.CouponBean;

/**
 * Created by 10960 on 2018/2/25.
 */

public class SelectCouponActivity extends BaseActivity {

    private ListView lv_listview;
    private SelectCouponAdapter adapter;
    private List<CouponBean> beanList;

    @Override
    public int getContentView() {
        return R.layout.activity_select_coupon;
    }

    @Override
    protected void initView() {
        lv_listview = findViewById(R.id.lv_listview);
        adapter = new SelectCouponAdapter(this);
        beanList = new ArrayList<>();
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void setData() {
        lv_listview.setAdapter(adapter);
        for(int i=0;i<8;i++){
            CouponBean bean = new CouponBean();
            bean.setSelect(false);
            bean.setCouponPrice(20);
            beanList.add(bean);
        }
        adapter.setData(beanList);
    }

    public static void startActivity(Context context, String param){
        Intent intent = new Intent(context,SelectCouponActivity.class);
        context.startActivity(intent);
    }

}
