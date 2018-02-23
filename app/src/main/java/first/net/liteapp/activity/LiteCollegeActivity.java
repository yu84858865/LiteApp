package first.net.liteapp.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import first.net.liteapp.R;
import first.net.liteapp.adapter.TabPageAdapter;
import first.net.liteapp.fragment.DataCollegeFragment;
import first.net.liteapp.fragment.TabFragment;

/**
 * Created by yuqiubo on 2018/2/2.
 */

public class LiteCollegeActivity extends BaseActivity {
    private TabLayout tlyt_tab;
    private ViewPager vp_container;
    private TabPageAdapter mAdapter;
    private String[] mTabNames = {"金融", "医疗", "留学", "养生", "大师"};

    @Override
    public int getContentView() {
        return R.layout.activity_litecollege;
    }

    @Override
    protected void initView() {
        tlyt_tab = findViewById(R.id.tlyt_tab);
        vp_container = findViewById(R.id.vp_container);

    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void setData() {
        mAdapter = new TabPageAdapter(getSupportFragmentManager(), mTabNames.length, TabFragment.class);
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
