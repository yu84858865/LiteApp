package first.net.liteapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import first.net.liteapp.R;
import first.net.liteapp.utils.GlideUtils;
import first.net.liteapp.view.TitleView;

/**
 * Created by 10960 on 2018/2/24.
 * 课程详情和活动详情
 */

public class CourseDetailActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_title, tv_price, tv_author, tv_person, tv_time, tv_location, tv_description, tv_attend;
    private ImageView iv_photo, iv_interested, iv_comment, iv_share;
    private WebView webView;
    private TitleView cus_title;
    private boolean isFromCourse;

    @Override
    public int getContentView() {
        return R.layout.activity_course_detail;
    }

    @Override
    protected void initView() {
        tv_title = findViewById(R.id.tv_title);
        tv_price = findViewById(R.id.tv_price);
        tv_author = findViewById(R.id.tv_author);
        tv_person = findViewById(R.id.tv_person);
        tv_time = findViewById(R.id.tv_time);
        tv_location = findViewById(R.id.tv_location);
        tv_description = findViewById(R.id.tv_description);
        tv_attend = findViewById(R.id.tv_attend);
        iv_photo = findViewById(R.id.iv_photo);
        iv_interested = findViewById(R.id.iv_interested);
        iv_comment = findViewById(R.id.iv_comment);
        iv_share = findViewById(R.id.iv_share);
        webView = findViewById(R.id.webView);
        cus_title = findViewById(R.id.cus_title);
    }

    @Override
    protected void setListener() {
        iv_interested.setOnClickListener(this);
        iv_comment.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        tv_attend.setOnClickListener(this);
    }

    @Override
    protected void setData() {
        Intent intent = getIntent();
        if(intent != null){
            isFromCourse = intent.getBooleanExtra("isFromCourse",false);
        }
        if(isFromCourse){
            cus_title.setTitle(getResources().getString(R.string.course_detail));
            tv_description.setText(getResources().getString(R.string.course_description));
            tv_attend.setText(getResources().getString(R.string.course_attend));
        }else{
            cus_title.setTitle(getResources().getString(R.string.activity_detail));
            tv_description.setText(getResources().getString(R.string.activity_description));
            tv_attend.setText(getResources().getString(R.string.activity_attend));
        }

        String imageUrl = "http://img.hb.aicdn.com/304be7e36ac024383bba16f3f32e01f7408f644e7bf9a-d5k7ZW_fw658";
        GlideUtils.displayNormalImgByOther(this,imageUrl,iv_photo);

        tv_title.setText("探讨生命的真谛");
        tv_price.setText("HKD "+120);
        tv_author.setText("余教授");
        tv_person.setText("200人");
        tv_time.setText("2018/1/1至2018/2/1");
        tv_location.setText("香港香格里拉");

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        final String url = "http://www.baidu.com";
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if(url.contains("http") || url.contains("https")){
                    view.loadUrl(url);
                }
                return true;
            }
        });

    }



    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.iv_interested:
                break;
            case R.id.iv_comment:
                break;
            case R.id.iv_share:
                ShareActivity.ShareLive(this,"","","","",true);
                break;
            case R.id.tv_attend:
                ConfirmOrderActivity.startActivity(this,"");
                break;
        }
    }

    public static void startActivity(Context context, String address, boolean isFromCourse){
        Intent intent = new Intent(context, CourseDetailActivity.class);
        intent.putExtra("isFromCourse",isFromCourse);
        context.startActivity(intent);
    }

}
