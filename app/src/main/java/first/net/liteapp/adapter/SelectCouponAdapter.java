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
import first.net.liteapp.bean.CouponBean;

/**
 * Created by 10960 on 2018/2/25.
 */

public class SelectCouponAdapter extends BaseAdapter {

    private Context mContext;
    private List<CouponBean> beanList;

    public SelectCouponAdapter(Context context){
        this.mContext = context;
        beanList = new ArrayList<>();
    }

    public void setData(List<CouponBean> list){
        if(list != null){
            beanList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return beanList==null?0:beanList.size();
    }

    @Override
    public CouponBean getItem(int i) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_select_coupon,null);
            holder.tv_select = view.findViewById(R.id.tv_select);
            holder.tv_coupon = view.findViewById(R.id.tv_coupon);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        final CouponBean bean = getItem(position);
        if(bean != null){
            holder.tv_coupon.setText(bean.getCouponPrice()+"");
            if(bean.isSelect()){
                holder.tv_select.setBackgroundResource(R.drawable.shape_have_select);
                holder.tv_select.setTextColor(Color.parseColor("#ffffff"));
            }else{
                holder.tv_select.setBackgroundResource(R.drawable.shape_not_select);
                holder.tv_select.setTextColor(Color.parseColor("#333333"));
            }
        }
        holder.tv_select.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(bean.isSelect()){
                    bean.setSelect(false);
                }else{
                    bean.setSelect(true);
                }
                notifyDataSetChanged();
            }
        });
        return view;
    }

    class ViewHolder{
        private TextView tv_select, tv_coupon;
    }


}
