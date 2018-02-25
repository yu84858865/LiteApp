package first.net.liteapp.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import first.net.liteapp.R;
import first.net.liteapp.utils.GlideUtils;

/**
 * Created by 10960 on 2018/2/24.
 * 支付订单
 */

public class PayOrderActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG="PayOrderActivity";
    private TextView tv_pay_order, tv_title, tv_location, tv_time, tv_price, tv_pay_money;
    private ImageView iv_photo;
    private RadioButton rb_wechat, rb_alipay, rb_credit_card, rb_paypal;
    private RadioGroup rg_radiogroup;

    @Override
    public int getContentView() {
        return R.layout.activity_pay_order;
    }

    @Override
    protected void initView() {
        tv_pay_order = findViewById(R.id.tv_pay_order);
        tv_title = findViewById(R.id.tv_title);
        tv_location = findViewById(R.id.tv_location);
        tv_time = findViewById(R.id.tv_time);
        tv_price = findViewById(R.id.tv_price);
        iv_photo = findViewById(R.id.iv_photo);
        tv_pay_money = findViewById(R.id.tv_pay_money);
        rb_wechat = findViewById(R.id.rb_wechat);
        rb_alipay = findViewById(R.id.rb_alipay);
        rb_credit_card = findViewById(R.id.rb_credit_card);
        rb_paypal = findViewById(R.id.rb_paypal);
        rg_radiogroup = findViewById(R.id.rg_radiogroup);
    }

    @Override
    protected void setListener() {
        tv_pay_order.setOnClickListener(this);
        rg_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.rb_wechat:
                        Log.i(TAG,"rb_wechat------");
                        break;
                    case R.id.rb_alipay:
                        Log.i(TAG,"rb_wechat------");
                        break;
                    case R.id.rb_credit_card:
                        Log.i(TAG,"rb_wechat------");
                        break;
                    case R.id.rb_paypal:
                        Log.i(TAG,"rb_wechat------");
                        break;
                }
            }
        });
    }

    @Override
    protected void setData() {
        String imageUrl = "http://img.hb.aicdn.com/304be7e36ac024383bba16f3f32e01f7408f644e7bf9a-d5k7ZW_fw658";
        GlideUtils.displayNormalImgByOther(this,imageUrl,iv_photo);
        tv_title.setText("尊享人生，探讨生命的真谛,探讨生命的真谛");
        tv_price.setText("HKD "+120);
        tv_location.setText("香港");
        tv_time.setText("2018/1/1");
        tv_pay_money.setText("100");
    }

    public static void startActivity(Context context, String param){
        Intent intent = new Intent(context,PayOrderActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_pay_order:
                Log.i(TAG,"tv_pay_order------");
                break;
        }
    }
}
