package first.net.liteapp.adapter;

import android.content.Context;
import android.content.Intent;
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
import first.net.liteapp.bean.TabLiveBean;

/**
 * Created by yuqiubo on 2018/2/2.
 */

public class TabRvAdapter extends RecyclerView.Adapter<TabRvAdapter.ViewHolder> {
    private Context mContext;
    private List<TabLiveBean> mList;

    public TabRvAdapter(Context context){
        this.mContext = context;
        this.mList = new ArrayList<>();
    }

    public void addData(List<TabLiveBean> list){
        if (list!=null){
            this.mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public TabRvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tab_rv,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TabRvAdapter.ViewHolder holder, int position) {
        TabLiveBean bean = mList.get(position);
        holder.tv_count.setText(bean.getCount()+"");
        holder.tv_name.setText(bean.getName());
        holder.tv_info.setText(bean.getInfo());
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView iv_bg;
        private TextView tv_name;
        private TextView tv_info;
        private TextView tv_count;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_bg = itemView.findViewById(R.id.iv_bg);
            iv_bg .setBackgroundResource(R.mipmap.tab_bg);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_info = itemView.findViewById(R.id.tv_info);
            tv_count = itemView.findViewById(R.id.tv_count);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mContext.startActivity(new Intent(mContext, LiveDetailActivity.class));
        }
    }
}
