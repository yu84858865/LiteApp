package first.net.liteapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.support.design.widget.*;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import first.net.liteapp.R;
import first.net.liteapp.adapter.LivePageAdapter;
import first.net.liteapp.fragment.AuthorFragment;
import first.net.liteapp.fragment.ChatFragment;
import first.net.liteapp.fragment.GetGiftFragment;
import first.net.liteapp.fragment.RankingFragment;
import first.net.liteapp.utils.DataTools;
import first.net.liteapp.utils.ScreenUtils;
import first.net.liteapp.utils.ToastUtil;
import first.net.liteapp.view.TitleView;

/**
 * Created by 10960 on 2018/2/2.
 */

public class LiveDetailActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llyt_root;
    private LinearLayout llyt_reply;
    private EditText et_reply;
    private TXCloudVideoView video_tx;
    private TXLivePlayer mTxLivePlayer;
    private ImageView iv_fullscreen;
    private int mCurrentRenderRotation;
    private RelativeLayout rlyt_player;
    private LinearLayout llyt_bottom;
    private TitleView cus_title;
    private TabLayout tlyt_tab;
    private ViewPager vp_tab;
    private LinearLayout llyt_chat;
    private ImageView iv_flower;
    private TextView tv_reply;
    private TextView tv_give;
    private TextView tv_send;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private ImageView iv_videoback;
    private TextView tv_catagory;
    private String mAddress = null;
    private TXLivePlayConfig mPlayConfig;
    private int mPlayType;
    private String mTabNames[] = {"聊天", "主播", "排行榜", "获得礼物"};


    public static void startActivity(Context context, String address) {
        Intent intent = new Intent(context, LiveDetailActivity.class);
        intent.putExtra("address", address);
        context.startActivity(intent);
    }

    @Override
    protected void initFeature() {
        super.initFeature();
        mAddress = getIntent().getStringExtra("address");
        if (null == mAddress || "".equals(mAddress)) {
            mAddress = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
        } else {
            if (!checkPlayUrl(mAddress)) {
                finish();
            }
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_live_detail;
    }

    @Override
    protected void initView() {
        llyt_root = findViewById(R.id.llyt_root);
        llyt_reply = findViewById(R.id.llyt_reply);
        et_reply = findViewById(R.id.et_reply);
        cus_title = findViewById(R.id.cus_title);
        video_tx = findViewById(R.id.video_tx);
        iv_fullscreen = findViewById(R.id.iv_fullscreen);
        rlyt_player = findViewById(R.id.rlyt_player);
        tlyt_tab = findViewById(R.id.tlyt_tab);
        vp_tab = findViewById(R.id.vp_tab);
        llyt_chat = findViewById(R.id.llyt_chat);
        iv_flower = findViewById(R.id.iv_flower);
        tv_reply = findViewById(R.id.tv_reply);
        tv_give = findViewById(R.id.tv_give);
        tv_send = findViewById(R.id.tv_send);
        tv_catagory = findViewById(R.id.tv_catagory);
        iv_videoback = findViewById(R.id.iv_videoback);
        llyt_bottom = findViewById(R.id.llyt_bottom);
    }

    @Override
    protected void setListener() {
        iv_flower.setOnClickListener(this);
        tv_reply.setOnClickListener(this);
        tv_give.setOnClickListener(this);
        tv_send.setOnClickListener(this);
        tv_catagory.setOnClickListener(this);
        iv_videoback.setOnClickListener(this);
        video_tx.setOnClickListener(this);
        llyt_root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = llyt_root.getRootView().getHeight() - llyt_root.getHeight();
                Log.e(LiveDetailActivity.class.getSimpleName(), "" + heightDiff);
                int screenHeight = DataTools.getScreenHight(LiveDetailActivity.this);
                int virtualKeyHeight = DataTools.getHasVirtualKey(LiveDetailActivity.this);
                if (heightDiff > virtualKeyHeight - screenHeight + 100) { // 如果高度差超过100像素，就很有可能是有软键盘...
                    int vittualScreenHeight = DataTools.getHasVirtualKey(LiveDetailActivity.this);
                    llyt_reply.layout(0, vittualScreenHeight - heightDiff - ((int) getResources().getDimension(R.dimen.py180)),
                            DataTools.getScreenWith(LiveDetailActivity.this), vittualScreenHeight - heightDiff);
                    llyt_reply.setVisibility(View.VISIBLE);
                    et_reply.setFocusable(true);
                    et_reply.requestFocus();
                } else {
                    llyt_reply.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void setData() {
        initData();
        initViewPager();
    }

    private void initData() {
        mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_PORTRAIT;
        ViewGroup.LayoutParams lp = rlyt_player.getLayoutParams();
        lp.width = DataTools.getScreenWith(this);
        lp.height = DataTools.getScreenWith(this) * 9 / 16;
        rlyt_player.setLayoutParams(lp);
        if (mTxLivePlayer == null) {
            mTxLivePlayer = new TXLivePlayer(this);
        }
        mTxLivePlayer.setPlayerView(video_tx);
        mPlayConfig = new TXLivePlayConfig();

        //自动模式
//        mPlayConfig.setAutoAdjustCacheTime(true);
//        mPlayConfig.setMinAutoAdjustCacheTime(1);
//        mPlayConfig.setMaxAutoAdjustCacheTime(5);

//        //极速模式
        mPlayConfig.setAutoAdjustCacheTime(true);
        mPlayConfig.setMinAutoAdjustCacheTime(1);
        mPlayConfig.setMaxAutoAdjustCacheTime(1);
//
        //流畅模式
//        mPlayConfig.setAutoAdjustCacheTime(false);
//        mPlayConfig.setCacheTime(5);

        mPlayConfig.setEnableMessage(true);
        mTxLivePlayer.setConfig(mPlayConfig);
        mTxLivePlayer.setPlayListener(new ITXLivePlayListener() {
            @Override
            public void onPlayEvent(int event, Bundle param) {
                try {
                    if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {

                    } else if (event == TXLiveConstants.PLAY_EVT_GET_MESSAGE) {
                        String msg = null;

                        msg = new String(param.getByteArray(TXLiveConstants.EVT_GET_MSG), "UTF-8");
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNetStatus(Bundle status) {
            }
        });

        if (checkPlayUrl(mAddress)) {
//            mTxLivePlayer.startPlay(mAddress, mPlayType);

            iv_fullscreen.setOnClickListener(this);
        }

    }

    private void initViewPager() {
        mFragmentList.add(new ChatFragment());
        mFragmentList.add(new AuthorFragment());
        mFragmentList.add(new RankingFragment());
        mFragmentList.add(new GetGiftFragment());
        LivePageAdapter adapter = new LivePageAdapter(getSupportFragmentManager(), mFragmentList);
        // 给ViewPager设置适配器
        vp_tab.setAdapter(adapter);
        tlyt_tab.setTabMode(TabLayout.MODE_FIXED);
        // 使用 TabLayout 和 ViewPager 相关联
        tlyt_tab.setupWithViewPager(vp_tab);
        int tabCount = tlyt_tab.getTabCount();//获取TabLayout的个数
        for (int i = 0; i < tabCount; i++) {
            final int pos = i;
            View view = View.inflate(this, R.layout.item_tab, null);
            TextView tv_tab = view.findViewById(R.id.tv_tab);
            tv_tab.setText(mTabNames[pos]);
            TabLayout.Tab tab = tlyt_tab.getTabAt(pos);////获取TabLayout的子元素Tab
            tab.setCustomView(tv_tab);//设置TabLayout的子元素Tab的布局View
            ((View) tab.getCustomView().getParent()).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ScreenUtils.closeKeyboard(LiveDetailActivity.this);
                    llyt_chat.setVisibility(pos == 0 ? View.VISIBLE : View.GONE);
                }
            });
        }

    }


    private void transformSize() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        switchScreen(newConfig.orientation);
    }

    @Override
    public void onBackPressed() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            transformSize();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkPlayUrl(mAddress) && mTxLivePlayer != null && video_tx != null) {
            mTxLivePlayer.startPlay(mAddress, mPlayType);
        }
    }

    /**
     * 切换屏幕
     */

    private void switchScreen(int orientation) {
        if (orientation == -1) {
            orientation = getResources().getConfiguration().orientation;
        }
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            cus_title.setVisibility(View.GONE);
            iv_fullscreen.setVisibility(View.GONE);
            iv_videoback.setVisibility(View.VISIBLE);
//            tv_catagory.setVisibility(View.VISIBLE);
            llyt_bottom.setVisibility(View.GONE);

            ViewGroup.LayoutParams params = rlyt_player.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            rlyt_player.setLayoutParams(params);
//            mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_LANDSCAPE;
//            txLivePlayer.setRenderRotation(mCurrentRenderRotation);
            ScreenUtils.setFullScreen(this);
        } else {
            cus_title.setVisibility(View.VISIBLE);
            iv_fullscreen.setVisibility(View.VISIBLE);
            iv_videoback.setVisibility(View.GONE);
            tv_catagory.setVisibility(View.GONE);
            llyt_bottom.setVisibility(View.VISIBLE);

            ViewGroup.LayoutParams params = rlyt_player.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = DataTools.dip2px(this, 200);
            rlyt_player.setLayoutParams(params);
            ScreenUtils.quitFullScreen(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTxLivePlayer.stopPlay(true);
        video_tx.onDestroy();
    }

    @Override
    public void onClick(View view) {
        ScreenUtils.closeKeyboard(this);
        switch (view.getId()) {
            case R.id.iv_fullscreen:
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    transformSize();
                }
                break;
            case R.id.tv_catagory:
                if ("自动".equalsIgnoreCase(tv_catagory.getText().toString())) {
                    tv_catagory.setText("高清");
                    //流畅模式
                    mPlayConfig.setAutoAdjustCacheTime(false);
                    mPlayConfig.setCacheTime(5);
                    mPlayConfig.setEnableMessage(true);
                    mTxLivePlayer.setConfig(mPlayConfig);
                } else if ("高清".equalsIgnoreCase(tv_catagory.getText().toString())) {
                    tv_catagory.setText("标清");
                    //极速模式
                    mPlayConfig.setAutoAdjustCacheTime(true);
                    mPlayConfig.setMinAutoAdjustCacheTime(1);
                    mPlayConfig.setMaxAutoAdjustCacheTime(1);
                    mPlayConfig.setEnableMessage(true);
                    mTxLivePlayer.setConfig(mPlayConfig);
                } else if ("标清".equalsIgnoreCase(tv_catagory.getText().toString())) {
                    tv_catagory.setText("自动");
                    //自动模式
                    mPlayConfig.setAutoAdjustCacheTime(true);
                    mPlayConfig.setMinAutoAdjustCacheTime(1);
                    mPlayConfig.setMaxAutoAdjustCacheTime(5);
                    mPlayConfig.setEnableMessage(true);
                    mTxLivePlayer.setConfig(mPlayConfig);
                }
                break;

            case R.id.iv_videoback:
                transformSize();
                break;

            case R.id.video_tx:
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && Build.VERSION.SDK_INT >= 19) {
                    if (iv_videoback.getVisibility() == View.GONE) {
                        iv_videoback.setVisibility(View.VISIBLE);
//                        tv_catagory.setVisibility(View.VISIBLE);
                    } else {
                        iv_videoback.setVisibility(View.GONE);
//                        tv_catagory.setVisibility(View.GONE);
                    }
                    break;
                }
            case R.id.tv_reply:
                ScreenUtils.openKeyboard(tv_reply);
                break;
            case R.id.tv_send:
                String msg = et_reply.getText().toString().trim();
                if (!TextUtils.isEmpty(msg)) {
                    ScreenUtils.closeKeyboard(this);
                    et_reply.setText("");
                    ChatFragment chatFragment = (ChatFragment) mFragmentList.get(0);
                    chatFragment.sendMessage(msg);
                }
                break;
        }
    }

    private boolean checkPlayUrl(final String playUrl) {
        if (TextUtils.isEmpty(playUrl) || (!playUrl.startsWith("http://") && !playUrl.startsWith("https://") && !playUrl.startsWith("rtmp://") && !playUrl.startsWith("/"))) {
            ToastUtil.show("播放地址不合法，直播目前仅支持rtmp,flv播放方式!");
            return false;
        }

        if (playUrl.startsWith("rtmp://")) {
            if (playUrl.contains("&txSecret") && playUrl.contains("&txTime")) {
                mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP_ACC;
            } else mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;
        } else if ((playUrl.startsWith("http://") || playUrl.startsWith("https://")) && playUrl.contains(".flv")) {
            mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_FLV;
        } else {
            ToastUtil.show("播放地址不合法，直播目前仅支持rtmp,flv播放方式!");
            return false;
        }
        return true;
    }


}
