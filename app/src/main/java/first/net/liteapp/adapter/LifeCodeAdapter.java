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
import first.net.liteapp.bean.ActiveInfoBean;
import first.net.liteapp.utils.GlideUtils;

/**
 * Created by 10960 on 2018/2/23.
 */

public class LifeCodeAdapter extends BaseAdapter {

    private Context mContext;
    private List<ActiveInfoBean> beanList;

    public LifeCodeAdapter(Context context){
        this.mContext = context;
        beanList = new ArrayList<>();
    }

    public void setData(List<ActiveInfoBean> beans){
        this.beanList.addAll(beans);
    }

    @Override
    public int getCount() {
        return beanList==null? 0:beanList.size();
    }

    @Override
    public ActiveInfoBean getItem(int i) {
        return beanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return beanList.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_lifecode,null);
            holder = new ViewHolder();
            holder.iv_photo = view.findViewById(R.id.iv_photo);
            holder.tv_content = view.findViewById(R.id.tv_content);
            holder.tv_time = view.findViewById(R.id.tv_time);
            holder.tv_title = view.findViewById(R.id.tv_title);
            holder.tv_price = view.findViewById(R.id.tv_price);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        ActiveInfoBean bean = beanList.get(position);
        if(bean != null){
            GlideUtils.displayNormalImgByOther(mContext,bean.getImageUrl(),holder.iv_photo);
            holder.tv_title.setText(bean.getTitle());
            holder.tv_content.setText(bean.getContent());
            holder.tv_time.setText(bean.getTime());
            holder.tv_price.setText(bean.getPrice() + "");
        }
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }


    private class ViewHolder{
        private TextView tv_title, tv_content, tv_price, tv_time;
        private ImageView iv_photo;
    }

}
