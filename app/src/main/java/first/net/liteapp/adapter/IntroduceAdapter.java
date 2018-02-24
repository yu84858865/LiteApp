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
import first.net.liteapp.activity.IntroduceWebActivity;
import first.net.liteapp.activity.LiveDetailActivity;
import first.net.liteapp.bean.CourseLiveBean;
import first.net.liteapp.bean.IntroduceBean;

/**
 * Created by yuqiubo on 2018/2/2.
 */

public class IntroduceAdapter extends RecyclerView.Adapter<IntroduceAdapter.ViewHolder> {
    private Context mContext;
    private List<IntroduceBean> mList;

    public IntroduceAdapter(Context context){
        this.mContext = context;
        this.mList = new ArrayList<>();
    }

    public void addData(List<IntroduceBean> list){
        if (list!=null){
            this.mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public IntroduceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_introduce_rv,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IntroduceAdapter.ViewHolder holder, int position) {
        IntroduceBean bean = mList.get(position);
        holder.tv_name.setText(bean.getName());
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView iv_bg;
        private TextView tv_name;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_bg = itemView.findViewById(R.id.iv_bg);
            iv_bg .setBackgroundResource(R.mipmap.tab_bg);
            tv_name = itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String title = tv_name.getText().toString();
            IntroduceWebActivity.startActivity(mContext,title);
        }
    }
}
