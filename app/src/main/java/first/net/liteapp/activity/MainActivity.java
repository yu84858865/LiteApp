package first.net.liteapp.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import first.net.liteapp.R;
import first.net.liteapp.fragment.BaseFragment;
import first.net.liteapp.fragment.CollegeFragment;
import first.net.liteapp.fragment.HomeFragment;

/**
 * Created by yuqiubo on 2018/2/22.
 */

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private FrameLayout flyt_content;
    private RadioGroup rg_bottom;
    private FragmentManager mFragmentManager;
    private int mCurrentTab = 0;
    private List<BaseFragment> mFragmentList;

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        flyt_content = findViewById(R.id.flyt_content);
        rg_bottom = findViewById(R.id.rg_bottom);
    }

    @Override
    protected void setListener() {
        rg_bottom.setOnCheckedChangeListener(this);
    }

    @Override
    protected void setData() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new CollegeFragment());
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.flyt_content, new HomeFragment(), HomeFragment.class.getSimpleName()).commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        switch (checkedId) {
            case R.id.rb_home:
                if (mCurrentTab == 0) return;
                mCurrentTab = 0;
                break;
            case R.id.rb_colloge:
                if (mCurrentTab == 1) return;
                mCurrentTab = 1;
                break;
        }
        ((RadioButton) rg_bottom.getChildAt(mCurrentTab)).setChecked(true);
        Fragment fragment = mFragmentList.get(mCurrentTab);
        fragmentTransaction.replace(R.id.flyt_content, fragment, fragment.getClass().getSimpleName()).commit();

    }
}
