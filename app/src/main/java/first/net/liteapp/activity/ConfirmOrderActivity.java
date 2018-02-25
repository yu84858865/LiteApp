package first.net.liteapp.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import first.net.liteapp.R;
import first.net.liteapp.utils.GlideUtils;

/**
 * Created by 10960 on 2018/2/24.
 * 确认订单
 */

public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener{

    private TextView  tv_coupon_count, tv_commit_order, tv_title, tv_location, tv_time, tv_price;
    private ImageView iv_right_arrow, iv_photo;
    private RelativeLayout rl_coupon;

    @Override
    public int getContentView() {
        return R.layout.activity_confirm_order;
    }

    @Override
    protected void initView() {
        tv_coupon_count = findViewById(R.id.tv_coupon_count);
        tv_commit_order = findViewById(R.id.tv_commit_order);
        tv_title = findViewById(R.id.tv_title);
        tv_location = findViewById(R.id.tv_location);
        tv_time = findViewById(R.id.tv_time);
        tv_price = findViewById(R.id.tv_price);
        iv_right_arrow = findViewById(R.id.iv_right_arrow);
        iv_photo = findViewById(R.id.iv_photo);
        rl_coupon = findViewById(R.id.rl_coupon);
    }

    @Override
    protected void setListener() {
        iv_right_arrow.setOnClickListener(this);
        tv_commit_order.setOnClickListener(this);
    }

    @Override
    protected void setData() {
        String imageUrl = "http://img.hb.aicdn.com/304be7e36ac024383bba16f3f32e01f7408f644e7bf9a-d5k7ZW_fw658";
        GlideUtils.displayNormalImgByOther(this,imageUrl,iv_photo);
        tv_title.setText("尊享人生，探讨生命的真谛,探讨生命的真谛");
        tv_price.setText("HKD "+120);
        tv_location.setText("香港");
        tv_time.setText("2018/1/1");
        tv_coupon_count.setText("20抵扣券");
    }

    public static void startActivity(Context context, String address){
        Intent intent = new Intent(context, ConfirmOrderActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_right_arrow:
                SelectCouponActivity.startActivity(this,"");
                break;
            case R.id.tv_commit_order:
                PayOrderActivity.startActivity(this,"");
                break;
        }
    }
}
