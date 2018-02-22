package first.net.liteapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import first.net.liteapp.R;
import first.net.liteapp.bean.CycleViewBean;
import first.net.liteapp.constant.DataRequestResult;
import first.net.liteapp.utils.DataRequestUtils;
import first.net.liteapp.utils.GlideUtils;
import first.net.liteapp.utils.ToastUtil;
import first.net.liteapp.view.CycleViewPager;
import first.net.liteapp.view.TitleView;


/**
 * Created by yuqiubo on 2018/2/22.
 */

public class HomeFragment extends BaseFragment {

    private TitleView cus_title;
    private CycleViewPager cycleViewPager;
    private List<View> views;
    private List<String> imgList = new ArrayList<>();
    private FrameLayout fl_cycleViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        fl_cycleViewPager = (FrameLayout)view.findViewById(R.id.fl_cycleViewPager);
        cus_title = (TitleView) view.findViewById(R.id.cus_title);
        cus_title.findViewById(R.id.iv_search).setVisibility(View.VISIBLE);
        cus_title.setTitle(getString(R.string.header_home));
        imgList.add("http://testimg.ibaking.com.cn/ad/699a8b70c98741a8b00b59d92f03f4ed.jpg");
        imgList.add("http://testimg.ibaking.com.cn/ad/5c2e9fac47734420bad2f423ca63a66b.jpg");
        imgList.add("http://testimg.ibaking.com.cn/ad/4d1c5d9fea1b49c5bddd98add0d0884a.jpg");
        initCycleView();
//        getCycleView();
        return view;
    }

    public void initCycleView(){
        views = new ArrayList<View>();
        views.add(getImageView(mContext, imgList.get(2), null));
        views.add(getImageView(mContext, imgList.get(0), null));
        views.add(getImageView(mContext, imgList.get(1), null));
        views.add(getImageView(mContext, imgList.get(2), null));
        views.add(getImageView(mContext, imgList.get(0), null));
        cycleViewPager = new CycleViewPager();
        FragmentManager manager = ((FragmentActivity)mContext).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fl_cycleViewPager, cycleViewPager);
        transaction.commitAllowingStateLoss();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 设置循环，在调用setData方法前调用
                cycleViewPager.setCycle(true);
                // 在加载数据前设置是否循环
                cycleViewPager.setData(views);
                //设置轮播
                cycleViewPager.setWheel(true);
                cycleViewPager.setIndicatorCenter();
                // 设置轮播时间，默认5000ms
                cycleViewPager.setTime(3000);
                // 设置初始高度为屏幕的一半高度
                cycleViewPager.getView().getLayoutParams().height = getResources()
                        .getDisplayMetrics().widthPixels / 2;
            }
        },1000);
    }

    public void getCycleView(){
        String requestUrl = "";
        Map<String,String> map = new WeakHashMap<>();
        map.put("page","1");
        map.put("pageSize","3");
        DataRequestUtils.post(mContext, requestUrl, new Gson().toJson(map), new DataRequestResult() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFailed() {
                ToastUtil.showToastOnFinish("获取图片失败");
            }
        });
    }


    public View getImageView(Context context, String path, CycleViewBean entity) {
        if (context == null) {
            context = this.getActivity();
        }
        if (context == null) {
            return null;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.cooquan_fragment_home_page_other_top, null);
        ImageView iv_cycleview = (ImageView) view.findViewById(R.id.iv_cycleview);
        if (entity != null) {
            view.setTag(entity);
        }
        GlideUtils.displayNormalImgByOther(context,path,iv_cycleview);
        return view;
    }


}
