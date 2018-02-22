package first.net.liteapp.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * 能够与轮播共存的viewpager
 * 
 * @author
 *
 */
public class BaseViewPager extends ViewPager {
	private boolean scrollable = true;

	public BaseViewPager(Context context) {
		super(context);
	}

	public BaseViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 设置viewpager是否可以滚动
	 * 
	 * @param enable 滚动？
	 */
	public void setScrollable(boolean enable) {
		scrollable = enable;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (scrollable) {
			//默认行为
			return super.onInterceptTouchEvent(event);
		} else {
			//直接将事件分发给子view
			return false;
		}
	}

	public static class CycleViewPageHandler {
		/**
		 * 防止handler对activity有隐式引用，匿名内部类不会对外部类有引用
		 */
		public static class UnleakHandler extends Handler {
			private final WeakReference<Context> context;

			public UnleakHandler(Context context, Looper looper) {
				this.context = new WeakReference<Context>(context);
			}
		}
	}
	public interface CycleViewPagerIdleListener {
		void onPagerSelected(View view, int position);
	}
}