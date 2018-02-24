package first.net.liteapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import first.net.liteapp.R;
import first.net.liteapp.activity.LiveDetailActivity;
import first.net.liteapp.bean.NewCollegeBean;
import first.net.liteapp.bean.NewInfoBean;

/**
 * Created by yuqiubo on 2018/2/2.
 */

public class LawCollegeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<NewCollegeBean> mDoneList;
    private List<NewInfoBean> mUndoneList;
    private int mFlag;

    public LawCollegeAdapter(Context context) {
        this.mContext = context;
        this.mDoneList = new ArrayList<>();
        this.mUndoneList = new ArrayList<>();
    }

    public void addDoneData(List<NewCollegeBean> list) {
        if (list != null) {
            mFlag = 0;
            this.mDoneList.addAll(list);
        }
    }

    public void addUndoneData(List<NewInfoBean> list) {
        if (list != null) {
            mFlag = 1;
            this.mUndoneList.addAll(list);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        if (mFlag == 0) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_lawcollege_done, parent, false);
            viewHolder = new DoneViewHolder(view);
        }else if (mFlag == 1) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_lawcollege_undone, parent, false);
            viewHolder = new UndoneViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mFlag == 0){
            NewCollegeBean newCollegeBean = mDoneList.get(position);
            DoneViewHolder doneViewHolder = (DoneViewHolder) holder;
            doneViewHolder.tv_question.setText("问：要是投资理财产品，钱都没了怎么办");
            doneViewHolder.tv_name.setText("张律师");
            doneViewHolder.tv_duty.setText("擅长领域：金融、民事");
            doneViewHolder.tv_answer.setText(newCollegeBean.getInfo());
        }else if (mFlag == 1){
            NewInfoBean newInfoBean = mUndoneList.get(position);
            UndoneViewHolder undoneViewHolder = (UndoneViewHolder) holder;
            undoneViewHolder.tv_question.setText(newInfoBean.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        if (mFlag == 0) {
            return mDoneList == null ? 0 : mDoneList.size();
        } else if (mFlag == 1) return mUndoneList == null ? 0 : mUndoneList.size();
        return 0;
    }

    public class DoneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_question;
        private ImageView iv_icon;
        private TextView tv_name;
        private TextView tv_duty;
        private TextView tv_answer;

        public DoneViewHolder(View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.iv_img);
            tv_question = itemView.findViewById(R.id.tv_question);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_duty = itemView.findViewById(R.id.tv_duty);
            tv_answer = itemView.findViewById(R.id.tv_answer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public class UndoneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_question;

        public UndoneViewHolder(View itemView) {
            super(itemView);
            tv_question = itemView.findViewById(R.id.tv_question);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
