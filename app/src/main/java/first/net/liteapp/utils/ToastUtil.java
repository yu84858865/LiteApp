package first.net.liteapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import first.net.liteapp.LiteApplication;
import first.net.liteapp.R;

/**
 * 自定义Toast显示工具类
 *
 * @author Administrator
 */
@SuppressLint("InflateParams")
public class ToastUtil {
    public static void toastShort(final String msg){
        if("main".equals(Thread.currentThread().getName())){
            showToast(msg,LiteApplication.getContext());
        }else{
            // 子线程
            LiteApplication.getMainHandler().post(new Runnable() {
                @Override
                public void run() {
                    showToast(msg,LiteApplication.getContext());
                }
            });
        }
    }

    static Toast toast;

    public static void showToast(String text, Context cxt) {
        LayoutInflater mInflater = LayoutInflater.from(cxt);
        View v = mInflater.inflate(R.layout.district_include_toast, null);
        TextView tv = (TextView) v.findViewById(R.id.toast_tv_content);
        tv.setText(text);
        if (toast == null)
            toast = new Toast(LiteApplication.mContext);
        toast.setView(v);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    //设置成功
    public static void showSettingToast(String text, Context cxt) {
        LayoutInflater mInflater = LayoutInflater.from(LiteApplication.mContext);
        View v = mInflater.inflate(R.layout.district_setting_toast, null);
        TextView tv = (TextView) v.findViewById(R.id.tv_toast);
        tv.setText(text);
        Toast toast = new Toast(LiteApplication.mContext);
        toast.setView(v);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    public static void showToastOnFinish(int res) {
        LayoutInflater mInflater = LayoutInflater.from(LiteApplication.mContext);
        Toast toast;
        toast = new Toast(LiteApplication.mContext);
        View v = mInflater.inflate(R.layout.district_include_toast, null);
        TextView tv = (TextView) v.findViewById(R.id.toast_tv_content);
        toast.setView(v);
        toast.setDuration(Toast.LENGTH_SHORT);
        tv.setText(res);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToastOnFinish(String msg) {
        LayoutInflater mInflater = LayoutInflater.from(LiteApplication.mContext);
        Toast toast;
        toast = new Toast(LiteApplication.mContext);
        View v = mInflater.inflate(R.layout.district_include_toast, null);
        TextView tv = (TextView) v.findViewById(R.id.toast_tv_content);
        toast.setView(v);
        toast.setDuration(Toast.LENGTH_SHORT);
        tv.setText(msg);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
