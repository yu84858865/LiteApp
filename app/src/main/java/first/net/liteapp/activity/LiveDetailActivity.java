package first.net.liteapp.activity;


import android.content.res.Configuration;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
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
import first.net.liteapp.view.TitleView;

/**
 * Created by 10960 on 2018/2/2.
 */

public class LiveDetailActivity extends BaseActivity implements View.OnClickListener {

    private TXCloudVideoView video_tx;
    private TXLivePlayer mTxLivePlayer;
    private ImageView iv_fullscreen;
    private int mCurrentRenderRotation;
    private RelativeLayout rlyt_player;
    private LinearLayout llyt_bottom;
    private TitleView cus_title;
    private TabLayout tlyt_tab;
    private ViewPager vp_tab;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private EditText et_reply;
    private ImageView iv_videoback;
    private TextView tv_catagory;
    private String mUrl = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
    private TXLivePlayConfig mPlayConfig;
    private int mPlayType;


    @Override
    public int getContentView() {
        return R.layout.activity_live_detail;
    }

    @Override
    protected void initView() {
        cus_title = findViewById(R.id.cus_title);
        video_tx = findViewById(R.id.video_tx);
        iv_fullscreen = findViewById(R.id.iv_fullscreen);
        rlyt_player = findViewById(R.id.rlyt_player);
        tlyt_tab = findViewById(R.id.tlyt_tab);
        vp_tab = findViewById(R.id.vp_tab);
        et_reply = findViewById(R.id.et_reply);
        tv_catagory = findViewById(R.id.tv_catagory);
        iv_videoback = findViewById(R.id.iv_videoback);
        llyt_bottom = findViewById(R.id.llyt_bottom);
    }

    @Override
    protected void setListener() {
        tv_catagory.setOnClickListener(this);
        iv_videoback.setOnClickListener(this);
        video_tx.setOnClickListener(this);
    }

    @Override
    protected void setData() {
        initData();
        initViewPager();
    }

    private void initData() {
        cus_title.setTitle("直播详情");
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
//        mPlayConfig.setAutoAdjustCacheTime(true);
//        mPlayConfig.setMinAutoAdjustCacheTime(1);
//        mPlayConfig.setMaxAutoAdjustCacheTime(1);
//
        //流畅模式
        mPlayConfig.setAutoAdjustCacheTime(false);
        mPlayConfig.setCacheTime(5);

        mPlayConfig.setEnableMessage(true);
        mTxLivePlayer.setConfig(mPlayConfig);
        mTxLivePlayer.setPlayListener(new ITXLivePlayListener() {
            @Override
            public void onPlayEvent(int event, Bundle param) {
                if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {

                } else if (event == TXLiveConstants.PLAY_EVT_GET_MESSAGE) {
                    String msg = null;
                    try {
                        msg = new String(param.getByteArray(TXLiveConstants.EVT_GET_MSG), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNetStatus(Bundle status) {
            }
        });

        if (checkPlayUrl(mUrl)) {
            mTxLivePlayer.startPlay(mUrl, mPlayType);

            iv_fullscreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        transformSize();
                    }
                }
            });
        }
        //一定要设置设置滚动模式
        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }


    private void initViewPager() {
        mFragmentList.add(new ChatFragment());
        mFragmentList.add(new AuthorFragment());
        mFragmentList.add(new RankingFragment());
        mFragmentList.add(new GetGiftFragment());
        LivePageAdapter adapter = new LivePageAdapter(getSupportFragmentManager(), mFragmentList);
        // 给ViewPager设置适配器
        vp_tab.setAdapter(adapter);
        vp_tab.setCurrentItem(0);
        // TabLayout 指示器 (记得自己手动创建4个Fragment,注意是 app包下的Fragment 还是 V4包下的 Fragment)
        tlyt_tab.addTab(tlyt_tab.newTab());
        tlyt_tab.addTab(tlyt_tab.newTab());
        tlyt_tab.addTab(tlyt_tab.newTab());
        tlyt_tab.addTab(tlyt_tab.newTab());
        // 使用 TabLayout 和 ViewPager 相关联
        tlyt_tab.setupWithViewPager(vp_tab);
        // TabLayout指示器添加文本
        tlyt_tab.getTabAt(0).setText("聊天");
        tlyt_tab.getTabAt(1).setText("主播");
        tlyt_tab.getTabAt(2).setText("排行榜");
        tlyt_tab.getTabAt(3).setText("获得礼物");
        tlyt_tab.setTabMode(TabLayout.MODE_FIXED);

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
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            switchScreen(Configuration.ORIENTATION_LANDSCAPE);
        } else {
            switchScreen(Configuration.ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onBackPressed() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            transformSize();
        } else {
            super.onBackPressed();
        }
    }


    /**
     * 切换屏幕
     */

    private void switchScreen() {
        switchScreen(-1);
    }

    private void switchScreen(int orientation) {
        if (orientation == -1) {
            orientation = getResources().getConfiguration().orientation;
        }
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            cus_title.setVisibility(View.GONE);
            iv_fullscreen.setVisibility(View.GONE);
            iv_videoback.setVisibility(View.VISIBLE);
            tv_catagory.setVisibility(View.VISIBLE);
            llyt_bottom.setVisibility(View.GONE);
            et_reply.setVisibility(View.GONE);

            ViewGroup.LayoutParams params = rlyt_player.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            rlyt_player.setLayoutParams(params);
//            mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_LANDSCAPE;
//            txLivePlayer.setRenderRotation(mCurrentRenderRotation);
        } else {
            cus_title.setVisibility(View.VISIBLE);
            iv_fullscreen.setVisibility(View.VISIBLE);
            iv_videoback.setVisibility(View.GONE);
            tv_catagory.setVisibility(View.GONE);
            llyt_bottom.setVisibility(View.VISIBLE);
            et_reply.setVisibility(View.VISIBLE);

            ViewGroup.LayoutParams params = rlyt_player.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = DataTools.dip2px(this, 200);
            rlyt_player.setLayoutParams(params);
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
        switch (view.getId()) {
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
                        tv_catagory.setVisibility(View.VISIBLE);
                    } else {
                        iv_videoback.setVisibility(View.GONE);
                        tv_catagory.setVisibility(View.GONE);
                    }
                    break;
                }
        }
    }

    private boolean checkPlayUrl(final String playUrl) {
        if (TextUtils.isEmpty(playUrl) || (!playUrl.startsWith("http://") && !playUrl.startsWith("https://") && !playUrl.startsWith("rtmp://") && !playUrl.startsWith("/"))) {
            Toast.makeText(getApplicationContext(), "播放地址不合法，直播目前仅支持rtmp,flv播放方式!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (playUrl.startsWith("rtmp://")) {
            mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;
        } else if ((playUrl.startsWith("http://") || playUrl.startsWith("https://")) && playUrl.contains(".flv")) {
            mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_FLV;
        } else {
            Toast.makeText(getApplicationContext(), "播放地址不合法，直播目前仅支持rtmp,flv播放方式!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && Build.VERSION.SDK_INT >= 19) {
            if (hasFocus) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }

    }


}
