package first.net.liteapp.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import first.net.liteapp.R;
import first.net.liteapp.adapter.ActPageAdapter;
import first.net.liteapp.fragment.LifeCodeFragment;
import first.net.liteapp.fragment.MedAmeFragment;
import first.net.liteapp.fragment.MedicalLectureFragment;
import first.net.liteapp.utils.GlideUtils;

/**
 * Created by 10960 on 2018/2/23.
 * 热门活动
 */

public class PopularActActivity extends BaseActivity {

    private ImageView iv_bg;
    private TabLayout popular_tab;
    private ViewPager popular_vp;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.activity_popular;
    }

    @Override
    protected void initView() {
        iv_bg = findViewById(R.id.iv_bg);
        popular_tab = findViewById(R.id.popular_tab);
        popular_vp = findViewById(R.id.popular_vp);
        String url = "http://testimg.ibaking.com.cn/ad/5c2e9fac47734420bad2f423ca63a66b.jpg";
        GlideUtils.displayNormalImgByOther(this,url,iv_bg);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void setData() {
        initViewPager();
    }

    public void initViewPager(){
        fragmentList.add(new LifeCodeFragment());
        fragmentList.add(new MedicalLectureFragment());
        fragmentList.add(new MedAmeFragment());
        ActPageAdapter adapter = new ActPageAdapter(getSupportFragmentManager(),fragmentList);
        popular_vp.setAdapter(adapter);
        popular_vp.setCurrentItem(0);
        popular_tab.addTab(popular_tab.newTab());
        popular_tab.addTab(popular_tab.newTab());
        popular_tab.addTab(popular_tab.newTab());
        popular_tab.setupWithViewPager(popular_vp);
        popular_tab.getTabAt(0).setText(getResources().getString(R.string.lifecode));
        popular_tab.getTabAt(1).setText(getResources().getString(R.string.medicallecture));
        popular_tab.getTabAt(2).setText(getResources().getString(R.string.medame));
        popular_tab.setTabMode(TabLayout.MODE_FIXED);
    }

    public static void startActivity(Context context, String address) {
        Intent intent = new Intent(context, PopularActActivity.class);
        intent.putExtra("address", address);
        context.startActivity(intent);
    }


}
