package first.net.liteapp.activity;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.ArrayList;
import java.util.List;

import first.net.liteapp.LiteApplication;
import first.net.liteapp.R;
import first.net.liteapp.constant.IConfig;
import first.net.liteapp.utils.PushFlowUtils;
import first.net.liteapp.view.TitleView;

/**
 * Created by yuqiubo on 2018/2/8.
 */

public class LivePublishActivity extends BaseActivity implements ITXLivePushListener {
    private TXLivePushConfig mLivePushConfig;
    private TXLivePusher mLivePusher;
    private TXCloudVideoView mCaptureView;
    private TextView mNetBusyTips;
    private Button btn_play;
    private Button btn_orientation;
    private Button btn_carema;
    private Button btn_new;
    private EditText et_roomid;
    private TitleView cus_title;
    private FrameLayout flyt_video;
    private LinearLayout llyt_fun;
    private RelativeLayout rlyt_room;

    private Bitmap mBitmap;
    private int mNetBusyCount = 0;
    // 关注系统设置项“自动旋转”的状态切换
    private RotationObserver mRotationObserver = null;
    private boolean mPortrait = true;         //手动切换，横竖屏推流
    private boolean mFrontCamera = true;
//    private String mUrl = "rtmp://20981.livepush.myqcloud.com/live/20981_test12345?bizid=20981&txSecret=d704abac90abe1a8b967b397a5559f1e&txTime=5A7E9CE5";
    private boolean mVideoPublish;
    private boolean mHWVideoEncode = true;
    private boolean mIsRealTime = false;
    private final String TAG = LivePublishActivity.class.getSimpleName();
    private int mVideoQuality = TXLiveConstants.VIDEO_QUALITY_STANDARD_DEFINITION;
    private boolean mAutoBitrate = false;
    private boolean mAutoResolution = false;

    private int mBeautyLevel = 5;
    private int mWhiteningLevel = 3;
    private int mRuddyLevel = 2;
    private int mBeautyStyle = TXLiveConstants.BEAUTY_STYLE_SMOOTH;

    private static final int VIDEO_SRC_CAMERA = 0;
    private static final int VIDEO_SRC_SCREEN = 1;
    private int mVideoSrc = VIDEO_SRC_CAMERA;
    private int mCurrentVideoResolution = TXLiveConstants.VIDEO_RESOLUTION_TYPE_360_640;

    private Bitmap decodeResource(Resources resources, int id) {
        TypedValue value = new TypedValue();
        resources.openRawResource(id, value);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inTargetDensity = value.density;
        return BitmapFactory.decodeResource(resources, id, opts);
    }

    @Override
    protected void initFeature() {
        super.initFeature();
        mLivePusher = new TXLivePusher(this);
        mLivePushConfig = new TXLivePushConfig();
        mLivePusher.setBeautyFilter(mBeautyStyle, mBeautyLevel, mWhiteningLevel, mRuddyLevel);
        mLivePusher.setConfig(mLivePushConfig);

        mBitmap = decodeResource(getResources(), R.mipmap.ic_launcher);

        mRotationObserver = new RotationObserver(new Handler());
        mRotationObserver.startObserver();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_publish;
    }

    @Override
    protected void initView() {
        cus_title = findViewById(R.id.cus_title);
        mCaptureView = findViewById(R.id.video_view);
        mNetBusyTips = findViewById(R.id.netbusy_tv);
        btn_play = findViewById(R.id.btn_play);
        btn_carema = findViewById(R.id.btn_camera);
        btn_new = findViewById(R.id.btn_new);
        et_roomid = findViewById(R.id.et_roomid);
        btn_orientation = findViewById(R.id.btn_orientation);
        flyt_video = findViewById(R.id.flyt_video);
        llyt_fun = findViewById(R.id.llyt_fun);
        rlyt_room = findViewById(R.id.rlyt_room);

    }

    @Override
    protected void setListener() {
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mVideoPublish) {
                    stopPublishRtmp();
                } else {
                    if (mVideoSrc == VIDEO_SRC_CAMERA) {
                        FixOrAdjustBitrate();  //根据设置确定是“固定”还是“自动”码率
                    } else {
                        //录屏横竖屏采用两种分辨率，和摄像头推流逻辑不一样
                    }
                    mVideoPublish = startPublishRtmp();
                }

