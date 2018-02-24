package first.net.liteapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import first.net.liteapp.R;
import first.net.liteapp.utils.DataTools;

/**
 * Created by yuqiubo on 2018/2/24.
 */

public class ChatAdapter extends BaseAdapter {
    private List<String> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ChatAdapter(Context context) {
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
            itemView = new ItemView();
            convertView = View.inflate(mContext,R.layout.item_chat_lv,null);
            itemView.tv_chat = convertView.findViewById(R.id.tv_chat);
            convertView.setTag(itemView);
        } else {
            itemView = (ItemView) convertView.getTag();
        }
        itemView.tv_chat.setText(mList.get(position));
        return convertView;
    }

    class ItemView {
        public TextView tv_chat;
    }

}
