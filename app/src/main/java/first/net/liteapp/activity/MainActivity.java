package first.net.liteapp.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tencent.open.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import first.net.liteapp.R;
import first.net.liteapp.constant.DataRequestResult;
import first.net.liteapp.fragment.BaseFragment;
import first.net.liteapp.fragment.CollegeFragment;
import first.net.liteapp.fragment.HomeFragment;
import first.net.liteapp.utils.DataRequestUtils;
import first.net.liteapp.utils.ToastUtil;

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
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        for (int i = 0; i < mFragmentList.size(); i++) {
            fragmentTransaction.add(R.id.flyt_content, mFragmentList.get(i), mFragmentList.get(i).getClass().getSimpleName());
            fragmentTransaction.hide(mFragmentList.get(i));
        }
        fragmentTransaction.show(mFragmentList.get(0));
        fragmentTransaction.commit();
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
        for (int i = 0; i < rg_bottom.getChildCount(); i++) {
            if (mCurrentTab == i) {
                fragmentTransaction.show(mFragmentList.get(i));
            } else fragmentTransaction.hide(mFragmentList.get(i));
        }
        fragmentTransaction.commit();

    }
}
