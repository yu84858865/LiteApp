package first.net.liteapp.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import first.net.liteapp.R;
import first.net.liteapp.constant.Constant;
import first.net.liteapp.utils.BitmapUtils;
import first.net.liteapp.utils.ImageUtil;

/**
 * Created by 10960 on 2018/2/13.
 */

public class ShareActivity extends BaseActivity implements View.OnClickListener{

    private IWXAPI api;
    private ImageView iv_wechatfriend,iv_wechatcircle,iv_qqzone,iv_sina,iv_facebook,iv_twitter,iv_linkedin;
    private String mTitle,mImgUrl,mContent;
    private boolean isPortrait;

    @Override
    protected void initFeature() {
        super.initFeature();
        api = WXAPIFactory.createWXAPI(this, Constant.WECHAT_APP_ID,true);
        api.registerApp(Constant.WECHAT_APP_ID);
        Intent intent = getIntent();
        mTitle = intent.getStringExtra("title");
        mContent = intent.getStringExtra("content");
        mImgUrl = intent.getStringExtra("imgUrl");
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

    }

    private void sinaShare(String title, String content){

    }

    private void facebookShare(String title, String content){

    }

    private void twitterShare(String title, String content){

    }

    private void linkedinShare(String title, String content){

    }

    public static void ShareLive(Context context, String title, String content, String imgUrl, boolean isPortrait){
        Intent intent = new Intent(context,ShareActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        intent.putExtra("imgUrl",imgUrl);
        intent.putExtra("isPortrait",isPortrait);
        context.startActivity(intent);
    }



}
