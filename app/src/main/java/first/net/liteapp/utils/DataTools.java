package first.net.liteapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DataTools {
	/**
	 * dip转为 px
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 *  px 转为 dip
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}


	public  static  int getScreenWith(Context mCxt){
		DisplayMetrics metric = new DisplayMetrics();
		WindowManager wm = (WindowManager) mCxt.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metric);
		return  metric.widthPixels;
	}
	public  static  int getScreenHight(Context mCxt){
		DisplayMetrics metric = new DisplayMetrics();
		WindowManager wm = (WindowManager) mCxt.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metric);
		return  metric.heightPixels;
	}

	/**
	 * 通过反射，获取包含虚拟键的整体屏幕高度
	 *
	 * @return
	 */
	public static int getHasVirtualKey(Activity mCxt) {
		int dpi = 0;
		Display display = mCxt.getWindowManager().getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		@SuppressWarnings("rawtypes")
		Class c;
		try {
			c = Class.forName("android.view.Display");
			@SuppressWarnings("unchecked")
			Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
			method.invoke(display, dm);
			dpi = dm.heightPixels;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dpi;
	}

}
