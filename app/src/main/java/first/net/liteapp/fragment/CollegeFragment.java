package first.net.liteapp.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import first.net.liteapp.R;
import first.net.liteapp.activity.DataCollegeActivity;
import first.net.liteapp.activity.LiteCollegeActivity;
import first.net.liteapp.activity.NewCollegeActivity;
import first.net.liteapp.adapter.TabRvAdapter;
import first.net.liteapp.bean.TabLiveBean;

/**
 * Created by yuqiubo on 2018/2/22.
 */

public class CollegeFragment extends BaseFragment implements View.OnClickListener {
    private TextView tv_new;
    private TextView tv_lite;
    private TextView tv_data;
    private TextView tv_law;
    private RecyclerView rv_onlite;
    private TabRvAdapter mOnliteAdapter;
    private RecyclerView rv_prelite;
    private TabRvAdapter mPreliteAdapter;
    private RecyclerView rv_beflite;
    private TabRvAdapter mBefliteAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_college, container, false);
        initView(view);
        setListener();
        initData();
        return view;
    }

    private void initView(View view) {
        tv_new = view.findViewById(R.id.tv_new);
        tv_lite = view.findViewById(R.id.tv_lite);
        tv_data = view.findViewById(R.id.tv_data);
        tv_law = view.findViewById(R.id.tv_law);
        rv_onlite = view.findViewById(R.id.rv_onlite);
        rv_prelite = view.findViewById(R.id.rv_prelite);
        rv_beflite = view.findViewById(R.id.rv_beflite);
    }


    private void setListener() {
        tv_new.setOnClickListener(this);
        tv_lite.setOnClickListener(this);
        tv_data.setOnClickListener(this);
        tv_law.setOnClickListener(this);
    }

    private void initData(){
        int center = (int) getResources().getDimension(R.dimen.px6);
        int left = (int) getResources().getDimension(R.dimen.px27);
        initOnLite(center,left);
        initPreLite(center,left);
        initBefLite(center,left);
    }

    private void initOnLite(final int center, final int left) {
        GridLayoutManager manager=new GridLayoutManager(mContext,2);
        rv_onlite.addItemDecoration(new RecyclerView.ItemDecoration() {
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
        rv_onlite.setLayoutManager(manager);
        rv_onlite.setNestedScrollingEnabled(false);
        rv_onlite.setAdapter(mOnliteAdapter= new TabRvAdapter(mContext));

        List<TabLiveBean> list = new ArrayList<>();
        Random random = new Random(200);
        for (int i = 0; i < 4; i++) {
            TabLiveBean bean = new TabLiveBean();
            bean.setCount(random.nextInt(9000)+1000)
                    .setInfo("李爽教授亲自授课教学···")
                    .setName("李教授");
            list.add(bean);
        }
        mOnliteAdapter.addData(list);
    }

    private void initPreLite(final int center, final int left) {
        GridLayoutManager manager=new GridLayoutManager(mContext,2);
        rv_prelite.addItemDecoration(new RecyclerView.ItemDecoration() {
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
        rv_prelite.setLayoutManager(manager);
        rv_prelite.setNestedScrollingEnabled(false);
        rv_prelite.setAdapter(mPreliteAdapter= new TabRvAdapter(mContext));

        List<TabLiveBean> list = new ArrayList<>();
        Random random = new Random(200);
        for (int i = 0; i < 5; i++) {
            TabLiveBean bean = new TabLiveBean();
            bean.setCount(random.nextInt(9000)+1000)
                    .setInfo("李爽教授亲自授课教学···")
                    .setName("李教授");
            list.add(bean);
        }
        mPreliteAdapter.addData(list);
    }

    private void initBefLite(final int center, final int left) {
        GridLayoutManager manager=new GridLayoutManager(mContext,2);
        rv_beflite.addItemDecoration(new RecyclerView.ItemDecoration() {
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
        rv_beflite.setLayoutManager(manager);
        rv_beflite.setNestedScrollingEnabled(false);
        rv_beflite.setAdapter(mBefliteAdapter= new TabRvAdapter(mContext));

        List<TabLiveBean> list = new ArrayList<>();
        Random random = new Random(200);
        for (int i = 0; i < 5; i++) {
            TabLiveBean bean = new TabLiveBean();
            bean.setCount(random.nextInt(9000)+1000)
                    .setInfo("李爽教授亲自授课教学···")
                    .setName("李教授");
            list.add(bean);
        }
        mBefliteAdapter.addData(list);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_new:
                startActivity(new Intent(mContext, NewCollegeActivity.class));
                break;
            case R.id.tv_lite:
                startActivity(new Intent(mContext, LiteCollegeActivity.class));
                break;
            case R.id.tv_data:
                startActivity(new Intent(mContext, DataCollegeActivity.class));
                break;
            case R.id.tv_law:
                break;
        }
    }
}
