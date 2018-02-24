package first.net.liteapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import first.net.liteapp.R;
import first.net.liteapp.activity.CourseDetailActivity;
import first.net.liteapp.activity.LiveDetailActivity;
import first.net.liteapp.bean.NewCollegeBean;

/**
 * Created by yuqiubo on 2018/2/2.
 */

public class NewCollegeAdapter extends RecyclerView.Adapter<NewCollegeAdapter.ViewHolder> {
    private Context mContext;
    private List<NewCollegeBean> mList;

    public NewCollegeAdapter(Context context) {
        this.mContext = context;
        this.mList = new ArrayList<>();
    }

    public void addData(List<NewCollegeBean> list) {
        if (list != null) {
            this.mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public NewCollegeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_newcollege_rv, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewCollegeAdapter.ViewHolder holder, int position) {
        NewCollegeBean bean = mList.get(position);
        holder.tv_info.setText(bean.getInfo());
        holder.tv_area.setText(bean.getArea());
        holder.tv_time.setText(bean.getTime());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView iv_img;
        private TextView tv_info;
        private TextView tv_time;
        private TextView tv_area, tv_course_detail;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_info = itemView.findViewById(R.id.tv_info);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_area = itemView.findViewById(R.id.tv_area);
            tv_course_detail = itemView.findViewById(R.id.tv_course_detail);
            itemView.setOnClickListener(this);
            tv_course_detail.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_course_detail:
                    CourseDetailActivity.startActivity(mContext,"",true);
                    break;
                default:
                    LiveDetailActivity.startActivity(mContext, "");
                    break;
            }


        }
    }
}
