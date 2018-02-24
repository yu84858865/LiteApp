package first.net.liteapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import first.net.liteapp.R;
import first.net.liteapp.view.TitleView;

/**
 * Created by 10960 on 2018/2/24.
 */

public class IntroduceWebActivity extends BaseActivity {


    private TitleView tv_titleview;
    private WebView webView;

    @Override
    public int getContentView() {
        return R.layout.activity_introduce_web;
    }

    @Override
    protected void initView() {
        tv_titleview = findViewById(R.id.tv_titleview);
        webView = findViewById(R.id.webView);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void setData() {
        Intent intent = getIntent();
        if(intent != null){
            String title = intent.getStringExtra("title");
            tv_titleview.setTitle(title);
        }

        final String webUrl = "www.baidu.com";
        WebSettings settings = webView.getSettings();
        settings.setSupportZoom(true);
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(webUrl);
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
                view.loadUrl(webUrl);
                return true;
            }
        });

    }

    public static void startActivity(Context context, String title){
        Intent intent = new Intent(context,IntroduceWebActivity.class);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }

}
