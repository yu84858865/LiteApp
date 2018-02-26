package first.net.liteapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import first.net.liteapp.R;
import first.net.liteapp.bean.CommentBean;
import first.net.liteapp.bean.CouponBean;
import first.net.liteapp.utils.GlideUtils;
import first.net.liteapp.view.CircularImage;

/**
 * Created by 10960 on 2018/2/25.
 */

public class CourseCommentAdapter extends BaseAdapter {

    private Context mContext;
    private List<CommentBean> beanList;

    public CourseCommentAdapter(Context context){
        this.mContext = context;
        beanList = new ArrayList<>();
    }

    public void setData(List<CommentBean> list){
        if(list != null){
            beanList.clear();
            beanList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return beanList==null?0:beanList.size();
    }

    @Override
    public CommentBean getItem(int i) {
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
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_course_comment,null);
            holder.tv_name = view.findViewById(R.id.tv_name);
            holder.tv_comment = view.findViewById(R.id.tv_comment);
            holder.ci_img = view.findViewById(R.id.ci_img);
            holder.tv_praise = view.findViewById(R.id.tv_praise);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        final CommentBean bean = getItem(position);
        if(bean != null){
            GlideUtils.displayNormalImgByOther(mContext,bean.getHeadImg(),holder.ci_img);
            holder.tv_name.setText(bean.getName());
            holder.tv_comment.setText(bean.getComment());
            holder.tv_praise.setText(bean.getPraiseCount());
        }
        return view;
    }

    class ViewHolder{
        private CircularImage ci_img;
        private TextView tv_name, tv_comment, tv_praise;
    }


}
