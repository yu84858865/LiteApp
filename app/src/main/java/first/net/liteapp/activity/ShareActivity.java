package first.net.liteapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.net.MalformedURLException;
import java.net.URL;

import first.net.liteapp.R;
import first.net.liteapp.constant.Constant;
import first.net.liteapp.utils.BitmapUtils;
import first.net.liteapp.utils.ImageUtil;
import first.net.liteapp.utils.LoadingDialog;
import first.net.liteapp.utils.ToastUtil;

/**
 * Created by 10960 on 2018/2/13.
 */

public class ShareActivity extends BaseActivity implements View.OnClickListener, WbShareCallback {

    private IWXAPI api;
    private Tencent mTencent;
    private ImageView iv_wechatfriend, iv_wechatcircle, iv_qqzone, iv_sina, iv_facebook, iv_twitter, iv_linkedin, iv_qr;
    private String mTitle, mImgUrl, mContent, mTargetUrl;
    private boolean isPortrait;
    private LoadingDialog mLoadingDialog;
    private CallbackManager mCallbackManager;
    private ShareDialog mShareDialog;
    private WbShareHandler shareHandler;
    private BitmapDrawable bitmapDrawable;
    private TextView tv_cancel;
    private View mView;
    /**
     * 第三方登录AppID
     */
    private String APP_ID_QQ = "100556142";

    @Override
    protected void initFeature() {
        super.initFeature();
        api = WXAPIFactory.createWXAPI(this, Constant.WECHAT_APP_ID, true);
        api.registerApp(Constant.WECHAT_APP_ID);
        mTencent = Tencent.createInstance(APP_ID_QQ, this.getApplicationContext());
        WbSdk.install(this,new AuthInfo(this, Constant.WB_APP_KEY, Constant.WB_REDIRECT_URL, Constant.WB_SCOPE));
//        LISessionManager liSessionManager = LISessionManager.getInstance(this);
//        liSessionManager.init(this, Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE), new AuthListener() {
//            @Override
//            public void onAuthSuccess() {
//                Log.e("http_","onAuthSuccess----");
//            }
//
//            @Override
//            public void onAuthError(LIAuthError error) {
//                Log.e("http_","onAuthError----");
//            }
//        },false);
        Intent intent = getIntent();
        mTitle = intent.getStringExtra("title");
        mContent = intent.getStringExtra("content");
        mImgUrl = intent.getStringExtra("imgUrl");
        mTargetUrl = intent.getStringExtra("mTargetUrl");
        isPortrait = intent.getBooleanExtra("isPortrait", true);
        mTitle = "这是分享的标题";
        mContent = "这是分享的内容";
        mImgUrl = "http://img.hb.aicdn.com/304be7e36ac024383bba16f3f32e01f7408f644e7bf9a-d5k7ZW_fw658";
        mTargetUrl = "https://www.baidu.com/";
        Bitmap bitmap = ImageUtil.decodeSampledBitmapFromResource(mImgUrl, 768);
        bitmapDrawable = new BitmapDrawable(null, bitmap);

    }

    @Override
    public int getContentView() {
        return R.layout.activity_vertical_share;
    }

    @Override
    protected void initView() {
        shareHandler = new WbShareHandler(this);
        shareHandler.registerApp();
        iv_wechatfriend = findViewById(R.id.iv_wechatfriend);
        iv_wechatcircle = findViewById(R.id.iv_wechatcircle);
        iv_qqzone = findViewById(R.id.iv_qqzone);
        iv_sina = findViewById(R.id.iv_sina);
        iv_facebook = findViewById(R.id.iv_facebook);
        iv_twitter = findViewById(R.id.iv_twitter);
        iv_linkedin = findViewById(R.id.iv_linkedin);
        tv_cancel = findViewById(R.id.tv_cancel);
        iv_qr = findViewById(R.id.iv_qr);
        mView = findViewById(R.id.mView);
        mLoadingDialog = new LoadingDialog(ShareActivity.this);
    }

    @Override
    protected void setListener() {
        iv_wechatfriend.setOnClickListener(this);
        iv_wechatcircle.setOnClickListener(this);
        iv_qqzone.setOnClickListener(this);
        iv_sina.setOnClickListener(this);
        iv_facebook.setOnClickListener(this);
        iv_twitter.setOnClickListener(this);
        iv_linkedin.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        mView.setOnClickListener(this);
        iv_qr.setOnClickListener(this);
    }

