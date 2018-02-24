package first.net.liteapp.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import first.net.liteapp.R;
import first.net.liteapp.adapter.InfoPageAdapter;
import first.net.liteapp.fragment.HotInfoFragment;
import first.net.liteapp.fragment.IndustryFragment;

/**
 * Created by 10960 on 2018/2/23.
 * 最新资讯
 */

public class InfoActivity extends BaseActivity {


    private TabLayout info_tab;
    private ViewPager info_vp;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.activity_info;
    }

    @Override
    protected void initView() {
        info_tab = findViewById(R.id.info_tab);
        info_vp = findViewById(R.id.info_vp);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void setData() {
        initViewPager();
    }

    public void initViewPager(){
        fragmentList.add(new HotInfoFragment());
        fragmentList.add(new IndustryFragment());
        InfoPageAdapter adapter = new InfoPageAdapter(getSupportFragmentManager(),fragmentList);
        info_vp.setAdapter(adapter);
        info_vp.setCurrentItem(0);
        info_tab.addTab(info_tab.newTab());
        info_tab.addTab(info_tab.newTab());
        info_tab.addTab(info_tab.newTab());
        info_tab.setupWithViewPager(info_vp);
        info_tab.getTabAt(0).setText(getResources().getString(R.string.hotinformation));
        info_tab.getTabAt(1).setText(getResources().getString(R.string.industry));
        info_tab.setTabMode(TabLayout.MODE_FIXED);
    }

    public static void startActivity(Context context, String address) {
        Intent intent = new Intent(context, InfoActivity.class);
        intent.putExtra("address", address);
        context.startActivity(intent);
    }


}
