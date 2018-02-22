package first.net.liteapp.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import first.net.liteapp.R;
import first.net.liteapp.adapter.TabPageAdapter;
import first.net.liteapp.view.TitleView;

/**
 * Created by yuqiubo on 2018/2/2.
 */

public class MainActivity1 extends BaseActivity {
    private Button btn_start;
    private TitleView cus_title;
    private TabLayout tlyt_tab;
    private ViewPager vp_container;
    private EditText et_roomid;
    private Button btn_watch;
    private TabPageAdapter mAdapter;
    private String[] mTabNames = {"金融", "医疗", "留学", "养生", "大师"};

    @Override
    public int getContentView() {
        return R.layout.activity_main1;
    }

    @Override
    protected void initView() {
        btn_start = findViewById(R.id.btn_start);
        btn_watch = findViewById(R.id.btn_watch);
        et_roomid = findViewById(R.id.et_roomid);
        cus_title = findViewById(R.id.cus_title);
        tlyt_tab = findViewById(R.id.tlyt_tab);
        vp_container = findViewById(R.id.vp_container);

    }

    @Override
    protected void setListener() {
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity1.this.startActivity(new Intent(MainActivity1.this, LivePublishActivity.class));
            }
        });

        btn_watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = et_roomid.getText().toString().trim();
                if (TextUtils.isEmpty(address)){
                    Toast.makeText(MainActivity1.this,"地址不能为空！",Toast.LENGTH_SHORT).show();
                }else {
                    LiveDetailActivity.startActivity(MainActivity1.this,address);
                }
            }
        });
    }

    @Override
    protected void setData() {
        cus_title.setTitle("直播课堂");
        mAdapter = new TabPageAdapter(getSupportFragmentManager(), mTabNames.length);
        tlyt_tab.setTabMode(TabLayout.MODE_FIXED);
        vp_container.setAdapter(mAdapter);
        tlyt_tab.setupWithViewPager(vp_container, false);//第二个参数autoRefresh：是否自动刷新(切换的时候是否刷PagerAdapter选中的Fragment)
        //以下是对TabLayout进行相关设置
        int tabCount = tlyt_tab.getTabCount();//获取TabLayout的个数
        for (int i = 0; i < tabCount; i++) {
            View view = View.inflate(this, R.layout.item_tab, null);
            TextView tv_tab = view.findViewById(R.id.tv_tab);
            tv_tab.setText(mTabNames[i]);
            if (i == 0) {
                tv_tab.setSelected(false);
            }
            TabLayout.Tab tab = tlyt_tab.getTabAt(i);////获取TabLayout的子元素Tab
            tab.setCustomView(tv_tab);//设置TabLayout的子元素Tab的布局View
        }
    }
}
