package first.net.liteapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import first.net.liteapp.R;
import first.net.liteapp.bean.DataCollegeBean;

/**
 * Created by yuqiubo on 2018/2/2.
 */

public class DataCollegeAdapter extends RecyclerView.Adapter<DataCollegeAdapter.ViewHolder> {
    private Context mContext;
    private List<DataCollegeBean> mList;
    private int mFlag;
    public static final int FLAG_AUDIO = 100;
    public static final int FLAG_DOC = 200;

    public DataCollegeAdapter(Context context,int flag) {
        this.mContext = context;
        this.mList = new ArrayList<>();
        mFlag =flag;
    }

    public void addData(List<DataCollegeBean> list) {
        if (list != null) {
            this.mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_datacollege_rv, parent, false);
        if (mFlag == FLAG_DOC){
            view.findViewById(R.id.tv_time).setVisibility(View.GONE);
            view.findViewById(R.id.iv_control).setBackgroundResource(R.mipmap.college_data_dl);
        }
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataCollegeBean bean = mList.get(position);
        holder.tv_info.setText(bean.getInfo());
        holder.tv_time.setText(bean.getTime());
        holder.tv_size.setText(bean.getSize());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_info;
        private TextView tv_time;
        private TextView tv_size;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_info = itemView.findViewById(R.id.tv_info);
            tv_size = itemView.findViewById(R.id.tv_size);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
