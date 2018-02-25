package first.net.liteapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import first.net.liteapp.R;


/**
 * 圆角ImageView
 * 
 * @author skg
 * 
 */
public class CircularImage extends ImageView {

	public CircularImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public CircularImage(Context context) {
		this(context,null);
	}

	private final RectF roundRect = new RectF();
	private float rect_adius =5;
	private final Paint maskPaint = new Paint();
	private final Paint zonePaint = new Paint();

	private void init(Context context, AttributeSet attrs) {
		maskPaint.setAntiAlias(true);
		maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		
		zonePaint.setAntiAlias(true);
		zonePaint.setColor(Color.WHITE);
		TypedArray array =	context.getResources().obtainAttributes(attrs, R.styleable.circular_imageview);
		rect_adius= array.getFloat(R.styleable.circular_imageview_rect_radius, 50);
		float density = getResources().getDisplayMetrics().density;
		rect_adius = rect_adius * density;
		array.recycle();
	}

	public void setRectAdius(float adius) {
		rect_adius = adius;
		invalidate();
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		int w = getWidth();
		int h = getHeight();
		roundRect.set(0, 0, w, h);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);
		canvas.drawRoundRect(roundRect, rect_adius, rect_adius, zonePaint);
		//
		canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG);
		super.draw(canvas);
		canvas.restore();
	}

}
