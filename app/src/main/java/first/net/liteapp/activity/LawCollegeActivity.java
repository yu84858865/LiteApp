package first.net.liteapp.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import first.net.liteapp.R;
import first.net.liteapp.adapter.LawCollegeAdapter;
import first.net.liteapp.adapter.NewCollegeAdapter;
import first.net.liteapp.bean.DataCollegeBean;
import first.net.liteapp.bean.NewCollegeBean;
import first.net.liteapp.bean.NewInfoBean;

/**
 * Created by yuqiubo on 2018/2/23.
 */

public class LawCollegeActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout llyt_done;
    private LinearLayout llyt_undone;
    private RecyclerView rv_law;
    private TextView tv_ask;
    private LawCollegeAdapter mDoneAdapter;
    private LawCollegeAdapter mUndoneAdapter;
    private List<NewCollegeBean> mDoneList;
    private List<NewInfoBean> mUndoneList;

    @Override
    public int getContentView() {
        return R.layout.activity_lawcollege;
    }

    @Override
    protected void initView() {
        llyt_done =findViewById(R.id.llyt_done);
        llyt_undone =findViewById(R.id.llyt_undone);
        rv_law =findViewById(R.id.rv_law);
        tv_ask = findViewById(R.id.tv_ask);
    }

    @Override
    protected void setListener() {
        llyt_done.setOnClickListener(this);
        llyt_undone.setOnClickListener(this);
        tv_ask.setOnClickListener(this);
    }

    @Override
    protected void setData() {
        mDoneList = new ArrayList<>();
        mUndoneList = new ArrayList<>();
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rv_law.setLayoutManager(manager);

        mDoneAdapter= new LawCollegeAdapter(this);
        mDoneList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            NewCollegeBean bean = new NewCollegeBean();
            bean.setTime("11111111")
                    .setInfo("李爽教授亲自授课教学···")
                    .setArea("香港");
            mDoneList.add(bean);
        }
        mDoneAdapter.addDoneData(mDoneList);
        rv_law.setAdapter(mDoneAdapter);

        mUndoneAdapter= new LawCollegeAdapter(this);
        mUndoneList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            NewInfoBean bean = new NewInfoBean();
            bean.setTitle("李爽教授hhahahhahah···");
            mUndoneList.add(bean);
        }
        mUndoneAdapter.addUndoneData(mUndoneList);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.llyt_done:
                llyt_done.getChildAt(1).setVisibility(View.VISIBLE);
                llyt_undone.getChildAt(1).setVisibility(View.INVISIBLE);
                rv_law.setAdapter(mDoneAdapter);
                mDoneAdapter.addDoneData(mDoneList);
                break;
            case R.id.llyt_undone:
                llyt_undone.getChildAt(1).setVisibility(View.VISIBLE);
                llyt_done.getChildAt(1).setVisibility(View.INVISIBLE);
                rv_law.setAdapter(mUndoneAdapter);
                mUndoneAdapter.addUndoneData(mUndoneList);
                break;
            case R.id.tv_ask:
                break;
        }
    }
}
