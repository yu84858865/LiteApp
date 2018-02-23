package first.net.liteapp.activity;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import first.net.liteapp.R;
import first.net.liteapp.adapter.NewCollegeAdapter;
import first.net.liteapp.bean.NewCollegeBean;


/**
 * Created by yuqiubo on 2018/2/22.
 */

public class NewCollegeActivity extends BaseActivity {
    private RecyclerView rv_new;
    private NewCollegeAdapter mNewCollegeAdapter;


    @Override
    public int getContentView() {
        return R.layout.activity_newcollege;
    }

    @Override
    protected void initView() {
        rv_new =findViewById(R.id.rv_new);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void setData() {
        LinearLayoutManager manager=new LinearLayoutManager(this);
        rv_new.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildLayoutPosition(view)!=0)
                    outRect.top = (int) getResources().getDimension(R.dimen.py30);
            }
        });
        rv_new.setLayoutManager(manager);
        rv_new.setAdapter(mNewCollegeAdapter= new NewCollegeAdapter(this));

        List<NewCollegeBean> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            NewCollegeBean bean = new NewCollegeBean();
            bean.setTime("11111111")
                    .setInfo("李爽教授亲自授课教学···")
                    .setArea("香港");
            list.add(bean);
        }
        mNewCollegeAdapter.addData(list);
    }
}