    @Override
    protected void setData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_wechatfriend:
                wechatShare(0);
                break;
            case R.id.iv_wechatcircle:
                wechatShare(1);
                break;
            case R.id.iv_qqzone:
                qqzoneShare();
                break;
            case R.id.iv_sina:
                sinaShare();
                break;
            case R.id.iv_facebook:
                facebookShare();
                break;
            case R.id.iv_twitter:
                twitterShare();
                break;
            case R.id.iv_linkedin:
                linkedinShare();
                break;
            case R.id.iv_qr:
                qrShare();
                break;
            case R.id.tv_cancel:
                ShareActivity.this.finish();
                break;
            case R.id.mView:
                tv_cancel.performClick();
                break;
        }
    }

    private void wechatShare(int flag) {
//        WXTextObject wxTextObject = new WXTextObject();
//        wxTextObject.text = title;
        WXImageObject wxImageObject = new WXImageObject();
        wxImageObject.imagePath = mImgUrl;
        WXMediaMessage message = new WXMediaMessage();
        message.mediaObject = wxImageObject;
        message.title = flag == 0 ? mTitle : String.format("%s\n%s", mTitle, mContent);
        message.description = flag == 0 ? mContent : "";
        message.thumbData = BitmapUtils.bmpToByteArray(ImageUtil.decodeSampledBitmapFromResource(mImgUrl, 64), false);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = message;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

    private void qqzoneShare() {
        final Bundle params = new Bundle();
        Tencent mTencent = Tencent.createInstance(APP_ID_QQ, this.getApplicationContext());
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, mTitle);
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, mContent);
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, mTargetUrl);
        params.putString(QzoneShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, mImgUrl);
        params.putInt(QzoneShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        mTencent.shareToQQ(this, params, new BaseUiListener());
        closeSharePage();
    }

    private void sinaShare() {
        try {
            WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
            weiboMessage.textObject = getTextObj();
            weiboMessage.imageObject = getImageObj();
            weiboMessage.mediaObject = getWebpageObj();
            shareHandler.shareMessage(weiboMessage, false);
        } catch (Exception e) {
        }
    }

    private void facebookShare() {
        //这里分享一个链接，更多分享配置参考官方介绍：https://developers.facebook.com/docs/sharing/android
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent.Builder builder = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("https://developers.facebook.com"));
            builder.setContentTitle(mTitle);
            builder.setContentDescription(mContent);
            builder.setImageUrl(Uri.parse(mImgUrl));
            ShareLinkContent linkContent = builder.build();
            mShareDialog.show(linkContent);
        }
    }

    private void twitterShare() {
        //这里分享一个链接，更多分享配置参考官方介绍：https://dev.twitter.com/twitterkit/android/compose-tweets
        try {
            TweetComposer.Builder builder = new TweetComposer.Builder(ShareActivity.this)
                    .url(new URL("https://www.google.com/"));
            builder.text(mTitle);
            builder.image(Uri.parse(mImgUrl));
            builder.show();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void linkedinShare() {
        String url = "https://api.linkedin.com/v1/people/~/shares";
        String payload = "{" +
                "\"comment\":\"Check out developer.linkedin.com! " +
                "http://linkd.in/1FC2PyG\"," +
                "\"visibility\":{" +
                "    \"code\":\"anyone\"}" +
                "}";
        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.postRequest(this, url, payload, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse apiResponse) {
                // Success!
                Log.e("http_","onApiSuccess----");
            }

            @Override
            public void onApiError(LIApiError liApiError) {
                // Error making POST request!
                Log.e("http_","onApiError----");
            }
        });
    }

    private void qrShare(){

    }


    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        String textString = String.format("%s\n", mTitle);
        String contentString = String.format("%s\n", mContent);
        textObject.text = textString + mTargetUrl;
        return textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj() {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = mContent;
        mediaObject.description = mTargetUrl;
        byte[] bytes = BitmapUtils.bmpToByteArray(bitmapDrawable.getBitmap(), false);
        byte[] byteBitmap = BitmapUtils.resizeBitmap(bytes, 64, 64);
        mediaObject.setThumbImage(BitmapUtils.fromByteArray(byteBitmap));
        mediaObject.defaultText = mContent;
        return mediaObject;
    }


    @Override
    public void onWbShareSuccess() {
        closeSharePage();
        ToastUtil.showToastOnFinish("分享成功");
    }

    @Override
    public void onWbShareCancel() {
        closeSharePage();
        ToastUtil.showToastOnFinish("分享取消");
    }

    @Override
    public void onWbShareFail() {
        closeSharePage();
        ToastUtil.showToastOnFinish("分享失败");
    }


    /**
     * QQ回调
     */
    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            ToastUtil.showToastOnFinish("分享成功");
            closeSharePage();
        }

        @Override
        public void onError(UiError e) {
            if (e.errorDetail == null) {
                ToastUtil.showToastOnFinish("分享失败");
            } else {
                ToastUtil.showToastOnFinish("分享时出现错误");
            }
            closeSharePage();
        }

        @Override
        public void onCancel() {
            ToastUtil.showToastOnFinish("取消");
            closeSharePage();
        }
    }


    private void initFacebook() {
        mCallbackManager = CallbackManager.Factory.create();
        mShareDialog = new ShareDialog(this); // this part is optional
        mShareDialog.registerCallback(mCallbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) { //分享成功的回调，在这里做一些自己的逻辑处理
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }


    private void closeSharePage() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            try {
                mLoadingDialog.dismiss();
            } catch (Exception e) {

            }
        }
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        shareHandler.doResultIntent(intent,this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
    }

    public static void ShareLive(Context context, String title, String content, String imgUrl, String targetUrl, boolean isPortrait) {
        Intent intent = new Intent(context, ShareActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("imgUrl", imgUrl);
        intent.putExtra("mTargetUrl", targetUrl);
        intent.putExtra("isPortrait", isPortrait);
        context.startActivity(intent);
    }





}
