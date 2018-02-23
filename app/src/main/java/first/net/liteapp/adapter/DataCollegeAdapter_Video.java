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
import first.net.liteapp.bean.DataCollegeBean;

/**
 * Created by yuqiubo on 2018/2/2.
 */

public class DataCollegeAdapter_Video extends RecyclerView.Adapter<DataCollegeAdapter_Video.ViewHolder> {
    private Context mContext;
    private List<DataCollegeBean> mList;

    public DataCollegeAdapter_Video(Context context) {
        this.mContext = context;
        this.mList = new ArrayList<>();
    }

    public void addData(List<DataCollegeBean> list) {
        if (list != null) {
            this.mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_datacollege_rv_video, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataCollegeBean bean = mList.get(position);
        holder.tv_info.setText(bean.getInfo());
        holder.tv_time.setText(bean.getTime());
        holder.tv_count.setText(bean.getSize());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView iv_img;
        private TextView tv_info;
        private TextView tv_time;
        private TextView tv_count;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_info = itemView.findViewById(R.id.tv_info);
            tv_count = itemView.findViewById(R.id.tv_count);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
