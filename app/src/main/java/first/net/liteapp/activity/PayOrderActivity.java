package first.net.liteapp.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import first.net.liteapp.R;
import first.net.liteapp.utils.GlideUtils;

/**
 * Created by 10960 on 2018/2/24.
 * 支付订单
 */

public class PayOrderActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "PayOrderActivity";
    private TextView tv_pay_order, tv_title, tv_location, tv_time, tv_price, tv_pay_money;
    private ImageView iv_photo;
    private RadioButton rb_wechat, rb_alipay, rb_credit_card, rb_paypal;
    private RelativeLayout rl_wechat, rl_alipay, rl_credit_card, rl_paypal;
    private List<RadioButton> viewList;
    private int payMode;//支付方式

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
        rl_wechat = findViewById(R.id.rl_wechat);
        rl_alipay = findViewById(R.id.rl_alipay);
        rl_credit_card = findViewById(R.id.rl_credit_card);
        rl_paypal = findViewById(R.id.rl_paypal);
    }

    @Override
    protected void setListener() {
        tv_pay_order.setOnClickListener(this);
        rl_wechat.setOnClickListener(this);
        rl_alipay.setOnClickListener(this);
        rl_credit_card.setOnClickListener(this);
        rl_paypal.setOnClickListener(this);
        rb_wechat.setOnClickListener(this);
        rb_alipay.setOnClickListener(this);
        rb_credit_card.setOnClickListener(this);
        rb_paypal.setOnClickListener(this);
    }

    @Override
    protected void setData() {
        if (viewList == null) {
            viewList = new ArrayList<>();
        }
        viewList.add(rb_wechat);
        viewList.add(rb_alipay);
        viewList.add(rb_credit_card);
        viewList.add(rb_paypal);
        String imageUrl = "http://img.hb.aicdn.com/304be7e36ac024383bba16f3f32e01f7408f644e7bf9a-d5k7ZW_fw658";
        GlideUtils.displayNormalImgByOther(this, imageUrl, iv_photo);
        tv_title.setText("尊享人生，探讨生命的真谛,探讨生命的真谛");
        tv_price.setText("HKD " + 120);
        tv_location.setText("香港");
        tv_time.setText("2018/1/1");
        tv_pay_money.setText("100");
    }

    public static void startActivity(Context context, String param) {
        Intent intent = new Intent(context, PayOrderActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pay_order:
                switch (payMode){
                    case 0x00:
                        break;
                    case 0x01:
                        break;
                    case 0x02:
                        break;
                    case 0x03:
                        break;
                }
                break;
            case R.id.rl_wechat:
            case R.id.rb_wechat:
                selectRadioButton(0);
                payMode = 0x00;
                break;
            case R.id.rl_alipay:
            case R.id.rb_alipay:
                selectRadioButton(1);
                payMode = 0x01;
                break;
            case R.id.rl_credit_card:
            case R.id.rb_credit_card:
                selectRadioButton(2);
                payMode = 0x02;
                break;
            case R.id.rl_paypal:
            case R.id.rb_paypal:
                selectRadioButton(3);
                payMode = 0x03;
                break;
        }
    }

    public void selectRadioButton(int position) {
        for (int i = 0; i < viewList.size(); i++) {
            if (i == position) {
                viewList.get(position).setChecked(true);
                continue;
            }
            viewList.get(i).setChecked(false);
        }
    }


}
