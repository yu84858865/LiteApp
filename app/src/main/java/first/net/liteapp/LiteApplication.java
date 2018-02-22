package first.net.liteapp;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.twitter.sdk.android.core.Twitter;


/**
 * Created by yuqiubo on 2018/2/9.
 */

public class LiteApplication extends Application {
    public static Context mContext;
    private static Handler mMainHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mMainHandler = new Handler(Looper.getMainLooper());
        Twitter.initialize(this);
    }

    public static Context getContext() {
        return mContext;
    }

    public static Handler getMainHandler() {
        return mMainHandler;
    }

}
