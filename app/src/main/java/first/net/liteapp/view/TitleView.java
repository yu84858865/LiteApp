package first.net.liteapp.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import first.net.liteapp.R;
import first.net.liteapp.activity.LivePublishActivity;

/**
 * Created by yuqiubo on 2018/2/2.
 */

public class TitleView extends RelativeLayout {
    private Context mContext;
    private ImageView iv_cus_back;
    private TextView tv_cus_title;
    private ImageView iv_cus_search;
    private String mTitle;
    private boolean mIsShowback;
    private boolean mIsShowSearch;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.TitleView);
        mIsShowback = ta.getBoolean(R.styleable.TitleView_isShowBack, false);
        mIsShowSearch = ta.getBoolean(R.styleable.TitleView_isShowSearch, false);
        mTitle = ta.getString(R.styleable.TitleView_title);
        ta.recycle();
        mContext = context;
        init();
    }

    private void init() {
        View view = View.inflate(mContext, R.layout.item_titleview, this);
        iv_cus_back = view.findViewById(R.id.iv_cus_back);
        tv_cus_title = view.findViewById(R.id.tv_cus_title);
        iv_cus_search = findViewById(R.id.iv_cus_search);
        iv_cus_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) mContext).finish();
            }
        });

        tv_cus_title.setText(mTitle);
        iv_cus_back.setVisibility(mIsShowback ? VISIBLE : GONE);
        iv_cus_search.setVisibility(mIsShowSearch ? VISIBLE : GONE);
    }

    public void setTitle(String title) {
        tv_cus_title.setText(title);
    }

    public void setSearchVisibility(int visibility) {
        iv_cus_back.setVisibility(visibility);
    }

    public void setBackVisibility(int visibility) {
        iv_cus_search.setVisibility(visibility);
    }

    public void setBackground(int color) {
        findViewById(R.id.rlyt_root).setBackgroundColor(color);
    }
}
