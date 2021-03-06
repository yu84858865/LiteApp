package first.net.liteapp.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import first.net.liteapp.R;

/**
 * Created by yuqiubo on 2018/2/2.
 */

public class TitleView extends RelativeLayout {
    private Context mContext;
    private ImageView iv_back;
    private TextView tv_title;
    public TitleView(Context context) {
        this(context,null);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        View view = View.inflate(mContext, R.layout.item_titleview,this);
        iv_back = view.findViewById(R.id.iv_back);
        tv_title = view.findViewById(R.id.tv_title);
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) mContext).finish();
            }
        });
    }

    public TitleView setTitle(String title){
        tv_title.setText(title);
        return this;
    }

}
