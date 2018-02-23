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
import java.util.zip.Inflater;

import first.net.liteapp.R;
import first.net.liteapp.bean.NewInfoBean;
import first.net.liteapp.utils.GlideUtils;

/**
 * Created by 10960 on 2018/2/23.
 */

public class NewInfoAdapter extends BaseAdapter{

    private Context mContext;
    private List<NewInfoBean> beanList;

    public NewInfoAdapter(Context context){
        this.mContext = context;
        beanList = new ArrayList<>();
    }

    public void setData(List<NewInfoBean> list){
        if(list != null){
            beanList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.home_new_info,null);
            holder = new ViewHolder();
            holder.iv_img = view.findViewById(R.id.iv_img);
            holder.tv_count = view.findViewById(R.id.tv_count);
            holder.tv_title = view.findViewById(R.id.tv_title);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        NewInfoBean bean = beanList.get(position);
        if(bean != null){
            GlideUtils.displayNormalImgByOther(mContext,bean.getImg(),holder.iv_img);
            holder.tv_title.setText(bean.getTitle());
            holder.tv_count.setText("人气 "+bean.getCount());
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    @Override
    public int getCount() {
        return beanList == null? 0 : beanList.size();
    }

    @Override
    public NewInfoBean getItem(int i) {
        return beanList==null? null:beanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return beanList.get(i).getId();
    }

    class ViewHolder{
        private ImageView iv_img;
        private TextView tv_title, tv_count;
    }


}
