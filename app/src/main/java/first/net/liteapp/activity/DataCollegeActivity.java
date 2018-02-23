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
 * Created by yuqiubo on 2018/2/23.
 */

public class DataCollegeActivity extends BaseActivity {
    private ViewPager vp_container;
    private TabLayout tlyt_data;
    private TabPageAdapter mAdapter;
    private String[] mTabNames = {"音频", "视频", "文档"};

    @Override
    public int getContentView() {
        return R.layout.activity_datacollege;
    }

    @Override
    protected void initView() {
        vp_container = findViewById(R.id.vp_container);
        tlyt_data =findViewById(R.id.tlyt_data);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void setData() {
        mAdapter = new TabPageAdapter(getSupportFragmentManager(), mTabNames.length, DataCollegeFragment.class);
        tlyt_data.setTabMode(TabLayout.MODE_FIXED);
        vp_container.setAdapter(mAdapter);
        tlyt_data.setupWithViewPager(vp_container, false);//第二个参数autoRefresh：是否自动刷新(切换的时候是否刷PagerAdapter选中的Fragment)
        //以下是对TabLayout进行相关设置
        int tabCount = tlyt_data.getTabCount();//获取TabLayout的个数
        for (int i = 0; i < tabCount; i++) {
            View view = View.inflate(this, R.layout.item_tab, null);
            TextView tv_tab = view.findViewById(R.id.tv_tab);
            tv_tab.setText(mTabNames[i]);
            if (i == 0) {
                tv_tab.setSelected(false);
            }
            TabLayout.Tab tab = tlyt_data.getTabAt(i);////获取TabLayout的子元素Tab
            tab.setCustomView(tv_tab);//设置TabLayout的子元素Tab的布局View
        }
    }
}
