package first.net.liteapp.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import first.net.liteapp.fragment.BaseFragment;
import first.net.liteapp.fragment.DataCollegeFragment;
import first.net.liteapp.fragment.TabFragment;

public class TabPageAdapter extends FragmentPagerAdapter {
    private int mCount;
    public Class mClass;

    public TabPageAdapter(FragmentManager fm, int count, Class c) {
        super(fm);
        mCount = count;
        mClass = c;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        BaseFragment fragment = null;
        if (mClass.getSimpleName().equals(TabFragment.class.getSimpleName())){
            fragment = new TabFragment();
        }else if (mClass.getSimpleName().equals(DataCollegeFragment.class.getSimpleName())){
            fragment = new DataCollegeFragment();
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

}
