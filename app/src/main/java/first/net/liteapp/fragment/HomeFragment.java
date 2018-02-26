package first.net.liteapp.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;

import first.net.liteapp.R;
import first.net.liteapp.activity.NewInfoActivity;
import first.net.liteapp.activity.PopularActActivity;
import first.net.liteapp.adapter.CourseLiveAdapter;
import first.net.liteapp.adapter.IntroduceAdapter;
import first.net.liteapp.adapter.NewInfoAdapter;
import first.net.liteapp.adapter.TabRvAdapter;
import first.net.liteapp.bean.CourseLiveBean;
import first.net.liteapp.bean.CycleViewBean;
import first.net.liteapp.bean.IntroduceBean;
import first.net.liteapp.bean.NewInfoBean;
import first.net.liteapp.bean.TabLiveBean;
import first.net.liteapp.constant.DataRequestResult;
import first.net.liteapp.utils.DataRequestUtils;
import first.net.liteapp.utils.GlideUtils;
import first.net.liteapp.utils.ToastUtil;
import first.net.liteapp.view.CycleViewPager;
import first.net.liteapp.view.TitleView;
import first.net.liteapp.view.VerticalTextView;


/**
 * Created by yuqiubo on 2018/2/22.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener{

    private CycleViewPager cycleViewPager;
    private List<View> views;
    private List<String> imgList = new ArrayList<>();
    private FrameLayout fl_cycleViewPager;
    private ArrayList<String> mTitleList = new ArrayList<>();
    private VerticalTextView tv_vertical;
    private ImageView iv_popular, iv_information, iv_shop_mall, iv_guess;
    private LinearLayout ll_live_more, ll_info_more;
    private RecyclerView rv_course_live, rv_introduce;
    private CourseLiveAdapter courseLiveAdapter;
    private ListView info_listview;
    private IntroduceAdapter introduceAdapter;
    private NewInfoAdapter newInfoAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        fl_cycleViewPager = view.findViewById(R.id.fl_cycleViewPager);
        tv_vertical = view.findViewById(R.id.tv_vertical);
        iv_popular = view.findViewById(R.id.iv_popular);
        iv_information = view.findViewById(R.id.iv_information);
        iv_shop_mall = view.findViewById(R.id.iv_shop_mall);
        iv_guess = view.findViewById(R.id.iv_guess);
        ll_live_more = view.findViewById(R.id.ll_live_more);
        rv_course_live = view.findViewById(R.id.rv_course_live);
        ll_info_more = view.findViewById(R.id.ll_info_more);
        info_listview = view.findViewById(R.id.info_listview);
        rv_introduce = view.findViewById(R.id.rv_introduce);
        imgList.add("http://img.hb.aicdn.com/7d523da2b02206a2469e6d9efa714e0e993b8380a2759-vNzAdE_fw658");
        imgList.add("http://img.hb.aicdn.com/304be7e36ac024383bba16f3f32e01f7408f644e7bf9a-d5k7ZW_fw658");
        imgList.add("http://img.hb.aicdn.com/7e7c9d2b1a20238cdf6c3b49659c683c189548d1b0805-kHQpwM_fw658");
        setListener();
        initCycleView();
//        getCycleView();
        initVerticalView();
        initData();
        return view;
    }

    public void setListener(){
        iv_popular.setOnClickListener(this);
        iv_information.setOnClickListener(this);
        iv_shop_mall.setOnClickListener(this);
        iv_guess.setOnClickListener(this);
        ll_live_more.setOnClickListener(this);
        ll_info_more.setOnClickListener(this);
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

    private void initVerticalView(){
        mTitleList.add("你是天上最受宠的一架钢琴");
        mTitleList.add("我是丑人脸上的鼻涕");
        mTitleList.add("你发出完美的声音");
        mTitleList.add("我被默默揩去");
        mTitleList.add("你冷酷外表下藏着诗情画意");
        mTitleList.add("我已经够胖还吃东西");
        mTitleList.add("你踏着七彩祥云离去");
        mTitleList.add("我被留在这里");
        tv_vertical.setVisibility(View.VISIBLE);
        tv_vertical.setTextList(mTitleList);
        tv_vertical.setText(14, 0, R.color.color_999999);//设置属性
        tv_vertical.setTextStillTime(2000);//设置停留时长间隔
        tv_vertical.setAnimTime(300);//设置进入和退出的时间间隔
        tv_vertical.setOnItemClickListener(new VerticalTextView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }
        });
        tv_vertical.startAutoScroll();

    }

    private void initData(){
        int center = (int) getResources().getDimension(R.dimen.px6);
        int left = (int) getResources().getDimension(R.dimen.px27);
        initCourseLive(center,left);
        initInfo();
        initIntroduce(center,left);
    }

    public void initCourseLive(final int center, final int left){
        GridLayoutManager manager=new GridLayoutManager(mContext,2);
        rv_course_live.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildLayoutPosition(view)%2==1){
                    outRect.left = center;
                    outRect.right = left;
                }
                if (parent.getChildLayoutPosition(view)%2==0){
                    outRect.right = center;
                    outRect.left = left;
                }
            }
        });
        rv_course_live.setLayoutManager(manager);
        rv_course_live.setAdapter(courseLiveAdapter= new CourseLiveAdapter(mContext));
        List<CourseLiveBean> list = new ArrayList<>();
        Random random = new Random(200);
        for (int i = 0; i < 4; i++) {
            CourseLiveBean bean = new CourseLiveBean();
            bean.setCount(random.nextInt(9000)+1000)
                    .setInfo("李爽教授亲自授课教学···")
                    .setName("李教授");
            list.add(bean);
        }
        courseLiveAdapter.addData(list);
    }

    public void initIntroduce(final int center, final int left){
        GridLayoutManager manager=new GridLayoutManager(mContext,2);
        rv_introduce.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildLayoutPosition(view)%2==1){
                    outRect.left = center;
                    outRect.right = left;
                }
                if (parent.getChildLayoutPosition(view)%2==0){
                    outRect.right = center;
                    outRect.left = left;
                }
            }
        });
        rv_introduce.setLayoutManager(manager);
        rv_introduce.setAdapter(introduceAdapter = new IntroduceAdapter(mContext));
        List<IntroduceBean> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            IntroduceBean bean = new IntroduceBean();
            if(i == 0){
                bean.setName("卓越尚尊会");
            }else{
                bean.setName("卓越国际青年领袖协会");
            }
            list.add(bean);
        }
        introduceAdapter.addData(list);
    }

    public void initInfo(){
        List<NewInfoBean> beanList = new ArrayList<>();
        info_listview.setAdapter(newInfoAdapter = new NewInfoAdapter(mContext));
        for(int i=0; i<3; i++){
            NewInfoBean bean = new NewInfoBean();
            bean.setImg("http://testimg.ibaking.com.cn/ad/5c2e9fac47734420bad2f423ca63a66b.jpg");
            bean.setId(i);
            Random random = new Random();
            int count = random.nextInt();
            bean.setCount(count);
            bean.setTitle("只要大家轻轻一按，就和失眠说拜拜");
            beanList.add(bean);
        }
        newInfoAdapter.setData(beanList);
        setListViewHeightBasedOnChildren(info_listview);
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_popular:
                PopularActActivity.startActivity(mContext,"");
                break;
            case R.id.iv_information:
                NewInfoActivity.startActivity(mContext,"");
                break;
            case R.id.iv_shop_mall:
                break;
            case R.id.iv_guess:
                break;
            case R.id.ll_live_more:
                break;
            case R.id.ll_info_more:
                break;
        }
    }

    public void getCycleView(){
        String requestUrl = "";
        Map<String,String> map = new WeakHashMap<>();
        map.put("page","1");
        map.put("pageSize","3");
        DataRequestUtils.post(requestUrl, new Gson().toJson(map), new DataRequestResult() {
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tv_vertical.stopAutoScroll();
    }
}
