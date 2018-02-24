package first.net.liteapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import first.net.liteapp.R;

/**
 * Created by yuqiubo on 2018/2/24.
 */

public class RankingAdapter extends BaseAdapter {
    private List<String> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public RankingAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mList = new ArrayList<>();
    }

    public void setData(List<String> list) {
        if (list != null) {
            this.mList.clear();
            this.mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ItemView itemView;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_ranking_lv, null);
            itemView = new ItemView(convertView);
            convertView.setTag(itemView);
        } else {
            itemView = (ItemView) convertView.getTag();
        }
        itemView.iv_head.setBackgroundResource(R.mipmap.ic_launcher);
        itemView.tv_name.setText("李叫兽");
        itemView.tv_honor.setText("百度副总裁");
        itemView.tv_rank.setText("NO."+position+1);
        return convertView;
    }

    public static class ItemView {
        public ImageView iv_head;
        public TextView tv_name;
        public TextView tv_honor;
        public TextView tv_rank;

        public ItemView(View rootView) {
            this.iv_head = rootView.findViewById(R.id.iv_head);
            this.tv_name =  rootView.findViewById(R.id.tv_name);
            this.tv_honor = rootView.findViewById(R.id.tv_honor);
            this.tv_rank = rootView.findViewById(R.id.tv_rank);
        }

    }
}
