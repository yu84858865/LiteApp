package first.net.liteapp;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;


/**
 * Created by yuqiubo on 2018/2/9.
 */

public class LiteApplication extends Application {
    private static Context mContext;
    private static Handler mMainHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mMainHandler = new Handler(Looper.getMainLooper());
    }

    public static Context getContext() {
        return mContext;
    }

    public static Handler getMainHandler() {
        return mMainHandler;
    }

}
