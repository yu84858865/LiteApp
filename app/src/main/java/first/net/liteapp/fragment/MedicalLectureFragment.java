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
import first.net.liteapp.adapter.LifeCodeAdapter;
import first.net.liteapp.bean.ActiveInfoBean;

/**
 * Created by 10960 on 2018/2/23.
 */

public class MedicalLectureFragment extends BaseFragment {

    private ListView life_listview;
    private LifeCodeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lifecode,null);
        life_listview = view.findViewById(R.id.life_listview);
        initData();
        return view;
    }

    public void initData(){
        adapter = new LifeCodeAdapter(mContext);
        life_listview.setAdapter(adapter);
        List<ActiveInfoBean> beanList = new ArrayList<>();
        for(int i=0; i<10; i++){
            ActiveInfoBean bean = new ActiveInfoBean();
            bean.setImageUrl("http://testimg.ibaking.com.cn/ad/5c2e9fac47734420bad2f423ca63a66b.jpg");
            bean.setId(i);
            Random random = new Random();
            int count = random.nextInt(10);
            bean.setPrice(count * 10);
            bean.setTitle("年终福利大放送");
            bean.setContent("抽奖赠送健康课程，我在这里等你哟");
            bean.setTime("2018/1/1至2018/2/"+count);
            beanList.add(bean);
        }
        adapter.setData(beanList);
    }

}
