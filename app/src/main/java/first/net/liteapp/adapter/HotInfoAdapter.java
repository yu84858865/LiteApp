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
import first.net.liteapp.bean.HotInfoBean;
import first.net.liteapp.utils.GlideUtils;

/**
 * Created by 10960 on 2018/2/23.
 */

public class HotInfoAdapter extends BaseAdapter {

    private Context mContext;
    private List<HotInfoBean> beanList;

    public HotInfoAdapter(Context context){
        this.mContext = context;
        beanList = new ArrayList<>();
    }

    public void setData(List<HotInfoBean> beans){
        this.beanList.addAll(beans);
    }

    @Override
    public int getCount() {
        return beanList==null? 0:beanList.size();
    }

    @Override
    public HotInfoBean getItem(int i) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_hot_info,null);
            holder = new ViewHolder();
            holder.iv_photo = view.findViewById(R.id.iv_photo);
            holder.tv_skim = view.findViewById(R.id.tv_skim);
            holder.tv_praise_count = view.findViewById(R.id.tv_praise_count);
            holder.tv_title = view.findViewById(R.id.tv_title);
            holder.tv_comment = view.findViewById(R.id.tv_comment);
            holder.iv_praise = view.findViewById(R.id.iv_praise);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        HotInfoBean bean = beanList.get(position);
        if(bean != null){
            GlideUtils.displayNormalImgByOther(mContext,bean.getImageUrl(),holder.iv_photo);
            holder.tv_title.setText(bean.getTitle());
            holder.tv_comment.setText(bean.getCommentCount());
            holder.tv_praise_count.setText(bean.getPraiseCount());
            holder.tv_skim.setText(bean.getSkimCount());
        }
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }


    private class ViewHolder{
        private TextView tv_title, tv_skim, tv_praise_count, tv_comment;
        private ImageView iv_photo, iv_praise;
    }

}
