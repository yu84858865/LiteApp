package first.net.liteapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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

import first.net.liteapp.R;
import first.net.liteapp.constant.Constant;
import first.net.liteapp.utils.BitmapUtils;
import first.net.liteapp.utils.ImageUtil;
import first.net.liteapp.utils.LoadingDialog;
import first.net.liteapp.utils.ToastUtil;

/**
 * Created by 10960 on 2018/2/13.
 */

public class ShareActivity extends BaseActivity implements View.OnClickListener{

    private IWXAPI api;
    private Tencent mTencent;
    private ImageView iv_wechatfriend,iv_wechatcircle,iv_qqzone,iv_sina,iv_facebook,iv_twitter,iv_linkedin;
    private String mTitle,mImgUrl,mContent,mTargetUrl;
    private boolean isPortrait;
    private LoadingDialog mLoadingDialog;
    /**
     * 第三方登录AppID
     */
    private String APP_ID_QQ = "100556142";

    @Override
    protected void initFeature() {
        super.initFeature();
        api = WXAPIFactory.createWXAPI(this, Constant.WECHAT_APP_ID,true);
        api.registerApp(Constant.WECHAT_APP_ID);
        mTencent = Tencent.createInstance(APP_ID_QQ,this.getApplicationContext());
        Intent intent = getIntent();
        mTitle = intent.getStringExtra("title");
        mContent = intent.getStringExtra("content");
        mImgUrl = intent.getStringExtra("imgUrl");
        mTargetUrl = intent.getStringExtra("mTargetUrl");
        isPortrait = intent.getBooleanExtra("isPortrait",true);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_vertical_share;
    }

    @Override
    protected void initView() {
        iv_wechatfriend = findViewById(R.id.iv_wechatfriend);
        iv_wechatcircle = findViewById(R.id.iv_wechatcircle);
        iv_qqzone = findViewById(R.id.iv_qqzone);
        iv_sina = findViewById(R.id.iv_sina);
        iv_facebook = findViewById(R.id.iv_facebook);
        iv_twitter = findViewById(R.id.iv_twitter);
        iv_linkedin = findViewById(R.id.iv_linkedin);
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
    }

    @Override
    protected void setData() {

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.iv_wechatfriend:
                wechatShare(0,mTitle,mContent);
                break;
            case R.id.iv_wechatcircle:
                wechatShare(1,mTitle,mContent);
                break;
            case R.id.iv_qqzone:
                qqzoneShare(mTitle,mContent);
                break;
            case R.id.iv_sina:
                sinaShare(mTitle,mContent);
                break;
            case R.id.iv_facebook:
                facebookShare(mTitle,mContent);
                break;
            case R.id.iv_twitter:
                twitterShare(mTitle,mContent);
                break;
            case R.id.iv_linkedin:
                linkedinShare(mTitle,mContent);
                break;
        }
    }

    private void wechatShare(int flag, String title, String content){
//        WXTextObject wxTextObject = new WXTextObject();
//        wxTextObject.text = title;
        WXImageObject wxImageObject = new WXImageObject();
        wxImageObject.imagePath = mImgUrl;
        WXMediaMessage message = new WXMediaMessage();
        message.mediaObject = wxImageObject;
        message.title = flag == 0 ? title : String.format("%s\n%s", title, content);
        message.description = flag == 0 ? content : "";
        message.thumbData = BitmapUtils.bmpToByteArray(ImageUtil.decodeSampledBitmapFromResource(mImgUrl, 64), false);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = message;
        req.scene = flag == 0? SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

    private void qqzoneShare(String title, String content){
        final Bundle params = new Bundle();
        Tencent mTencent = Tencent.createInstance(APP_ID_QQ, this.getApplicationContext());
//        if (isImg) {
//            params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE);
//            params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
//            params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, mShareContent);
//            params.putString(QzoneShare.SHARE_TO_QQ_APP_NAME, "烤圈");
//            if (imgUrl.contains("file://")) {//"file:///storage/emulated/0/CooQuan//oven_photo/2017-10-09/CooQuan_P1009115023-4137_1507521023360.jpg";
//                String imgUrls = imgUrl.replace("file://", "");
//                MyLog.e(TAG, "imgUrls = " + imgUrls);
//                params.putString(QzoneShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imgUrls);
//            } else {//"/storage/emulated/0/CooQuan//oven_photo/2017-10-09/CooQuan_P1009115023-4137_1507521023360.jpg";
//                params.putString(QzoneShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imgUrl);
//            }
//            params.putInt(QzoneShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
//            mTencent.shareToQQ(this, params, new BaseUiListener());
//        } else {
            params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
            params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
            params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, content);
            params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, mTargetUrl);
            params.putString(QzoneShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, mImgUrl);
            params.putInt(QzoneShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
            mTencent.shareToQQ(this, params, new BaseUiListener());
//        }
        closeSharePage();
    }

    private void sinaShare(String title, String content){

    }

    private void facebookShare(String title, String content){

    }

    private void twitterShare(String title, String content){

    }

    private void linkedinShare(String title, String content){

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

    private void closeSharePage() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            try {
                mLoadingDialog.dismiss();
            } catch (Exception e) {

            }
        }
        finish();
    }

    public static void ShareLive(Context context, String title, String content, String imgUrl, String targetUrl, boolean isPortrait){
        Intent intent = new Intent(context,ShareActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        intent.putExtra("imgUrl",imgUrl);
        intent.putExtra("mTargetUrl",targetUrl);
        intent.putExtra("isPortrait",isPortrait);
        context.startActivity(intent);
    }



}
