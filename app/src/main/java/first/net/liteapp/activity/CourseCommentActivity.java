package first.net.liteapp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import first.net.liteapp.R;
import first.net.liteapp.adapter.CourseCommentAdapter;
import first.net.liteapp.bean.CommentBean;
import first.net.liteapp.dialog.CommentDialog;
import first.net.liteapp.view.TitleView;

/**
 * Created by 10960 on 2018/2/25.
 * 课程评论和活动评论
 */

public class CourseCommentActivity extends BaseActivity implements CommentDialog.CommentSubmitListener{

    private static final String TAG="CourseCommentActivity";
    private TitleView tv_titleview;
    private ListView lv_listview;
    private TextView tv_comment;
    private List<CommentBean> beanList;
    private CourseCommentAdapter adapter;
    private boolean isFromCourse;

    @Override
    public int getContentView() {
        return R.layout.activity_course_comment;
    }

    @Override
    protected void initView() {
        tv_titleview = findViewById(R.id.tv_titleview);
        lv_listview = findViewById(R.id.lv_listview);
        tv_comment = findViewById(R.id.tv_comment);
        beanList = new ArrayList<>();
        adapter = new CourseCommentAdapter(this);
    }

    @Override
    protected void setListener() {
        tv_comment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                CommentDialog commentDialog = new CommentDialog(CourseCommentActivity.this,true);
                commentDialog.registCommentListener(CourseCommentActivity.this);
                commentDialog.show();
            }
        });
    }

    @Override
    protected void setData() {
        Intent intent = getIntent();
        if(intent != null){
            isFromCourse = intent.getBooleanExtra("isFromCourse",false);
        }
        if(isFromCourse){
            tv_titleview.setTitle(getResources().getString(R.string.course_comment));
        }else{
            tv_titleview.setTitle(getResources().getString(R.string.activity_comment));
        }
        lv_listview.setAdapter(adapter);
        for(int i=0;i<2;i++){
            CommentBean bean = new CommentBean();
            bean.setHeadImg("http://img.hb.aicdn.com/e2eee499941b05e6c3d8971c3465f673bdeec45c35b2b-U1nU8R_fw658");
            bean.setName("流年似水");
            bean.setPraiseCount("30");
            bean.setComment("讲的太精彩了，给个赞");
            beanList.add(bean);
        }
        adapter.setData(beanList);
    }

    public static void startActivity(Context context, String param, boolean isFromCourse){
        Intent intent = new Intent(context,CourseCommentActivity.class);
        intent.putExtra("isFromCourse",isFromCourse);
        context.startActivity(intent);
    }

    @Override
    public void onSubmit(String comment) {
        Log.e(TAG,"comment = "+comment);
        CommentBean bean = new CommentBean();
        bean.setHeadImg("http://img.hb.aicdn.com/e2eee499941b05e6c3d8971c3465f673bdeec45c35b2b-U1nU8R_fw658");
        bean.setName("提莫队长");
        bean.setPraiseCount("30");
        bean.setComment(comment);
        beanList.add(bean);
        adapter.setData(beanList);
    }

    @Override
    public void onCancel() {

    }
}
