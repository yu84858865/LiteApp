package first.net.liteapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import first.net.liteapp.R;

/**
 * Created by yuqiubo on 2018/2/22.
 */

public class HomeFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView=new TextView(mContext);
        textView.setText(mContext.getString(R.string.bottom_home));
        return textView;
    }
}
