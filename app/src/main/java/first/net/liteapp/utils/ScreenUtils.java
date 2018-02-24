package first.net.liteapp.utils;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by yuqiubo on 2018/2/24.
 */

public class ScreenUtils {
    /**
     * 设置应用全屏
     * @author lanhm
     * @date 2014年11月18日 下午5:48:30
     * @MethodName: setFullScreen
     * @return void
     */
    public static void setFullScreen(Activity context){
        context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 退出全屏
     * @author lanhm
     * @date 2014年11月18日 下午5:48:52
     * @MethodName: quitFullScreen
     * @return void
     */
    public static void quitFullScreen(Activity context){
        final WindowManager.LayoutParams attrs = context.getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context.getWindow().setAttributes(attrs);
        context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}
