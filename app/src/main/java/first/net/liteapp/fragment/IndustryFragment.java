package first.net.liteapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import first.net.liteapp.R;
import first.net.liteapp.adapter.HotInfoAdapter;
import first.net.liteapp.bean.HotInfoBean;
import first.net.liteapp.utils.GlideUtils;

/**
 * Created by 10960 on 2018/2/23.
 * 行业动态
 */

public class IndustryFragment extends BaseFragment {

    private ListView hot_listview;
    private HotInfoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_info,null);
        hot_listview = view.findViewById(R.id.hot_listview);
        initData();
        return view;
    }

    public void initData(){
        View mViewTop = LayoutInflater.from(mContext).inflate(R.layout.fragment_hot_info_head,null);
        TextView tv_bg_title = mViewTop.findViewById(R.id.tv_bg_title);
        tv_bg_title.setText("高血压患者长期吃药不是最好的选择");
        ImageView iv_photo = mViewTop.findViewById(R.id.iv_photo);
        String imgUrl = "http://img.hb.aicdn.com/304be7e36ac024383bba16f3f32e01f7408f644e7bf9a-d5k7ZW_fw658";
        GlideUtils.displayNormalImgByOther(mContext,imgUrl,iv_photo);
        hot_listview.addHeaderView(mViewTop);
        adapter = new HotInfoAdapter(mContext);
        hot_listview.setAdapter(adapter);
        List<HotInfoBean> beanList = new ArrayList<>();
        for(int i=0; i<10; i++){
            HotInfoBean bean = new HotInfoBean();
            bean.setImageUrl("http://img.hb.aicdn.com/7d523da2b02206a2469e6d9efa714e0e993b8380a2759-vNzAdE_fw658");
            bean.setId(i);
            Random random = new Random();
            int count = random.nextInt(13);
            bean.setTitle("年终福利大放送年终福利大放送年终福利大放送年终福利大放送");
            bean.setSkimCount(count * 15+"");
            bean.setPraiseCount(12*count + "");
            bean.setCommentCount(17*count + "");
            beanList.add(bean);
        }
        adapter.setData(beanList);
    }

}
