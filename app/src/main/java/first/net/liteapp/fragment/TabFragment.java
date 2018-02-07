package first.net.liteapp.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import first.net.liteapp.R;
import first.net.liteapp.adapter.TabRvAdapter;
import first.net.liteapp.bean.TabLiveBean;

/**
 * Created by yuqiubo on 2018/2/2.
 */

public class TabFragment extends Fragment {
    private Context mContext;
    private RecyclerView rv_content;
    private TabRvAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab,container,false);
        rv_content = view.findViewById(R.id.rv_content);
        mContext = getActivity();
        init();
        return view;
    }

    private void init() {
        GridLayoutManager manager=new GridLayoutManager(mContext,2);
        rv_content.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildLayoutPosition(view)%2==1){
                    outRect.left = 12;
                }
                if (parent.getChildLayoutPosition(view)%2==0){
                    outRect.right = 12;
                }
            }
        });
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter= new TabRvAdapter(mContext));

        List<TabLiveBean> list = new ArrayList<>();
        Random random = new Random(200);
        for (int i = 0; i < 5; i++) {
            TabLiveBean bean = new TabLiveBean();
            bean.setCount(random.nextInt(9000)+1000)
                    .setInfo("李爽教授亲自授课教学···")
                    .setName("李教授");
            list.add(bean);
        }
        mAdapter.addData(list);
    }


}
