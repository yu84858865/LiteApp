package first.net.liteapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import first.net.liteapp.R;
import first.net.liteapp.adapter.DataCollegeAdapter;
import first.net.liteapp.adapter.DataCollegeAdapter_Video;
import first.net.liteapp.bean.DataCollegeBean;

/**
 * Created by yuqiubo on 2018/2/2.
 */

public class DataCollegeFragment extends BaseFragment {
    private RecyclerView rv_content;
    private DataCollegeAdapter mAdapter;
    private DataCollegeAdapter_Video mVideoAdapter;
    private int mFlag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab,container,false);
        rv_content = view.findViewById(R.id.rv_content);
        init();
        return view;
    }

    private void init() {
        mFlag = getArguments().getInt("position");
        LinearLayoutManager manager=new LinearLayoutManager(mContext);
        rv_content.setLayoutManager(manager);

        List<DataCollegeBean> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            DataCollegeBean bean = new DataCollegeBean();
            bean.setTime("05分01秒")
                    .setInfo("作为一个优秀的理财师该具备什么样的知识呢？")
                    .setSize("5.35M");
            list.add(bean);
        }
        if (mFlag == 0){
            mAdapter=new DataCollegeAdapter(mContext,DataCollegeAdapter.FLAG_AUDIO);
            rv_content.setAdapter(mAdapter);
            mAdapter.addData(list);
        }else if (mFlag == 1){
            mVideoAdapter=new DataCollegeAdapter_Video(mContext);
            rv_content.setAdapter(mVideoAdapter);
            mVideoAdapter.addData(list);
        }else if (mFlag == 2){
            mAdapter=new DataCollegeAdapter(mContext,DataCollegeAdapter.FLAG_DOC);
            rv_content.setAdapter(mAdapter);
            mAdapter.addData(list);
        }
    }


}
