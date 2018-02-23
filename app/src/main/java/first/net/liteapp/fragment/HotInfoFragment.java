package first.net.liteapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import first.net.liteapp.R;
import first.net.liteapp.adapter.HotInfoAdapter;
import first.net.liteapp.bean.HotInfoBean;

/**
 * Created by 10960 on 2018/2/23.
 * 热点资讯
 */

public class HotInfoFragment extends BaseFragment {

    private ListView hot_listview;
    private HotInfoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_info,null);
        hot_listview = view.findViewById(R.id.hot_listview);
//        initData();
        return view;
    }

    public void initData(){
        View mViewTop = LayoutInflater.from(mContext).inflate(R.layout.fragment_hot_info_head,null);
        hot_listview.addHeaderView(mViewTop);
        adapter = new HotInfoAdapter(mContext);
        hot_listview.setAdapter(adapter);
        List<HotInfoBean> beanList = new ArrayList<>();
        for(int i=0; i<10; i++){
            HotInfoBean bean = new HotInfoBean();
            bean.setImageUrl("http://testimg.ibaking.com.cn/ad/5c2e9fac47734420bad2f423ca63a66b.jpg");
            bean.setId(i);
            Random random = new Random();
            int count = random.nextInt(10);
            bean.setTitle("年终福利大放送");
            bean.setLookCount(count * 10+"");
            bean.setPraiseCount(10*count + "");
            bean.setReplyCount(10*count + "");
            beanList.add(bean);
        }
        adapter.setData(beanList);
    }

}