                btn_play.setBackgroundResource(mVideoPublish ? R.mipmap.play_pause : R.mipmap.play_start);
            }
        });
        btn_orientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPortrait = !mPortrait;
                int renderRotation;
                boolean screenCaptureLandscape = false;
                if (mPortrait) {
                    mLivePushConfig.setHomeOrientation(TXLiveConstants.VIDEO_ANGLE_HOME_DOWN);
                    btn_orientation.setBackgroundResource(R.mipmap.landscape);
                    renderRotation = 0;
                } else {
                    mLivePushConfig.setHomeOrientation(TXLiveConstants.VIDEO_ANGLE_HOME_RIGHT);
                    btn_orientation.setBackgroundResource(R.mipmap.portrait);
                    screenCaptureLandscape = true;
                    renderRotation = 90;
                }
                if (VIDEO_SRC_SCREEN == mVideoSrc) {
                    //录屏横竖屏推流的判断条件是，视频分辨率取360*640还是640*360
                    switch (mCurrentVideoResolution) {
                        case TXLiveConstants.VIDEO_RESOLUTION_TYPE_360_640:
                            if (screenCaptureLandscape)
                                mLivePushConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_640_360);
                            else
                                mLivePushConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_360_640);
                            break;
                        case TXLiveConstants.VIDEO_RESOLUTION_TYPE_540_960:
                            if (screenCaptureLandscape)
                                mLivePushConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_960_540);
                            else
                                mLivePushConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_540_960);
                            break;
                        case TXLiveConstants.VIDEO_RESOLUTION_TYPE_720_1280:
                            if (screenCaptureLandscape)
                                mLivePushConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_1280_720);
                            else
                                mLivePushConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_720_1280);
                            break;
                    }

                }
                if (mLivePusher.isPushing()) {
                    if (VIDEO_SRC_CAMERA == mVideoSrc) {
                        mLivePusher.setConfig(mLivePushConfig);
                    } else if (VIDEO_SRC_SCREEN == mVideoSrc) {
                        mLivePusher.setConfig(mLivePushConfig);
                        mLivePusher.stopScreenCapture();
                        mLivePusher.startScreenCapture();
                    }
                } else mLivePusher.setConfig(mLivePushConfig);
                mLivePusher.setRenderRotation(renderRotation);
            }
        });

        btn_carema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFrontCamera = !mFrontCamera;

                if (mLivePusher.isPushing()) {
                    mLivePusher.switchCamera();
                }
                mLivePushConfig.setFrontCamera(mFrontCamera);
                btn_carema.setBackgroundResource(mFrontCamera ? R.mipmap.camera_change_front : R.mipmap.camera_change_back);
            }
        });

        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long time = System.currentTimeMillis() / 1000 + 24 * 60 * 60L;
                String url = IConfig.CONFIG_URL_FLOW + "&" + PushFlowUtils.getSafeUrl(IConfig.CONFIG_KEY_SAFETY, IConfig.CONFIG_NUMBER_HOUSE, time);
                et_roomid.setText(url);
                Toast.makeText(LivePublishActivity.this, "获取推流地址成功，对应播放地址已复制到剪贴板", Toast.LENGTH_LONG).show();
                String realtimePlayUrl = url.replace(".livepush.myqcloud.com",".liveplay.myqcloud.com");
                String rtmpPlayUrl = realtimePlayUrl.substring(0,url.indexOf("?"));
                String flvPlayUrl = "http"+rtmpPlayUrl.substring(4)+".flv";
                String hlsPlayUrl = "http"+rtmpPlayUrl.substring(4)+".m3u8";
                String playUrl = String.format("rtmp 协议：%s\n", rtmpPlayUrl)
                        + String.format("flv 协议：%s\n", flvPlayUrl)
                        + String.format("hls 协议：%s\n", hlsPlayUrl)
                        + String.format("低时延播放：%s", realtimePlayUrl);
                Log.d(TAG, "fetch play url : " + playUrl);
                try {
                    ClipboardManager cmb = (ClipboardManager) LivePublishActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                    cmb.setText(playUrl);
                } catch (Exception e) {

                }
            }
        });

        flyt_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mVideoPublish) {
                    if (cus_title.isShown() || llyt_fun.isShown()) {
                        cus_title.setVisibility(View.GONE);
                        llyt_fun.setVisibility(View.GONE);
                        rlyt_room.setVisibility(View.GONE);
                    } else {
                        cus_title.setVisibility(View.VISIBLE);
                        llyt_fun.setVisibility(View.VISIBLE);
                        rlyt_room.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @Override
    protected void setData() {
        cus_title.setBackground(Color.parseColor("#00ffffff"));
        if (isActivityCanRotation()) {
            btn_orientation.setVisibility(View.GONE);
        }
        checkPublishPermission();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mCaptureView != null) {
            mCaptureView.onPause();
        }

        if (mVideoPublish && mLivePusher != null && mVideoSrc == VIDEO_SRC_CAMERA) {
            mLivePusher.pausePusher();
            mLivePusher.pauseBGM();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mCaptureView != null) {
            mCaptureView.onResume();
        }

        if (mVideoPublish && mLivePusher != null && mVideoSrc == VIDEO_SRC_CAMERA) {
            mLivePusher.resumePusher();
            mLivePusher.resumeBGM();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPublishRtmp();
        if (mCaptureView != null) {
            mCaptureView.onDestroy();
        }

        mRotationObserver.stopObserver();
    }


    private boolean startPublishRtmp() {
        String rtmpUrl = "";
        String inputUrl = et_roomid.getText().toString().trim();
        if (!TextUtils.isEmpty(inputUrl)) {
            String url[] = inputUrl.split("###");
            if (url.length > 0) {
                rtmpUrl = url[0];
            }
        }

        if (TextUtils.isEmpty(rtmpUrl) || (!rtmpUrl.trim().toLowerCase().startsWith("rtmp://"))) {
            Toast.makeText(getApplicationContext(), "推流地址不合法，目前支持rtmp推流!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mVideoSrc != VIDEO_SRC_SCREEN) {
            mCaptureView.setVisibility(View.VISIBLE);
        }
        // demo默认不加水印
        //mLivePushConfig.setWatermark(mBitmap, 0.02f, 0.05f, 0.2f);

        int customModeType = 0;

        //【示例代码1】设置自定义视频采集逻辑 （自定义视频采集逻辑不要调用startPreview）
//        customModeType |= TXLiveConstants.CUSTOM_MODE_VIDEO_CAPTURE;
//        mLivePushConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_640_360);
//        mLivePushConfig.setAutoAdjustBitrate(false);
//        mLivePushConfig.setVideoBitrate(1300);
//        mLivePushConfig.setVideoEncodeGop(3);
//        new Thread() {  //视频采集线程
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        FileInputStream in = new FileInputStream("/sdcard/dump_1280_720.yuv");
//                        int len = 1280 * 720 * 3 / 2;
//                        byte buffer[] = new byte[len];
//                        int count;
//                        while ((count = in.read(buffer)) != -1) {
//                            if (len == count) {
//                                mLivePusher.sendCustomVideoData(buffer, TXLivePusher.YUV_420SP);
//                            } else {
//                                break;
//                            }
//                            sleep(50, 0);
//                        }
//                        in.close();
//                    } catch (Exception e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.start();

        //【示例代码2】设置自定义音频采集逻辑（音频采样位宽必须是16）
//        mLivePushConfig.setAudioSampleRate(44100);
//        mLivePushConfig.setAudioChannels(1);
//        customModeType |= TXLiveConstants.CUSTOM_MODE_AUDIO_CAPTURE;
//        new Thread() {  //音频采集线程
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        FileInputStream in = new FileInputStream("/sdcard/dump.pcm");
//                        int len = 2048;
//                        byte buffer[] = new byte[len];
//                        int count;
//                        while ((count = in.read(buffer)) != -1) {
//                            if (len == count) {
//                                mLivePusher.sendCustomPCMData(buffer);
//                            } else {
//                                break;
//                            }
//                            sleep(10, 0);
//                        }
//                        in.close();
//                    } catch (Exception e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.start();

        //【示例代码3】设置自定义视频预处理逻辑
//        customModeType |= TXLiveConstants.CUSTOM_MODE_VIDEO_PREPROCESS;
//        String path = this.getActivity().getApplicationInfo().dataDir + "/lib";
//        mLivePushConfig.setCustomVideoPreProcessLibrary(path +"/libvideo.so", "tx_video_process");

        //【示例代码4】设置自定义音频预处理逻辑
//        customModeType |= TXLiveConstants.CUSTOM_MODE_AUDIO_PREPROCESS;
//        String path = this.getActivity().getApplicationInfo().dataDir + "/lib";
//        mLivePushConfig.setCustomAudioPreProcessLibrary(path +"/libvideo.so", "tx_audio_process");

//        mLivePushConfig.setAutoAdjustBitrate(true);
//        mLivePushConfig.setMaxVideoBitrate(1200);
//        mLivePushConfig.setMinVideoBitrate(500);
//        mLivePushConfig.setVideoBitrate(600);
//        mLivePushConfig.enablePureAudioPush(true);
        //mLivePushConfig.enableHighResolutionCaptureMode(false);
        if (isActivityCanRotation()) {
            onActivityRotation();
        }
        mLivePushConfig.setCustomModeType(customModeType);
        mLivePusher.setPushListener(this);
        mLivePushConfig.setPauseImg(300, 5);
        Bitmap bitmap = decodeResource(getResources(), R.mipmap.pause_publish);
        mLivePushConfig.setPauseImg(bitmap);
        mLivePushConfig.setPauseFlag(TXLiveConstants.PAUSE_FLAG_PAUSE_VIDEO | TXLiveConstants.PAUSE_FLAG_PAUSE_AUDIO);
        if (mVideoSrc != VIDEO_SRC_SCREEN) {
            mLivePushConfig.setFrontCamera(mFrontCamera);
            mLivePushConfig.setBeautyFilter(mBeautyLevel, mWhiteningLevel, mRuddyLevel);
            mLivePusher.setConfig(mLivePushConfig);
            mLivePusher.startCameraPreview(mCaptureView);
        } else {
            mLivePusher.setConfig(mLivePushConfig);
            mLivePusher.startScreenCapture();
        }

        mLivePusher.startPusher(rtmpUrl.trim());

        return true;
    }

    private void stopPublishRtmp() {
        mVideoPublish = false;
        mLivePusher.stopBGM();
        mLivePusher.stopCameraPreview(true);
        mLivePusher.stopScreenCapture();
        mLivePusher.setPushListener(null);
        mLivePusher.stopPusher();
        mCaptureView.setVisibility(View.GONE);

        mLivePushConfig.setHardwareAcceleration(mHWVideoEncode ? TXLiveConstants.ENCODE_VIDEO_HARDWARE : TXLiveConstants.ENCODE_VIDEO_SOFTWARE);

        if (mLivePushConfig != null) {
            mLivePushConfig.setPauseImg(null);
        }

        llyt_fun.setVisibility(View.VISIBLE);
        btn_play.setBackgroundResource(R.mipmap.play_start);
        rlyt_room.setVisibility(View.VISIBLE);
        cus_title.setVisibility(View.VISIBLE);
    }

    public void FixOrAdjustBitrate() {
        if (mLivePushConfig == null || mLivePusher == null) {
            return;
        }
        int mode = 2;
        mIsRealTime = false;
        switch (mode) {
            case 1: /*360p*/
                if (mLivePusher != null) {
                    mVideoQuality = TXLiveConstants.VIDEO_QUALITY_STANDARD_DEFINITION;
                    mLivePusher.setVideoQuality(mVideoQuality, mAutoBitrate, mAutoResolution);
                    mCurrentVideoResolution = TXLiveConstants.VIDEO_RESOLUTION_TYPE_360_640;
                    //标清默认开启了码率自适应，需要关闭码率自适应
                    mLivePushConfig.setAutoAdjustBitrate(false);
                    mLivePushConfig.setVideoBitrate(700);
                    mLivePusher.setConfig(mLivePushConfig);
                    //标清默认关闭硬件加速
                    mHWVideoEncode = false;
                }
                break;
            case 2: /*540p*/
                if (mLivePusher != null) {
                    mVideoQuality = TXLiveConstants.VIDEO_QUALITY_HIGH_DEFINITION;
                    mLivePusher.setVideoQuality(mVideoQuality, mAutoBitrate, mAutoResolution);
                    mCurrentVideoResolution = TXLiveConstants.VIDEO_RESOLUTION_TYPE_540_960;
                    mHWVideoEncode = false;
                }
                break;
            case 3: /*720p*/
                if (mLivePusher != null) {
                    mVideoQuality = TXLiveConstants.VIDEO_QUALITY_SUPER_DEFINITION;
                    mLivePusher.setVideoQuality(mVideoQuality, mAutoBitrate, mAutoResolution);
                    mCurrentVideoResolution = TXLiveConstants.VIDEO_RESOLUTION_TYPE_720_1280;
                    //超清默认开启硬件加速
                    if (Build.VERSION.SDK_INT >= 18) {
                        mHWVideoEncode = true;
                    }
                }
                break;
            case 4: /*连麦大主播*/
                if (mLivePusher != null) {
                    mVideoQuality = TXLiveConstants.VIDEO_QUALITY_LINKMIC_MAIN_PUBLISHER;
                    mLivePusher.setVideoQuality(mVideoQuality, mAutoBitrate, mAutoResolution);
                    mCurrentVideoResolution = TXLiveConstants.VIDEO_RESOLUTION_TYPE_360_640;
                    mHWVideoEncode = true;
                }
                break;
            case 5: /*连麦小主播*/
                if (mLivePusher != null) {
                    mVideoQuality = TXLiveConstants.VIDEO_QUALITY_LINKMIC_SUB_PUBLISHER;
                    mLivePusher.setVideoQuality(mVideoQuality, mAutoBitrate, mAutoResolution);
                    mCurrentVideoResolution = TXLiveConstants.VIDEO_RESOLUTION_TYPE_320_480;
                    mHWVideoEncode = true;
                }
                break;
            case 6: /*实时*/
                if (mLivePusher != null) {
                    mVideoQuality = TXLiveConstants.VIDEO_QUALITY_REALTIEM_VIDEOCHAT;
                    mLivePusher.setVideoQuality(mVideoQuality, mAutoBitrate, mAutoResolution);
                    mCurrentVideoResolution = TXLiveConstants.VIDEO_RESOLUTION_TYPE_360_640;
                    //超清默认开启硬件加速
                    if (Build.VERSION.SDK_INT >= 18) {
                        mHWVideoEncode = true;
                    }
                    mIsRealTime = true;
                }
                break;
            default:
                break;
        }
    }


    private boolean checkPublishPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(this,
                        permissions.toArray(new String[0]),
                        100);
                return false;
            }
        }

        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        onActivityRotation();
        super.onConfigurationChanged(newConfig);
    }

    protected void onActivityRotation() {
        // 自动旋转打开，Activity随手机方向旋转之后，需要改变推流方向
        int mobileRotation = this.getWindowManager().getDefaultDisplay().getRotation();
        int pushRotation = TXLiveConstants.VIDEO_ANGLE_HOME_DOWN;
        boolean screenCaptureLandscape = false;
        switch (mobileRotation) {
            case Surface.ROTATION_0:
                pushRotation = TXLiveConstants.VIDEO_ANGLE_HOME_DOWN;
                break;
            case Surface.ROTATION_180:
                pushRotation = TXLiveConstants.VIDEO_ANGLE_HOME_UP;
                break;
            case Surface.ROTATION_90:
                pushRotation = TXLiveConstants.VIDEO_ANGLE_HOME_RIGHT;
                screenCaptureLandscape = true;
                break;
            case Surface.ROTATION_270:
                pushRotation = TXLiveConstants.VIDEO_ANGLE_HOME_LEFT;
                screenCaptureLandscape = true;
                break;
            default:
                break;
        }
        mLivePusher.setRenderRotation(0); //因为activity也旋转了，本地渲染相对正方向的角度为0。
        mLivePushConfig.setHomeOrientation(pushRotation);
        if (mLivePusher.isPushing()) {
            if (VIDEO_SRC_CAMERA == mVideoSrc) {
                mLivePusher.setConfig(mLivePushConfig);
                mLivePusher.stopCameraPreview(true);
                mLivePusher.startCameraPreview(mCaptureView);
            } else if (VIDEO_SRC_SCREEN == mVideoSrc) {
                //录屏横竖屏推流的判断条件是，视频分辨率取360*640还是640*360
                switch (mCurrentVideoResolution) {
                    case TXLiveConstants.VIDEO_RESOLUTION_TYPE_360_640:
                        if (screenCaptureLandscape)
                            mLivePushConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_640_360);
                        else
                            mLivePushConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_360_640);
                        break;
                    case TXLiveConstants.VIDEO_RESOLUTION_TYPE_540_960:
                        if (screenCaptureLandscape)
                            mLivePushConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_960_540);
                        else
                            mLivePushConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_540_960);
                        break;
                    case TXLiveConstants.VIDEO_RESOLUTION_TYPE_720_1280:
                        if (screenCaptureLandscape)
                            mLivePushConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_1280_720);
                        else
                            mLivePushConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_720_1280);
                        break;
                }
                mLivePusher.setConfig(mLivePushConfig);
                mLivePusher.stopScreenCapture();
                mLivePusher.startScreenCapture();
            }
        }
    }

    /**
     * 判断Activity是否可旋转。只有在满足以下条件的时候，Activity才是可根据重力感应自动旋转的。
     * 系统“自动旋转”设置项打开；
     *
     * @return false---Activity可根据重力感应自动旋转
     */
    protected boolean isActivityCanRotation() {
        // 判断自动旋转是否打开
        int flag = Settings.System.getInt(this.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
        if (flag == 0) {
            return false;
        }
        return true;
    }

    @Override
    public void onPushEvent(int event, Bundle param) {
//        Log.e("NotifyCode","LivePublisherActivity :" + event);
        String msg = param.getString(TXLiveConstants.EVT_DESCRIPTION);
        String pushEventLog = "receive event: " + event + ", " + msg;
        Log.d(TAG, pushEventLog);
//        if (mLivePusher != null) {
//            mLivePusher.onLogRecord("[event:" + event + "]" + msg + "\n");
//        }
        //错误还是要明确的报一下
        if (event < 0) {
            Toast.makeText(getApplicationContext(), param.getString(TXLiveConstants.EVT_DESCRIPTION), Toast.LENGTH_SHORT).show();
            if (event == TXLiveConstants.PUSH_ERR_OPEN_CAMERA_FAIL || event == TXLiveConstants.PUSH_ERR_OPEN_MIC_FAIL) {
                stopPublishRtmp();
            }
        }

        if (event == TXLiveConstants.PUSH_ERR_NET_DISCONNECT) {
            stopPublishRtmp();
        } else if (event == TXLiveConstants.PUSH_WARNING_HW_ACCELERATION_FAIL) {
            Toast.makeText(getApplicationContext(), param.getString(TXLiveConstants.EVT_DESCRIPTION), Toast.LENGTH_SHORT).show();
            mLivePushConfig.setHardwareAcceleration(TXLiveConstants.ENCODE_VIDEO_SOFTWARE);
            mLivePusher.setConfig(mLivePushConfig);
            mHWVideoEncode = false;
        } else if (event == TXLiveConstants.PUSH_ERR_SCREEN_CAPTURE_UNSURPORT) {
            stopPublishRtmp();
        } else if (event == TXLiveConstants.PUSH_ERR_SCREEN_CAPTURE_START_FAILED) {
            stopPublishRtmp();
        } else if (event == TXLiveConstants.PUSH_EVT_CHANGE_RESOLUTION) {
            Log.d(TAG, "change resolution to " + param.getInt(TXLiveConstants.EVT_PARAM2) + ", bitrate to" + param.getInt(TXLiveConstants.EVT_PARAM1));
        } else if (event == TXLiveConstants.PUSH_EVT_CHANGE_BITRATE) {
            Log.d(TAG, "change bitrate to" + param.getInt(TXLiveConstants.EVT_PARAM1));
        } else if (event == TXLiveConstants.PUSH_WARNING_NET_BUSY) {
            ++mNetBusyCount;
            Log.d(TAG, "net busy. count=" + mNetBusyCount);
            showNetBusyTips();
        } else if (event == TXLiveConstants.PUSH_EVT_START_VIDEO_ENCODER) {
            int encType = param.getInt(TXLiveConstants.EVT_PARAM1);
            mHWVideoEncode = (encType == 1);
        }
    }

    @Override
    public void onNetStatus(Bundle status) {
        String str = getNetStatusString(status);
        Log.d(TAG, "Current status, CPU:" + status.getString(TXLiveConstants.NET_STATUS_CPU_USAGE) +
                ", RES:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH) + "*" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT) +
                ", SPD:" + status.getInt(TXLiveConstants.NET_STATUS_NET_SPEED) + "Kbps" +
                ", FPS:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS) +
                ", ARA:" + status.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE) + "Kbps" +
                ", VRA:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE) + "Kbps");
//        if (mLivePusher != null){
//            mLivePusher.onLogRecord("[net state]:\n"+str+"\n");
//        }
    }

    //公用打印辅助函数
    protected String getNetStatusString(Bundle status) {
        String str = String.format("%-14s %-14s %-12s\n%-8s %-8s %-8s %-8s\n%-14s %-14s %-12s\n%-14s %-14s",
                "CPU:" + status.getString(TXLiveConstants.NET_STATUS_CPU_USAGE),
                "RES:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH) + "*" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT),
                "SPD:" + status.getInt(TXLiveConstants.NET_STATUS_NET_SPEED) + "Kbps",
                "JIT:" + status.getInt(TXLiveConstants.NET_STATUS_NET_JITTER),
                "FPS:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS),
                "GOP:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_GOP) + "s",
                "ARA:" + status.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE) + "Kbps",
                "QUE:" + status.getInt(TXLiveConstants.NET_STATUS_CODEC_CACHE) + "|" + status.getInt(TXLiveConstants.NET_STATUS_CACHE_SIZE),
                "DRP:" + status.getInt(TXLiveConstants.NET_STATUS_CODEC_DROP_CNT) + "|" + status.getInt(TXLiveConstants.NET_STATUS_DROP_SIZE),
                "VRA:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE) + "Kbps",
                "SVR:" + status.getString(TXLiveConstants.NET_STATUS_SERVER_IP),
                "AUDIO:" + status.getString(TXLiveConstants.NET_STATUS_AUDIO_INFO));
        return str;
    }


    private void showNetBusyTips() {
        if (mNetBusyTips.isShown()) {
            return;
        }
        mNetBusyTips.setVisibility(View.VISIBLE);
        LiteApplication.getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mNetBusyTips.setVisibility(View.GONE);
            }
        }, 5000);
    }


    //观察屏幕旋转设置变化，类似于注册动态广播监听变化机制
    private class RotationObserver extends ContentObserver {
        ContentResolver mResolver;

        public RotationObserver(Handler handler) {
            super(handler);
            mResolver = LivePublishActivity.this.getContentResolver();
        }

        //屏幕旋转设置改变时调用
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            //更新按钮状态
            if (isActivityCanRotation()) {
                btn_orientation.setVisibility(View.GONE);
                onActivityRotation();
            } else {
                btn_orientation.setVisibility(View.VISIBLE);
                mPortrait = true;
                mLivePushConfig.setHomeOrientation(TXLiveConstants.VIDEO_ANGLE_HOME_DOWN);
                btn_orientation.setBackgroundResource(R.mipmap.landscape);
                mLivePusher.setRenderRotation(0);
                mLivePusher.setConfig(mLivePushConfig);
            }

        }

        public void startObserver() {
            mResolver.registerContentObserver(Settings.System.getUriFor(Settings.System.ACCELEROMETER_ROTATION), false, this);
        }

        public void stopObserver() {
            mResolver.unregisterContentObserver(this);
        }
    }
}
